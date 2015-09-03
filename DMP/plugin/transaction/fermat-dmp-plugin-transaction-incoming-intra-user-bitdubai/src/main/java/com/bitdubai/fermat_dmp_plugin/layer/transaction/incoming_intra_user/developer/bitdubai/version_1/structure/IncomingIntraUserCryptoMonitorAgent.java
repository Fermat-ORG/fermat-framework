package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIntraUserCryptoMonitorAgentException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAcquireResponsibilityException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.util.EventWrapper;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.util.IncomingIntraUserCryptoSourceAdministrator;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The class <code>com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.structure.IncomingIntraUserCryptoMonitorAgent</code>
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

    public void start() throws CantStartIntraUserCryptoMonitorAgentException {
        this.monitorAgent = new MonitorAgent(incomingCryptoManager,errorManager,registry);
        try {
            this.monitorAgent.initialize();
            this.agentThread = new Thread(this.monitorAgent);
            this.agentThread.start();
        } catch (Exception exception) {
            throw new CantStartIntraUserCryptoMonitorAgentException(CantStartIntraUserCryptoMonitorAgentException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "You should inspect the cause");
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
        private IncomingIntraUserCryptoSourceAdministrator sourceAdministrator;


        private static final int SLEEP_TIME = 5000;

        /**
         * MonitorAgent methods.
         */
        private void initialize () {
            this.sourceAdministrator = new IncomingIntraUserCryptoSourceAdministrator(this.incomingCryptoManager);
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
            EventWrapper eventWrapper = null;
            try {
                eventWrapper     = this.registry.getNextCryptoPendingEvent();
                while(eventWrapper != null) {
                    processEvent(eventWrapper);
                    eventWrapper = this.registry.getNextCryptoPendingEvent();
                }
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }

        private void processEvent(EventWrapper eventWrapper) {
            // We have here new pending transactions, we will check the source and ask for the right
            // TransactionSender

            TransactionProtocolManager<CryptoTransaction> source = null;
            try {
                source = this.sourceAdministrator.getSourceAdministrator(EventSource.getByCode(eventWrapper.getEventSource()));
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            }

            // Now we ask for the pending transactions
            try {
                List<Transaction<CryptoTransaction>> transactionList = source.getPendingTransactions(Specialist.EXTRA_USER_SPECIALIST);
                System.out.println("TTF - INTRA USER MONITOR: " + transactionList.size() + " TRAMSACTION(s) DETECTED");
                // Now we save the list in the registry
                this.registry.acknowledgeTransactions(transactionList);
                System.out.println("TTF - INTRA USER MONITOR: " + transactionList.size() + " TRAMSACTION(s) ACKNOWLEDGED");
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            }

            // Now we take all the transactions in state (ACKNOWLEDGE,TO_BE_NOTIFIED)
            // Remember that this list can be more extensive than the one we saved, this is
            // because the system could have shut down in this step of the protocol making old
            // transactions to be stored but not precessed.
            List<Transaction<CryptoTransaction>> acknowledgedTransactions = null;
            try {
                acknowledgedTransactions = this.registry.getAcknowledgedTransactions();
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            }


            // An finally, for each transaction we confirm it and then register responsibility.
            for(Transaction<CryptoTransaction> transaction : acknowledgedTransactions){
                try {
                    source.confirmReception(transaction.getTransactionID());
                    System.out.println("TTF - INTRA USER MONITOR: TRANSACTION RESPONSIBILITY ACQUIRED");
                    registry.acquireResponsibility(transaction);
                } catch (CantConfirmTransactionException | IncomingIntraUserCantAcquireResponsibilityException exception) {
                    // TODO: Consultar si esto hace lo que pienso, si falla no registra en base de datos
                    //       la transacción
                    // We will inform the exception and try again in the next round
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                } catch (Exception e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
            // After finishing all the steps we mark the event as seen.
            try {
                registry.disableEvent(eventWrapper.getEventId());
                System.out.println("TTF - INTRA USER MONITOR: EVENT DISABLED");
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