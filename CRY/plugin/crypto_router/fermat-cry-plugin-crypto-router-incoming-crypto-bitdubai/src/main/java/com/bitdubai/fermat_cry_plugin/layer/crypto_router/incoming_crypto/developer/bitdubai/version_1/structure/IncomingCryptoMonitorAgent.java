package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure;

/**
 * Created by ciencias on 3/30/15.
 * Modified by Arturo Vallone 25/04/2015
 */


import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.interfaces.TransactionAgent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartAgentException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.SourceAdministrator;

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


public class IncomingCryptoMonitorAgent implements DealsWithErrors, TransactionAgent {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * IncomingCryptoMonitorAgent member variables.
     */
    //private UUID pluginId;

    //TODO: Uncomment
    //private IncomingCryptoRegistry registry;
    //private SourceAdministrator sourceAdministrator = new SourceAdministrator();
    
    /**
     * TransactionAgent Member Variables.
     */
    private Thread agentThread;
    private MonitorAgent monitorAgent;

    /**
     *DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * IncomingCryptoMonitorAgent methods.

    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }*/


     //TODO: uncomment
      public void setRegistry(IncomingCryptoRegistry registry) {
      //  this.registry = registry;
    }


    /**
     * TransactionAgent Interface implementation.
     */
    @Override
    public void start() throws CantStartAgentException {



        this.monitorAgent = new MonitorAgent ();
        try {
            // TODO: Descomentar al tener la referencia a la vault
            //this.monitorAgent.initialize(this.registry, this.sourceAdministrator);
            this.monitorAgent.setErrorManager(this.errorManager);

            this.agentThread = new Thread(this.monitorAgent);
            this.agentThread.start();
        }
        catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantStartAgentException();
        }

    }

    @Override
    public void stop() {
        
        this.agentThread.interrupt();
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private static class MonitorAgent implements DealsWithErrors, Runnable  {

        /**
         * DealsWithErrors Interface member variables.
         */
        private ErrorManager errorManager;

        /**
         * MonitorAgent member variables.
         */
        // private UUID pluginId;

        //private IncomingCryptoRegistry registry;
        //private SourceAdministrator sourceAdministrator;

        private static final int SLEEP_TIME = 5000;

        /**
         *DealsWithErrors Interface implementation.
         */
        @Override
        public void setErrorManager(ErrorManager errorManager) {
            this.errorManager = errorManager;
        }


        /**
         * MonitorAgent methods.
         */
        private void initialize (IncomingCryptoRegistry registry, SourceAdministrator sourceAdministrator) {
            //this.pluginId = pluginId;
            //this.registry = registry;
            //this.sourceAdministrator = sourceAdministrator;
        }

        /**
         * Runnable Interface implementation.
         */
        @Override
        public void run() {

            /**
             * Infinite loop.
             */
            while (true) {

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
          // TODO: Borrar cuando tenga la referencia a la Bault...
          errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception());

          /*
                TODO: We need the reference to crypto Vault in the SourceAdministrator class before uncommenting this

            IncomingCryptoRegistry.EventWrapper eventWrapper = null;
            try {
                eventWrapper = this.registry.getNextPendingEvent();
            } catch (CantReadEvent cantReadEvent) {
                // we can report the exception and try again in next call.
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantReadEvent);
            }
            if(eventWrapper != null){
                // We have here new pending transactions, we will check the source and ask for the right
                // TransactionSender



                TransactionSender<CryptoTransaction> source = this.sourceAdministrator.getSourceAdministrator(eventWrapper.eventSource);

                // Now we ask for the pending transactions
                List<Transaction<CryptoTransaction>> transactionList = null;
                try {
                    transactionList = source.getPendingTransactions(Specialist.CRYPTO_ROUTER);
                } catch (CantDeliverPendingTransactionsException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    //if somethig wrong happenned we try in the next round
                    return;
                }

                // Now we save the list in the registry
                if(transactionList != null){
                    this.registry.acknowledgeTransactions(transactionList);
                } else {
                  // if sombething failed we try in next round
                  return;
                }

                // Now we take all the transactions in state (ACKNOWLEDGE,TO_BE_NOTIFIED)
                // Remember that this list can be more extensive than the one we saved, this is
                // because the system could have shut down in this step of the protocol making old
                // transactions to be stored but not precessed.
                List<Transaction<CryptoTransaction>> acknowledgedTransactions = this.registry.getAcknowledgedTransactions();


                // An finally, for each transaction we confirm it and then register responsibility.
                for(Transaction<CryptoTransaction> transaction : acknowledgedTransactions){
                    try {
                        source.confirmTransaction(transaction.getTransactionID());
                        this.registry.acquireResponsibility(transaction);
                    } catch (CantConfirmTransactionException e) {
                        // TODO: Consultar si esto hace lo que pienso, si falla no registra en base de datos
                        //       la transacción
                        // We will inform the exception and try again in the next round
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    }
                }
                // After finishing all the steps we mark the event as seen.
                try {
                    this.registry.disableEvent(eventWrapper.eventId);
                } catch (Exception e) { // There are two exceptions and we react in the same way to both
                    // We will inform the exception and try again in the next round
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }*/
        }

        
        private void cleanResources() {
            
            /**
             * Disconnect from database and explicitly set all references to null.
             */
            
        }
    }
}
