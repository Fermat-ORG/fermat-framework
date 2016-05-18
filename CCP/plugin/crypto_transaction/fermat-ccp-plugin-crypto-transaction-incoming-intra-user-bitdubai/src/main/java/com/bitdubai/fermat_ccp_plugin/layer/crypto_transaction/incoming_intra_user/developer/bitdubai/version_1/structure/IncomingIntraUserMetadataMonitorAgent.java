package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.FermatCryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CantSetToSeenByCryptoVaultException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.EventWrapper;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.IncomingIntraUserMetadataSourceAdministrator;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The class <code>IncomingIntraUserMetadataMonitorAgent</code>
 * is responsible of taking the responsibility of the transactions metadata sent by the Crypto Transmission Network Service plugin.
 */
public class IncomingIntraUserMetadataMonitorAgent {

    private ErrorManager                            errorManager;
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;
    private IncomingIntraUserRegistry               registry;


    /**
     * TransactionAgent Member Variables.
     */
    private Thread agentThread;
    private MonitorAgent monitorAgent;


    public IncomingIntraUserMetadataMonitorAgent(final ErrorManager errorManager, final CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager, final IncomingIntraUserRegistry registry){
        this.errorManager                            = errorManager;
        this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
        this.registry                                = registry;
    }

    public void start() throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIntraUserCryptoMonitorAgentException {
        this.monitorAgent = new MonitorAgent(cryptoTransmissionNetworkServiceManager,errorManager,registry);
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

        private ErrorManager                            errorManager;
        private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;
        private IncomingIntraUserRegistry               registry;


        public MonitorAgent(CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager, ErrorManager errorManager , IncomingIntraUserRegistry incomingIntraUserRegistry){
            this.errorManager                            = errorManager;
            this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
            this.registry                                = incomingIntraUserRegistry;
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
        private IncomingIntraUserMetadataSourceAdministrator sourceAdministrator;


        private static final int SLEEP_TIME = 10000;

        /**
         * MonitorAgent methods.
         */
        private void initialize () {
            this.sourceAdministrator = new IncomingIntraUserMetadataSourceAdministrator(this.cryptoTransmissionNetworkServiceManager);
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
                eventWrapper     = this.registry.getNextMetadataPendingEvent();
                while(thisIsAPendingEvent(eventWrapper)) {
                    processEvent(eventWrapper);
                    eventWrapper = this.registry.getNextMetadataPendingEvent();
                }
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }

        private boolean thisIsAPendingEvent(EventWrapper eventWrapper){
            return eventWrapper != null;
        }

        private void processEvent(EventWrapper eventWrapper) {
            try {
                TransactionProtocolManager<FermatCryptoTransaction> source = this.sourceAdministrator.getSourceAdministrator(EventSource.getByCode(eventWrapper.getEventSource()));
                List<Transaction<FermatCryptoTransaction>> transactionList = source.getPendingTransactions(Specialist.INTRA_USER_SPECIALIST);

                System.out.println("TTF - INTRA USER METADATA MONITOR: " + transactionList.size() + " TRANSACTION(s) DETECTED");

                this.registry.acknowledgeFermatCryptoTransactions(transactionList);

                System.out.println("TTF - INTRA USER METADATA MONITOR: " + transactionList.size() + " TRANSACTION(s) ACKNOWLEDGED");

                // Now we take all the transactions in state (ACKNOWLEDGE,TO_BE_NOTIFIED)
                // Remember that this list can be more extensive than the one we saved, this is
                // because the system could have shut down in this step of the protocol making old
                // transactions to be stored but not precessed.
                List<Transaction<FermatCryptoTransaction>> acknowledgedTransactions = this.registry.getAcknowledgedFermatCryptoTransactions();

                for(Transaction<FermatCryptoTransaction> transaction : acknowledgedTransactions){
                    try {
                        source.confirmReception(transaction.getTransactionID());
                        System.out.println("TTF - INTRA USER MONITOR METADATA: TRANSACTION RESPONSIBILITY ACQUIRED");
                        registry.acquireFermatCryptoTransactionResponsibility(transaction);

                        //notified Transmission NS that transaction Seen By Vault
                       cryptoTransmissionNetworkServiceManager.informTransactionSeenByVault(transaction.getTransactionID());

                    } catch (CantConfirmTransactionException | com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAcquireResponsibilityException exception) {
                        // TODO: Consultar si esto hace lo que pienso, si falla no registra en base de datos
                        //       la transacción
                        // We will inform the exception and try again in the next round
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                    }
                   catch(CantSetToSeenByCryptoVaultException e){
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

                    }
                }



                registry.disableEvent(eventWrapper.getEventId());
                System.out.println("TTF - INTRA USER METADATA MONITOR: EVENT DISABLED");
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
