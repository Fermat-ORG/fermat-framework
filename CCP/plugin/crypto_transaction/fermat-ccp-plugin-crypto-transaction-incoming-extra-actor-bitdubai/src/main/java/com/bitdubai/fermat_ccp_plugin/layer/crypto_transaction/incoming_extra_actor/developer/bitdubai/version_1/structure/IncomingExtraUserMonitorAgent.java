package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure;

/**
 * Created by ciencias on 3/30/15.
 * Modified by Arturo Vallone 25/04/2015
 */


import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantAcknowledgeTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantAcquireResponsibilityException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantSaveEventException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantStartAgentException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.interfaces.DealsWithRegistry;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.interfaces.TransactionAgent;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.util.SourceAdministrator;
import com.bitdubai.fermat_bch_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Este agente corre en su propio Thread.
 *
 * Se despierta cada unos segundo a ver si se han registrado eventos de incoming crypto.
 *
 * Si se han registrado, entonces se activa y procede a ir a buscar al plugin que corresponda la transaccion entrante.
 *
 * Si no se han registrado, igual cada cierto tiempo va y verifica contra la lista de plugins que pueden recibir incoming crypto.
 *
 * Cuando hace la verificacion contra un plugin, registra la transaccion en su base de datos propia y le confirma al plugin la recepcion.
 *
 *
 * * * * * * * * * * * * * * * * * * * * * * * 
 */


public class IncomingExtraUserMonitorAgent implements DealsWithRegistry, TransactionAgent {

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithIncomingCrypto Interface member variables.
     */
    private IncomingCryptoManager incomingCryptoManager;

    /**
     * DealsWithRegistry Interface member variables.
     */
    private IncomingExtraUserRegistry registry;


    /**
     * TransactionAgent Member Variables.
     */
    private Thread agentThread;
    private MonitorAgent monitorAgent;


    public IncomingExtraUserMonitorAgent(final ErrorManager errorManager, final IncomingCryptoManager incomingCryptoManager, final IncomingExtraUserRegistry registry){
        this.errorManager = errorManager;
        this.incomingCryptoManager = incomingCryptoManager;
        this.registry = registry;
    }

    /**
     *DealsWithRegistry Interface implementation.
     */
    @Override
    public void setRegistry(IncomingExtraUserRegistry registry) {
        this.registry = registry;
    }


    /**
     * TransactionAgent Interface implementation.
     */
    @Override
    public void start() throws CantStartAgentException {



        this.monitorAgent = new MonitorAgent (errorManager, incomingCryptoManager);
        try {
            this.monitorAgent.setRegistry(this.registry);
            this.monitorAgent.initialize();

            this.agentThread = new Thread(this.monitorAgent);
            this.agentThread.start();
        }
        catch (Exception exception) {
            throw new CantStartAgentException(CantStartAgentException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "You should inspect the cause");
        }

    }

    public void stop(){
        this.monitorAgent.stop();
    }

    public boolean isRunning(){
        return this.monitorAgent.isRunning();
    }




    private static class MonitorAgent implements DealsWithRegistry, Runnable  {

        private AtomicBoolean running = new AtomicBoolean(false);

        public boolean isRunning(){
            return running.get();
        }

        public void stop(){
            running.set(false);
        }
        /**
         * DealsWithCryptoVault Interface member variables.
         */
        private IncomingCryptoManager incomingCryptoManager;

        /**
         * DealsWithErrors Interface member variables.
         */
        private ErrorManager errorManager;

        public MonitorAgent(ErrorManager errorManager, IncomingCryptoManager incomingCryptoManager) {
            this.errorManager = errorManager;
            this.incomingCryptoManager = incomingCryptoManager;
        }

        /**
         * DealsWithRegistry Interface member variables.
         */
        private IncomingExtraUserRegistry registry;


        /*
         * MonitorAgent member variables
         */
        private SourceAdministrator sourceAdministrator;



        private static final int SLEEP_TIME = 10000;

        /**
         *DealsWithRegistry Interface implementation.
         */
        @Override
        public void setRegistry(IncomingExtraUserRegistry registry){
            this.registry = registry;
        }

        /**
         * MonitorAgent methods.
         */
        private void initialize () {
            this.sourceAdministrator = new SourceAdministrator(incomingCryptoManager);
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
            IncomingExtraUserRegistry.EventWrapper eventWrapper = null;
            try {
                // TODO (lnacosta) change this for processing all pending events and not to wait until the next agent wake up
                 eventWrapper     = this.registry.getNextPendingEvent();
                while(thisIsAPendingEvent(eventWrapper)) {
                    processEvent(eventWrapper);
                    eventWrapper = this.registry.getNextPendingEvent();
                }
            } catch (com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantReadEventException cantReadEvent) {
                // we can report the exception and try again in next call.
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantReadEvent);
                return;
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            }
        }

        private boolean thisIsAPendingEvent(IncomingExtraUserRegistry.EventWrapper eventWrapper){
            return eventWrapper != null;
        }

        private void processEvent(IncomingExtraUserRegistry.EventWrapper eventWrapper) {
            System.out.println("TTF - EXTRA USER MONITOR: NEW EVENT DETECTED");
            // We have here new pending transactions, we will check the source and ask for the right
            // TransactionSender

            TransactionProtocolManager<CryptoTransaction> source = null;
            try {
                source = this.sourceAdministrator.getSourceAdministrator(EventSource.getByCode(eventWrapper.eventSource));
            } catch (InvalidParameterException | com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.SourceNotRecognizedException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            }

            // Now we ask for the pending transactions
            try {
                List<Transaction<CryptoTransaction>> transactionList = source.getPendingTransactions(Specialist.EXTRA_USER_SPECIALIST);
                System.out.println("TTF - EXTRA USER MONITOR: " + transactionList.size() + " TRAMSACTION(s) DETECTED");
                // Now we save the list in the registry
                this.registry.acknowledgeTransactions(transactionList);
                System.out.println("TTF - EXTRA USER MONITOR: " + transactionList.size() + " TRAMSACTION(s) ACKNOWLEDGED");
            } catch (CantDeliverPendingTransactionsException | CantAcknowledgeTransactionException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
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
            } catch (InvalidParameterException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            }


            // An finally, for each transaction we confirm it and then register responsibility.
            for(Transaction<CryptoTransaction> transaction : acknowledgedTransactions){
                try {
                    source.confirmReception(transaction.getTransactionID());
                    System.out.println("TTF - EXTRA USER MONITOR: TRANSACTION RESPONSIBILITY ACQUIRED");
                    registry.acquireResponsibility(transaction);
                } catch (CantConfirmTransactionException | CantAcquireResponsibilityException exception) {
                    // TODO: Consultar si esto hace lo que pienso, si falla no registra en base de datos
                    //       la transacción
                    // We will inform the exception and try again in the next round
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                } catch (Exception e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    return;
                }
            }
            // After finishing all the steps we mark the event as seen.
            try {
                registry.disableEvent(eventWrapper.eventId);
                System.out.println("TTF - EXTRA USER MONITOR: EVENT DISABLED");
            } catch (com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantReadEventException | CantSaveEventException exception) { // There are two exceptions and we react in the same way to both
                // We will inform the exception and try again in the next round
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            }
        }
        
        private void cleanResources() {
            
            /**
             * Disconnect from database and explicitly set all references to null.
             */
            
        }
    }
}
