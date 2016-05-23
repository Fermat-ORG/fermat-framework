package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The class <code>IncomingIntraUserCryptoMonitorAgent</code>
 * is in charge of taking the responsibility of the transactions sent by the Incoming Crypto plugin.
 */
public class IncomingIntraUserCryptoMonitorAgent {

    private ErrorManager              errorManager;
    private IncomingCryptoManager     incomingCryptoManager;
    private IncomingIntraUserRegistry registry;


    /**
     * TransactionAgent Member Variables.
     */
    private Thread agentThread;
    private MonitorAgent monitorAgent;


    public IncomingIntraUserCryptoMonitorAgent(final ErrorManager errorManager, final IncomingCryptoManager incomingCryptoManager, final IncomingIntraUserRegistry registry){
        this.errorManager          = errorManager;
        this.incomingCryptoManager = incomingCryptoManager;
        this.registry              = registry;
    }

    public void start() throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIntraUserCryptoMonitorAgentException {
        this.monitorAgent = new MonitorAgent(incomingCryptoManager,errorManager,registry);
        try {
            this.monitorAgent.initialize();
            this.agentThread = new Thread(this.monitorAgent);
            this.agentThread.start();
        } catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIntraUserCryptoMonitorAgentException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIntraUserCryptoMonitorAgentException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "You should inspect the cause");
        }
    }

    public void stop(){
        this.monitorAgent.stop();
    }

    public boolean isRunning(){
        return this.monitorAgent.isRunning();
    }


    private static class MonitorAgent implements Runnable {

        private AtomicBoolean running = new AtomicBoolean(false);

        private ErrorManager              errorManager;
        private IncomingCryptoManager     incomingCryptoManager;
        private IncomingIntraUserRegistry registry;


        public MonitorAgent(IncomingCryptoManager incomingCryptoManager, ErrorManager errorManager , IncomingIntraUserRegistry incomingIntraUserRegistry){
            this.errorManager          = errorManager;
            this.incomingCryptoManager = incomingCryptoManager;
            this.registry              = incomingIntraUserRegistry;
        }

        public boolean isRunning(){
            return running.get();
        }

        public void stop(){
            running.set(false);
        }


        /*
         * MonitorAgent member variables
         */
        private com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.IncomingIntraUserCryptoSourceAdministrator sourceAdministrator;


        private static final int SLEEP_TIME = 10000;

        /**
         * MonitorAgent methods.
         */
        private void initialize () {
            this.sourceAdministrator = new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.IncomingIntraUserCryptoSourceAdministrator(this.incomingCryptoManager);
        }

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
                    break;
                }
            }
            cleanResources();
        }

        private void doTheMainTask() {
            com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.EventWrapper eventWrapper = null;
            try {
                eventWrapper     = this.registry.getNextCryptoPendingEvent();
                while(thisIsAPendingEvent(eventWrapper)) {
                    processEvent(eventWrapper);
                    eventWrapper = this.registry.getNextCryptoPendingEvent();
                }
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }

        private boolean thisIsAPendingEvent(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.EventWrapper eventWrapper){
            return eventWrapper != null;
        }

        private void processEvent(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.EventWrapper eventWrapper) {
            try {
                TransactionProtocolManager<CryptoTransaction> source = this.sourceAdministrator.getSourceAdministrator(EventSource.getByCode(eventWrapper.getEventSource()));
                List<Transaction<CryptoTransaction>> transactionList = source.getPendingTransactions(Specialist.INTRA_USER_SPECIALIST);

                System.out.println("TTF -  INTRA USER CRYPTO MONITOR: " + transactionList.size() + " TRAMSACTION(s) DETECTED");

                this.registry.acknowledgeTransactions(transactionList);

                System.out.println("TTF - INTRA USER CRYPTO MONITOR: " + transactionList.size() + " TRAMSACTION(s) ACKNOWLEDGED");

                // Now we take all the transactions in state (ACKNOWLEDGE,TO_BE_NOTIFIED)
                // Remember that this list can be more extensive than the one we saved, this is
                // because the system could have shut down in this step of the protocol making old
                // transactions to be stored but not precessed.
                List<Transaction<CryptoTransaction>> acknowledgedTransactions = this.registry.getAcknowledgedTransactions();

                // And finally, for each transaction we confirm it and then register responsibility.
                for(Transaction<CryptoTransaction> transaction : acknowledgedTransactions){
                    try {
                        source.confirmReception(transaction.getTransactionID());
                        System.out.println("TTF - INTRA USER CRYPTO MONITOR: TRANSACTION RESPONSIBILITY ACQUIRED");
                        registry.acquireResponsibility(transaction);
                    } catch (CantConfirmTransactionException | com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAcquireResponsibilityException exception) {
                        // TODO: Consultar si esto hace lo que pienso, si falla no registra en base de datos
                        //       la transacción
                        // We will inform the exception and try again in the next round
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                    }
                }

                // After finishing all the steps we mark the event as seen.
                registry.disableEvent(eventWrapper.getEventId());
                System.out.println("TTF - INTRA USER CRYPTO MONITOR: EVENT DISABLED");
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }

        private void cleanResources() {
            /**
             * Disconnect from database and explicitly set all references to null.
             */
        }
    }
}