package com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_world.Agent;
import com.bitdubai.fermat_api.layer._11_world.blockchain_info.exceptions.CantInitializeMonitorAgentException;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer._11_world.blockchain_info.exceptions.CantStartAgentException;

/**
 * Created by ciencias on 3/19/15.
 */
public class BlockchainInfoIncomingCryptoMonitorAgent implements Agent, DealsWithErrors, DealsWithPluginDatabaseSystem {
    /**
     * DealsWithEvents Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Agent Member Variables.
     */
    Thread agentThread;
    MonitorAgent monitorAgent;

    /**
     * Agent interface implementation.
     */

    @Override
    public void start() throws CantStartAgentException {

        this.monitorAgent = new MonitorAgent ();

        ((DealsWithPluginDatabaseSystem) this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);

        try {
            ((MonitorAgent) this.monitorAgent).Initialize();
        }
        catch (CantInitializeMonitorAgentException cantInitializeCryptoRegistryException) {
            /**
             * I cant continue if this happens.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantInitializeCryptoRegistryException);

            throw new CantStartAgentException();
        }

        this.agentThread = new Thread(new MonitorAgent());
        this.agentThread.start();
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    /**
     *DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    private class MonitorAgent implements DealsWithPluginDatabaseSystem, Runnable  {

        private final int SLEEP_TIME = 5000;


        /**
         * DealsWithPluginDatabaseSystem Interface member variables.
         */
        PluginDatabaseSystem pluginDatabaseSystem;


        /**
         * MonitorAgent interface implementation.
         */
        private void Initialize () throws CantInitializeMonitorAgentException {

            /**
             * Here I open the database read the event table and load it to memory.
             */

            readEvents();
        }

        /**
         * DealsWithPluginDatabaseSystem interface implementation.
         */
        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        /**
         * Runnable Interface implementation.
         */
        @Override
        public void run() {

            /**
             * Infinite loop.
             */
            while (true == true) {

                /**
                 * Sleep for a while.
                 */
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    cleanResources();
                    return;
                }

                /**
                 * Now I do the main task.
                 */
                doTheMainTask();

                /**
                 * Check if I have been Interrupted.
                 */
                if (Thread.currentThread().isInterrupted()) {
                    cleanResources();
                    return;
                }
            }
        }

        private void doTheMainTask() {

            /**
             * Read the EventsRecorded table to see if there are new Events.
             */
            readEvents();

            /**
             * If there are, I bring the transactions to this plug-in.
             */

        }

        private void readEvents() {

        }

        private void cleanResources() {

            /**
             * Disconnect from database and explicitly set all references to null.
             */

        }
    }
}

