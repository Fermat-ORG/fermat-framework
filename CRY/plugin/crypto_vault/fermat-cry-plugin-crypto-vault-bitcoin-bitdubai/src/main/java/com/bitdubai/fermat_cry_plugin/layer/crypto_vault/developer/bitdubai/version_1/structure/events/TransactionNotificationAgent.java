package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantInitializeMonitorAgentException;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cry_api.layer.definition.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultTransactionNotificationAgent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.TransactionProtocolAgentMaxIterationsReachedException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseFactory;

import java.util.UUID;

/**
 * Created by rodrigo on 2015.06.18..
 * Modified by lnacosta (laion.cj91@gmail.com) on 15/10/2015.
 */
public class TransactionNotificationAgent implements Agent {

    /**
     * TransactionNotificationAgent variables
     */
    Database database;

    /**
     * Agent interface member variables
     */
    Thread agentThread;
    MonitorAgent monitorAgent;

    private final LogManager           logManager          ;
    private final EventManager         eventManager        ;
    private final ErrorManager         errorManager        ;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;
    private final String               userPublicKey       ;

    /**
     * Constructor with final params...
     */
    public TransactionNotificationAgent(final EventManager         eventManager        ,
                                        final ErrorManager         errorManager        ,
                                        final LogManager           logManager          ,
                                        final PluginDatabaseSystem pluginDatabaseSystem,
                                        final UUID                 pluginId            ,
                                        final String               userPublicKey       ){

        this.eventManager         = eventManager        ;
        this.errorManager         = errorManager        ;
        this.logManager           = logManager          ;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.userPublicKey        = userPublicKey       ;
    }

    /**
     * Agent interface implementation
     * @throws CantStartAgentException
     */
    @Override
    public void start() throws CantStartAgentException {
        /**
         * I initialize the monitor agent private class that will run in a new thread
         */
        monitorAgent = new MonitorAgent(
                errorManager,
                pluginDatabaseSystem
        );

        try {
            this.monitorAgent.Initialize();
        } catch (CantInitializeMonitorAgentException cantInitializeCryptoRegistryException) {

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantInitializeCryptoRegistryException);
        }

        /**
         * I start the thread that will get from the DB the confirmation
         * that there are or not pending transactions to notify
         */
        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    /**
     * Private class which implements runnable and is started by the Agent
     */
    private class MonitorAgent implements CryptoVaultTransactionNotificationAgent, Runnable {

        /**
         * how often I will search for transactions to notify
         */
        public final int SLEEP_TIME = CryptoVaultTransactionNotificationAgent.AGENT_SLEEP_TIME;
        int iteration = 0;

        private final ErrorManager         errorManager        ;
        private final PluginDatabaseSystem pluginDatabaseSystem;

        public MonitorAgent(final ErrorManager         errorManager        ,
                            final PluginDatabaseSystem pluginDatabaseSystem) {

            this.errorManager         = errorManager        ;
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        public void Initialize() throws CantInitializeMonitorAgentException {

            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId, userPublicKey);
            } catch (DatabaseNotFoundException databaseNotFoundException) {

                CryptoVaultDatabaseFactory databaseFactory = new CryptoVaultDatabaseFactory();
                databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

                try {

                    database = databaseFactory.createDatabase(pluginId, userPublicKey);

                } catch (CantCreateDatabaseException cantCreateDatabaseException) {

                    throw new CantInitializeMonitorAgentException();

                }
            } catch (CantOpenDatabaseException cantOpenDatabaseException) {

                throw new CantInitializeMonitorAgentException();
            }
        }

        @Override
        public void run() {
            /**
             * this will run in an infinite loop
             */
            logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "Transaction Protocol Notification Agent: running...", null, null);

            while (true)
            {
                /**
                 * Increase the iteration counter
                 */
                iteration++;
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    return;
                }

                /**
                 * now I will check if there are pending transactions to raise the event
                 */
                try {

                    logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iteration, null, null);
                    doTheMainTask();
                } catch (CantExecuteQueryException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
               } catch (FermatException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }

        }
        /**
         * Implements the agent
         */
        private void doTheMainTask() throws CantExecuteQueryException, TransactionProtocolAgentMaxIterationsReachedException {
            boolean found = false;
            /**
             * I search for transactions not yet notified. If I found something, Ill raise an event
             */
            CryptoVaultDatabaseActions db = new CryptoVaultDatabaseActions(database, eventManager);

            /**
             * If I found transactions on Crypto_Statuts  ON_CryptoNetwork and Protocol_Status PENDING_NOTIFIED, lanzo el evento
             */
            if (isTransactionToBeNotified(CryptoStatus.ON_CRYPTO_NETWORK)){
                found = true;
                FermatEvent event = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK);
                event.setSource(EventSource.CRYPTO_VAULT);


                logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "Found transactions pending to be notified in ON_CRYPTO_NETWORK Status! Raising INCOMING_CRYPTO_ON_CRYPTO_NETWORK event.", null, null);
                eventManager.raiseEvent(event);


                logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "No other plugin is consuming Vault transactions.", "Transaction Protocol Notification Agent: iteration number " + this.iteration+ " without other plugins consuming transaction.",null);
                if (ITERATIONS_THRESHOLD < this.iteration){
                    throw new TransactionProtocolAgentMaxIterationsReachedException("The max limit configured for the Transaction Protocol Agent has been reached.", null,"Iteration Limit: " + ITERATIONS_THRESHOLD, "Notify developer.");
                }
            }

            /**
             * If I found transactions on Crypto_Statuts  ON_BLOCKCHAIN and Protocol_Status PENDING_NOTIFIED, lanzo el evento
             */
            if (isTransactionToBeNotified(CryptoStatus.ON_BLOCKCHAIN)){
                found = true;
                FermatEvent event = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN);
                event.setSource(EventSource.CRYPTO_VAULT);

                logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "Found transactions pending to be notified in ON_BLOCKCHAIN Status! Raising INCOMING_CRYPTO_ON_BLOCKCHAIN event.", null, null);
                eventManager.raiseEvent(event);



                logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "No other plugin is consuming Vault transactions.", "Transaction Protocol Notification Agent: iteration number " + iteration + " without other plugins consuming transaction.",null);
                if (ITERATIONS_THRESHOLD < this.iteration){
                    throw new TransactionProtocolAgentMaxIterationsReachedException("The max limit configured for the Transaction Protocol Agent has been reached.", null,"Iteration Limit: " + ITERATIONS_THRESHOLD, "Notify developer.");
                }
            }

            /**
             * If I found transactions on Crypto_Statuts  REVERSED_ON_CryptoNetwork and Protocol_Status PENDING_NOTIFIED, lanzo el evento
             */
            if (isTransactionToBeNotified(CryptoStatus.REVERSED_ON_CRYPTO_NETWORK)){
                found = true;
                FermatEvent event = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK);
                event.setSource(EventSource.CRYPTO_VAULT);

                logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "Found transactions pending to be notified in REVERSED_ON_CRYPTO_NETWORK Status! Raising INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK event.", null, null);
                eventManager.raiseEvent(event);


                logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "No other plugin is consuming Vault transactions.", "Transaction Protocol Notification Agent: iteration number " + iteration + " without other plugins consuming transaction.",null);
                if (ITERATIONS_THRESHOLD < this.iteration){
                    throw new TransactionProtocolAgentMaxIterationsReachedException("The max limit configured for the Transaction Protocol Agent has been reached.", null,"Iteration Limit: " + ITERATIONS_THRESHOLD, "Notify developer.");
                }
            }

            /**
             * If I found transactions on Crypto_Statuts  REVERSED_ON_BLOCKCHAIN and Protocol_Status PENDING_NOTIFIED, lanzo el evento
             */
            if (isTransactionToBeNotified(CryptoStatus.REVERSED_ON_BLOCKCHAIN)){
                found = true;
                FermatEvent event = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN);
                event.setSource(EventSource.CRYPTO_VAULT);

                logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "Found transactions pending to be notified in REVERSED_ON_BLOCKCHAIN Status! Raising INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN event.", null, null);
                eventManager.raiseEvent(event);


                logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "No other plugin is consuming Vault transactions.", "Transaction Protocol Notification Agent: iteration number " + iteration + " without other plugins consuming transaction.",null);
                if (ITERATIONS_THRESHOLD < this.iteration){
                    throw new TransactionProtocolAgentMaxIterationsReachedException("The max limit configured for the Transaction Protocol Agent has been reached.", null,"Iteration Limit: " + ITERATIONS_THRESHOLD, "Notify developer.");
                }
            }
            /**
            * there are no transactions pending. I will reset the counter to 0.
            */
            if (!found){
                db.updateTransactionProtocolStatus(false);
                this.iteration = 0;
            } else {
                 this.iteration = db.updateTransactionProtocolStatus(true);
            }

        }

        private boolean isTransactionToBeNotified(final CryptoStatus cryptoStatus) throws CantExecuteQueryException {

            CryptoVaultDatabaseActions db = new CryptoVaultDatabaseActions(database, eventManager);
            return db.isPendingTransactions(cryptoStatus);
        }

    }
}
