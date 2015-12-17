package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantGetExtendedPublicKeyException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccountType;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.VaultSeedGenerator;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.CantCreateAssetVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.CantLoadExistingVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantAddHierarchyAccountException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantDeriveNewKeysException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.database.BitcoinCurrencyCryptoVaultDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantInitializeBitcoinCurrencyCryptoVaultDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantValidateCryptoNetworkIsActiveException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.InvalidSeedException;
import com.squareup.okhttp.internal.Network;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.wallet.DeterministicSeed;

import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 12/17/15.
 */
public class BitcoinCurrencyCryptoVaultManager {
    /**
     * BitcoinCurrencyCryptoVaultManager variables
     */
    UUID pluginId;
    VaultKeyHierarchyGenerator vaultKeyHierarchyGenerator;
    BitcoinCurrencyCryptoVaultDao dao;


    /**
     * File name information where the seed will be stored
     */
    private final String BITCOIN_VAULT_SEED_FILEPATH = "BitcoinVaultSeed";
    private final String BITCOIN_VAULT_SEED_FILENAME;


    /**
     * platform interfaces definition
     */
    BitcoinNetworkManager bitcoinNetworkManager;
    PluginFileSystem pluginFileSystem;
    PluginDatabaseSystem pluginDatabaseSystem;


    /**
     * Constructor
     * @param pluginId
     * @param pluginFileSystem
     */
    public BitcoinCurrencyCryptoVaultManager(UUID pluginId,
                                   PluginFileSystem pluginFileSystem,
                                   PluginDatabaseSystem pluginDatabaseSystem,
                                   String seedFileName,
                                   BitcoinNetworkManager bitcoinNetworkManager) throws InvalidSeedException {

        this.pluginId = pluginId;
        BITCOIN_VAULT_SEED_FILENAME = seedFileName;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.bitcoinNetworkManager = bitcoinNetworkManager;

        /**
         * I will let the VaultKeyHierarchyGenerator to start and generate the hierarchy in a new thread
         */
        vaultKeyHierarchyGenerator = new VaultKeyHierarchyGenerator(getBitcoinVaultSeed(), pluginDatabaseSystem, this.bitcoinNetworkManager, this.pluginId);
        new Thread(vaultKeyHierarchyGenerator).start();
    }

    /**
     * Creates a new Seed or loads and existing one for the user logged.
     * @return
     * @throws CantCreateAssetVaultSeed
     * @throws CantLoadExistingVaultSeed
     */
    private DeterministicSeed getBitcoinVaultSeed()  throws InvalidSeedException{
        try{
            VaultSeedGenerator vaultSeedGenerator = new VaultSeedGenerator(this.pluginFileSystem, this.pluginId, BITCOIN_VAULT_SEED_FILEPATH, BITCOIN_VAULT_SEED_FILENAME);
            if (!vaultSeedGenerator.seedExists()){
                vaultSeedGenerator.create();
                /**
                 * I realod it to make sure I'm using the seed I will start using from now on. Issue #3330
                 */
                vaultSeedGenerator.load();
            } else
                vaultSeedGenerator.load();

            DeterministicSeed seed = new DeterministicSeed(vaultSeedGenerator.getSeedBytes(), vaultSeedGenerator.getMnemonicCode(), vaultSeedGenerator.getCreationTimeSeconds());
            seed.check();
            return seed;
        } catch (CantLoadExistingVaultSeed cantLoadExistingVaultSeed) {
            throw new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, cantLoadExistingVaultSeed, "there was an error trying to load an existing seed.", null);
        } catch (CantCreateAssetVaultSeed cantCreateAssetVaultSeed) {
            throw new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, cantCreateAssetVaultSeed, "there was an error trying to create a new seed.", null);
        } catch (MnemonicException e) {
            throw new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, e, "the seed that was generated is not valid.", null);
        }
    }


    /**
     * Will get a new crypto address from the Bitcoin vault account.
     * @param blockchainNetworkType
     * @return
     * @throws GetNewCryptoAddressException
     */

    public CryptoAddress getNewBitcoinVaultCryptoAddress(BlockchainNetworkType blockchainNetworkType) throws GetNewCryptoAddressException {
        /**
         * I create the account manually instead of getting it from the database because this method always returns addresses
         * from the Bitcoin vault account with Id 0.
         */
        com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount vaultAccount = new com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount(0, "Bitcoin Vault account", HierarchyAccountType.MASTER_ACCOUNT);
        return vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getBitcoinAddress(blockchainNetworkType, vaultAccount);
    }

    public long getAvailableBalanceForTransaction(String genesisTransaction) {
        return 0;
    }


    /**
     * Creates a bitcoinj Wallet from the already derived keys of the specified account.
     * @param vaultAccount
     * @param networkParameters
     * @return
     */
    private Wallet getWalletForAccount(com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount vaultAccount, NetworkParameters networkParameters) {
        List<ECKey> derivedKeys = vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getDerivedKeys(vaultAccount);
        Wallet wallet = Wallet.fromKeys(networkParameters, derivedKeys);
        return wallet;
    }

    /**
     * Transform a CryptoAddress into a BitcoinJ Address
     * * @param networkParameters the network parameters where we are using theis address.
     * @param cryptoAddress the Crypto Address
     * @return a bitcoinJ address.
     */
    private Address getBitcoinAddress(NetworkParameters networkParameters, CryptoAddress cryptoAddress) throws AddressFormatException {
        Address address = new Address(networkParameters, cryptoAddress.getAddress());
        return address;
    }

    /**
     * Gets the next available key from the specified account.
     * @return
     */
    private ECKey getNextAvailableECKey(com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount hierarchyAccount) throws CantExecuteDatabaseOperationException {
        ECKey ecKey = vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getNextAvailableKey(hierarchyAccount);
        return ecKey;
    }


    /**
     * Will make sure that we have a listening network running for this address that we are trying to send bitcoins to.
     * @param cryptoAddress
     */
    private BlockchainNetworkType validateNetorkIsActiveForCryptoAddress(CryptoAddress cryptoAddress) throws CantValidateCryptoNetworkIsActiveException {
        /**
         * I need to make sure that we have generated a key on the network type to which the address belongs
         * to, so we can be sure that the Crypto Network is listening on this network.
         */
        try {
            List<BlockchainNetworkType> networkTypes = getDao().getActiveNetworkTypes();
            BlockchainNetworkType addressNetworkType = BitcoinNetworkSelector.getBlockchainNetworkType(getNetworkParametersFromAddress(cryptoAddress.getAddress()));

            /**
             * If the address Network Type is not registered, then I won't go on because I know I'm not listening to it.
             */
            if (!networkTypes.contains(addressNetworkType)){
                StringBuilder output = new StringBuilder("The specified address belongs to a Bitcoin network we are not listening to.");
                output.append(System.lineSeparator());
                output.append("BlockchainNetworkType: " + addressNetworkType.toString());
                output.append(System.lineSeparator());
                output.append("Active Networks are: " + networkTypes.toString());
                throw new CantValidateCryptoNetworkIsActiveException(CantValidateCryptoNetworkIsActiveException.DEFAULT_MESSAGE, null, output.toString(), null);
            }

            return addressNetworkType;
        } catch (CantExecuteDatabaseOperationException e) {
            /**
             * If I can't validate this. I will continue because I may be listening to this network already.
             */
            e.printStackTrace();
            return BlockchainNetworkType.DEFAULT;
        } catch (AddressFormatException e) {
            /**
             * If the passed address doesn't have the correct format, I can't go on.
             */
            throw new CantValidateCryptoNetworkIsActiveException(CantValidateCryptoNetworkIsActiveException.DEFAULT_MESSAGE, e, "The specified address is not in the right format: " + cryptoAddress.getAddress(), null);
        }
    }

    /**
     * Calculates the network parameters from the specified address.
     * @param addressTo
     * @return
     * @throws AddressFormatException in case the address is not in the correct format.
     */
    private NetworkParameters getNetworkParametersFromAddress(String addressTo) throws AddressFormatException {
        NetworkParameters networkParameters = Address.getParametersFromAddress(addressTo);

        /**
         * if the network parameters calculated is different that the Default network I will double check
         */
        if (BitcoinNetworkSelector.getBlockchainNetworkType(networkParameters) != BlockchainNetworkType.DEFAULT){
            return BitcoinNetworkSelector.getNetworkParameter(BlockchainNetworkType.DEFAULT);
        } else
            return networkParameters;
    }

    /**
     * instantiates and creates the dao object to access the database
     * @return
     */
    private BitcoinCurrencyCryptoVaultDao getDao(){
        if (dao == null){
            try {
                dao = new BitcoinCurrencyCryptoVaultDao(this.pluginDatabaseSystem, this.pluginId);
            } catch (CantInitializeBitcoinCurrencyCryptoVaultDatabaseException e) {
                e.printStackTrace();
            }
        }

        return dao;
    }

    /**
     * Gets the amount of unused keys that are available from the passed account.
     * @param  account the hierarchy account to get the keys from
     * @return
     */
    public int getAvailableKeyCount(com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount account){
        //todo implement: I will use this to validate when new assets are created in the factory the amount of available keys.
        // if the amount of keys is less than the amount of assets to create, then I will invoke deriveKeys
        return 0;
    }


    /**
     * Derives the specified amount of keys in the selected account. Only some plugins can execute this method.
     * @param pluginId the pluginId invoking this call. Might not have permissions to create new keys.
     * @param account the account to derive keys from.
     * @param keysToDerive thre amount of keys to derive.
     * @throws CantDeriveNewKeysException
     */
    public void deriveKeys(UUID pluginId, com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount account, int keysToDerive) throws CantDeriveNewKeysException{
        //todo implement when creating assets, If I create more assets than available keys, then first I need to generate new keys.
    }

    /**
     * * Creates a new hierarchy Account in the vault.
     * This will create the sets of keys and start monitoring the default network with these keys.
     * @param description
     * @param hierarchyAccountType
     * @return
     * @throws CantAddHierarchyAccountException
     */
    public HierarchyAccount addHierarchyAccount(String description, HierarchyAccountType hierarchyAccountType) throws CantAddHierarchyAccountException {
        /**
         * I will insert the record in the database. First I will get the next Id available from the database
         */
        int hierarchyAccountID;
        try {
            hierarchyAccountID = getDao().getNextAvailableHierarchyAccountId();
        } catch (CantExecuteDatabaseOperationException e) {
            throw new CantAddHierarchyAccountException(CantAddHierarchyAccountException.DEFAULT_MESSAGE, e, "Can't get next available Id from the database.", "database issue");
        }

        /**
         * I create the HierarchyAccount and add it to the database.
         */
        HierarchyAccount hierarchyAccount = new HierarchyAccount(hierarchyAccountID, description, hierarchyAccountType);

        try {
            this.getDao().addNewHierarchyAccount(hierarchyAccount);
        } catch (CantExecuteDatabaseOperationException e) {
            throw new CantAddHierarchyAccountException(CantAddHierarchyAccountException.DEFAULT_MESSAGE, e, "Can't insert the next Hierarchy in the database.", "database issue");
        }

        /**
         * Restart the Hierarchy Maintainer so that it loads the new added Hierarchy Account and start the monitoring.
         */
        this.vaultKeyHierarchyGenerator.vaultKeyHierarchyMaintainer.stop();
        try {
            this.vaultKeyHierarchyGenerator.vaultKeyHierarchyMaintainer.start();
        } catch (CantStartAgentException e) {
            e.printStackTrace();
        }

        return hierarchyAccount;
    }

    /**
     * Gets the Extended Public Key from the specified account. Can't be from a master account.
     * @param hierarchyAccount a Redeem Point account.
     * @return the DeterministicKey that will be used by the redeem Points.
     * @throws CantGetExtendedPublicKeyException
     */
    public DeterministicKey getExtendedPublicKey(HierarchyAccount hierarchyAccount) throws CantGetExtendedPublicKeyException {
        /**
         * get the master account key
         */
        DeterministicKey accountMasterKey = this.vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getAddressKeyFromAccount(hierarchyAccount);

        // Serialize the pub key.
        byte[] pubKeyBytes = accountMasterKey.getPubKey();
        byte[] chainCode = accountMasterKey.getChainCode();


        // Deserialize the pub key.
        final DeterministicKey watchPubKeyAccountZero = HDKeyDerivation.createMasterPubKeyFromBytes(pubKeyBytes, chainCode);

        /**
         * return the extended public Key
         */
        return watchPubKeyAccountZero;
    }

    /**
     * Determines if the passsed CryptoAddress is valid.
     * @param addressTo the address to validate
     * @return true if valid, false if it is not.
     */
    public boolean isValidAddress(CryptoAddress addressTo) {
        /**
         * I extract the network Parameter from the address
         */
        NetworkParameters networkParameters;
        try {
            networkParameters = Address.getParametersFromAddress(addressTo.getAddress());
        } catch (AddressFormatException e) {
            /**
             * If there is an error, I will use the default parameters.
             */
            networkParameters = BitcoinNetworkSelector.getNetworkParameter(BlockchainNetworkType.DEFAULT);
        }

        /**
         * If the address is correct, then no exception raised.
         */
        try {
            Address address = new Address(networkParameters, addressTo.getAddress());
            return true;
        } catch (AddressFormatException e) {
            return false;
        }
    }

    /**
     * gets a fresh un used crypto Address from the vault
     */
    public CryptoAddress getAddress() {
        return null;
    }

    /**
     * Send bitcoins to the specified address. The Address must be a valid address in the network being used
     * and we must have enought funds to send this money. It allows including an Op_return output value.
     * @param walletPublicKey
     * @param FermatTrId
     * @param addressTo
     * @param satoshis
     * @param op_Return
     * @return
     * @throws InsufficientCryptoFundsException
     * @throws InvalidSendToAddressException
     * @throws CouldNotSendMoneyException
     * @throws CryptoTransactionAlreadySentException
     */
    public String sendBitcoins (String walletPublicKey, UUID FermatTrId,  CryptoAddress addressTo, long satoshis, String op_Return)
            throws InsufficientCryptoFundsException,
            InvalidSendToAddressException,
            CouldNotSendMoneyException,
            CryptoTransactionAlreadySentException {
        return null;
    }

}
