package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantMonitorBitcoinNetworkException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.VaultKeyMaintenanceParameters;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
         * current key usage from the database
         */
        int currentGeneratedKeys, currentUsedKeys, currentThreshold;


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

        /**
         * main executor of the agent
         */
        private void doTheMainTask(){
            /**
             * I get all the accounts that are available from the database
             */
            for (HierarchyAccount hierarchyAccount : getHierarchyAccounts()){
                /**
                 * for each account, I will get the currentGeneratedKeys value
                 */
                currentGeneratedKeys = getCurrentGeneratedKeys(hierarchyAccount);
                /**
                 * now I will get the currentUsedKeys from the database
                 */
                currentUsedKeys = getCurrentUsedKeys(hierarchyAccount);
                /**
                 * I will calculate the current threshold to see if we need to create new keys
                 */
                currentThreshold = 100 - ((currentUsedKeys * 100) / currentGeneratedKeys);
                if (currentThreshold <= VaultKeyMaintenanceParameters.KEY_PERCENTAGE_GENERATION_THRESHOLD){
                    /**
                     * The current threshold is lower than the limit imposed, we need to generate new keys
                     * I start by updating the database and defining the new GeneratedKeys values
                     */
                    int newGeneratedKeys = currentGeneratedKeys + VaultKeyMaintenanceParameters.KEY_GENERATION_BLOCK;
                    setGeneratedKeysValue(newGeneratedKeys);

                    /**
                     * I will generate the list of keys from zero to the new value and pass that to the bitcoin network to resync.
                     */
                    DeterministicHierarchy pubKeyHierarchy = vaultKeyHierarchy.getAddressPublicHierarchyFromAccount(hierarchyAccount);

                    List<ECKey> publicKeys = new ArrayList<>();
                    for (int i=0; i < newGeneratedKeys; i++){
                        // I derive the key at position i
                        DeterministicKey derivedPubKey = pubKeyHierarchy.deriveChild(pubKeyHierarchy.getRootKey().getPath(), true, true, new ChildNumber(i, false));
                        // I add this key to the ECKey list
                        publicKeys.add(ECKey.fromPublicOnly(derivedPubKey.getPubKeyPoint()));
                    }

                    /**
                     * Once I derived all the keys, I'm passing these keys to the bitcoin network to start listening to them
                     */
                    try {
                        bitcoinNetworkManager.monitorNetworkFromKeyList(publicKeys);
                    } catch (CantMonitorBitcoinNetworkException e) {
                        //todo handle
                    }

                }

                /**
                 * I will update the stats of the Hierarchy Maintainer in the database
                 */
                updateMaintainerStats(hierarchyAccount.getId(),
                                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                                    currentGeneratedKeys,
                                    currentUsedKeys,
                                    currentThreshold);
            }
        }

        private void setGeneratedKeysValue(int value) {
            //todo update the table key_Maintenance column generatedKeys with the passed value
        }

        /**
         * Will update the statistics of the maintainer execution
         * @param hierarchyAccountId
         * @param date
         * @param currentGeneratedKeys
         * @param currentUsedKeys
         * @param currentThreshold
         */
        private void updateMaintainerStats(int hierarchyAccountId, String date, int currentGeneratedKeys, int currentUsedKeys, int currentThreshold) {
            //todo complete maintainer update stats
        }

        /**
         * Gets the actual amount of used keys for the specified account.
         * Keys used are the ones that where used to generate addresses
         * @param hierarchyAccount
         * @return
         */
        private int getCurrentUsedKeys(HierarchyAccount hierarchyAccount) {
            return 0;
        }

        /**
         * get the amount of how many keys have been generated for the specified amount
         * @param hierarchyAccount
         * @return
         */
        private int getCurrentGeneratedKeys(HierarchyAccount hierarchyAccount) {
            return 0;
        }

        /**
         * Gets the available Accounts from the database
         * @return
         */
        private List<HierarchyAccount> getHierarchyAccounts() {

            return null;
        }
    }

}
