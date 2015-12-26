package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
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
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.events.CustomerAckMerchandiseConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CannotSendContractHashException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantupdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.CustomerAckOnlineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.exceptions.IncomingOnlineMerchandiseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/12/15.
 */
public class CustomerAckOnlineMerchandiseMonitorAgent implements
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

    public CustomerAckOnlineMerchandiseMonitorAgent(
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

        ((DealsWithPluginDatabaseSystem) this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        ((DealsWithErrors) this.monitorAgent).setErrorManager(this.errorManager);

        try {
            ((MonitorAgent) this.monitorAgent).Initialize();
        } catch (CantInitializeCBPAgent exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
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
        CustomerAckOnlineMerchandiseBusinessTransactionDao customerAckOnlineMerchandiseBusinessTransactionDao;
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
            logManager.log(CustomerAckOnlineMerchandisePluginRoot.getLogLevelByClass(this.getClass().getName()),
                    "Customer Ack Online Merchandise Monitor Agent: running...", null, null);
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

                    logManager.log(CustomerAckOnlineMerchandisePluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CannotSendContractHashException | CantUpdateRecordException | CantSendContractNewStatusNotificationException e) {
                    errorManager.reportUnexpectedPluginException(
                            Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                            UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                            e);
                }

            }

        }
        public void Initialize() throws CantInitializeCBPAgent {
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId,
                        CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME);
            }
            catch (DatabaseNotFoundException databaseNotFoundException) {

                //Logger LOG = Logger.getGlobal();
                //LOG.info("Database in Open Contract monitor agent doesn't exists");
                CustomerAckOnlineMerchandiseBusinessTransactionDatabaseFactory customerAckOnlineMerchandiseBusinessTransactionDatabaseFactory=
                        new CustomerAckOnlineMerchandiseBusinessTransactionDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = customerAckOnlineMerchandiseBusinessTransactionDatabaseFactory.createDatabase(pluginId,
                            CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    errorManager.reportUnexpectedPluginException(
                            Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                            UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                            cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException,
                            "Initialize Monitor Agent - trying to create the plugin database",
                            "Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                errorManager.reportUnexpectedPluginException(
                        Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
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
                customerAckOnlineMerchandiseBusinessTransactionDao =new CustomerAckOnlineMerchandiseBusinessTransactionDao(
                        pluginDatabaseSystem,
                        pluginId,
                        database);

                BusinessTransactionRecord businessTransactionRecord;
                String contractHash;
                /**
                 * Check if pending incoming money events
                 */
                List<String> pendingMoneyEventIdList= customerAckOnlineMerchandiseBusinessTransactionDao.getPendingIncomingMoneyEvents();
                for(String eventId : pendingMoneyEventIdList){
                    checkPendingMoneyEvents(eventId);
                }

                /**
                 * Check contract status to send. - Customer Side
                 * The status to verify is PENDING_SUBMIT_ONLINE_MERCHANDISE_NOTIFICATION, it represents that the merchandise is
                 * acknowledge by the customer.
                 */
                List<BusinessTransactionRecord> pendingToSubmitNotificationList=
                        customerAckOnlineMerchandiseBusinessTransactionDao.getPendingToSubmitNotificationList();
                for(BusinessTransactionRecord pendingToSubmitNotificationRecord : pendingToSubmitNotificationList){
                    contractHash=pendingToSubmitNotificationRecord.getTransactionHash();
                    transactionTransmissionManager.sendContractStatusNotificationToCryptoCustomer(
                            pendingToSubmitNotificationRecord.getBrokerPublicKey(),
                            pendingToSubmitNotificationRecord.getCustomerPublicKey(),
                            contractHash,
                            pendingToSubmitNotificationRecord.getTransactionId(),
                            ContractTransactionStatus.ONLINE_MERCHANDISE_ACK
                    );
                    customerAckOnlineMerchandiseBusinessTransactionDao.updateContractTransactionStatus(
                            contractHash,
                            ContractTransactionStatus.ONLINE_MERCHANDISE_ACK
                    );
                }

                /**
                 * Check pending notifications - Broker side
                 */
                List<BusinessTransactionRecord> pendingToSubmitConfirmationList=
                        customerAckOnlineMerchandiseBusinessTransactionDao.getPendingToSubmitNotificationList();
                for(BusinessTransactionRecord pendingToSubmitConfirmationRecord : pendingToSubmitConfirmationList){
                    contractHash=pendingToSubmitConfirmationRecord.getTransactionHash();
                    transactionTransmissionManager.sendContractStatusNotificationToCryptoBroker(
                            pendingToSubmitConfirmationRecord.getCustomerPublicKey(),
                            pendingToSubmitConfirmationRecord.getBrokerPublicKey(),
                            contractHash,
                            pendingToSubmitConfirmationRecord.getTransactionId(),
                            ContractTransactionStatus.CONFIRM_ONLINE_ACK_MERCHANDISE
                    );
                    customerAckOnlineMerchandiseBusinessTransactionDao.updateContractTransactionStatus(
                            contractHash,
                            ContractTransactionStatus.CONFIRM_ONLINE_ACK_MERCHANDISE
                    );
                }

                /**
                 * Check if pending events
                 */
                List<String> pendingEventsIdList= customerAckOnlineMerchandiseBusinessTransactionDao.getPendingEvents();
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
            }*/ catch (IncomingOnlineMerchandiseException e) {
                //TODO: I need to discuss if I need to raise an event here, for now I going to report the error
                errorManager.reportUnexpectedPluginException(
                        Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                        e);
            }

        }

        private void raiseAckConfirmationEvent(String contractHash){
            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.CUSTOMER_ACK_MERCHANDISE_CONFIRMED);
            CustomerAckMerchandiseConfirmed customerAckMerchandiseConfirmed = (CustomerAckMerchandiseConfirmed) fermatEvent;
            customerAckMerchandiseConfirmed.setSource(EventSource.CUSTOMER_ACK_ONLINE_MERCHANDISE);
            customerAckMerchandiseConfirmed.setContractHash(contractHash);
            customerAckMerchandiseConfirmed.setPaymentType(PaymentType.CRYPTO_MONEY);
            eventManager.raiseEvent(customerAckMerchandiseConfirmed);
        }

        private void checkPendingMoneyEvents(String eventId) throws
                IncomingOnlineMerchandiseException,
                CantUpdateRecordException {
            String senderPublicKey;
            com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.IncomingMoneyEventWrapper incomingMoneyEventWrapper;
            BusinessTransactionRecord businessTransactionRecord;
            long contractCryptoAmount;
            long incomingCryptoAmount;
            String contractHash;
            String receiverActorPublicKey;
            String expectedActorPublicKey;
            String contractWalletPublicKey;
            String incomingWalletPublicKey;
            try{
                incomingMoneyEventWrapper= customerAckOnlineMerchandiseBusinessTransactionDao.getIncomingMoneyEventWrapper(
                        eventId);
                senderPublicKey=incomingMoneyEventWrapper.getSenderPublicKey();
                businessTransactionRecord =
                        customerAckOnlineMerchandiseBusinessTransactionDao.
                                getBusinessTransactionRecordByBrokerPublicKey(senderPublicKey);
                if(businessTransactionRecord ==null){
                    //Case: the contract event is not processed or the incoming money is not link to a contract.
                    return;
                }
                contractHash= businessTransactionRecord.getContractHash();
                incomingCryptoAmount=incomingMoneyEventWrapper.getCryptoAmount();
                contractCryptoAmount= businessTransactionRecord.getCryptoAmount();
                if(incomingCryptoAmount!=contractCryptoAmount){
                    throw new IncomingOnlineMerchandiseException("The incoming crypto amount received is "+incomingCryptoAmount+"\n" +
                            "The amount excepted in contract "+contractHash+"\n" +
                            "is "+contractCryptoAmount
                    );
                }
                receiverActorPublicKey=incomingMoneyEventWrapper.getReceiverPublicKey();
                expectedActorPublicKey= businessTransactionRecord.getBrokerPublicKey();
                if(!receiverActorPublicKey.equals(expectedActorPublicKey)){
                    throw new IncomingOnlineMerchandiseException("The actor public key that receive the money is "+receiverActorPublicKey+"\n" +
                            "The broker public key in contract "+contractHash+"\n" +
                            "is "+expectedActorPublicKey
                    );
                }
                incomingWalletPublicKey=incomingMoneyEventWrapper.getWalletPublicKey();
                contractWalletPublicKey= businessTransactionRecord.getExternalWalletPublicKey();
                if(!incomingWalletPublicKey.equals(contractWalletPublicKey)){
                    throw new IncomingOnlineMerchandiseException("The wallet public key that receive the money is "+incomingWalletPublicKey+"\n" +
                            "The wallet public key in contract "+contractHash+"\n" +
                            "is "+contractWalletPublicKey
                    );
                }
                businessTransactionRecord.setContractTransactionStatus(
                        ContractTransactionStatus.PENDING_ACK_ONLINE_MERCHANDISE_NOTIFICATION);
                customerAckOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(
                        businessTransactionRecord);

            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                throw new IncomingOnlineMerchandiseException(
                        e,
                        "Checking the incoming payment",
                        "The database return an unexpected result");
            }

        }

        private void checkPendingEvent(String eventId) throws  UnexpectedResultReturnedFromDatabaseException {

            try{
                String eventTypeCode= customerAckOnlineMerchandiseBusinessTransactionDao.getEventType(eventId);
                String contractHash;
                BusinessTransactionMetadata businessTransactionMetadata;
                ContractTransactionStatus contractTransactionStatus;
                BusinessTransactionRecord businessTransactionRecord;
                if(eventTypeCode.equals(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE.getCode())){
                    //This will happen in broker side
                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList=
                            transactionTransmissionManager.getPendingTransactions(
                                    Specialist.UNKNOWN_SPECIALIST);
                    for(Transaction<BusinessTransactionMetadata> record : pendingTransactionList){
                        businessTransactionMetadata=record.getInformation();
                        contractHash=businessTransactionMetadata.getContractHash();
                        if(customerAckOnlineMerchandiseBusinessTransactionDao.isContractHashInDatabase(contractHash)){
                            contractTransactionStatus= customerAckOnlineMerchandiseBusinessTransactionDao.
                                    getContractTransactionStatus(contractHash);
                            //TODO: analyze what we need to do here.
                        }else{
                            CustomerBrokerContractSale customerBrokerContractPurchase=
                                    customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(
                                            contractHash);
                            customerAckOnlineMerchandiseBusinessTransactionDao.persistContractInDatabase(
                                    customerBrokerContractPurchase);
                            customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(
                                    contractHash,
                                    ContractStatus.READY_TO_CLOSE);
                            raiseAckConfirmationEvent(contractHash);
                        }
                        transactionTransmissionManager.confirmReception(record.getTransactionID());
                    }
                    customerAckOnlineMerchandiseBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                }
                if(eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())){
                    //This will happen in customer side
                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList=
                            transactionTransmissionManager.getPendingTransactions(
                                    Specialist.UNKNOWN_SPECIALIST);
                    for(Transaction<BusinessTransactionMetadata> record : pendingTransactionList){
                        businessTransactionMetadata=record.getInformation();
                        contractHash=businessTransactionMetadata.getContractHash();
                        if(customerAckOnlineMerchandiseBusinessTransactionDao.isContractHashInDatabase(contractHash)){
                            businessTransactionRecord =
                                    customerAckOnlineMerchandiseBusinessTransactionDao.
                                            getBusinessTransactionRecordByContractHash(contractHash);
                            contractTransactionStatus= businessTransactionRecord.getContractTransactionStatus();
                            if(contractTransactionStatus.getCode().equals(ContractTransactionStatus.ONLINE_PAYMENT_ACK.getCode())){
                                businessTransactionRecord.setContractTransactionStatus(ContractTransactionStatus.CONFIRM_ONLINE_ACK_PAYMENT);
                                customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(
                                        contractHash,
                                        ContractStatus.READY_TO_CLOSE);
                                raiseAckConfirmationEvent(contractHash);
                            }
                        }
                        transactionTransmissionManager.confirmReception(record.getTransactionID());
                    }
                    customerAckOnlineMerchandiseBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                }
                if(eventTypeCode.equals(EventType.BROKER_ACK_PAYMENT_CONFIRMED.getCode())){
                    //the eventId from this event is the contractId - Customer side
                    CustomerBrokerContractPurchase customerBrokerContractPurchase=
                            customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(
                                    eventId);
                    customerAckOnlineMerchandiseBusinessTransactionDao.persistContractInDatabase(
                            customerBrokerContractPurchase);

                }

            } catch (CantUpdateRecordException e) {
                e.printStackTrace();
            } catch (CantConfirmTransactionException e) {
                e.printStackTrace();
            } catch (CantupdateCustomerBrokerContractSaleException e) {
                e.printStackTrace();
            } catch (CantDeliverPendingTransactionsException e) {
                e.printStackTrace();
            } catch (CantInsertRecordException e) {
                e.printStackTrace();
            } catch (CantGetListCustomerBrokerContractPurchaseException e) {
                e.printStackTrace();
            } catch (CantGetListCustomerBrokerContractSaleException e) {
                e.printStackTrace();
            }


        }

    }

}

