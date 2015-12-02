package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantSendAssetBitcoinsToUserException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.VaultSeedGenerator;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.exceptions.CantCreateAssetVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.exceptions.CantLoadExistingVaultSeed;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.database.AssetsOverBitcoinCryptoVaultDao;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.InvalidSeedException;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.wallet.CoinSelection;
import org.bitcoinj.wallet.CoinSelector;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.WalletTransaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptovault.assetsoverbitcoin.developer.bitdubai.version_1.structure.AssetCryptoVaultManager</code>
 * In in charge of creating the master key from the generated seed and starts the agents that will create the
 * key hierarchy and the Hierarchy maintainer agent.<p/>
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 06/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetCryptoVaultManager  {
    /**
     * AssetVaultManager variables
     */
    UUID pluginId;
    VaultKeyHierarchyGenerator vaultKeyHierarchyGenerator;
    AssetsOverBitcoinCryptoVaultDao dao;


    /**
     * File name information where the seed will be stored
     */
    private final String ASSET_VAULT_SEED_FILEPATH = "AssetVaultSeed";
    private final String ASSET_VAULT_SEED_FILENAME;


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
    public AssetCryptoVaultManager(UUID pluginId,
                                   PluginFileSystem pluginFileSystem,
                                   PluginDatabaseSystem pluginDatabaseSystem,
                                   String seedFileName,
                                   BitcoinNetworkManager bitcoinNetworkManager) throws InvalidSeedException {

        this.pluginId = pluginId;
        ASSET_VAULT_SEED_FILENAME = seedFileName;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.bitcoinNetworkManager = bitcoinNetworkManager;

        /**
         * I will let the VaultKeyHierarchyGenerator to start and generate the hierarchy in a new thread
         */
        vaultKeyHierarchyGenerator = new VaultKeyHierarchyGenerator(getAssetVaultSeed(), pluginDatabaseSystem, this.bitcoinNetworkManager, this.pluginId);
        new Thread(vaultKeyHierarchyGenerator).start();
    }

    /**
     * Creates a new Seed or loads and existing one for the user logged.
     * @return
     * @throws CantCreateAssetVaultSeed
     * @throws CantLoadExistingVaultSeed
     */
    private DeterministicSeed getAssetVaultSeed()  throws InvalidSeedException{
        try{
            VaultSeedGenerator vaultSeedGenerator = new VaultSeedGenerator(this.pluginFileSystem, this.pluginId, ASSET_VAULT_SEED_FILEPATH, ASSET_VAULT_SEED_FILENAME);
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
     * Will get a new crypto address from the asset vault account.
     * @param blockchainNetworkType
     * @return
     * @throws GetNewCryptoAddressException
     */

    public CryptoAddress getNewAssetVaultCryptoAddress(BlockchainNetworkType blockchainNetworkType) throws GetNewCryptoAddressException {
        /**
         * I create the account manually instead of getting it from the database because this method always returns addresses
         * from the asset vault account with Id 0.
         */
        HierarchyAccount vaultAccount = new HierarchyAccount(0, "Asset Vault");
        return vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getBitcoinAddress(blockchainNetworkType, vaultAccount);
    }

    public long getAvailableBalanceForTransaction(String genesisTransaction) {
        return 0;
    }

    /**
     * * Sends bitcoins to the specified address. It will create a new wallet object from the Keys generated from the
     * VaultKeyHierarchyGenerator and set an UTXO provider from the CryptoNetwork. Using my UTXO, I will create a new
     * transaction and broadcast it on the corresponding network.
     * @param genesisTransactionId
     * @param addressTo
     * @param amount
     * @return the Transaction hash
     * @throws CantSendAssetBitcoinsToUserException
     */
    public String sendAssetBitcoins(String genesisTransactionId, CryptoAddress addressTo, long amount) throws CantSendAssetBitcoinsToUserException{
        /**
         * I get the network for this address.
         */
        BlockchainNetworkType networkType = validateNetorkIsActiveForCryptoAddress(addressTo);
        final NetworkParameters networkParameters = BitcoinNetworkSelector.getNetworkParameter(networkType);

        /**
         * I will get the genesis transaction  I will use to form the input from the CryptoNetwork
         */
        final Transaction genesisTransaction = bitcoinNetworkManager.getBitcoinTransaction(networkType, genesisTransactionId);
        if (genesisTransaction  == null){
            StringBuilder output = new StringBuilder("The specified transaction hash ");
            output.append(genesisTransactionId);
            output.append(System.lineSeparator());
            output.append("doesn't exists in the CryptoNetwork.");
            throw new CantSendAssetBitcoinsToUserException(CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, null, output.toString(), null);
        }


        /**
         * I get the bitcoin address
         */
        Address address = null;
        try {
            address = getBitcoinAddress(networkParameters,addressTo);
        } catch (AddressFormatException e) {
            throw new CantSendAssetBitcoinsToUserException(CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, e, "The specified address " + addressTo.getAddress() + " is not valid.", null);
        }


        /**
         * Create the bitcoinj wallet from the keys of this account
         */
        HierarchyAccount vaultAccount = new HierarchyAccount(0, "Asset Vault");
        final Wallet wallet = getWalletForAccount(vaultAccount, networkParameters);

        /**
         * Adds the Genesis Transaction as a UTXO
         */
        WalletTransaction walletTransaction = new WalletTransaction(WalletTransaction.Pool.UNSPENT, genesisTransaction);
        wallet.addWalletTransaction(walletTransaction);

        /**
         * Calculates the amount to be sent by removing the fee from the passed value.
         */
        Coin fee = Coin.valueOf(10000);
        final Coin coinToSend = Coin.valueOf(amount).subtract(fee);

        /**
         * creates the send request and broadcast it on the network.
         */
        wallet.allowSpendingUnconfirmedTransactions();
        wallet.setAcceptRiskyTransactions(true);

        Wallet.SendRequest sendRequest = Wallet.SendRequest.to(address, coinToSend);
        sendRequest.fee = fee;
        sendRequest.feePerKb = Coin.ZERO;

        try {
            wallet.completeTx(sendRequest);
        } catch (InsufficientMoneyException e) {

            StringBuilder output = new StringBuilder("Not enought money to send bitcoins.");
            output.append(System.lineSeparator());
            output.append("Current balance available for this transaction: " + wallet.getBalance().getValue());
            output.append(System.lineSeparator());
            output.append("Current value to send: " + coinToSend.getValue() + " (+fee: " + fee.getValue() + ")");
            throw new CantSendAssetBitcoinsToUserException(CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, e, output.toString(), null);
        }

        try {
            bitcoinNetworkManager.broadcastTransaction(networkType, sendRequest.tx);
        } catch (CantBroadcastTransactionException e) {
            e.printStackTrace();
        }

        return sendRequest.tx.getHashAsString();
    }

    /**
     * Creates a bitcoinj Wallet from the already derived keys of the specified account.
     * @param vaultAccount
     * @param networkParameters
     * @return
     */
    private Wallet getWalletForAccount(HierarchyAccount vaultAccount, NetworkParameters networkParameters) {
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
    private ECKey getNextAvailableECKey(HierarchyAccount hierarchyAccount) throws CantExecuteDatabaseOperationException {
        ECKey ecKey = vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getNextAvailableKey(hierarchyAccount);
        return ecKey;
    }


    /**
     * Will make sure that we have a listening network running for this address that we are trying to send bitcoins to.
     * @param cryptoAddress
     * @throws CantSendAssetBitcoinsToUserException
     */
    private BlockchainNetworkType validateNetorkIsActiveForCryptoAddress(CryptoAddress cryptoAddress) throws CantSendAssetBitcoinsToUserException {
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
                throw new CantSendAssetBitcoinsToUserException(CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, null, output.toString(), null);
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
            throw new CantSendAssetBitcoinsToUserException(CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, e, "The specified address is not in the right format: " + cryptoAddress.getAddress(), null);
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
    private AssetsOverBitcoinCryptoVaultDao getDao(){
        if (dao == null){
            try {
                dao = new AssetsOverBitcoinCryptoVaultDao(this.pluginDatabaseSystem, this.pluginId);
            } catch (CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException e) {
                e.printStackTrace();
            }
        }

        return dao;
    }
}
