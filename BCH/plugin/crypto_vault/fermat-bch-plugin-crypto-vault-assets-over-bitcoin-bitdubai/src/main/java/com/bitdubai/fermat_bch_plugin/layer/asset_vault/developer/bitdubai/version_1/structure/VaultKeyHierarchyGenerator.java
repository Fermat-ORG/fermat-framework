package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.google.common.collect.ImmutableList;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.wallet.DeterministicSeed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 10/4/15.
 */
public class VaultKeyHierarchyGenerator implements Runnable{
    /**
     * Unique seed used to generate all the hierarchies
     */
    private DeterministicSeed seed;

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
    private BitcoinNetworkManager bitcoinNetworkManager;

    /**
     * Constructor
     * @param seed
     * @param pluginDatabaseSystem
     */
    public VaultKeyHierarchyGenerator(DeterministicSeed seed, PluginDatabaseSystem pluginDatabaseSystem, BitcoinNetworkManager bitcoinNetworkManager) {
        this.seed = seed;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
    }

    @Override
    public void run() {
        doTheMainTask();
    }

    /**
     * Starting point of the agent
     */
    private void doTheMainTask(){
        /**
         * I generate the rootKey (m) of the hierarchy.
         */
        rootKey = generateRootKeyFromSeed(seed);

        /**
         * I create the VaultKeyHierarchy from the master key
         */
        vaultKeyHierarchy = new VaultKeyHierarchy(rootKey);

        /**
         * I will get from the database the list of accounts to create
         * and add them to the hierarchy
         */
        for (HierarchyAccount hierarchyAccount : getHierarchyAccounts()){
            vaultKeyHierarchy.addVaultAccount(hierarchyAccount);
        }

        /**
         * once the hierarchy is created, I will start the HierarchyMaintainer agent that will load the keys, and the crypto network
         */
        VaultKeyHierarchyMaintainer vaultKeyHierarchyMaintainer = new VaultKeyHierarchyMaintainer(this.vaultKeyHierarchy, this.pluginDatabaseSystem, this.bitcoinNetworkManager);
        try {
            vaultKeyHierarchyMaintainer.start();
        } catch (CantStartAgentException e) {
            //todo handle
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

    private List<HierarchyAccount> getHierarchyAccounts(){
        List<HierarchyAccount> hierarchyAccounts = new ArrayList<>();
        //todo add DAO logic to get the value from the database and form the accounts object


        /**
         * If there are no accounts in the database, these means is the first time the plugin runs, so I will create
         * the account 0 that will be used by the asset vault.
         */
        if (hierarchyAccounts.size() == 0){
            HierarchyAccount accountZero = new HierarchyAccount(0, "Asset vault account");
            hierarchyAccounts.add(accountZero);
            //todo Add this account to database
        }
        return hierarchyAccounts;
    }

    /**
     * Returns the created vault Hierarchy
     * @return
     */
    public VaultKeyHierarchy getVaultKeyHierarchy() {
        return vaultKeyHierarchy;
    }
}
