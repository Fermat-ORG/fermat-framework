package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.agent.CBPTransactionAgent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CannotSendContractHashException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.events.NewContractOpened;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantupdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantupdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions.CantSendBusinessTransactionHashException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions.CantSendContractNewStatusNotificationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.CustomerOnlinePaymentPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.IntraActorCryptoTransactionManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class CustomerOnlinePaymentMonitorAgent implements
        CBPTransactionAgent,
        DealsWithLogger,
        DealsWithEvents,
        DealsWithErrors,
        DealsWithPluginDatabaseSystem,
        DealsWithPluginIdentity {

    Database database;
    MonitorAgent monitorAgent;
    Thread agentThread;
    LogManager logManager;
    EventManager eventManager;
    ErrorManager errorManager;
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;
    TransactionTransmissionManager transactionTransmissionManager;
    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    IntraActorCryptoTransactionManager intraActorCryptoTransactionManager;

    public CustomerOnlinePaymentMonitorAgent(PluginDatabaseSystem pluginDatabaseSystem,
                                    LogManager logManager,
                                    ErrorManager errorManager,
                                    EventManager eventManager,
                                    UUID pluginId,
                                    TransactionTransmissionManager transactionTransmissionManager,
                                    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
                                    OutgoingIntraActorManager outgoingIntraActorManager) throws CantSetObjectException {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.logManager=logManager;
        this.transactionTransmissionManager=transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager=customerBrokerContractPurchaseManager;
        setIntraActorCryptoTransactionManager(outgoingIntraActorManager);
    }

    private void setIntraActorCryptoTransactionManager(
            OutgoingIntraActorManager outgoingIntraActorManager) throws CantSetObjectException {
        if(outgoingIntraActorManager==null){
            throw new CantSetObjectException("outgoingIntraActorManager is null");
        }
        try {
            this.intraActorCryptoTransactionManager=outgoingIntraActorManager.getTransactionManager();
        } catch (CantGetOutgoingIntraActorTransactionManagerException e) {
            throw new CantSetObjectException(
                    e,
                    "Setting the intraActorCryptoTransactionManager",
                    "Cannot get the intraActorCryptoTransactionManager from outgoingIntraActorManager");
        }
    }

    @Override
    public void start() throws CantStartAgentException {

        Logger LOG = Logger.getGlobal();
        LOG.info("Open contract monitor agent starting");
        monitorAgent = new MonitorAgent();

        ((DealsWithPluginDatabaseSystem) this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        ((DealsWithErrors) this.monitorAgent).setErrorManager(this.errorManager);

        try {
            ((MonitorAgent) this.monitorAgent).Initialize();
        } catch (CantInitializeCBPAgent exception) {
            errorManager.reportUnexpectedPluginException(Plugins.OPEN_CONTRACT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }

        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();

    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager=errorManager;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager=eventManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager=logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem=pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId=pluginId;
    }

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
    private class MonitorAgent implements DealsWithPluginDatabaseSystem, DealsWithErrors, Runnable{

        ErrorManager errorManager;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = 5000;
        int iterationNumber = 0;
        CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;
        boolean threadWorking;

        @Override
        public void setErrorManager(ErrorManager errorManager) {
            this.errorManager = errorManager;
        }

        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }
        @Override
        public void run() {

            threadWorking=true;
            logManager.log(CustomerOnlinePaymentPluginRoot.getLogLevelByClass(this.getClass().getName()),
                    "Customer Online Payment Monitor Agent: running...", null, null);
            while(threadWorking){
                /**
                 * Increase the iteration counter
                 */
                iterationNumber++;
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    return;
                }

                /**
                 * now I will check if there are pending transactions to raise the event
                 */
                try {

                    logManager.log(CustomerOnlinePaymentPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CannotSendContractHashException | CantUpdateRecordException | CantSendContractNewStatusNotificationException e) {
                    errorManager.reportUnexpectedPluginException(
                            Plugins.CUSTOMER_ONLINE_PAYMENT,
                            UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                            e);
                }

            }

        }
        public void Initialize() throws CantInitializeCBPAgent {
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId,
                        CustomerOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME);
            }
            catch (DatabaseNotFoundException databaseNotFoundException) {

                Logger LOG = Logger.getGlobal();
                LOG.info("Database in Open Contract monitor agent doesn't exists");
                CustomerOnlinePaymentBusinessTransactionDatabaseFactory CustomerOnlinePaymentBusinessTransactionDatabaseFactory=
                        new CustomerOnlinePaymentBusinessTransactionDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = CustomerOnlinePaymentBusinessTransactionDatabaseFactory.createDatabase(pluginId,
                            CustomerOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    errorManager.reportUnexpectedPluginException(
                            Plugins.CUSTOMER_ONLINE_PAYMENT,
                            UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                            cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException,
                            "Initialize Monitor Agent - trying to create the plugin database",
                            "Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                errorManager.reportUnexpectedPluginException(
                        Plugins.CUSTOMER_ONLINE_PAYMENT,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        exception);
                throw new CantInitializeCBPAgent(exception,
                        "Initialize Monitor Agent - trying to open the plugin database",
                        "Please, check the cause");
            }
        }

        private void doTheMainTask() throws
                CannotSendContractHashException,
                CantUpdateRecordException,
                CantSendContractNewStatusNotificationException {

            try{
                customerOnlinePaymentBusinessTransactionDao =new CustomerOnlinePaymentBusinessTransactionDao(
                        pluginDatabaseSystem,
                        pluginId,
                        database);

                UUID outgoingCryptoTransactionId;
                CustomerOnlinePaymentRecord customerOnlinePaymentRecord;

                /**
                 * Check if there is some crypto to send
                 */
                List<String> pendingToSubmitCrypto=customerOnlinePaymentBusinessTransactionDao.getPendingToSubmitCryptoList();
                for(String constractHash : pendingToSubmitCrypto){
                    customerOnlinePaymentRecord=customerOnlinePaymentBusinessTransactionDao.
                            getCustomerOnlinePaymentRecord(constractHash);
                    /*outgoingCryptoTransactionId=intraActorCryptoTransactionManager.sendCrypto(
                            customerOnlinePaymentRecord.getWalletPublicKey(),
                            customerOnlinePaymentRecord.getCryptoAddress(),
                    );*/
                }
                //TODO: finish this

                /**
                 * Check if pending events
                 */
                List<String> pendingEventsIdList= customerOnlinePaymentBusinessTransactionDao.getPendingEvents();
                for(String eventId : pendingEventsIdList){
                    checkPendingEvent(eventId);
                }


            } catch (CantGetContractListException e) {
                throw new CannotSendContractHashException(
                        e,
                        "Sending contract hash",
                        "Cannot get the contract list from database");
            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                throw new CannotSendContractHashException(
                        e,
                        "Sending contract hash",
                        "Unexpected result in database");
            }  /*catch (CantSendBusinessTransactionHashException e) {
                throw new CannotSendContractHashException(
                        e,
                        "Sending contract hash",
                        "Error in Transaction Transmission Network Service");
            }
            catch (CantSendContractNewStatusNotificationException e) {
                throw new CantSendContractNewStatusNotificationException(
                        CantSendContractNewStatusNotificationException.DEFAULT_MESSAGE,
                        e,
                        "Sending contract hash",
                        "Error in Transaction Transmission Network Service");
            }*/

        }

        private void raiseNewContractEvent(){
            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.NEW_CONTRACT_OPENED);
            NewContractOpened newContractOpened = (NewContractOpened) fermatEvent;
            newContractOpened.setSource(EventSource.BUSINESS_TRANSACTION_OPEN_CONTRACT);
            eventManager.raiseEvent(newContractOpened);
        }

        private void checkPendingEvent(String eventId) throws  UnexpectedResultReturnedFromDatabaseException {
            /*try{

                //TODO: look a better way to deal with this exceptions
            } catch (CantDeliverPendingTransactionsException e) {
                e.printStackTrace();
            } catch (CantUpdateRecordException e) {
                e.printStackTrace();
            } catch (CantupdateCustomerBrokerContractPurchaseException e) {
                e.printStackTrace();
            } catch (CantConfirmTransactionException e) {
                e.printStackTrace();
            } catch (CantupdateCustomerBrokerContractSaleException e) {
                e.printStackTrace();
            }*/

        }

    }

}

