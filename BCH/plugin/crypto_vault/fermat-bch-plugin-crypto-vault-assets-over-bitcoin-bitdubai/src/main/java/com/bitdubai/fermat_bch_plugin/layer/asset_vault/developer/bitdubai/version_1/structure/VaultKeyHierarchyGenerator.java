package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccountType;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.database.AssetsOverBitcoinCryptoVaultDao;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantLoadHierarchyAccountsException;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.wallet.DeterministicSeed;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptovault.assetsoverbitcoin.developer.bitdubai.version_1.structure.VaultKeyHierarchyGenerator</code>
 * Is in charge of creating the Vault Key hierarchy from the master seed passed. It will get all the accounts created on this device
 * and generate the key hierarchy for all of them.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 06/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
class VaultKeyHierarchyGenerator implements Runnable{
    /**
     * Unique seed used to generate all the hierarchies
     */
    private DeterministicSeed seed;

    /**
     * The HierarchyMaintainer agent that keeps tracks of the keys used and generated.
     */
    VaultKeyHierarchyMaintainer vaultKeyHierarchyMaintainer;

    /**
     * Holds the list of Keys generated at the HierarchyMaintainer that are passed to the Crypto Network.
     */
    private List<ECKey> allAccountsKeyList;

    /**
     * dao object to access the database
     */
    AssetsOverBitcoinCryptoVaultDao dao;

    /**
     * RootKey of the hierarchy that will be generated
     */
    private DeterministicKey rootKey;

    /**
     * The hierarchy of the vault
     */
    private VaultKeyHierarchy vaultKeyHierarchy;


    /**
     * Platform services
     */
    private PluginDatabaseSystem pluginDatabaseSystem;
    private  BlockchainManager<ECKey, Transaction> bitcoinNetworkManager;
    UUID pluginId;
    ErrorManager errorManager;

    /**
     * Constructor
     * @param seed
     * @param pluginDatabaseSystem
     */
    public VaultKeyHierarchyGenerator(DeterministicSeed seed, PluginDatabaseSystem pluginDatabaseSystem,  BlockchainManager<ECKey, Transaction> bitcoinNetworkManager, UUID pluginId, ErrorManager errorManager) {
        this.seed = seed;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.pluginId = pluginId;
        this.errorManager = errorManager;

    }

    @Override
    public void run() {
        try {
            doTheMainTask();
        } catch (CantLoadHierarchyAccountsException e) {
            /**
             * If there was an error, i will just log it.
             */
            e.printStackTrace();
        }
    }

    /**
     * Starting point of the agent
     */
    private void doTheMainTask() throws CantLoadHierarchyAccountsException {
        try {
            /**
             * I generate the rootKey (m) of the hierarchy.
             */
            rootKey = generateRootKeyFromSeed(seed);

            /**
             * I create the VaultKeyHierarchy from the master key
             */
            vaultKeyHierarchy = new VaultKeyHierarchy(rootKey, pluginDatabaseSystem, pluginId, errorManager);

            /**
             * I will get from the database the list of accounts to create
             * and add them to the hierarchy
             */
            for (com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount hierarchyAccount : getHierarchyAccounts()) {
                vaultKeyHierarchy.addVaultAccount(hierarchyAccount);
            }

            /**
             * once the hierarchy is created, I will start the HierarchyMaintainer agent that will load the keys, and the crypto network
             */
            vaultKeyHierarchyMaintainer = new VaultKeyHierarchyMaintainer(this.vaultKeyHierarchy, this.pluginDatabaseSystem, this.bitcoinNetworkManager, this.pluginId);
            try {
                vaultKeyHierarchyMaintainer.start();
            } catch (CantStartAgentException e) {
                // I will log this error for now.
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Generates the root or master key from a valid seed
     * @param seed
     * @return the rootKey (m)
     */
    private DeterministicKey generateRootKeyFromSeed(DeterministicSeed seed) {
        return HDKeyDerivation.createMasterPrivateKey(seed.getSeedBytes());
    }

    /**
     * Gets the list of stored HierarchyAccounts. If no accounts exists, it will create the zero account.
     * @return the store list of accounts
     */
    private List<com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount> getHierarchyAccounts() throws CantLoadHierarchyAccountsException {
        List<com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount> hierarchyAccounts = new ArrayList<>();

        /**
         * Gets the Hierarchy accouns from the database
         */
        try {
            hierarchyAccounts = getDao().getHierarchyAccounts();
        } catch (CantExecuteDatabaseOperationException e) {
            /**
             * If there was an error creating or loading the database, or getting the list of accounts, I can't go on.
             */
            throw new CantLoadHierarchyAccountsException(
                    CantLoadHierarchyAccountsException.DEFAULT_MESSAGE,
                    e,
                    "Error trying to load the Hierarchy Accounts from the database.", "database issue");
        }


        /**
         * If there are no accounts in the database, these means is the first time the plugin runs, so I will create
         * the account 0 that will be used by the asset vault.
         */
        if (hierarchyAccounts.size() == 0){
            com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount accountZero = new com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount(0, "Asset Vault account", HierarchyAccountType.MASTER_ACCOUNT);
            hierarchyAccounts.add(accountZero);

            /**
             * And I will also try to add this to the database so I can load it the next time.
             */
                try {
                    getDao().addNewHierarchyAccount(accountZero);
                } catch (CantExecuteDatabaseOperationException e) {
                    // I don't need to handle this error.
                }
        }
        return hierarchyAccounts;
    }

    /**
     * Gets and instance of the AssetsOverBitcoinCryptoVaultDao class used to access database objects.
     * @return
     * @throws CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException
     */
    private AssetsOverBitcoinCryptoVaultDao getDao() {
        if (dao == null){
            try {
                dao = new AssetsOverBitcoinCryptoVaultDao(pluginDatabaseSystem, pluginId);
            } catch (CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException e) {
                e.printStackTrace();
            }
        }
        return dao;
    }

    /**
     * Returns the created vault Hierarchy
     * @return
     */
    public VaultKeyHierarchy getVaultKeyHierarchy() {
        return vaultKeyHierarchy;
    }

    /**
     * Gets the list of keys that the network is listening to. This list was generated by the Hierarchy Maintainer
     * @return
     */
    public List<ECKey> getAllAccountsKeyList() {
        allAccountsKeyList = vaultKeyHierarchyMaintainer.getAllAccountsKeyList();
        return allAccountsKeyList;
    }
}
