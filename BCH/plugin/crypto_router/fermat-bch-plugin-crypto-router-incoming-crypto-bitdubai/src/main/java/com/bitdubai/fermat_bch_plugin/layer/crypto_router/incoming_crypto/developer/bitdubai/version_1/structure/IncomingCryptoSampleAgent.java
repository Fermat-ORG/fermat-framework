package com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure;

/**
 * Created by ciencias on 3/30/15.
 * Modified by Arturo Vallone 25/04/2015
 */


import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantInitializeCryptoRegistryException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.interfaces.TransactionAgent;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartAgentException;

import java.util.concurrent.atomic.AtomicBoolean;

public class IncomingCryptoSampleAgent implements DealsWithErrors, TransactionAgent {

    /**
     * DealsWithEvents Interface member variables.
     */
    private ErrorManager errorManager;


    /**
     * TransactionAgent Member Variables.
     */
    private Thread agentThread;
    private MonitorAgent monitorAgent;

    public IncomingCryptoSampleAgent(final ErrorManager errorManager){
        this.errorManager = errorManager;
    }

    /**
     *DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * TransactionAgent Interface implementation.
     */
    @Override
    public void start() throws CantStartAgentException {

        this.monitorAgent = new MonitorAgent ();

        //((DealsWithPluginDatabaseSystem) this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);

        try {
            this.monitorAgent.Initialize();
        }
        catch (CantInitializeCryptoRegistryException cantInitializeCryptoRegistryException) {
            /**
             * I cant continue if this happens.
             */
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,cantInitializeCryptoRegistryException);
            throw new CantStartAgentException("Agent failed to start",cantInitializeCryptoRegistryException,"","");
        }

        this.agentThread = new Thread(new MonitorAgent());
        this.agentThread.start();

    }

    @Override
    public void stop() {
        if(monitorAgent != null)
            monitorAgent.stop();

    }


    private static class MonitorAgent implements Runnable  {

        private static final int SLEEP_TIME = 5000;

        private AtomicBoolean running;

        public  MonitorAgent(){
            running = new AtomicBoolean(false);
        }

        public boolean isRunning(){
            return running.get();
        }

        public void stop(){
            running.set(false);
        }

        /**
         * MonitorAgent interface implementation.
         */
        private void Initialize () throws CantInitializeCryptoRegistryException {

            /**
             * Here I open the database read the event table and load it to memory.
             */

            readEvents();
        }

        /**

        /**
         * Runnable Interface implementation.
         */
        @Override
        public void run() {

            /**
             * Infinite loop.
             */
            running.set(true);

            while (running.get()) {

                /**
                 * Sleep for a while.
                 */
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    break;
                }

                /**
                 * Now I do the main task.
                 */
                doTheMainTask();

                /**
                 * Check if I have been Interrupted.
                 */
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            cleanResources();
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
