package com.bitdubai.fermat_bch_plugin_layer.watch_only_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.BlockchainNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.KeyHierarchy;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.CryptoVaultDao;
import com.bitdubai.fermat_bch_plugin_layer.watch_only_vault.developer.bitdubai.version_1.database.BitcoinWatchOnlyCryptoVaultDao;

import com.bitdubai.fermat_bch_plugin_layer.watch_only_vault.developer.bitdubai.version_1.exceptions.CantInitializeBitcoinWatchOnlyCryptoVaultDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptovault.assetsoverbitcoin.developer.bitdubai.version_1.structure.VaultKeyHierarchy</code>
 * Defines the internal Hierarchy object used on the Crypto Vault. The hierarchy is created from a root key each time the platform
 * is initiated. The Hierarchy is in charge of generating new bitcoin addresses when request from the public Keys derived for each account.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 06/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
class VaultKeyHierarchy extends KeyHierarchy {

    /**
     * Holds the list of Accounts and master keys of the hierarchy
     */
    DeterministicKey accountMasterKey;
    private Map<Integer, DeterministicKey> accountsMasterKeys;

    /**
     * Holds the DAO object to access the database
     */
    BitcoinWatchOnlyCryptoVaultDao dao;

    /**
     * Platform variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;

    /**
     * Constructor
     * @param rootKey (m) key
     */
    public VaultKeyHierarchy(DeterministicKey rootKey, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        super(rootKey);
        this.accountMasterKey = rootKey;
        accountsMasterKeys = new HashMap<>();
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * Generates a master Deterministic Key for the given account on the account path
     * These are the m/n paths...for example m/0 , m/1, ... m m/n
     * @param account
     */
    public void addVaultAccount(com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount account){
        //DeterministicKey accountMasterKey = this.deriveChild(account.getAccountPath(), true, true, ChildNumber.ZERO);
        accountsMasterKeys.put(account.getId(), accountMasterKey);
    }

    /**
     * Returns the master private key for the specified account
     * @param hierarchyAccount
     * @return the fist key of the path m/HierarchyAccount/0. Example: m/0/0
     */
    public DeterministicKey getAddressKeyFromAccount(com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount hierarchyAccount){
//        /**
//         * gets the masterKey for this account
//         */
//        DeterministicKey masterKey = accountsMasterKeys.get(hierarchyAccount.getId());
//
//        /**
//         * Serialize the pubkey of the master key
//         */
//        byte[] publicKeyBytes = masterKey.getPubKey();
//        byte[] chainCode = masterKey.getChainCode();
//
//        return HDKeyDerivation.createMasterPubKeyFromBytes(publicKeyBytes, chainCode);
        return accountMasterKey;
    }

    /**
     * Generates a new hierarchy on the path m/account/0 with keys
     * @param hierarchyAccount
     * @return a new hierarchy used to generate bitcoin addresses
     */
    public DeterministicHierarchy getKeyHierarchyFromAccount(com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount hierarchyAccount){
        DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(getAddressKeyFromAccount(hierarchyAccount));
        return deterministicHierarchy;
    }

    /**
     * Generates a Bitcoin Address from the specified networkType and Account.
     * It wil use the next available publicKey from the hierarchy for that account.
     * @param blockchainNetworkType
     * @return the crypto address
     */
    public CryptoAddress getBitcoinAddress(BlockchainNetworkType blockchainNetworkType, com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount hierarchyAccount) throws GetNewCryptoAddressException {
        /**
         * I get the next available key for this account
         */
        ECKey ecKey = null;
        try {
            ecKey = getNextAvailableKey(hierarchyAccount);
        } catch (CantExecuteDatabaseOperationException e) {
            throw new GetNewCryptoAddressException(GetNewCryptoAddressException.DEFAULT_MESSAGE, e, "There was an error getting the actual unused Key depth to derive a new key.", "database problem.");
        }
        /**
         * I will create the CryptoAddress with the key I just got
         */
        String address = ecKey.toAddress(BlockchainNetworkSelector.getNetworkParameter(blockchainNetworkType)).toString();
        CryptoAddress cryptoAddress = new CryptoAddress(address, CryptoCurrency.BITCOIN);

        /**
         * I need to make the network that I used to generate the address active, if it is different than the default network.
         * BlockchainNetworkType has MainNet, RegTest and TestNet. The default value is the one used for the platform.
         * If the address generated is for a network different than default, I need to update the database so we start monitoring this network
         */
        if (BlockchainNetworkSelector.getNetworkParameter(blockchainNetworkType) != BlockchainNetworkSelector.getNetworkParameter(BlockchainNetworkType.getDefaultBlockchainNetworkType())){
            setActiveNetwork(blockchainNetworkType);
        }

        /**
         * I will update the detailed key maintenance information by adding this generated address to the table.
         */
        try {
            updateKeyDetailedStatsWithNewAddress(hierarchyAccount.getId(), ecKey, cryptoAddress, blockchainNetworkType);
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            throw new GetNewCryptoAddressException(GetNewCryptoAddressException.DEFAULT_MESSAGE, e, null, null);
        }

        return cryptoAddress;
    }

    /**
     * Updates the Detailed Key maintenance table by marking the passed key as used with the passed cryptoAddress
     * @param hierarchyAccountId
     * @param ecKey
     * @param cryptoAddress
     * @param blockchainNetworkType
     */
    private void updateKeyDetailedStatsWithNewAddress(int hierarchyAccountId, ECKey ecKey, CryptoAddress cryptoAddress, BlockchainNetworkType blockchainNetworkType) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            getDao().updateKeyDetailedStatsWithNewAddress(hierarchyAccountId, ecKey, cryptoAddress, blockchainNetworkType);
        } catch (CantExecuteDatabaseOperationException e) {
            /**
             * will continue because this is not critical.
             */
            e.printStackTrace();
        } catch (com.bitdubai.fermat_bch_plugin_layer.watch_only_vault.developer.bitdubai.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets the next available key from the specified account
     * @param account
     * @return
     */
    public ECKey getNextAvailableKey(com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount account) throws CantExecuteDatabaseOperationException {
        /**
         * I get from database the next available key depth
         */
        int keyDepth = 0;
        keyDepth = getNextAvailableKeyDepth(account);

        /**
         * I will derive a new Key from this account
         */
        DeterministicHierarchy keyHierarchy = getKeyHierarchyFromAccount(account);
        DeterministicKey deterministicKey = keyHierarchy.deriveChild(keyHierarchy.getRootKey().getPath(), true, false, new ChildNumber(keyDepth, false));

        /**
         * I convert from a HD key to a ECKey
         */
        ECKey ecKey = ECKey.fromPublicOnly(deterministicKey.getPubKey());
        return ecKey;
    }

    /**
     * Updates the database to active a new network
     * @param blockchainNetworkType
     */
    private void setActiveNetwork(BlockchainNetworkType blockchainNetworkType) {
        try {
            getDao().setActiveNetworkType(blockchainNetworkType);
        } catch (CantExecuteDatabaseOperationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the net keyDepth that is available to generate a new key.
     * It sets this value in the database.
     * @param hierarchyAccount
     * @return
     */
    private int getNextAvailableKeyDepth(com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount hierarchyAccount) throws CantExecuteDatabaseOperationException {
        int returnValue = 0;
        int currentUsedKey = getDao().getCurrentUsedKeys(hierarchyAccount.getId());
        /**
         * I set the new value of the key depth
         */
        returnValue = currentUsedKey +1;

        /**
         * and Update this value in the database
         */
        getDao().setNewCurrentUsedKeyValue(hierarchyAccount.getId(), returnValue);

        return returnValue;
    }

    /**
     * gets the dao instance to access database operations
     * @return
     */
    private BitcoinWatchOnlyCryptoVaultDao getDao(){
        if (dao == null){
            try {
                dao = new BitcoinWatchOnlyCryptoVaultDao(pluginDatabaseSystem, pluginId);
            } catch (CantInitializeBitcoinWatchOnlyCryptoVaultDatabaseException e) {
                e.printStackTrace();
            }
        }

        return dao;
    }

    /**
     * Gets all the already derived keys from the hierarchy on this account.
     * @param account
     * @return
     */
    public List<ECKey> getDerivedKeys(com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount account){
        DeterministicHierarchy keyHierarchy = getKeyHierarchyFromAccount(account);
        List<ECKey> childKeys = new ArrayList<>();

        /**
         * I will get how many keys are already generated for this account.
         */
        int generatedKeys;
        try {
            generatedKeys  = this.getDao().getCurrentGeneratedKeys(account.getId());
        } catch (CantExecuteDatabaseOperationException e) {
            generatedKeys = 200;
        }
        for (int i = 0; i < generatedKeys; i++) {
            // I derive the key at position i
            DeterministicKey derivedKey = keyHierarchy.deriveChild(keyHierarchy.getRootKey().getPath(), true, false, new ChildNumber(i, false));
            // I add this key to the ECKey list
            childKeys.add(ECKey.fromPrivate(derivedKey.getPrivKey()));
        }

        return childKeys;
    }

    @Override
    public CryptoVaultDao getCryptoVaultDao() {
        return this.getDao();
    }
}
