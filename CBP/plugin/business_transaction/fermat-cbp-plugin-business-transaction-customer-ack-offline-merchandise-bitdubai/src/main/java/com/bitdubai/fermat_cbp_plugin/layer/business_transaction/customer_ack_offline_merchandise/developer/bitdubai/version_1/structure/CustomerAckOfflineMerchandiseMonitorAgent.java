package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.agent.CBPTransactionAgent;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.PaymentType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.events.CustomerAckMerchandiseConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CannotSendContractHashException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantUpdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantConfirmNotificationReceptionException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.CustomerAckOfflineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.database.CustomerAckOfflineMerchandiseBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.database.CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.database.CustomerAckOfflineMerchandiseBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/12/15.
 */
public class CustomerAckOfflineMerchandiseMonitorAgent implements
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
    CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    public CustomerAckOfflineMerchandiseMonitorAgent(
            PluginDatabaseSystem pluginDatabaseSystem,
            LogManager logManager,
            ErrorManager errorManager,
            EventManager eventManager,
            UUID pluginId,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager)  {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.logManager=logManager;
        this.transactionTransmissionManager=transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager=customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager=customerBrokerContractSaleManager;
    }

    @Override
    public void start() throws CantStartAgentException {

        //Logger LOG = Logger.getGlobal();
        //LOG.info("Customer online payment monitor agent starting");
        monitorAgent = new MonitorAgent();

        this.monitorAgent.setPluginDatabaseSystem(this.pluginDatabaseSystem);
        this.monitorAgent.setErrorManager(this.errorManager);

        try {
            this.monitorAgent.Initialize();
        } catch (CantInitializeCBPAgent exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_OFFLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
        }catch (Exception exception){
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_OFFLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    FermatException.wrapException(exception));
        }

        this.agentThread = new Thread(monitorAgent,this.getClass().getSimpleName());
        this.agentThread.start();

    }

    @Override
    public void stop() {
        try{
            this.agentThread.interrupt();
        }catch(Exception exception){
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_OFFLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
        }
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
        CustomerAckOfflineMerchandiseBusinessTransactionDao customerAckOfflineMerchandiseBusinessTransactionDao;
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
            logManager.log(CustomerAckOfflineMerchandisePluginRoot.getLogLevelByClass(this.getClass().getName()),
                    "Customer Ack Offline Merchandise Monitor Agent: running...", null, null);
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

                    logManager.log(CustomerAckOfflineMerchandisePluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CannotSendContractHashException | CantUpdateRecordException | CantSendContractNewStatusNotificationException e) {
                    errorManager.reportUnexpectedPluginException(
                            Plugins.CUSTOMER_ACK_OFFLINE_MERCHANDISE,
                            UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                            e);
                }

            }

        }
        public void Initialize() throws CantInitializeCBPAgent {
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId,
                        CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME);
            }
            catch (DatabaseNotFoundException databaseNotFoundException) {

                //Logger LOG = Logger.getGlobal();
                //LOG.info("Database in Open Contract monitor agent doesn't exists");
                CustomerAckOfflineMerchandiseBusinessTransactionDatabaseFactory customerAckOfflineMerchandiseBusinessTransactionDatabaseFactory=
                        new CustomerAckOfflineMerchandiseBusinessTransactionDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = customerAckOfflineMerchandiseBusinessTransactionDatabaseFactory.createDatabase(pluginId,
                            CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    errorManager.reportUnexpectedPluginException(
                            Plugins.CUSTOMER_ACK_OFFLINE_MERCHANDISE,
                            UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                            cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException,
                            "Initialize Monitor Agent - trying to create the plugin database",
                            "Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                errorManager.reportUnexpectedPluginException(
                        Plugins.CUSTOMER_ACK_OFFLINE_MERCHANDISE,
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
                customerAckOfflineMerchandiseBusinessTransactionDao =new CustomerAckOfflineMerchandiseBusinessTransactionDao(
                        pluginDatabaseSystem,
                        pluginId,
                        database,
                        errorManager);

                String contractHash;

                /**
                 * Check contract status to send. - Customer Side
                 * The status to verify is PENDING_SUBMIT_OFFLINE_MERCHANDISE_NOTIFICATION, it represents that the merchandise is
                 * acknowledge by the customer.
                 */
                List<BusinessTransactionRecord> pendingToSubmitNotificationList = customerAckOfflineMerchandiseBusinessTransactionDao.getPendingToSubmitNotificationList();
                for(BusinessTransactionRecord pendingToSubmitNotificationRecord : pendingToSubmitNotificationList){

                    contractHash=pendingToSubmitNotificationRecord.getTransactionHash();

                    System.out.println("\nTEST CONTRACT - ACK OFFLINE MERCHANDISE - AGENT - doTheMainTask() - getPendingToSubmitNotificationList(): " +contractHash+"\n");

                    transactionTransmissionManager.sendContractStatusNotification(
                            pendingToSubmitNotificationRecord.getCustomerPublicKey(),
                            pendingToSubmitNotificationRecord.getBrokerPublicKey(),
                            contractHash,
                            pendingToSubmitNotificationRecord.getTransactionId(),
                            ContractTransactionStatus.OFFLINE_MERCHANDISE_ACK,
                            Plugins.CUSTOMER_ACK_OFFLINE_MERCHANDISE,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER
                    );

                    customerAckOfflineMerchandiseBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.OFFLINE_MERCHANDISE_ACK);
                }

                /**
                 * Check pending notifications - Broker side
                 */
                List<BusinessTransactionRecord> pendingToSubmitConfirmationList = customerAckOfflineMerchandiseBusinessTransactionDao.getPendingToSubmitConfirmList();
                for(BusinessTransactionRecord pendingToSubmitConfirmationRecord : pendingToSubmitConfirmationList){

                    System.out.println("\nTEST CONTRACT - ACK OFFLINE MERCHANDISE - AGENT - doTheMainTask() - getPendingToSubmitNotificationList()\n");
                    contractHash=pendingToSubmitConfirmationRecord.getTransactionHash();

                    transactionTransmissionManager.confirmNotificationReception(
                            pendingToSubmitConfirmationRecord.getBrokerPublicKey(),
                            pendingToSubmitConfirmationRecord.getCustomerPublicKey(),
                            contractHash,
                            pendingToSubmitConfirmationRecord.getTransactionId(),
                            Plugins.CUSTOMER_ACK_OFFLINE_MERCHANDISE,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);

                    customerAckOfflineMerchandiseBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONFIRM_OFFLINE_ACK_MERCHANDISE);
                }

                /**
                 * Check if pending events
                 */
                List<String> pendingEventsIdList= customerAckOfflineMerchandiseBusinessTransactionDao.getPendingEvents();
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
            } catch (CantConfirmNotificationReceptionException e) {
                throw new CannotSendContractHashException(
                        e,
                        "Sending confirm contract hash",
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

        private void raiseAckConfirmationEvent(String contractHash){
            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.CUSTOMER_ACK_MERCHANDISE_CONFIRMED);
            CustomerAckMerchandiseConfirmed customerAckMerchandiseConfirmed = (CustomerAckMerchandiseConfirmed) fermatEvent;
            customerAckMerchandiseConfirmed.setSource(EventSource.CUSTOMER_ACK_OFFLINE_MERCHANDISE);
            customerAckMerchandiseConfirmed.setContractHash(contractHash);
            customerAckMerchandiseConfirmed.setPaymentType(PaymentType.CRYPTO_MONEY);
            eventManager.raiseEvent(customerAckMerchandiseConfirmed);
        }


        private void checkPendingEvent(String eventId) throws  UnexpectedResultReturnedFromDatabaseException {

            try{
                String eventTypeCode= customerAckOfflineMerchandiseBusinessTransactionDao.getEventType(eventId);
                String contractHash;
                BusinessTransactionMetadata businessTransactionMetadata;
                ContractTransactionStatus contractTransactionStatus;
                BusinessTransactionRecord businessTransactionRecord;

                //EVENT FOR CONTRACT STATUS UPDATE
                if (eventTypeCode.equals(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE.getCode())) {

                    System.out.print("\nTEST CONTRACT - ACK OFFLINE MERCHANDISE - AGENT - checkPendingEvent() - INCOMING_NEW_CONTRACT_STATUS_UPDATE\n");

                    //This will happen in broker side
                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList =
                            transactionTransmissionManager.getPendingTransactions(
                                    Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {

                        businessTransactionMetadata = record.getInformation();
                        contractHash = businessTransactionMetadata.getContractHash();

                        if (customerAckOfflineMerchandiseBusinessTransactionDao.isContractHashInDatabase(contractHash)) {

                            contractTransactionStatus = customerAckOfflineMerchandiseBusinessTransactionDao.
                                    getContractTransactionStatus(contractHash);
                            //TODO: analyze what we need to do here.

                        } else {

                            System.out.print("\nTEST CONTRACT - ACK OFFLINE MERCHANDISE - AGENT - checkPendingEvent() - INCOMING_NEW_CONTRACT_STATUS_UPDATE - VAL\n");

                            CustomerBrokerContractSale customerBrokerContractSale = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
                            //If the contract is null, I cannot handle with this situation
                            ObjectChecker.checkArgument(customerBrokerContractSale);
                            customerAckOfflineMerchandiseBusinessTransactionDao.persistContractInDatabase(customerBrokerContractSale);
//                            customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(contractHash, ContractStatus.COMPLETED);
                            customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(contractHash, ContractStatus.READY_TO_CLOSE);
                            Date date = new Date();
                            customerAckOfflineMerchandiseBusinessTransactionDao.setCompletionDateByContractHash(contractHash, date.getTime());
                            raiseAckConfirmationEvent(contractHash);
                        }
                        transactionTransmissionManager.confirmReception(record.getTransactionID());
                    }
                    customerAckOfflineMerchandiseBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);

                }

                //EVENT FOR TRANSACTION RESPONSE
                if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {

                    System.out.print("\nTEST CONTRACT - ACK OFFLINE MERCHANDISE - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE\n");

                    //This will happen in customer side
                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList =
                            transactionTransmissionManager.getPendingTransactions(
                                    Specialist.UNKNOWN_SPECIALIST);

                    for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {

                        businessTransactionMetadata = record.getInformation();
                        contractHash = businessTransactionMetadata.getContractHash();

                        if (customerAckOfflineMerchandiseBusinessTransactionDao.isContractHashInDatabase(contractHash)) {

                            businessTransactionRecord = customerAckOfflineMerchandiseBusinessTransactionDao.getBusinessTransactionRecordByContractHash(contractHash);
                            contractTransactionStatus = businessTransactionRecord.getContractTransactionStatus();

                            if (contractTransactionStatus.getCode().equals(ContractTransactionStatus.OFFLINE_MERCHANDISE_ACK.getCode())) {

                                System.out.print("\nTEST CONTRACT - ACK OFFLINE MERCHANDISE - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - VAL\n");

//                                businessTransactionRecord.setContractTransactionStatus(ContractTransactionStatus.CONFIRM_OFFLINE_ACK_MERCHANDISE);
                                customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(contractHash, ContractStatus.READY_TO_CLOSE);
//                                customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(contractHash, ContractStatus.COMPLETED);
                                Date date = new Date();
                                customerAckOfflineMerchandiseBusinessTransactionDao.setCompletionDateByContractHash(contractHash, date.getTime());
                                customerAckOfflineMerchandiseBusinessTransactionDao.updateContractTransactionStatus(contractHash,ContractTransactionStatus.CONFIRM_OFFLINE_ACK_MERCHANDISE);
                                raiseAckConfirmationEvent(contractHash);

                            }
                        }
                        transactionTransmissionManager.confirmReception(record.getTransactionID());

                    }
                    customerAckOfflineMerchandiseBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);

                }

                //EVENT FOR ACK PAYMENT CONFIRMED
                if (eventTypeCode.equals(EventType.BROKER_ACK_PAYMENT_CONFIRMED.getCode())) {

                    System.out.print("\nTEST CONTRACT - ACK OFFLINE MERCHANDISE - AGENT - checkPendingEvent() - INCOMING_NEW_CONTRACT_STATUS_UPDATE\n");

                    //the eventId from this event is the contractId - Customer side
                    CustomerBrokerContractPurchase customerBrokerContractPurchase =
                            customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(eventId);
                    customerAckOfflineMerchandiseBusinessTransactionDao.persistContractInDatabase(customerBrokerContractPurchase);

                }

            } catch (CantUpdateRecordException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        exception,
                        "Checking pending events",
                        "Cannot update the database");
            } catch (CantConfirmTransactionException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        exception,
                        "Checking pending events",
                        "Cannot confirm the transaction");
            } catch (CantUpdateCustomerBrokerContractSaleException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        exception,
                        "Checking pending events",
                        "Cannot update the contract sale status");
            } catch (CantDeliverPendingTransactionsException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        exception,
                        "Checking pending events",
                        "Cannot get the pending transactions from transaction transmission plugin");
            } catch (CantInsertRecordException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        exception,
                        "Checking pending events",
                        "Cannot insert a record in database");
            } catch (CantGetListCustomerBrokerContractPurchaseException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        exception,
                        "Checking pending events",
                        "Cannot get the purchase contract");
            } catch (CantUpdateCustomerBrokerContractPurchaseException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        exception,
                        "Checking pending events",
                        "Cannot update the contract purchase status");
            } catch (CantGetListCustomerBrokerContractSaleException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        exception,
                        "Checking pending events",
                        "Cannot update the contract sale status");
            } catch (ObjectNotSetException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        exception,
                        "Checking pending events",
                        "The customerBrokerContractSale is null");
            }

        }

    }

}


