package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
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
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CannotSendContractHashException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.events.CustomerOnlinePaymentConfirmed;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantUpdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.CustomerOnlinePaymentPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetCryptoStatusException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetSendCryptoTransactionHashException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.IntraActorCryptoTransactionManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;

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
    CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    IntraActorCryptoTransactionManager intraActorCryptoTransactionManager;
    OutgoingIntraActorManager outgoingIntraActorManager;

    public CustomerOnlinePaymentMonitorAgent(
            PluginDatabaseSystem pluginDatabaseSystem,
            LogManager logManager,
            ErrorManager errorManager,
            EventManager eventManager,
            UUID pluginId,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            OutgoingIntraActorManager outgoingIntraActorManager) throws CantSetObjectException {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.logManager=logManager;
        this.transactionTransmissionManager=transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager=customerBrokerContractPurchaseManager;
        this.outgoingIntraActorManager=outgoingIntraActorManager;
        this.customerBrokerContractSaleManager=customerBrokerContractSaleManager;
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

        //Logger LOG = Logger.getGlobal();
        //LOG.info("Customer online payment monitor agent starting");
        monitorAgent = new MonitorAgent();

        ((DealsWithPluginDatabaseSystem) this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        ((DealsWithErrors) this.monitorAgent).setErrorManager(this.errorManager);

        try {
            ((MonitorAgent) this.monitorAgent).Initialize();
        } catch (CantInitializeCBPAgent exception) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_ONLINE_PAYMENT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
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

                //Logger LOG = Logger.getGlobal();
                //LOG.info("Database in Open Contract monitor agent doesn't exists");
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
                BusinessTransactionRecord businessTransactionRecord;
                String contractHash;
                /**
                 * Check if there is some crypto to send
                 */
                List<String> pendingToSubmitCrypto=customerOnlinePaymentBusinessTransactionDao.getPendingToSubmitCryptoList();
                for(String pendingContractHash : pendingToSubmitCrypto){
                    businessTransactionRecord =customerOnlinePaymentBusinessTransactionDao.
                            getCustomerOnlinePaymentRecord(pendingContractHash);
                    outgoingCryptoTransactionId=intraActorCryptoTransactionManager.sendCrypto(
                            businessTransactionRecord.getExternalWalletPublicKey(),
                            businessTransactionRecord.getCryptoAddress(),
                            businessTransactionRecord.getCryptoAmount(),
                            "Payment from Crypto Customer contract " + pendingContractHash,
                            businessTransactionRecord.getCustomerPublicKey(),
                            businessTransactionRecord.getBrokerPublicKey(),
                            Actors.CBP_CRYPTO_CUSTOMER,
                            Actors.CBP_CRYPTO_BROKER,
                            ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET
                    );
                    customerOnlinePaymentBusinessTransactionDao.persistsCryptoTransactionUUID(
                            pendingContractHash,
                            outgoingCryptoTransactionId);
                    customerOnlinePaymentBusinessTransactionDao.updateContractTransactionStatus(
                            pendingContractHash,
                            ContractTransactionStatus.ONLINE_PAYMENT_SUBMITTED);
                }
                //TODO: finish this

                /**
                 * Check contract status to send.
                 */
                List<BusinessTransactionRecord> pendingToSubmitNotificationList=
                        customerOnlinePaymentBusinessTransactionDao.getPendingToSubmitNotificationList();
                for(BusinessTransactionRecord pendingToSubmitNotificationRecord : pendingToSubmitNotificationList){
                    contractHash=pendingToSubmitNotificationRecord.getTransactionHash();
                    transactionTransmissionManager.sendContractStatusNotificationToCryptoBroker(
                            pendingToSubmitNotificationRecord.getCustomerPublicKey(),
                            pendingToSubmitNotificationRecord.getBrokerPublicKey(),
                            contractHash,
                            pendingToSubmitNotificationRecord.getTransactionId(),
                            ContractTransactionStatus.CRYPTO_PAYMENT_SUBMITTED
                    );
                    customerOnlinePaymentBusinessTransactionDao.updateContractTransactionStatus(
                            contractHash,
                            ContractTransactionStatus.CRYPTO_PAYMENT_SUBMITTED
                    );
                }

                /**
                 * Check pending notifications - Broker side
                 */
                List<BusinessTransactionRecord> pendingToSubmitConfirmationList=
                        customerOnlinePaymentBusinessTransactionDao.getPendingToSubmitNotificationList();
                for(BusinessTransactionRecord pendingToSubmitConfirmationRecord : pendingToSubmitConfirmationList){
                    contractHash=pendingToSubmitConfirmationRecord.getTransactionHash();
                    transactionTransmissionManager.sendContractStatusNotificationToCryptoCustomer(
                            pendingToSubmitConfirmationRecord.getBrokerPublicKey(),
                            pendingToSubmitConfirmationRecord.getCustomerPublicKey(),
                            contractHash,
                            pendingToSubmitConfirmationRecord.getTransactionId(),
                            ContractTransactionStatus.CONFIRM_ONLINE_PAYMENT
                    );
                    customerOnlinePaymentBusinessTransactionDao.updateContractTransactionStatus(
                            contractHash,
                            ContractTransactionStatus.CONFIRM_ONLINE_PAYMENT
                    );
                }

                /**
                 * Check pending transactions
                 */
                List<BusinessTransactionRecord> pendingTransactions=
                        customerOnlinePaymentBusinessTransactionDao.getPendingCryptoTransactionList();
                for(BusinessTransactionRecord onlinePaymentRecord: pendingTransactions){
                    checkPendingTransaction(onlinePaymentRecord);
                }

                /**
                 * Check if pending to submit crypto status
                 */
                List<BusinessTransactionRecord> pendingSubmitContractList=
                        customerOnlinePaymentBusinessTransactionDao.getPendingToSubmitCryptoStatusList();
                CryptoStatus cryptoStatus;
                for(BusinessTransactionRecord pendingSubmitContractRecord : pendingSubmitContractList){
                    cryptoStatus=outgoingIntraActorManager.getTransactionStatus(
                            pendingSubmitContractRecord.getTransactionHash());
                    pendingSubmitContractRecord.setCryptoStatus(cryptoStatus);
                    customerOnlinePaymentBusinessTransactionDao.updateBusinessTransactionRecord(
                            pendingSubmitContractRecord);
                }

                /**
                 * Check if on crypto network crypto status
                 */
                List<BusinessTransactionRecord> pendingOnCryptoNetworkContractList=
                        customerOnlinePaymentBusinessTransactionDao.getOnCryptoNetworkCryptoStatusList();
                for(BusinessTransactionRecord onCryptoNetworkContractRecord : pendingOnCryptoNetworkContractList){
                    cryptoStatus=outgoingIntraActorManager.getTransactionStatus(
                            onCryptoNetworkContractRecord.getTransactionHash());
                    onCryptoNetworkContractRecord.setCryptoStatus(cryptoStatus);
                    customerOnlinePaymentBusinessTransactionDao.updateBusinessTransactionRecord(
                            onCryptoNetworkContractRecord);
                }

                /**
                 * Check if on blockchain crypto status
                 */
                List<BusinessTransactionRecord> pendingOnBlockchainContractList=
                        customerOnlinePaymentBusinessTransactionDao.getOnBlockchainkCryptoStatusList();
                for(BusinessTransactionRecord onBlockchainContractRecord : pendingOnBlockchainContractList){
                    cryptoStatus=outgoingIntraActorManager.getTransactionStatus(
                            onBlockchainContractRecord.getTransactionHash());
                    onBlockchainContractRecord.setCryptoStatus(cryptoStatus);
                    onBlockchainContractRecord.setContractTransactionStatus(
                            ContractTransactionStatus.ONLINE_PAYMENT_SUBMITTED);
                    customerOnlinePaymentBusinessTransactionDao.updateBusinessTransactionRecord(
                            onBlockchainContractRecord);
                }

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
            }*/ catch (OutgoingIntraActorInsufficientFundsException e) {
                //TODO: I want to get a better handler for this exception
                e.printStackTrace();
                errorManager.reportUnexpectedPluginException(
                        Plugins.CUSTOMER_ONLINE_PAYMENT,
                        UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                        e);
            } catch (OutgoingIntraActorCantSendFundsExceptions outgoingIntraActorCantSendFundsExceptions) {
                outgoingIntraActorCantSendFundsExceptions.printStackTrace();
                errorManager.reportUnexpectedPluginException(
                        Plugins.CUSTOMER_ONLINE_PAYMENT,
                        UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                        outgoingIntraActorCantSendFundsExceptions);
            } catch (OutgoingIntraActorCantGetCryptoStatusException e) {
                e.printStackTrace();
                errorManager.reportUnexpectedPluginException(
                        Plugins.CUSTOMER_ONLINE_PAYMENT,
                        UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                        e);
            }

        }


        private void raisePaymentConfirmationEvent(){
            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.CUSTOMER_ONLINE_PAYMENT_CONFIRMED);
            CustomerOnlinePaymentConfirmed customerOnlinePaymentConfirmed = (CustomerOnlinePaymentConfirmed) fermatEvent;
            customerOnlinePaymentConfirmed.setSource(EventSource.CUSTOMER_ONLINE_PAYMENT);
            eventManager.raiseEvent(customerOnlinePaymentConfirmed);
        }

        //TODO: raise an event only in broker side, notifying the incoming online payment. Create the event.
        //TODO: raise an event only in customer side, notifying the reception of an online payment. Create the event.
        private void checkPendingTransaction(
                BusinessTransactionRecord businessTransactionRecord)throws CantUpdateRecordException
        {
            UUID transactionUUID=UUID.fromString(businessTransactionRecord.getTransactionId());
            //Get transaction hash from IntraActorCryptoTransactionManager
            try {
                String transactionHash =
                        intraActorCryptoTransactionManager.getSendCryptoTransactionHash(
                                transactionUUID);
                if(transactionHash==null){
                    //If transactionHash is null the crypto amount is not sent yet, I'll check for this later.
                    return;
                }
                CryptoStatus cryptoStatus=outgoingIntraActorManager.getTransactionStatus(transactionHash);
                businessTransactionRecord.setTransactionHash(transactionHash);
                businessTransactionRecord.setCryptoStatus(cryptoStatus);
                customerOnlinePaymentBusinessTransactionDao.updateBusinessTransactionRecord(businessTransactionRecord);
            } catch (OutgoingIntraActorCantGetSendCryptoTransactionHashException e) {
                //I want to know a better way to handle with this exception, for now I going to print the exception and return this method.
                e.printStackTrace();
            } catch (OutgoingIntraActorCantGetCryptoStatusException e) {
                //I want to know a better way to handle with this exception, for now I going to print the exception and return this method.
                e.printStackTrace();
            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                throw new CantUpdateRecordException(
                        UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE,
                        e,
                        "Checking the crypto status",
                        "I get an unexpected results in database");
            }
        }

        private void checkPendingEvent(String eventId) throws  UnexpectedResultReturnedFromDatabaseException {

            //TODO: events from customer side, listen contract status confirmation, the record must be in PENDING_ONLINE_PAYMENT_NOTIFICATION, raise an event
            try{

                String eventTypeCode=customerOnlinePaymentBusinessTransactionDao.getEventType(eventId);
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
                        if(customerOnlinePaymentBusinessTransactionDao.isContractHashInDatabase(contractHash)){
                            contractTransactionStatus=customerOnlinePaymentBusinessTransactionDao.
                                    getContractTransactionStatus(contractHash);
                            //TODO: analyze what we need to do here.
                        }else{
                            CustomerBrokerContractSale customerBrokerContractSale=
                                    customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(
                                    contractHash);
                            customerOnlinePaymentBusinessTransactionDao.persistContractInDatabase(
                                    customerBrokerContractSale);
                            customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(
                                    contractHash,
                                    ContractStatus.PAYMENT_SUBMIT);
                            raisePaymentConfirmationEvent();
                        }
                        transactionTransmissionManager.confirmReception(record.getTransactionID());
                    }
                    customerOnlinePaymentBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                }
                if(eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())){
                    //This will happen in customer side
                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList=
                            transactionTransmissionManager.getPendingTransactions(
                                    Specialist.UNKNOWN_SPECIALIST);
                    for(Transaction<BusinessTransactionMetadata> record : pendingTransactionList){
                        businessTransactionMetadata=record.getInformation();
                        contractHash=businessTransactionMetadata.getContractHash();
                        if(customerOnlinePaymentBusinessTransactionDao.isContractHashInDatabase(contractHash)){
                            businessTransactionRecord =
                                    customerOnlinePaymentBusinessTransactionDao.
                                            getCustomerOnlinePaymentRecord(contractHash);
                            contractTransactionStatus= businessTransactionRecord.getContractTransactionStatus();
                            if(contractTransactionStatus.getCode().equals(ContractTransactionStatus.ONLINE_PAYMENT_SUBMITTED.getCode())){
                                businessTransactionRecord.setContractTransactionStatus(ContractTransactionStatus.CONFIRM_ONLINE_PAYMENT);
                                customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(
                                        contractHash,
                                        ContractStatus.PAYMENT_SUBMIT);
                                raisePaymentConfirmationEvent();
                            }
                        }
                        transactionTransmissionManager.confirmReception(record.getTransactionID());
                    }
                    customerOnlinePaymentBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                }
                //TODO: look a better way to deal with this exceptions
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
            }

        }

    }

}

