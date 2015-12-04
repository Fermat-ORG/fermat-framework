package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
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
import com.bitdubai.fermat_cbp_api.all_definition.contract.Contract;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractPurchaseRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractSaleRecord;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantupdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions.CantSendBusinessTransactionHashException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.OpenContractPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.exceptions.CannotFindKeyValueException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.exceptions.CannotSendContractHashException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.exceptions.CantGetContractListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/12/15.
 */
public class OpenContractMonitorAgent implements
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

    public OpenContractMonitorAgent(PluginDatabaseSystem pluginDatabaseSystem,
                                    LogManager logManager,
                                    ErrorManager errorManager,
                                    EventManager eventManager,
                                    UUID pluginId,
                                    TransactionTransmissionManager transactionTransmissionManager,
                                    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
                                    CustomerBrokerContractSaleManager customerBrokerContractSaleManager) throws CantSetObjectException {
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
        OpenContractBusinessTransactionDao openContractBusinessTransactionDao;
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
            logManager.log(OpenContractPluginRoot.getLogLevelByClass(this.getClass().getName()),
                    "Open Contract Monitor Agent: running...", null, null);
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

                    logManager.log(OpenContractPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CannotSendContractHashException | CantUpdateRecordException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }

            }

        }
        public void Initialize() throws CantInitializeCBPAgent {
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId,
                        OpenContractBusinessTransactionDatabaseConstants.DATABASE_NAME);
            }
            catch (DatabaseNotFoundException databaseNotFoundException) {

                Logger LOG = Logger.getGlobal();
                LOG.info("Database in Open Contract monitor agent doesn't exists");
                OpenContractBusinessTransactionDatabaseFactory openContractBusinessTransactionDatabaseFactory=new OpenContractBusinessTransactionDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = openContractBusinessTransactionDatabaseFactory.createDatabase(pluginId,
                            OpenContractBusinessTransactionDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    errorManager.reportUnexpectedPluginException(
                            Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION,
                            UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                            cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException,
                            "Initialize Monitor Agent - trying to create the plugin database",
                            "Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                errorManager.reportUnexpectedPluginException(
                        Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        exception);
                throw new CantInitializeCBPAgent(exception,
                        "Initialize Monitor Agent - trying to open the plugin database",
                        "Please, check the cause");
            }
        }

        private void doTheMainTask() throws
                CannotSendContractHashException,
                CantUpdateRecordException {

            try{
                openContractBusinessTransactionDao=new OpenContractBusinessTransactionDao(
                        pluginDatabaseSystem,
                        pluginId,
                        database);
                /**
                 * Check if exist in database new contracts to send
                 */
                List<String> contractPendingToSubmitList=openContractBusinessTransactionDao.getPendingToSubmitContractHash();
                String contractXML;
                ContractPurchaseRecord purchaseContract=new ContractPurchaseRecord();
                ContractSaleRecord saleContract=new ContractSaleRecord();
                ContractType contractType;
                UUID transactionId;
                if(!contractPendingToSubmitList.isEmpty()){
                    for(String hashToSubmit: contractPendingToSubmitList){
                        System.out.println("OPEN CONTRACT - Hash to submit:\n"+hashToSubmit);
                        contractXML=openContractBusinessTransactionDao.getContractXML(hashToSubmit);
                        contractType=openContractBusinessTransactionDao.getContractType(hashToSubmit);
                        transactionId=openContractBusinessTransactionDao.getTransactionId(hashToSubmit);
                        switch (contractType){
                            case PURCHASE:
                                purchaseContract=(ContractPurchaseRecord)XMLParser.parseXML(
                                        contractXML,
                                        purchaseContract);
                                transactionTransmissionManager.sendContractHashToCryptoBroker(
                                        transactionId,
                                        purchaseContract.getPublicKeyCustomer(),
                                        purchaseContract.getPublicKeyBroker(),
                                        hashToSubmit,
                                        purchaseContract.getNegotiationId());
                                break;
                            case SALE:
                                saleContract=(ContractSaleRecord)XMLParser.parseXML(
                                        contractXML,
                                        saleContract);
                                transactionTransmissionManager.sendContractHashToCryptoCustomer(
                                        transactionId,
                                        saleContract.getPublicKeyBroker(),
                                        saleContract.getPublicKeyCustomer(),
                                        hashToSubmit,
                                        saleContract.getNegotiationId());
                                break;
                        }
                        //Update the ContractTransactionStatus
                        openContractBusinessTransactionDao.updateContractTransactionStatus(
                                hashToSubmit,
                                ContractTransactionStatus.CHECKING_HASH);
                    }

                }

                /**
                 * Check if pending contract to confirm
                 */
                List<String> contractPendingToConfirmList=openContractBusinessTransactionDao.getPendingToConfirmContractHash();
                if(!contractPendingToConfirmList.isEmpty()){
                    for(String hashToSubmit: contractPendingToSubmitList){
                        System.out.println("OPEN CONTRACT - Hash to confirm:\n"+hashToSubmit);
                        contractXML=openContractBusinessTransactionDao.getContractXML(hashToSubmit);
                        contractType=openContractBusinessTransactionDao.getContractType(hashToSubmit);
                        switch (contractType){
                            case PURCHASE:
                                purchaseContract=(ContractPurchaseRecord)XMLParser.parseXML(
                                        contractXML,
                                        purchaseContract);
                                transactionTransmissionManager.sendTransactionNewStatusNotification(
                                        purchaseContract.getPublicKeyCustomer(),
                                        purchaseContract.getPublicKeyBroker(),
                                        hashToSubmit,
                                        ContractTransactionStatus.CONTRACT_CONFIRMED);
                                break;
                            case SALE:
                                saleContract=(ContractSaleRecord)XMLParser.parseXML(
                                        contractXML,
                                        saleContract);
                                transactionTransmissionManager.sendTransactionNewStatusNotification(
                                        purchaseContract.getPublicKeyBroker(),
                                        purchaseContract.getPublicKeyCustomer(),
                                        hashToSubmit,
                                        ContractTransactionStatus.CONTRACT_CONFIRMED);
                                break;
                        }
                        //Update the ContractTransactionStatus
                        openContractBusinessTransactionDao.updateContractTransactionStatus(
                                hashToSubmit,
                                ContractTransactionStatus.CONTRACT_CONFIRMED);
                    }

                }

                /**
                 * Check if pending events
                 */
                List<String> pendingEventsIdList=openContractBusinessTransactionDao.getPendingEvents();
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
            }  catch (CantSendBusinessTransactionHashException e) {
                throw new CannotSendContractHashException(
                        e,
                        "Sending contract hash",
                        "Error in Transaction Transmission Network Service");
            }

        }

        private void checkPendingEvent(String eventId) throws  UnexpectedResultReturnedFromDatabaseException {
            try{
                String eventTypeCode=openContractBusinessTransactionDao.getEventType(eventId);
                String contractHash;
                String negotiationId;
                String negotiationIdFromDatabase;
                ContractType contractType;
                BusinessTransactionMetadata businessTransactionMetadata;
                ContractTransactionStatus contractTransactionStatus;
                if(eventTypeCode.equals(EventType.INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH.getCode())){
                    //Check if contract is created:
                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList=transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for(Transaction<BusinessTransactionMetadata> record : pendingTransactionList){
                        businessTransactionMetadata=record.getInformation();
                        contractHash=businessTransactionMetadata.getContractHash();
                        if(openContractBusinessTransactionDao.isContractHashExists(contractHash)){
                            negotiationId=businessTransactionMetadata.getNegotiationId();
                            negotiationIdFromDatabase=businessTransactionMetadata.getNegotiationId();
                            if(negotiationId.equals(negotiationIdFromDatabase)){
                                contractTransactionStatus=ContractTransactionStatus.PENDING_CONFIRMATION;
                            } else{
                                contractTransactionStatus=ContractTransactionStatus.HASH_REJECTED;
                            }
                            openContractBusinessTransactionDao.updateContractTransactionStatus(
                                    contractHash,
                                    contractTransactionStatus);
                            openContractBusinessTransactionDao.updateEventStatus(
                                    contractHash,
                                    EventStatus.NOTIFIED);
                            transactionTransmissionManager.confirmReception(record.getTransactionID());
                        }
                    }

                }

                if(eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT.getCode())){
                    //Check if contract hash was sent.
                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList=transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for(Transaction<BusinessTransactionMetadata> record : pendingTransactionList){
                        businessTransactionMetadata=record.getInformation();
                        contractHash=businessTransactionMetadata.getContractHash();
                        if(openContractBusinessTransactionDao.isContractHashSentConfirmation(contractHash)){
                            openContractBusinessTransactionDao.updateContractTransactionStatus(
                                    contractHash,
                                    ContractTransactionStatus.PENDING_RESPONSE);
                            openContractBusinessTransactionDao.updateEventStatus(
                                    contractHash,
                                    EventStatus.NOTIFIED);
                            transactionTransmissionManager.confirmReception(record.getTransactionID());
                        }
                    }

                }

                if(eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())){
                    //TODO: check if contract hash was sent.
                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList=transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for(Transaction<BusinessTransactionMetadata> record : pendingTransactionList){
                        businessTransactionMetadata=record.getInformation();
                        contractHash=businessTransactionMetadata.getContractHash();
                        if(openContractBusinessTransactionDao.isContractHashPendingResponse(contractHash)){
                            openContractBusinessTransactionDao.updateContractTransactionStatus(
                                    contractHash,
                                    ContractTransactionStatus.CONTRACT_OPENED);
                            openContractBusinessTransactionDao.updateEventStatus(
                                    contractHash,
                                    EventStatus.NOTIFIED);
                            contractType=openContractBusinessTransactionDao.getContractType(contractHash);
                            switch (contractType){
                                case PURCHASE:
                                    customerBrokerContractPurchaseManager.
                                            updateStatusCustomerBrokerPurchaseContractStatus(
                                                    contractHash,
                                                    ContractStatus.PENDING_PAYMENT);
                            }
                            transactionTransmissionManager.confirmReception(record.getTransactionID());
                            //TODO: raise an event.
                        }
                    }

                }
                //TODO: look a better way to deal with this exceptions
            } catch (CantDeliverPendingTransactionsException e) {
                e.printStackTrace();
            } catch (CantUpdateRecordException e) {
                e.printStackTrace();
            } catch (CantupdateCustomerBrokerContractPurchaseException e) {
                e.printStackTrace();
            } catch (CantConfirmTransactionException e) {
                e.printStackTrace();
            }

        }

    }

}
