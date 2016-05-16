package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantCreateBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantGetActiveRedeemPointAddressesException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantGetActiveRedeemPointsException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantGetExtendedPublicKeyException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantSendAssetBitcoinsToUserException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.CryptoVault;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccountType;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.InvalidSeedException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantAddHierarchyAccountException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantCreateDraftTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantDeriveNewKeysException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantSignTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.ExtendedPublicKey;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.database.AssetsOverBitcoinCryptoVaultDao;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantValidateActiveNetworkException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutPoint;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.wallet.WalletTransaction;

import java.util.ArrayList;
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
public class AssetCryptoVaultManager  extends CryptoVault{
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
    ErrorManager errorManager;


    /**
     * Constructor
     * @param pluginId
     * @param pluginFileSystem
     */
    public AssetCryptoVaultManager(UUID pluginId,
                                   PluginFileSystem pluginFileSystem,
                                   PluginDatabaseSystem pluginDatabaseSystem,
                                   String seedFileName,
                                   BitcoinNetworkManager bitcoinNetworkManager,
                                   ErrorManager errorManager) throws InvalidSeedException {

        super (pluginFileSystem, pluginId, bitcoinNetworkManager, "AssetVaultSeed", seedFileName);

        this.pluginId = pluginId;
        ASSET_VAULT_SEED_FILENAME = seedFileName;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.errorManager = errorManager;

        /**
         * I will let the VaultKeyHierarchyGenerator to start and generate the hierarchy in a new thread
         */
        vaultKeyHierarchyGenerator = new VaultKeyHierarchyGenerator(this.getVaultSeed(), pluginDatabaseSystem, this.bitcoinNetworkManager, this.pluginId, errorManager);
        new Thread(vaultKeyHierarchyGenerator).start();
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
        com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount vaultAccount = new com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount(0, "Asset Vault account", HierarchyAccountType.MASTER_ACCOUNT);
        return vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getBitcoinAddress(blockchainNetworkType, vaultAccount);
    }

    public long getAvailableBalanceForTransaction(String genesisTransaction) {
        return 0;
    }

    /**
     * * Sends bitcoins to the specified address. It will create a new wallet object from the Keys generated from the
     * VaultKeyHierarchyGenerator and set an UTXO provider from the CryptoNetwork. Using my UTXO, I will create a new
     * transaction and broadcast it on the corresponding network.
     * @param inputTransaction
     * @param genesisBlockHash
     * @param addressTo
     * @return the Transaction hash
     * @throws CantSendAssetBitcoinsToUserException
     */
    public String sendAssetBitcoins(String inputTransaction, String genesisBlockHash, CryptoAddress addressTo, BlockchainNetworkType blockchainNetworkType) throws CantSendAssetBitcoinsToUserException{

        /**
         * I validate the network is active
         */
        try {
           validateNetorkIsActive(blockchainNetworkType);
        } catch (CantValidateActiveNetworkException e) {
            throw new CantSendAssetBitcoinsToUserException (CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, e, "Network is not active for this address.", "wrong address");
        }

        final NetworkParameters networkParameters = BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType);

        /**
         * I will get the input transaction  I will use to form the input from the CryptoNetwork
         */
        Transaction genesisTransaction = bitcoinNetworkManager.getBitcoinTransaction(blockchainNetworkType, inputTransaction);

        /**
         * If I couldn't get it I can't go on.
         */
        if (genesisTransaction  == null){
            StringBuilder output = new StringBuilder("The specified transaction hash ");
            output.append(inputTransaction);
            output.append(System.lineSeparator());
            output.append("doesn't exists in the CryptoNetwork.");
            CantSendAssetBitcoinsToUserException exception = new CantSendAssetBitcoinsToUserException(CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, null, output.toString(), null);
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }

        /**
         * I get the bitcoin address
         */
        Address address = null;
        try {
            address = getBitcoinAddress(networkParameters,addressTo);
        } catch (AddressFormatException e) {
            CantSendAssetBitcoinsToUserException exception = new CantSendAssetBitcoinsToUserException(CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, e, "The specified address " + addressTo.getAddress() + " is not valid.", null);
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }

        /**
         * Create the bitcoinj wallet from the keys of all accounts
         */
        final Wallet wallet;
        Context walletContext = new Context(networkParameters);
        try {
            wallet = Wallet.fromSeed(walletContext.getParams(), getVaultSeed());
        } catch (InvalidSeedException e) {
            CantSendAssetBitcoinsToUserException exception = new CantSendAssetBitcoinsToUserException(CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, e, "Unable to create wallet from seed.", "seed issue");
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }

        try {
            wallet.importKeys(getKeysForAllAccounts(networkParameters));
        } catch (CantExecuteDatabaseOperationException e) {
            CantSendAssetBitcoinsToUserException exception = new CantSendAssetBitcoinsToUserException(CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, e, "Error getting the stored accounts to get the keys", "database issue");
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }

        /**
         * Adds the Genesis Transaction as a UTXO
         */
        WalletTransaction walletTransaction = new WalletTransaction(WalletTransaction.Pool.UNSPENT, genesisTransaction);
        wallet.addWalletTransaction(walletTransaction);

        /**
         * Calculates the amount to be sent by removing the fee from the available balance.
         * I'm ignoring the GenesisAmount passed because this might not be the right value.
         */
        Coin fee = Coin.valueOf(BitcoinNetworkConfiguration.FIXED_FEE_VALUE);
        final Coin coinToSend = wallet.getBalance().subtract(fee);

        /**
         * validates we are not sending less than permited.
         */
        if (isDustySend(coinToSend.getValue()))
            throw new CantSendAssetBitcoinsToUserException(CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, null, "Dusty send request: " + coinToSend.getValue(), "send more bitcoins!");

        /**
         * if the value to send is negative or zero, I will inform of the error
         */
        if (coinToSend.isNegative() || coinToSend.isZero()){
            StringBuilder output = new StringBuilder("The resulting value to be send is insufficient.");
            output.append(System.lineSeparator());
            output.append("We are trying to send " + coinToSend.getValue() + " satoshis, which is ValueToSend - fee (" + wallet.getBalance() + " - " + fee.getValue() + ")");

            CantSendAssetBitcoinsToUserException exception = new CantSendAssetBitcoinsToUserException(CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, null, output.toString(), null);
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }

        /**
         * creates the send request and broadcast it on the network.
         */
        wallet.allowSpendingUnconfirmedTransactions();
        wallet.setAcceptRiskyTransactions(true);

        Wallet.SendRequest sendRequest = Wallet.SendRequest.to(address, coinToSend);
        sendRequest.fee = fee;
        sendRequest.feePerKb = Coin.ZERO;


        /**
         * I'm ready so will complete the transaction.
         */
        try {
            wallet.completeTx(sendRequest);
        } catch (InsufficientMoneyException e) {
            StringBuilder output = new StringBuilder("Not enought money to send bitcoins.");
            output.append(System.lineSeparator());
            output.append("Current balance available for this transaction: " + wallet.getBalance().getValue());
            output.append(System.lineSeparator());
            output.append("Current value to send: " + coinToSend.getValue() + " (+fee: " + fee.getValue() + ")");
            CantSendAssetBitcoinsToUserException exception = new CantSendAssetBitcoinsToUserException(CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, e, output.toString(), null);
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }

        try {
            /**
             * Once I formed the transaction, I will store it in the CryptoNetwork so that is ready for broadcasting.
             */
            bitcoinNetworkManager.storeBitcoinTransaction(blockchainNetworkType, sendRequest.tx, UUID.randomUUID(), true);
            bitcoinNetworkManager.broadcastTransaction(sendRequest.tx.getHashAsString());
        } catch (CantStoreBitcoinTransactionException e) {
            CantSendAssetBitcoinsToUserException exception = new CantSendAssetBitcoinsToUserException(CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, e, "There was an error storing the created transaction in the CryptoNetwork", "Crypto Network issue");
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        } catch (CantBroadcastTransactionException e) {
            CantSendAssetBitcoinsToUserException exception = new CantSendAssetBitcoinsToUserException(CantSendAssetBitcoinsToUserException.DEFAULT_MESSAGE, e, "There was an error broadcasting in the CryptoNetwork", "Crypto Network issue");
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }

        return sendRequest.tx.getHashAsString();
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
     * Will make sure that we have a listening network running for the passed blockchain networktype
     * @param blockchainNetworkType
     * @throws CantSendAssetBitcoinsToUserException
     */
    private void validateNetorkIsActive(BlockchainNetworkType blockchainNetworkType) throws CantValidateActiveNetworkException {
        /**
         * I need to make sure that we have generated a key on the network type to which the address belongs
         * to, so we can be sure that the Crypto Network is listening on this network.
         */
        try {
            List<BlockchainNetworkType> networkTypes = getDao().getActiveNetworkTypes();

            /**
             * If the address Network Type is not registered, then I won't go on because I know I'm not listening to it.
             */
            if (!networkTypes.contains(blockchainNetworkType)){
                StringBuilder output = new StringBuilder("The specified networkType " + blockchainNetworkType.getCode() + " is not active");
                output.append(System.lineSeparator());
                output.append("Active Networks are: " + networkTypes.toString());
                throw new CantValidateActiveNetworkException(CantValidateActiveNetworkException.DEFAULT_MESSAGE, null, output.toString(), null);
            }

        } catch (CantExecuteDatabaseOperationException e) {
            /**
             * If I can't validate this. I will continue because I may be listening to this network already.
             */
            e.printStackTrace();
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
        if (BitcoinNetworkSelector.getBlockchainNetworkType(networkParameters) != BlockchainNetworkType.getDefaultBlockchainNetworkType()){
            try {
                // If only one network is enabled, then I will return the default
                if (getDao().getActiveNetworkTypes().size() == 1)
                    return BitcoinNetworkSelector.getNetworkParameter(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                else {
                    // If I have TestNet and RegTest registered, I may return any of them since they share the same prefix.
                    return networkParameters;
                }
            } catch (CantExecuteDatabaseOperationException e) {
                return BitcoinNetworkSelector.getNetworkParameter(BlockchainNetworkType.getDefaultBlockchainNetworkType());
            }
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

    /**
     * Gets the amount of unused keys that are available from the passed account.
     * @param  account the hierarchy account to get the keys from
     * @return
     */
    public int getAvailableKeyCount(com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount account){
        try {
            int currentGeneratedCount = getDao().getCurrentGeneratedKeys(account.getId());
            int currentUsedCount = getDao().getCurrentUsedKeys(account.getId());
            return currentGeneratedCount - currentUsedCount;
        } catch (CantExecuteDatabaseOperationException e) {
            return 0;
        }


    }


    /**
     * Derives the specified amount of keys in the selected account. Only some plugins can execute this method.
     * @param plugin the pluginId invoking this call. Might not have permissions to create new keys.
     * @param keysToDerive thre amount of keys to derive.
     * @throws CantDeriveNewKeysException
     */
    public void deriveKeys(Plugins plugin, int keysToDerive) throws CantDeriveNewKeysException{
        if (plugin == Plugins.ASSET_ISSUING){
            
        }
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
            CantAddHierarchyAccountException exception = new CantAddHierarchyAccountException(CantAddHierarchyAccountException.DEFAULT_MESSAGE, e, "Can't insert the next Hierarchy in the database.", "database issue");
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
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
     * @param redeemPointPublicKey a Redeem Point publicKey
     * @return the DeterministicKey that will be used by the redeem Points.
     * @throws CantGetExtendedPublicKeyException
     */
    public ExtendedPublicKey getRedeemPointExtendedPublicKey(String redeemPointPublicKey) throws CantGetExtendedPublicKeyException {
        if (redeemPointPublicKey == null)
            throw new CantGetExtendedPublicKeyException(CantGetExtendedPublicKeyException.DEFAULT_MESSAGE, null, "RedeemPoint Public Key can't be null.", null);


        /**
         * if I don't have an account with this publicKey, then I will create it.
         */
        HierarchyAccount redeemPointAccount = null;
        if (!isExistingRedeemPoint(redeemPointPublicKey)){
            /**
             * I will create the new account
             */

            try {
                redeemPointAccount = createNewRedeemPointAccount(redeemPointPublicKey);
                this.vaultKeyHierarchyGenerator.getVaultKeyHierarchy().addVaultAccount(redeemPointAccount);
            } catch (CantExecuteDatabaseOperationException e) {
                CantGetExtendedPublicKeyException exception = new  CantGetExtendedPublicKeyException(CantGetExtendedPublicKeyException.DEFAULT_MESSAGE, e, "There was an error creating and persisting the new account in database.", "database issue");
                errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw exception;
            }
        } else{
            /**
             * will load the existing account
             */
            try {
                redeemPointAccount = getDao().getHierarchyAccount(redeemPointPublicKey);
            } catch (CantExecuteDatabaseOperationException e) {
                CantGetExtendedPublicKeyException exception = new CantGetExtendedPublicKeyException(CantGetExtendedPublicKeyException.DEFAULT_MESSAGE, e, "Error getting existing Hierarchy Account", "database issue");
                errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw exception;
            }
        }


        /**
         * get the master account key for the specified account.
         */
        DeterministicKey accountMasterKey = this.vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getAddressKeyFromAccount(redeemPointAccount);

        // Serialize the pub key.
        byte[] pubKeyBytes = accountMasterKey.getPubKey();
        byte[] chainCode = accountMasterKey.getChainCode();

        /**
         * return the extended public Key
         */
        ExtendedPublicKey extendedPublicKey = new ExtendedPublicKey(redeemPointPublicKey, pubKeyBytes, chainCode);
        return extendedPublicKey;
    }

    /**
     * creates a new redeem point account by adding it to the database
     * @param redeemPointPublicKey
     * @return
     */
    private HierarchyAccount createNewRedeemPointAccount(String redeemPointPublicKey) throws CantExecuteDatabaseOperationException {

        /**
         * gets the next available ID that is free to be used.
         */
        int accountId = getNextAvailableAccountId();

        /**
         * creates the account and stores it.
         */
        HierarchyAccount hierarchyAccount = new HierarchyAccount(accountId, redeemPointPublicKey, HierarchyAccountType.REDEEMPOINT_ACCOUNT);
        getDao().addNewHierarchyAccount(hierarchyAccount);
        return hierarchyAccount;
    }

    /**
     * Finds out what is the next available ID that can be used for creating and hierarcht account
     * @return
     */
    private int getNextAvailableAccountId() {
        try {
            return getDao().getNextAvailableHierarchyAccountId();
        } catch (CantExecuteDatabaseOperationException e) {
            return 0;
        }
    }

    /**
     * Searches the databases for this public key
     * @param redeemPointPublicKey
     * @return
     */
    private boolean isExistingRedeemPoint(String redeemPointPublicKey) {
        try {
            return getDao().isExistingRedeemPoint(redeemPointPublicKey);
        } catch (CantExecuteDatabaseOperationException e) {
            return false;
        }
    }

    /**
     * If the redeem point keys are initialized, will return all the generated addresses
     * @param redeemPointPublicKey
     * @return
     * @throws CantGetActiveRedeemPointAddressesException
     */
    public List<CryptoAddress> getActiveRedeemPointAddresses(String redeemPointPublicKey) throws CantGetActiveRedeemPointAddressesException {
        /**
         * will get the hierarchy account for this public key
         */
        HierarchyAccount hierarchyAccount = null;
        try {
            hierarchyAccount = getDao().getHierarchyAccount(redeemPointPublicKey);

            if (hierarchyAccount == null)
                throw new CantGetActiveRedeemPointAddressesException(CantGetActiveRedeemPointAddressesException.DEFAULT_MESSAGE, null, "the specified public key does not exists: " + redeemPointPublicKey, null);

            if (hierarchyAccount.getHierarchyAccountType() != HierarchyAccountType.REDEEMPOINT_ACCOUNT)
                throw new CantGetActiveRedeemPointAddressesException(CantGetActiveRedeemPointAddressesException.DEFAULT_MESSAGE, null, "the specified public key " + redeemPointPublicKey + " is not from a Redeem Point account", null);

        } catch (CantExecuteDatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetActiveRedeemPointAddressesException(CantGetActiveRedeemPointAddressesException.DEFAULT_MESSAGE, e, "Error getting hierarchy account from database.", "database error");
        }

        /**
         * will get the current amount of generated keys
         */
        int generatedKeys;
        try {
            generatedKeys = getDao().getCurrentGeneratedKeys(hierarchyAccount.getId());
        } catch (CantExecuteDatabaseOperationException e) {
            generatedKeys = 0;
        }

        /**
         * Will derive all keys and return them. With the keys, I will generate address for all active networks.
         */
        List<CryptoAddress> cryptoAddresses = new ArrayList<>();
        for (int i=1; i<generatedKeys; i++){
            try {
                try {
                    // I derive the key and generate the address on each network type I'm listening to.
                    for (BlockchainNetworkType blockchainNetworkType : this.getDao().getActiveNetworkTypes()){
                        cryptoAddresses.add(this.getCryptoAddressFromRedemPoint(hierarchyAccount, i, blockchainNetworkType));
                    }
                } catch (CantExecuteDatabaseOperationException e) {
                    // if there was an error, I will only get address from the default network
                    cryptoAddresses.add(this.getCryptoAddressFromRedemPoint(hierarchyAccount, i, BlockchainNetworkType.getDefaultBlockchainNetworkType()));
                }

            } catch (GetNewCryptoAddressException e) {
                return cryptoAddresses;
            }
        }

        return cryptoAddresses;
    }

    /**
     * Will get the CryptoAddress for the given account at the passed position.
     * the difference of getting the address from a redeem point is that it won't mark the address as used.
     * @param hierarchyAccount
     * @param position
     * @return
     */
    private CryptoAddress getCryptoAddressFromRedemPoint(HierarchyAccount hierarchyAccount, int position, BlockchainNetworkType blockchainNetworkType) throws GetNewCryptoAddressException {
        return vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getRedeemPointBitcoinAddress(hierarchyAccount, position, blockchainNetworkType);

    }

    /**
     * Returns the private Keys of all the active Redeem Points hierarchies in the asset vault
     * @return
     */
    public List<String> getActiveRedeemPoints() throws CantGetActiveRedeemPointsException {
        List<String> publicKeys = new ArrayList<>();
        try {
            for (HierarchyAccount hierarchyAccount : getDao().getHierarchyAccounts()){
                if (hierarchyAccount.getHierarchyAccountType() == HierarchyAccountType.REDEEMPOINT_ACCOUNT)
                    publicKeys.add(hierarchyAccount.getDescription());
            }
        } catch (CantExecuteDatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetActiveRedeemPointsException(CantGetActiveRedeemPointsException.DEFAULT_MESSAGE, e, "database error getting the list of active hierarchy accounts.", "database issue");
        }

        return publicKeys;
    }

    /**
     * When we receive assets from a Redeemption processes, the Issuer that granted the extended public key to the redeem point
     * needs to inform us when an address is used, so we can generate more if needed.
     * @param cryptoAddress
     * @param redeemPointPublicKey
     */
    public void notifyUsedRedeemPointAddress(CryptoAddress cryptoAddress, String redeemPointPublicKey) {
        /**
         * I will get the NetworkParameters from the passed Address
         */
        NetworkParameters networkParameters;
        try {
            networkParameters = Address.getParametersFromAddress(cryptoAddress.getAddress());
        } catch (AddressFormatException e) {
            networkParameters = BitcoinNetworkSelector.getNetworkParameter(BlockchainNetworkType.getDefaultBlockchainNetworkType());
        }

        /**
         * I get the Address I will be comparing to.
         */
        Address usedAddress;
        try {
            usedAddress = new Address(networkParameters, cryptoAddress.getAddress());
        } catch (AddressFormatException e) {
            e.printStackTrace();
            return;
        }

        /**
         * I get the Hierarchy Account Key that corresponds to this redeem point
         */
        HierarchyAccount hierarchyAccount = null;

        try {
            hierarchyAccount = getDao().getHierarchyAccount(redeemPointPublicKey);
        } catch (CantExecuteDatabaseOperationException e) {
            e.printStackTrace();
            return;
        }


        /**
         * I need to derive Bitcoin Address from all the generated Keys that I have for the Redeem Point
         * until I found a match.
         */
        List<ECKey> derivedKeys = this.vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getDerivedKeys(hierarchyAccount);
        int position = 0;
        for (ECKey key : derivedKeys){
            if (usedAddress.equals(key.toAddress(networkParameters))){
                /**
                 * I will leave the loop once I found the key to determine the position of the key.
                 */
                break;
            }
            position++;
        }

        /**
         * I will get the current amount of generated keys, to update this value only if it is greater.
         */
        int currentUsedKeys;
        try {
            currentUsedKeys = getDao().getCurrentUsedKeys(hierarchyAccount.getId());
        } catch (CantExecuteDatabaseOperationException e) {
            e.printStackTrace();
            return;
        }

        /**
         * update the new value of generated keys so that the maintainer will generate new ones when possible.
         */
        if (currentUsedKeys < position){
            try {
                getDao().setNewCurrentUsedKeyValue(hierarchyAccount.getId(), position);
            } catch (CantExecuteDatabaseOperationException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    /**
     * Will create a Bitcoin transaction and prepare it to be broadcasted later.
     * This transaction locks the bitcoins associated with the passed input (if valid).
     * @param inputTransaction the Transaction hash that will be used to get the funds from.
     * @param addressTo the destination of the bitcoins.
     * @return the Transaction Hash of the new transaction
     * @throws CantCreateBitcoinTransactionException
     */
    public synchronized String createBitcoinTransaction(String inputTransaction, CryptoAddress addressTo, BlockchainNetworkType blockchainNetworkType) throws CantCreateBitcoinTransactionException {
        /**
         * I get the network for this address.
         */
        try {
            validateNetorkIsActive(blockchainNetworkType);
        } catch (CantValidateActiveNetworkException e) {
            throw new CantCreateBitcoinTransactionException (CantCreateBitcoinTransactionException.DEFAULT_MESSAGE, e, "Network is not active!", "invalid network type");
        }

        final NetworkParameters networkParameters = BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType);

        /**
         * I will get the input transaction  I will use to form the input from the CryptoNetwork
         */
        Transaction genesisTransaction = bitcoinNetworkManager.getBitcoinTransaction(blockchainNetworkType, inputTransaction);

        /**
         * If I couldn't get it I can't go on.
         */
        if (genesisTransaction  == null){
            StringBuilder output = new StringBuilder("The specified transaction hash ");
            output.append(inputTransaction);
            output.append(System.lineSeparator());
            output.append("doesn't exists in the CryptoNetwork.");
            throw new CantCreateBitcoinTransactionException(CantCreateBitcoinTransactionException.DEFAULT_MESSAGE, null, output.toString(), null);
        }

        /**
         * I get the bitcoin address
         */
        Address address = null;
        try {
            address = getBitcoinAddress(networkParameters,addressTo);
        } catch (AddressFormatException e) {
            CantCreateBitcoinTransactionException exception = new CantCreateBitcoinTransactionException(CantCreateBitcoinTransactionException.DEFAULT_MESSAGE, e, "The specified address " + addressTo.getAddress() + " is not valid.", null);
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        }

        /**
         * Create the bitcoinj wallet from the keys of all accounts
         */
        final Wallet wallet;
        Context walletContext = new Context(networkParameters);
        try {
            wallet = Wallet.fromSeed(walletContext.getParams(), getVaultSeed());
        } catch (InvalidSeedException e) {
            CantCreateBitcoinTransactionException exception = new CantCreateBitcoinTransactionException(CantCreateBitcoinTransactionException.DEFAULT_MESSAGE, e, "Unable to create wallet from seed.", "seed issue");
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        }

        try {
            wallet.importKeys(getKeysForAllAccounts(networkParameters));
        } catch (CantExecuteDatabaseOperationException e) {
            CantCreateBitcoinTransactionException exception = new CantCreateBitcoinTransactionException(CantCreateBitcoinTransactionException.DEFAULT_MESSAGE, e, "Error getting the stored accounts to get the keys", "database issue");
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        }

        /**
         * Adds the Genesis Transaction as a UTXO
         */
        WalletTransaction walletTransaction = new WalletTransaction(WalletTransaction.Pool.UNSPENT, genesisTransaction);
        wallet.addWalletTransaction(walletTransaction);

        /**
         * Calculates the amount to be sent by removing the fee from the available balance.
         * I'm ignoring the GenesisAmount passed because this might not be the right value.
         */
        Coin fee = Coin.valueOf(BitcoinNetworkConfiguration.FIXED_FEE_VALUE);
        final Coin coinToSend = wallet.getBalance().subtract(fee);

        /**
         * if the value to send is negative or zero, I will inform of the error
         */
        if (coinToSend.isNegative() || coinToSend.isZero()){
            StringBuilder output = new StringBuilder("The resulting value to be send is insufficient.");
            output.append(System.lineSeparator());
            output.append("We are trying to send " + coinToSend.getValue() + " satoshis, which is ValueToSend - fee (" + wallet.getBalance() + " - " + fee.getValue() + ")");

            CantCreateBitcoinTransactionException exception = new CantCreateBitcoinTransactionException(CantCreateBitcoinTransactionException.DEFAULT_MESSAGE, null, output.toString(), null);
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        }

        /**
         * creates the send request and broadcast it on the network.
         */
        wallet.allowSpendingUnconfirmedTransactions();
        wallet.setAcceptRiskyTransactions(true);

        Wallet.SendRequest sendRequest = Wallet.SendRequest.to(address, coinToSend);
        sendRequest.fee = fee;
        sendRequest.feePerKb = Coin.ZERO;


        /**
         * I'm ready so will complete the transaction.
         */
        try {
            wallet.completeTx(sendRequest);
        } catch (InsufficientMoneyException e) {
            StringBuilder output = new StringBuilder("Not enought money to send bitcoins.");
            output.append(System.lineSeparator());
            output.append("Current balance available for this transaction: " + wallet.getBalance().getValue());
            output.append(System.lineSeparator());
            output.append("Current value to send: " + coinToSend.getValue() + " (+fee: " + fee.getValue() + ")");

            CantCreateBitcoinTransactionException exception = new CantCreateBitcoinTransactionException(CantCreateBitcoinTransactionException.DEFAULT_MESSAGE, e, output.toString(), null);

            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;

        }

        try {
            /**
             * Once I formed the transaction, I will store it in the CryptoNetwork so that is ready for broadcasting.
             */
            bitcoinNetworkManager.storeBitcoinTransaction(blockchainNetworkType, sendRequest.tx, UUID.randomUUID(), false);
        } catch (CantStoreBitcoinTransactionException e) {
            throw new CantCreateBitcoinTransactionException(CantCreateBitcoinTransactionException.DEFAULT_MESSAGE, e, "There was an error storing the created transaction in the CryptoNetwork", "Crypto Network issue");
        }

        return sendRequest.tx.getHashAsString();
    }

    /**
     * gets the current timestamp
     * @return
     */
    private long getCurrentTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * Creates a BitcoinWallet from all derived keys for all accounts stored in this vault.
     * @param networkParameters
     * @return
     */
    private List<ECKey> getKeysForAllAccounts(NetworkParameters networkParameters) throws CantExecuteDatabaseOperationException {
        List<ECKey> allAccountsKeys = new ArrayList<>();
        List<HierarchyAccount> hierarchyAccounts = getDao().getHierarchyAccounts();

        /**
         * I will derive and collect the Keys for each stored account
         */
        for (HierarchyAccount hierarchyAccount : hierarchyAccounts){
            List<ECKey> derivedKeys = vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getDerivedKeys(hierarchyAccount);
            allAccountsKeys.addAll(derivedKeys);
        }
        return allAccountsKeys;
    }

    /**
     * Creates a non complete, unsigned draft bitcoin transaction given the passed input and address to.
     * @param inputTransaction the Input transaction hash used to take funds from.
     * @param addressTo the address to whom we are giving the funds.
     * @return a DraftTransaction class
     * @throws CantCreateDraftTransactionException
     */
    public DraftTransaction createDraftTransaction(String inputTransaction, CryptoAddress addressTo, BlockchainNetworkType blockchainNetworkType) throws CantCreateDraftTransactionException {
        if (inputTransaction.isEmpty() || addressTo == null || blockchainNetworkType == null)
            throw new CantCreateDraftTransactionException(CantCreateDraftTransactionException.DEFAULT_MESSAGE, null, "InputTransaction or AddressTo can't be null", null);

        /**
         * I validate the network is valid
         */
        try {
            validateNetorkIsActive(blockchainNetworkType);
        } catch (CantValidateActiveNetworkException e) {
            CantCreateDraftTransactionException  exception = new CantCreateDraftTransactionException (CantCreateDraftTransactionException.DEFAULT_MESSAGE, e, "Network is not active.", "invalid network type");

            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        }

        final NetworkParameters networkParameters = BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType);

        /**
         * I will get the input transaction  I will use to form the input from the CryptoNetwork
         */
        Transaction genesisTransaction = bitcoinNetworkManager.getBitcoinTransaction(blockchainNetworkType, inputTransaction);

        /**
         * If I couldn't get it I can't go on.
         */
        if (genesisTransaction  == null){
            StringBuilder output = new StringBuilder("The specified transaction hash ");
            output.append(inputTransaction);
            output.append(System.lineSeparator());
            output.append("doesn't exists in the CryptoNetwork.");
            CantCreateDraftTransactionException exception = new CantCreateDraftTransactionException(CantCreateDraftTransactionException.DEFAULT_MESSAGE, null, output.toString(), null);

            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        }

        /**
         * I get the bitcoin address
         */
        Address address = null;
        try {
            address = getBitcoinAddress(networkParameters,addressTo);
        } catch (AddressFormatException e) {
            CantCreateDraftTransactionException exception = new CantCreateDraftTransactionException(CantCreateDraftTransactionException.DEFAULT_MESSAGE, e, "The specified address " + addressTo.getAddress() + " is not valid.", null);
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        }

        /**
         * Calculates the amount to be sent by removing the fee from the available balance.
         * I'm ignoring the GenesisAmount passed because this might not be the right value.
         */
        Coin fee = Coin.valueOf(BitcoinNetworkConfiguration.FIXED_FEE_VALUE);
        final Coin coinToSend = genesisTransaction.getOutput(0).getValue().subtract(fee);

        /**
         * validates we are not sending less than permited.
         */
        if (isDustySend(coinToSend.getValue()))
            throw new CantCreateDraftTransactionException(CantCreateDraftTransactionException.DEFAULT_MESSAGE, null, "Dusty send request: " + coinToSend.getValue(), "send more bitcoins!");

        /**
         * if the value to send is negative or zero, I will inform of the error
         */
        if (coinToSend.isNegative() || coinToSend.isZero()){
            StringBuilder output = new StringBuilder("The resulting value to be send is insufficient.");
            output.append(System.lineSeparator());
            output.append("We are trying to send " + coinToSend.getValue() + " satoshis, which is ValueToSend - fee (" + genesisTransaction.getOutput(0).getValue().getValue() + " - " + fee.getValue() + ")");

            CantCreateDraftTransactionException exception = new CantCreateDraftTransactionException(CantCreateDraftTransactionException.DEFAULT_MESSAGE, null, output.toString(), null);

            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_ASSET_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        }

        /**
         * I will create the Bitcoin transaction
         */
        Transaction transaction = new Transaction(networkParameters);
        TransactionOutput output = genesisTransaction.getOutput(0);
        TransactionOutPoint transactionOutPoint = new TransactionOutPoint(networkParameters, output);
        byte[] script = output.getScriptBytes();
        Coin inputValue = output.getValue();
        TransactionInput transactionInput = new TransactionInput(networkParameters, genesisTransaction, script, transactionOutPoint, inputValue);

        transaction.addInput(transactionInput);


        transaction.addOutput(coinToSend, address);

        DraftTransaction draftTransaction = new DraftTransaction(transaction);

        draftTransaction.addValue(inputValue.value);

        // I add the buyer crypto address
        draftTransaction.setBuyerCryptoAddress(addressTo);

        System.out.println("***CryptoVault*** Draft Transaction created");
        System.out.println(draftTransaction.toString());

        return draftTransaction;
    }

    /**
     * generates a final transaction based on a draft transaction and prepares it to be broadcasted.
     * @param draftTransaction the completed and signed transaction
     * @return the final transactionHash
     * @throws CantCreateBitcoinTransactionException
     */
    public String createBitcoinTransaction(DraftTransaction draftTransaction) throws CantCreateBitcoinTransactionException {
        try {
            /**
             * Once I formed the transaction, I will store it in the CryptoNetwork so that is ready for broadcasting.
             */
            bitcoinNetworkManager.storeBitcoinTransaction(draftTransaction.getNetworkType(), draftTransaction.getBitcoinTransaction(), UUID.randomUUID(), false);
        } catch (CantStoreBitcoinTransactionException e) {
            throw new CantCreateBitcoinTransactionException(CantCreateBitcoinTransactionException.DEFAULT_MESSAGE, e, "There was an error storing the created transaction in the CryptoNetwork", "Crypto Network issue");
        }

        return draftTransaction.getBitcoinTransaction().getHashAsString();
    }

    /**
     * Signs the owned inputs of the passed Draft transaction
     * @param draftTransaction the transaction to sign
     * @return the signed Transaction
     * @throws CantSignTransactionException
     */
    public DraftTransaction signTransaction(DraftTransaction draftTransaction) throws CantSignTransactionException {
        if (draftTransaction == null)
            throw new CantSignTransactionException (CantSignTransactionException.DEFAULT_MESSAGE, null, "DraftTransaction can't be null", "null parameter");

        Transaction transaction = draftTransaction.getBitcoinTransaction();

        HierarchyAccount masterHierarchyAccount;
        try {
            masterHierarchyAccount = this.getDao().getHierarchyAccounts().get(0);
        } catch (CantExecuteDatabaseOperationException e) {
            //If there was an error, I will create a master account manually
            masterHierarchyAccount = new HierarchyAccount(0, "Asset Vault", HierarchyAccountType.MASTER_ACCOUNT);
        }

        /**
         * I get a private key and the list of public keys we are using to monitor the network.
         */
        ECKey privateKey = null;
        List<ECKey> walletKeys = null;
        try {
            privateKey = this.vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getNextAvailableKey(masterHierarchyAccount);
            walletKeys = this.vaultKeyHierarchyGenerator.getAllAccountsKeyList();
        } catch (CantExecuteDatabaseOperationException e) {
            throw new CantSignTransactionException(CantSignTransactionException.DEFAULT_MESSAGE, e, "Error getting a private key from the key hierarchy. Can't sign a transaction.", "No private key to sign");
        }

        /**
         * I get a signed transaction from the abstract class CryptoVault.
         */
        transaction = this.signTransaction(walletKeys, transaction);
        System.out.println("***AssetVault*** Transaction signed.");
        System.out.println(transaction.toString());

        /**
         * add it to the draft transaction and return it.
         */
        draftTransaction.setBitcoinTransaction(transaction);
        return draftTransaction;
    }

    @Override
    public ECKey getPrivateKey(Address address) {
        return this.vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getPrivateKey(address);
    }
}
