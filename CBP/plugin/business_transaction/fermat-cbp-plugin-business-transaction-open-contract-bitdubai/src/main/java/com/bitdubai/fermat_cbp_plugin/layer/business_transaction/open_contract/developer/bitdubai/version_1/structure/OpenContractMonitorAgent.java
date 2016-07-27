package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CannotSendContractHashException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.events.NewContractOpened;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractPurchaseRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractSaleRecord;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantUpdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantConfirmNotificationReceptionException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendBusinessTransactionHashException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.OpenContractPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;

import java.util.List;
import java.util.UUID;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/12/15.
 */
public class OpenContractMonitorAgent implements
        CBPTransactionAgent,
        DealsWithLogger,
        DealsWithEvents,
        DealsWithPluginDatabaseSystem,
        DealsWithPluginIdentity {

    Database database;
    MonitorAgent monitorAgent;
    Thread agentThread;
    LogManager logManager;
    EventManager eventManager;
    OpenContractPluginRoot pluginRoot;
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;
    TransactionTransmissionManager transactionTransmissionManager;
    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    public OpenContractMonitorAgent(PluginDatabaseSystem pluginDatabaseSystem,
                                    LogManager logManager,
                                    OpenContractPluginRoot pluginRoot,
                                    EventManager eventManager,
                                    UUID pluginId,
                                    TransactionTransmissionManager transactionTransmissionManager,
                                    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
                                    CustomerBrokerContractSaleManager customerBrokerContractSaleManager) throws CantSetObjectException {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginRoot = pluginRoot;
        this.pluginId = pluginId;
        this.logManager = logManager;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
    }

    @Override
    public void start() throws CantStartAgentException {

        //Logger LOG = Logger.getGlobal();
        //LOG.info("Open contract monitor agent starting");
        monitorAgent = new MonitorAgent(pluginRoot);

        this.monitorAgent.setPluginDatabaseSystem(this.pluginDatabaseSystem);

        try {
            this.monitorAgent.Initialize();
        } catch (CantInitializeCBPAgent exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        } catch (Exception exception) {
            this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(exception));
        }

        this.agentThread = new Thread(monitorAgent, this.getClass().getSimpleName());
        this.agentThread.start();

    }

    @Override
    public void stop() {
        try {
            this.agentThread.interrupt();
        } catch (Exception exception) {
            this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
        }
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
    private class MonitorAgent implements DealsWithPluginDatabaseSystem, Runnable {

        OpenContractPluginRoot pluginRoot;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = 5000;
        int iterationNumber = 0;
        OpenContractBusinessTransactionDao openContractBusinessTransactionDao;
        boolean threadWorking;

        public MonitorAgent(OpenContractPluginRoot pluginRoot) {
            this.pluginRoot = pluginRoot;
        }

        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        @Override
        public void run() {

            threadWorking = true;
            logManager.log(OpenContractPluginRoot.getLogLevelByClass(this.getClass().getName()),
                    "Open Contract Monitor Agent: running...", null, null);
            while (threadWorking) {
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

                    logManager.log(OpenContractPluginRoot.getLogLevelByClass(this.getClass().getName()), new StringBuilder().append("Iteration number ").append(iterationNumber).toString(), null, null);
                    doTheMainTask();
                } catch (CannotSendContractHashException | CantUpdateRecordException | CantSendContractNewStatusNotificationException e) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }

            }

        }

        public void Initialize() throws CantInitializeCBPAgent {
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId,
                        OpenContractBusinessTransactionDatabaseConstants.DATABASE_NAME);
            } catch (DatabaseNotFoundException databaseNotFoundException) {

                //Logger LOG = Logger.getGlobal();
                //LOG.info("Database in Open Contract monitor agent doesn't exists");
                OpenContractBusinessTransactionDatabaseFactory openContractBusinessTransactionDatabaseFactory = new OpenContractBusinessTransactionDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = openContractBusinessTransactionDatabaseFactory.createDatabase(pluginId,
                            OpenContractBusinessTransactionDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException,
                            "Initialize Monitor Agent - trying to create the plugin database",
                            "Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantInitializeCBPAgent(exception,
                        "Initialize Monitor Agent - trying to open the plugin database",
                        "Please, check the cause");
            }
        }

        private void doTheMainTask() throws CannotSendContractHashException, CantUpdateRecordException, CantSendContractNewStatusNotificationException {

            try {
                openContractBusinessTransactionDao = new OpenContractBusinessTransactionDao(pluginDatabaseSystem, pluginId, database, pluginRoot);

                String contractXML;
                ContractPurchaseRecord purchaseContract = new ContractPurchaseRecord();
                ContractSaleRecord saleContract = new ContractSaleRecord();
                ContractType contractType;
                UUID transmissionId = UUID.randomUUID();

                // Check if exist in database new contracts to send
                List<String> contractPendingToSubmitList = openContractBusinessTransactionDao.getPendingToSubmitContractHash();
                if (!contractPendingToSubmitList.isEmpty()) {
                    for (String hashToSubmit : contractPendingToSubmitList) {
                        contractXML = openContractBusinessTransactionDao.getContractXML(hashToSubmit);
                        contractType = openContractBusinessTransactionDao.getContractType(hashToSubmit);

                        System.out.println(new StringBuilder().append("\nTEST CONTRACT - OPEN CONTRACT - AGENT - doTheMainTask() - getPendingToSubmitContractHash() - contractType: ").append(contractType).append("\n").toString());

                        switch (contractType) {
                            case PURCHASE:
                                System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - doTheMainTask() - getPendingToSubmitContractHash() - PURCHASE\n");
                                purchaseContract = (ContractPurchaseRecord) XMLParser.parseXML(contractXML, purchaseContract);
                                transactionTransmissionManager.sendContractHash(
                                        transmissionId,
                                        transmissionId,
                                        purchaseContract.getPublicKeyCustomer(),
                                        purchaseContract.getPublicKeyBroker(),
                                        hashToSubmit,
                                        purchaseContract.getNegotiationId(),
                                        Plugins.OPEN_CONTRACT,
                                        PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                                        PlatformComponentType.ACTOR_CRYPTO_BROKER);
                                break;
                            case SALE:
                                System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - doTheMainTask() - getPendingToSubmitContractHash() - SALE\n");
                                saleContract = (ContractSaleRecord) XMLParser.parseXML(contractXML, saleContract);
                                transactionTransmissionManager.sendContractHash(
                                        transmissionId,
                                        transmissionId,
                                        saleContract.getPublicKeyBroker(),
                                        saleContract.getPublicKeyCustomer(),
                                        hashToSubmit,
                                        saleContract.getNegotiationId(),
                                        Plugins.OPEN_CONTRACT,
                                        PlatformComponentType.ACTOR_CRYPTO_BROKER,
                                        PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);
                                break;
                        }
                        //Update the ContractTransactionStatus
                        openContractBusinessTransactionDao.updateContractTransactionStatus(hashToSubmit, ContractTransactionStatus.CHECKING_HASH);
                        transactionTransmissionManager.confirmReception(transmissionId);
                    }
                }

                // Check if pending contract to confirm
                /*List<String> contractPendingToConfirmList = openContractBusinessTransactionDao.getPendingToConfirmContractHash();
                if (!contractPendingToConfirmList.isEmpty()) {
                    System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - doTheMainTask() - getPendingToConfirmContractHash()\n");
                    for (String hashToSubmit : contractPendingToConfirmList) {
                        contractXML = openContractBusinessTransactionDao.getContractXML(hashToSubmit);
                        contractType = openContractBusinessTransactionDao.getContractType(hashToSubmit);
                        switch (contractType) {
                            case PURCHASE:
                                System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - doTheMainTask() - getPendingToConfirmContractHash() - PURCHASE\n");
                                purchaseContract = (ContractPurchaseRecord) XMLParser.parseXML(contractXML, purchaseContract);
                                transactionTransmissionManager.confirmNotificationReception(
                                        purchaseContract.getPublicKeyCustomer(),
                                        purchaseContract.getPublicKeyBroker(),
                                        hashToSubmit,
                                        transmissionId.toString(),
                                        Plugins.OPEN_CONTRACT,
                                        PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                                        PlatformComponentType.ACTOR_CRYPTO_BROKER);
                                break;
                            case SALE:
                                System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - doTheMainTask() - getPendingToConfirmContractHash() - SALE\n");
                                saleContract = (ContractSaleRecord) XMLParser.parseXML(contractXML, saleContract);
                                transactionTransmissionManager.confirmNotificationReception(
                                        saleContract.getPublicKeyBroker(),
                                        saleContract.getPublicKeyCustomer(),
                                        hashToSubmit,
                                        transmissionId.toString(),
                                        Plugins.OPEN_CONTRACT,
                                        PlatformComponentType.ACTOR_CRYPTO_BROKER,
                                        PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);
                                break;
                        }
                        //Update the ContractTransactionStatus
                        openContractBusinessTransactionDao.updateContractTransactionStatus(hashToSubmit, ContractTransactionStatus.CONTRACT_ACK_CONFIRMED);
                        transactionTransmissionManager.confirmReception(transmissionId);
                    }

                }*/

                // Check if pending contract to Ack confirm
                /*List<String> contractPendingToAckConfirmList = openContractBusinessTransactionDao.getPendingToAskConfirmContractHash();
                if (!contractPendingToAckConfirmList.isEmpty()) {
                    System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - doTheMainTask() - getPendingToAskConfirmContractHash()\n");
                    for (String hashToSubmit : contractPendingToAckConfirmList) {
                        contractXML = openContractBusinessTransactionDao.getContractXML(hashToSubmit);
                        contractType = openContractBusinessTransactionDao.getContractType(hashToSubmit);
                        switch (contractType) {
                            case PURCHASE:
                                purchaseContract = (ContractPurchaseRecord) XMLParser.parseXML(contractXML, purchaseContract);
                                System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - doTheMainTask() - getPendingToAskConfirmContractHash() - PURCHASE\n");
                                transactionTransmissionManager.ackConfirmNotificationReception(
                                        purchaseContract.getPublicKeyCustomer(),
                                        purchaseContract.getPublicKeyBroker(),
                                        hashToSubmit,
                                        transmissionId.toString(),
                                        Plugins.OPEN_CONTRACT,
                                        PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                                        PlatformComponentType.ACTOR_CRYPTO_BROKER);
                                break;
                            case SALE:
                                saleContract = (ContractSaleRecord) XMLParser.parseXML(contractXML, saleContract);
                                System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - doTheMainTask() - getPendingToAskConfirmContractHash() - SALE\n");

                                transactionTransmissionManager.ackConfirmNotificationReception(
                                        saleContract.getPublicKeyBroker(),
                                        saleContract.getPublicKeyCustomer(),
                                        hashToSubmit,
                                        transmissionId.toString(),
                                        Plugins.OPEN_CONTRACT,
                                        PlatformComponentType.ACTOR_CRYPTO_BROKER,
                                        PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);
                                break;
                        }
                        //Update the ContractTransactionStatus
                        openContractBusinessTransactionDao.updateContractTransactionStatus(hashToSubmit, ContractTransactionStatus.CONTRACT_CONFIRMED);
                        transactionTransmissionManager.confirmReception(transmissionId);
                    }
                }*/

                // Check if pending events
                List<String> pendingEventsIdList = openContractBusinessTransactionDao.getPendingEvents();
                for (String eventId : pendingEventsIdList) {
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
            } catch (CantSendBusinessTransactionHashException e) {
                throw new CannotSendContractHashException(
                        e,
                        "Sending contract hash",
                        "Error in Transaction Transmission Network Service");

            } catch (CantConfirmTransactionException e) {
                throw new CannotSendContractHashException(
                        e,
                        "Confirm Reception contract",
                        "Error in Transaction Transmission Network Service");
            }
        }

        private void raiseNewContractEvent(String contractHash) {
            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.NEW_CONTRACT_OPENED);
            NewContractOpened newContractOpened = (NewContractOpened) fermatEvent;
            newContractOpened.setSource(EventSource.BUSINESS_TRANSACTION_OPEN_CONTRACT);
            newContractOpened.setContractHash(contractHash);
            eventManager.raiseEvent(newContractOpened);
        }

        private void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {
            try {
                String contractHash;
                ContractType contractType;
                BusinessTransactionMetadata businessTransactionMetadata;
                ContractTransactionStatus contractTransactionStatusRemote;

                String eventTypeCode = openContractBusinessTransactionDao.getEventType(eventId);

                List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);

                for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {

                    businessTransactionMetadata = record.getInformation();
                    contractHash = businessTransactionMetadata.getContractHash();
                    contractTransactionStatusRemote = businessTransactionMetadata.getContractTransactionStatus();
                    UUID transmissionId = UUID.randomUUID();

                    if (businessTransactionMetadata.getRemoteBusinessTransaction().getCode().equals(Plugins.OPEN_CONTRACT.getCode())) {

                        System.out.print(new StringBuilder().append("\nTEST CONTRACT - OPEN CONTRACT - AGENT - checkPendingEvent() - EVENT - TYPE: ").append(eventTypeCode).append("\n").toString());

                        //EVENT FOR CONTRACT HASH
                        if (eventTypeCode.equals(EventType.INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH.getCode())) {

                            if (contractTransactionStatusRemote.getCode().equals(ContractTransactionStatus.PENDING_REMOTE_CONFIRMATION.getCode())) {

                                System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - checkPendingEvent() - INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH\n");

                                if (openContractBusinessTransactionDao.isContractHashExists(contractHash)) {

                                    System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - checkPendingEvent() - INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH - HASH - VAL\n");

                                    //SEND CONFIRM RECEPTION HASH
                                    transactionTransmissionManager.confirmNotificationReception(
                                            businessTransactionMetadata.getReceiverId(),
                                            businessTransactionMetadata.getSenderId(),
                                            contractHash,
                                            transmissionId.toString(),
                                            Plugins.OPEN_CONTRACT,
                                            businessTransactionMetadata.getReceiverType(),
                                            businessTransactionMetadata.getSenderType()
                                    );

                                    //CHANGE STATUS TRANSACTION
                                    openContractBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONTRACT_ACK_CONFIRMED);

                                    //CONFIRM TRANSMISSION OF SEND
                                    transactionTransmissionManager.confirmReception(transmissionId);

                                    //CONFIRM RECEPTION OF TRANSMISSION
                                    transactionTransmissionManager.confirmReception(record.getTransactionID());

                                    //CONFIRM RECEPTION OF NOTIFICATION EVENT
                                    openContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);

                                }
                            }
                        }

                        //EVENT FOR CONFIRM CONTRACT HASH
                        if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {

                            System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - CONFIRMATION\n");

                            if (contractTransactionStatusRemote.getCode().equals(ContractTransactionStatus.NOTIFICATION_CONFIRMED.getCode())) {

                                System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - CONFIRMATION - VAL\n");

                                //SEND CONFIRM RECEPTION HASH
                                transactionTransmissionManager.ackConfirmNotificationReception(
                                        businessTransactionMetadata.getReceiverId(),
                                        businessTransactionMetadata.getSenderId(),
                                        contractHash,
                                        transmissionId.toString(),
                                        transmissionId,
                                        Plugins.OPEN_CONTRACT,
                                        businessTransactionMetadata.getReceiverType(),
                                        businessTransactionMetadata.getSenderType()
                                );

                                //CHANGE STATUS TRANSACTION
                                openContractBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONTRACT_CONFIRMED);

                                //CONFIRM SEND OF TRANSMISSION
                                transactionTransmissionManager.confirmReception(transmissionId);

                                //CONFIRM RECEPTION OF TRANSMISSION
                                transactionTransmissionManager.confirmReception(record.getTransactionID());

                                //CONFIRM RECEPTION OF NOTIFICATION EVENT
                                openContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                            }

                        }

                        //EVENT FOR ACK CONFIRM CONTRACT HASH
                        if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT.getCode())) {

                            System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - ACK CONFIRMATION\n");

                            if (contractTransactionStatusRemote.getCode().equals(ContractTransactionStatus.NOTIFICATION_ACK_CONFIRMED.getCode())) {

                                System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - ACK CONFIRMATION - VAL\n");
                                openContractBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONTRACT_OPENED);
                                contractType = openContractBusinessTransactionDao.getContractType(contractHash);
                                switch (contractType) {
                                    case PURCHASE:
                                        CustomerBrokerContractPurchase contractPurchase = customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(contractHash);
                                        if (!contractPurchase.getStatus().getCode().equals(ContractStatus.CANCELLED.getCode())) {
                                            customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(contractHash,
                                                    ContractStatus.PENDING_PAYMENT);
                                        }
                                        break;
                                    case SALE:
                                        CustomerBrokerContractSale contractSale = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
                                        if (!contractSale.getStatus().getCode().equals(ContractStatus.CANCELLED.getCode())) {
                                            customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(contractHash,
                                                    ContractStatus.PENDING_PAYMENT);
                                        }
                                        break;
                                }

                                //CONFIRM RECEPTION OF TRANSMISSION
                                transactionTransmissionManager.confirmReception(record.getTransactionID());

                                //CONFIRM RECEPTION OF NOTIFICATION EVENT
                                openContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);

                                //RAISE EVENT NEW_CONTRACT_OPENED
                                if (businessTransactionMetadata.getReceiverType() == PlatformComponentType.ACTOR_CRYPTO_BROKER)
                                    raiseNewContractEvent(contractHash);
                            }

                        }
                    }
                }

                //TODO: look a better way to deal with this exceptions
            } catch (CantDeliverPendingTransactionsException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        e,
                        "Checking pending transactions",
                        "Cannot deliver pending transaction");
            } catch (CantUpdateRecordException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        e,
                        "Checking pending transactions",
                        "Cannot update the database record");
            } catch (CantUpdateCustomerBrokerContractPurchaseException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        e,
                        "Checking pending transactions",
                        "Cannot update the purchase contract");
            } catch (CantGetListCustomerBrokerContractPurchaseException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        e,
                        "Checking pending transactions",
                        "Cannot update the purchase contract");
            } catch (CantConfirmTransactionException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        e,
                        "Checking pending transactions",
                        "Cannot confirm transaction");
            } catch (CantUpdateCustomerBrokerContractSaleException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        e,
                        "Checking pending transactions",
                        "Cannot update the sale contract");
            } catch (CantGetListCustomerBrokerContractSaleException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        e,
                        "Checking pending transactions",
                        "Cannot update the sale contract");
            } catch (CantConfirmNotificationReceptionException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        e,
                        "Sending Confirm contract",
                        "Error in Transaction Transmission Network Service");
            }
        }

    }

}
