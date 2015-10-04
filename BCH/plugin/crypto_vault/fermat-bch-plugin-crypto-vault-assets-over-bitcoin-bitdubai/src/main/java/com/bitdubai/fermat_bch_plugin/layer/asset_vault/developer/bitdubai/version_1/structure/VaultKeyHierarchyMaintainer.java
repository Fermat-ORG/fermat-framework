package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;

/**
 * Created by rodrigo on 10/4/15.
 * Maintains the list of keys generated for each account. The list of keys are used to generate crypto address.
 * This agent takes care to validate we always have enought keys to pass to the crypto network.
 * Its goal is to pass the list of ECKeys to the bitcoin Network so we are monitoring all the ever created keys plus a bag of un used keys
 */
class VaultKeyHierarchyMaintainer implements Agent {
    /**
     * controller of the agent execution thread
     */
    boolean isSupposedToRun;

    /**
     * The vault complete key hierarchy
     */
    private VaultKeyHierarchy vaultKeyHierarchy;


    /**
     * platform services variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    BitcoinNetworkManager bitcoinNetworkManager;

    /**
     * Constructor
     * @param vaultKeyHierarchy
     * @param pluginDatabaseSystem
     */
    public VaultKeyHierarchyMaintainer(VaultKeyHierarchy vaultKeyHierarchy, PluginDatabaseSystem pluginDatabaseSystem, BitcoinNetworkManager bitcoinNetworkManager) {
        this.vaultKeyHierarchy = vaultKeyHierarchy;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
    }

    @Override
    public void start() throws CantStartAgentException {
        isSupposedToRun = true;
        Thread agentThread = new Thread(new VaultKeyHierarchyMaintainerAgent());
        agentThread.start();
    }

    @Override
    public void stop() {
        isSupposedToRun = false;
    }

    private class VaultKeyHierarchyMaintainerAgent implements Runnable{
        /**
         * Sleep time of the agent between iterations
         */
        final long AGENT_SLEEP_TIME = 10000;


        @Override
        public void run() {
            while (isSupposedToRun){
                doTheMainTask();
                try {
                    Thread.sleep(AGENT_SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void doTheMainTask(){

        }
    }

}
