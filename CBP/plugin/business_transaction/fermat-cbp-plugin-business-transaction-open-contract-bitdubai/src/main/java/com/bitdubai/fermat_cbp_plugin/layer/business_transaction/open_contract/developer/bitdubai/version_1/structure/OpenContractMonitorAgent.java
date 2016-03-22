package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.CantStartAgentException;
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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.events.NewContractOpened;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractPurchaseRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractSaleRecord;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantUpdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantConfirmNotificationReception;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendBusinessTransactionHashException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.OpenContractPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CannotSendContractHashException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


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
        this.logManager = logManager;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
    }

    @Override
    public void start() throws CantStartAgentException {

        //Logger LOG = Logger.getGlobal();
        //LOG.info("Open contract monitor agent starting");
        monitorAgent = new MonitorAgent();

        this.monitorAgent.setPluginDatabaseSystem(this.pluginDatabaseSystem);
        this.monitorAgent.setErrorManager(this.errorManager);

        try {
            this.monitorAgent.Initialize();
        } catch (CantInitializeCBPAgent exception) {
            errorManager.reportUnexpectedPluginException(Plugins.OPEN_CONTRACT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        } catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.OPEN_CONTRACT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(exception));
        }

        this.agentThread = new Thread(monitorAgent, this.getClass().getSimpleName());
        this.agentThread.start();

    }

    @Override
    public void stop() {
        try {
            this.agentThread.interrupt();
        } catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.OPEN_CONTRACT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
        }
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
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
    private class MonitorAgent implements DealsWithPluginDatabaseSystem, DealsWithErrors, Runnable {

        ErrorManager errorManager;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = 5000;
        int iterationNumber = 0;
        OpenContractBusinessTransactionDao openContractBusinessTransactionDao;
        boolean threadWorking;
        Map<UUID, UUID> transmissionSend = new HashMap<>();

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

                    logManager.log(OpenContractPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CannotSendContractHashException | CantUpdateRecordException | CantSendContractNewStatusNotificationException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.OPEN_CONTRACT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
                    errorManager.reportUnexpectedPluginException(
                            Plugins.OPEN_CONTRACT,
                            UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                            cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException,
                            "Initialize Monitor Agent - trying to create the plugin database",
                            "Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                errorManager.reportUnexpectedPluginException(
                        Plugins.OPEN_CONTRACT,
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

            try {
                openContractBusinessTransactionDao = new OpenContractBusinessTransactionDao(pluginDatabaseSystem, pluginId, database, errorManager);

                // Check if exist in database new contracts to send
                List<String> contractPendingToSubmitList = openContractBusinessTransactionDao.getPendingToSubmitContractHash();
                String contractXML;
                ContractPurchaseRecord purchaseContract = new ContractPurchaseRecord();
                ContractSaleRecord saleContract = new ContractSaleRecord();
                ContractType contractType;
                UUID transactionId;
                UUID transmissionId = UUID.randomUUID();
                
                if (!contractPendingToSubmitList.isEmpty()) {
                    for (String hashToSubmit : contractPendingToSubmitList) {
                        System.out.println("OPEN CONTRACT - Hash to submit:\n" + hashToSubmit);
                        contractXML = openContractBusinessTransactionDao.getContractXML(hashToSubmit);
                        contractType = openContractBusinessTransactionDao.getContractType(hashToSubmit);
                        transactionId = openContractBusinessTransactionDao.getTransactionId(hashToSubmit);

                        switch (contractType) {
                            case PURCHASE:
                                purchaseContract = (ContractPurchaseRecord) XMLParser.parseXML(contractXML, purchaseContract);
                                transactionTransmissionManager.sendContractHash(
//                                        transactionId,
                                        transmissionId,
                                        purchaseContract.getPublicKeyCustomer(),
                                        purchaseContract.getPublicKeyBroker(),
                                        hashToSubmit,
                                        purchaseContract.getNegotiationId(),
                                        Plugins.OPEN_CONTRACT,
                                        PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                                        PlatformComponentType.ACTOR_CRYPTO_BROKER);
                                transmissionSend.put(transmissionId,transactionId);
                                break;
                            case SALE:
                                saleContract = (ContractSaleRecord) XMLParser.parseXML(contractXML, saleContract);
                                transactionTransmissionManager.sendContractHash(
//                                        transactionId,
                                        transmissionId,
                                        saleContract.getPublicKeyBroker(),
                                        saleContract.getPublicKeyCustomer(),
                                        hashToSubmit,
                                        saleContract.getNegotiationId(),
                                        Plugins.OPEN_CONTRACT,
                                        PlatformComponentType.ACTOR_CRYPTO_BROKER,
                                        PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);
                                transmissionSend.put(transmissionId, transactionId);
                                break;
                        }
                        //Update the ContractTransactionStatus
                        openContractBusinessTransactionDao.updateContractTransactionStatus(hashToSubmit, ContractTransactionStatus.CHECKING_HASH);
                    }
                }

                // Check if pending contract to confirm
                List<String> contractPendingToConfirmList = openContractBusinessTransactionDao.getPendingToConfirmContractHash();
                if (!contractPendingToConfirmList.isEmpty()) {
                    //TODO YORDIN: era contractPendingToConfirmList no contractPendingToSubmitList
                    for (String hashToSubmit : contractPendingToConfirmList) {
                        System.out.println("OPEN CONTRACT - Hash to confirm:\n" + hashToSubmit);
                        transactionId = openContractBusinessTransactionDao.getTransactionId(hashToSubmit);
                        contractXML = openContractBusinessTransactionDao.getContractXML(hashToSubmit);
                        contractType = openContractBusinessTransactionDao.getContractType(hashToSubmit);
                        switch (contractType) {
                            case PURCHASE:
                                System.out.println("OPEN CONTRACT - Hash to confirm PURCHASE\n");
                                purchaseContract = (ContractPurchaseRecord) XMLParser.parseXML(contractXML, purchaseContract);
//                                transactionTransmissionManager.sendContractStatusNotification(
                                transactionTransmissionManager.confirmNotificationReception(
                                        purchaseContract.getPublicKeyCustomer(),
                                        purchaseContract.getPublicKeyBroker(),
                                        hashToSubmit,
//                                        transactionId.toString(),
                                        transmissionId.toString(),
//                                        ContractTransactionStatus.CONTRACT_CONFIRMED,
                                        Plugins.OPEN_CONTRACT,
                                        PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                                        PlatformComponentType.ACTOR_CRYPTO_BROKER);
                                transmissionSend.put(transmissionId, transactionId);
                                break;
                            case SALE:
                                System.out.println("OPEN CONTRACT - Hash to confirm SALE\n");
                                saleContract = (ContractSaleRecord) XMLParser.parseXML(contractXML, saleContract);
                                //transactionTransmissionManager.sendContractStatusNotification(
                                //TODO YORDIN: era saleContract.getPublicKeyBroker(), saleContract.getPublicKeyCustomer() no purchaseContract.getPublicKeyCustomer(), purchaseContract.getPublicKeyBroker()
                                transactionTransmissionManager.confirmNotificationReception(
                                        saleContract.getPublicKeyBroker(),
                                        saleContract.getPublicKeyCustomer(),
                                        hashToSubmit,
//                                        transactionId.toString(),
                                        transmissionId.toString(),
//                                        ContractTransactionStatus.CONTRACT_CONFIRMED,
                                        Plugins.OPEN_CONTRACT,
                                        PlatformComponentType.ACTOR_CRYPTO_BROKER,
                                        PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);
                                transmissionSend.put(transmissionId, transactionId);
                                break;
                        }
                        //Update the ContractTransactionStatus
                        openContractBusinessTransactionDao.updateContractTransactionStatus(hashToSubmit, ContractTransactionStatus.CONTRACT_ACK_CONFIRMED);
                    }

                    //TODO YORDIN: envia confimacion de recepcion de la confirmacion del contrato en la contraparte
                    // Check if pending contract to Ack confirm
                    List<String> contractPendingToAckConfirmList = openContractBusinessTransactionDao.getPendingToAskConfirmContractHash();
                    if (!contractPendingToAckConfirmList.isEmpty()) {
                        System.out.println("1 OPEN CONTRACT - Hash to Ack confirm:\n");
                        for (String hashToSubmit : contractPendingToAckConfirmList) {
                            System.out.println("2 OPEN CONTRACT - Hash to Ack confirm:\n" + hashToSubmit);
                            transactionId = openContractBusinessTransactionDao.getTransactionId(hashToSubmit);
                            contractXML = openContractBusinessTransactionDao.getContractXML(hashToSubmit);
                            contractType = openContractBusinessTransactionDao.getContractType(hashToSubmit);
                            switch (contractType) {
                                case PURCHASE:
                                    purchaseContract = (ContractPurchaseRecord) XMLParser.parseXML(contractXML, purchaseContract);
//                                transactionTransmissionManager.sendContractStatusNotification(
                                    System.out.println("3 OPEN CONTRACT - Hash to Ack confirm: PURCHASE\n");
                                    transactionTransmissionManager.ackConfirmNotificationReception(
                                            purchaseContract.getPublicKeyCustomer(),
                                            purchaseContract.getPublicKeyBroker(),
                                            hashToSubmit,
//                                            transactionId.toString(),
                                            transmissionId.toString(),
                                            Plugins.OPEN_CONTRACT,
                                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                                            PlatformComponentType.ACTOR_CRYPTO_BROKER);
                                    transmissionSend.put(transmissionId, transactionId);
                                    break;
                                case SALE:
                                    saleContract = (ContractSaleRecord) XMLParser.parseXML(contractXML, saleContract);
                                    //transactionTransmissionManager.sendContractStatusNotification(
                                    System.out.println("3 OPEN CONTRACT - Hash to Ack confirm SALE\n");
                                    transactionTransmissionManager.ackConfirmNotificationReception(
                                            saleContract.getPublicKeyBroker(),
                                            saleContract.getPublicKeyCustomer(),
                                            hashToSubmit,
//                                            transactionId.toString(),
                                            transmissionId.toString(),
                                            Plugins.OPEN_CONTRACT,
                                            PlatformComponentType.ACTOR_CRYPTO_BROKER,
                                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);
                                    transmissionSend.put(transmissionId, transactionId);
                                    break;
                            }
                            //Update the ContractTransactionStatus
                            openContractBusinessTransactionDao.updateContractTransactionStatus(hashToSubmit, ContractTransactionStatus.CONTRACT_CONFIRMED);
                        }
                    }

                }

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
            } catch (CantConfirmNotificationReception e){
                throw new CannotSendContractHashException(
                        e,
                        "Sending Confirm contract",
                        "Error in Transaction Transmission Network Service");

//            } catch (CantSendContractNewStatusNotificationException e) {
//                throw new CantSendContractNewStatusNotificationException(
//                        CantSendContractNewStatusNotificationException.DEFAULT_MESSAGE,
//                        e,
//                        "Sending contract hash",
//                        "Error in Transaction Transmission Network Service");
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
                String negotiationId;
                String negotiationIdFromDatabase;
                ContractType contractType;
                BusinessTransactionMetadata businessTransactionMetadata;
                ContractTransactionStatus contractTransactionStatus;

                String eventTypeCode = openContractBusinessTransactionDao.getEventType(eventId);

                List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);

                for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {

                    businessTransactionMetadata = record.getInformation();
                    contractHash = businessTransactionMetadata.getContractHash();
                    
                    if(transmissionSend.get(record.getTransactionID()) == null) {
                        //EVENT FOR CONTRAT HASH
                        if (eventTypeCode.equals(EventType.INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH.getCode())) {

                            if (openContractBusinessTransactionDao.isContractHashExists(contractHash)) {
                                System.out.print("\nINCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH - HASH" +
                                        "\nCOD MES: " + businessTransactionMetadata.getContractTransactionStatus().getCode() +
                                        "\nCOD REQ: " + ContractTransactionStatus.PENDING_REMOTE_CONFIRMATION.getCode() +
                                        "\n");

                                if (businessTransactionMetadata.getContractTransactionStatus().getCode().equals(ContractTransactionStatus.PENDING_REMOTE_CONFIRMATION.getCode())) {
                                    System.out.print("\nINCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH - HASH VAL\n");
                                    //TODO YORDIN: mismo contenido, nunca entrara al HASH_REJECTED.
                                    negotiationId = businessTransactionMetadata.getNegotiationId();
                                    negotiationIdFromDatabase = businessTransactionMetadata.getNegotiationId();

                                    if (negotiationId.equals(negotiationIdFromDatabase))
                                        contractTransactionStatus = ContractTransactionStatus.PENDING_CONFIRMATION;
                                    else
                                        contractTransactionStatus = ContractTransactionStatus.HASH_REJECTED;

                                    openContractBusinessTransactionDao.updateContractTransactionStatus(contractHash, contractTransactionStatus);
                                    openContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                                    transactionTransmissionManager.confirmReception(record.getTransactionID());
                                }
                            }

                        }

                        //EVENT FOR CONFIRM CONTRAT HASH
                        if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {

                            System.out.print("\nINCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - CONFIRMATION" +
                                    "\nCOD MES: " + businessTransactionMetadata.getContractTransactionStatus().getCode() +
                                    "\nCOD REQ: " + ContractTransactionStatus.NOTIFICATION_CONFIRMED.getCode() +
                                    "\n");
                            //                        if (openContractBusinessTransactionDao.isContractHashSentConfirmation(contractHash)) {
                            if (businessTransactionMetadata.getContractTransactionStatus().getCode().equals(ContractTransactionStatus.NOTIFICATION_CONFIRMED.getCode())) {
                                System.out.print("\nINCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - CONFIRMATION VAL\n");
                                openContractBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.PENDING_RESPONSE);
                                openContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                                transactionTransmissionManager.confirmReception(record.getTransactionID());
                            }

                        }

                        //EVENT FOR ACK CONFIRM CONTRAT HASH
                        if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT.getCode())) {

                            System.out.print("\nINCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - ACK CONFIRMATION" +
                                    "\nCOD MES: " + businessTransactionMetadata.getContractTransactionStatus().getCode() +
                                    "\nCOD REQ: " + ContractTransactionStatus.NOTIFICATION_ACK_CONFIRMED.getCode() +
                                    "\n");
                            //                        if (openContractBusinessTransactionDao.isContractHashPendingResponse(contractHash)) {
                            if (businessTransactionMetadata.getContractTransactionStatus().getCode().equals(ContractTransactionStatus.NOTIFICATION_ACK_CONFIRMED.getCode())) {
                                System.out.print("\nnINCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - ACK CONFIRMATION VAL\n");
                                openContractBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONTRACT_OPENED);
                                openContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                                contractType = openContractBusinessTransactionDao.getContractType(contractHash);
                                switch (contractType) {
                                    case PURCHASE:
                                        customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(contractHash,
                                                ContractStatus.PENDING_PAYMENT);
                                        break;
                                    case SALE:
                                        customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(contractHash,
                                                ContractStatus.PENDING_PAYMENT);
                                }
                                transactionTransmissionManager.confirmReception(record.getTransactionID());
                            }

                        }
                    } else {
                        transactionTransmissionManager.confirmReception(record.getTransactionID());
                    }
                }
                /*

                if (eventTypeCode.equals(EventType.INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH.getCode())) {
                    //Check if contract is created:
                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);

                    for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                        businessTransactionMetadata = record.getInformation();
                        contractHash = businessTransactionMetadata.getContractHash();
                        System.out.print("\nINCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH - Sending confirmation\n");
                        if (openContractBusinessTransactionDao.isContractHashExists(contractHash)) {
                            //TODO YORDIN: mismo contenido, nunca entrara al HASH_REJECTED.
                            negotiationId = businessTransactionMetadata.getNegotiationId();
                            negotiationIdFromDatabase = businessTransactionMetadata.getNegotiationId();

                            if (negotiationId.equals(negotiationIdFromDatabase))
                                contractTransactionStatus = ContractTransactionStatus.PENDING_CONFIRMATION;
                            else
                                contractTransactionStatus = ContractTransactionStatus.HASH_REJECTED;

                            openContractBusinessTransactionDao.updateContractTransactionStatus(contractHash, contractTransactionStatus);
                            openContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);

                            final UUID transactionId = businessTransactionMetadata.getTransactionId();
                            System.out.print("\nHASH transactionId: "+ transactionId + " record.getTransactionID()" + record.getTransactionID());
                            transactionTransmissionManager.confirmReception(record.getTransactionID());
                            //TODO YORDIN: se debe cambiar el estatus para que el agente persista el envio de la confirmacion en el metodo doTheMainTask() y no llamar al metodo confirmNotificationReception() aca
//                            transactionTransmissionManager.confirmNotificationReception(
//                                    businessTransactionMetadata.getSenderId(),
//                                    businessTransactionMetadata.getReceiverId(),
//                                    contractHash,
//                                    transactionId.toString(),
//                                    Plugins.OPEN_CONTRACT,
//                                    businessTransactionMetadata.getSenderType(),
//                                    businessTransactionMetadata.getReceiverType());
                        }
                    }
                }

                //TODO YORDIN: cambia el tipo de evento para mantener el estandar en las transacciones
//                if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT.getCode())) {
                if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {
                    //Check if contract hash was sent.
                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                        businessTransactionMetadata = record.getInformation();
                        contractHash = businessTransactionMetadata.getContractHash();
                        System.out.print("\nINCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - Sending confirmation\n");
                        if (openContractBusinessTransactionDao.isContractHashSentConfirmation(contractHash)) {
                            openContractBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.PENDING_RESPONSE);
                            openContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                            final UUID transactionId = businessTransactionMetadata.getTransactionId();
                            System.out.print("\nHASH CONFIRM transactionId: "+ transactionId + " record.getTransactionID()" + record.getTransactionID());
                            transactionTransmissionManager.confirmReception(record.getTransactionID());
                            //TODO YORDIN: se debe cambiar el estatus para que el agente persista el envio de la confirmacion en el metodo doTheMainTask() y no llamar al metodo ackConfirmNotificationReception() aca
//                            transactionTransmissionManager.ackConfirmNotificationReception(
//                                    businessTransactionMetadata.getSenderId(),
//                                    businessTransactionMetadata.getReceiverId(),
//                                    contractHash,
//                                    transactionId.toString(),
//                                    Plugins.OPEN_CONTRACT,
//                                    businessTransactionMetadata.getSenderType(),
//                                    businessTransactionMetadata.getReceiverType());
                        }
                    }

                }

                //TODO YORDIN: cambia el tipo de evento para mantener el estandar en las transacciones
//                if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {
                if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT.getCode())) {
                    //TODO: check if contract hash was sent.
                    System.out.print("\nINCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - CONTRACT_OPENED\n");
                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                        businessTransactionMetadata = record.getInformation();
                        contractHash = businessTransactionMetadata.getContractHash();

                        if (openContractBusinessTransactionDao.isContractHashPendingResponse(contractHash)) {
                            openContractBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONTRACT_OPENED);
                            openContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                            contractType = openContractBusinessTransactionDao.getContractType(contractHash);
                            switch (contractType) {
                                case PURCHASE:
                                    customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(contractHash,
                                            ContractStatus.PENDING_PAYMENT);
                                    break;
                                case SALE:
                                    customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(contractHash,
                                            ContractStatus.PENDING_PAYMENT);
                            }

                            final UUID transactionId = businessTransactionMetadata.getTransactionId();
                            System.out.print("\nACK CONFIRM transactionId: "+ transactionId + " record.getTransactionID()" + record.getTransactionID());
                            transactionTransmissionManager.confirmReception(record.getTransactionID());
                        }
                    }

                }

                */
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
//            } catch (CantConfirmNotificationReception e) {
//                throw new UnexpectedResultReturnedFromDatabaseException(
//                        e,
//                        "Checking pending transactions",
//                        "Cannot send the confirm notification reception message");
            }

        }

    }

}
