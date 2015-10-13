package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.UUID;


/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptonetwork.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkEventsAgent</code>
 * Monitors the database for pending transactions to be notified, and raises the Events if pending transactions are foind.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 13/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BitcoinCryptoNetworkEventsAgent implements Agent {
    /**
     * class variables
     */
    private boolean isSupossedToBeRunning;
    private final int AGENT_DELAY = 10000; // 5 seconds of delay

    /**
     * platform variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;
    EventManager eventManager;

    /**
     * Constructor
     * @param pluginDatabaseSystem
     * @param pluginId
     * @param eventManager
     */
    public BitcoinCryptoNetworkEventsAgent(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, EventManager eventManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.eventManager = eventManager;
    }

    /**
     * starts the agent in a new thread.
     * @throws CantStartAgentException
     */
    @Override
    public void start() throws CantStartAgentException {
        isSupossedToBeRunning = true;
        Thread agentThread = new Thread(new NetworkAgent());
        agentThread.start();
    }

    /**
     * stops the agent thread.
     */
    @Override
    public void stop() {
        isSupossedToBeRunning = false;
    }

    /**
     * Class that executes the agent
     */
    private class NetworkAgent implements Runnable{
        @Override
        public void run() {
            /**
             * While isSupossedToBeRunning true, i will run this.
             */
            while (isSupossedToBeRunning){
                doTheMainTask();

                /**
                 * wait for a delay to re execute
                 */
                try {
                    Thread.sleep(AGENT_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * performs the main task
         */
        private void doTheMainTask() {
            /**
             * I will get the pending transactions to be notified on each table
             */
            int pendingIncoming = getPendingNotifiedIncomingTransactions();
            int pendingOutgoing = getPendingNotifiedOutgoingTransactions();

            /**
             * update the statistics of the agent
             */
            updateEventAgentStats(pendingIncoming, pendingOutgoing);

            /**
             * raise the events
             */
            if (pendingIncoming > 0)
                raiseIncomingTransactionEvent();

            if (pendingOutgoing > 0)
                raiseOutgoingTransactionEvent();

        }

        private void raiseOutgoingTransactionEvent() {
            //todo raise event
        }

        private void raiseIncomingTransactionEvent() {
            //todo raise event
        }

        private void updateEventAgentStats(int pendingIncoming, int pendingOutgoing) {
            //todo update stats in database
        }

        /**
         * searchs the database for PendingNotified protocolStatus transactions
         * @return
         */
        private int getPendingNotifiedIncomingTransactions(){
            //todo get this from the database
            return 0;
        }

        /**
         * searchs the database for PendingNotified protocolStatus transactions
         * @return
         */
        private int getPendingNotifiedOutgoingTransactions(){
            //todo get this from the database
            return 0;
        }
    }
}
