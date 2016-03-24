package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure;

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
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

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

        private void doTheMainTask() throws CannotSendContractHashException, CantUpdateRecordException, CantSendContractNewStatusNotificationException {

            try {
                openContractBusinessTransactionDao = new OpenContractBusinessTransactionDao(pluginDatabaseSystem, pluginId, database, errorManager);


                String contractXML;
                ContractPurchaseRecord purchaseContract = new ContractPurchaseRecord();
                ContractSaleRecord saleContract = new ContractSaleRecord();
                ContractType contractType;
                UUID transmissionId = UUID.randomUUID();

                // Check if exist in database new contracts to send
                List<String> contractPendingToSubmitList = openContractBusinessTransactionDao.getPendingToSubmitContractHash();
                if (!contractPendingToSubmitList.isEmpty()) {
                    for (String hashToSubmit : contractPendingToSubmitList) {
                        System.out.println("OPEN CONTRACT - Hash to submit:\n" + hashToSubmit);
                        contractXML = openContractBusinessTransactionDao.getContractXML(hashToSubmit);
                        contractType = openContractBusinessTransactionDao.getContractType(hashToSubmit);

                        switch (contractType) {
                            case PURCHASE:
                                purchaseContract = (ContractPurchaseRecord) XMLParser.parseXML(contractXML, purchaseContract);
                                transactionTransmissionManager.sendContractHash(
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
                                saleContract = (ContractSaleRecord) XMLParser.parseXML(contractXML, saleContract);
                                transactionTransmissionManager.sendContractHash(
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
                List<String> contractPendingToConfirmList = openContractBusinessTransactionDao.getPendingToConfirmContractHash();
                if (!contractPendingToConfirmList.isEmpty()) {
                    for (String hashToSubmit : contractPendingToConfirmList) {
                        System.out.println("OPEN CONTRACT - Hash to confirm:\n" + hashToSubmit);
                        contractXML = openContractBusinessTransactionDao.getContractXML(hashToSubmit);
                        contractType = openContractBusinessTransactionDao.getContractType(hashToSubmit);
                        switch (contractType) {
                            case PURCHASE:
                                System.out.println("OPEN CONTRACT - Hash to confirm PURCHASE\n");
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
                                System.out.println("OPEN CONTRACT - Hash to confirm SALE\n");
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

                }

                // Check if pending contract to Ack confirm
                List<String> contractPendingToAckConfirmList = openContractBusinessTransactionDao.getPendingToAskConfirmContractHash();
                if (!contractPendingToAckConfirmList.isEmpty()) {
                    for (String hashToSubmit : contractPendingToAckConfirmList) {
                        contractXML = openContractBusinessTransactionDao.getContractXML(hashToSubmit);
                        contractType = openContractBusinessTransactionDao.getContractType(hashToSubmit);
                        switch (contractType) {
                            case PURCHASE:
                                purchaseContract = (ContractPurchaseRecord) XMLParser.parseXML(contractXML, purchaseContract);
                                System.out.println("3 OPEN CONTRACT - Hash to Ack confirm: PURCHASE");
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
                                System.out.println("3 OPEN CONTRACT - Hash to Ack confirm SALE");

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
            } catch (CantConfirmNotificationReception e) {
                throw new CannotSendContractHashException(
                        e,
                        "Sending Confirm contract",
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

                    //EVENT FOR CONTRACT HASH
                    if (eventTypeCode.equals(EventType.INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH.getCode())) {

                        if (openContractBusinessTransactionDao.isContractHashExists(contractHash)) {

                            System.out.println("INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH - HASH" +
                                    "\nCOD MES: " + businessTransactionMetadata.getContractTransactionStatus().getCode() +
                                    "\nCOD REQ: " + ContractTransactionStatus.PENDING_REMOTE_CONFIRMATION.getCode());

                            if (businessTransactionMetadata.getContractTransactionStatus() == ContractTransactionStatus.PENDING_REMOTE_CONFIRMATION) {
                                System.out.println("INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH - HASH VAL");

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

                    //EVENT FOR CONFIRM CONTRACT HASH
                    if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {

                        System.out.println("INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - CONFIRMATION" +
                                "\nCOD MES: " + businessTransactionMetadata.getContractTransactionStatus().getCode() +
                                "\nCOD REQ: " + ContractTransactionStatus.NOTIFICATION_CONFIRMED.getCode());

                        if (businessTransactionMetadata.getContractTransactionStatus() == ContractTransactionStatus.NOTIFICATION_CONFIRMED) {
                            System.out.println("INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - CONFIRMATION VAL");

                            openContractBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.PENDING_RESPONSE);
                            openContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                            transactionTransmissionManager.confirmReception(record.getTransactionID());
                        }

                    }

                    //EVENT FOR ACK CONFIRM CONTRACT HASH
                    if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT.getCode())) {

                        System.out.println("INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - ACK CONFIRMATION" +
                                "\nCOD MES: " + businessTransactionMetadata.getContractTransactionStatus().getCode() +
                                "\nCOD REQ: " + ContractTransactionStatus.NOTIFICATION_ACK_CONFIRMED.getCode());

                        if (businessTransactionMetadata.getContractTransactionStatus() == ContractTransactionStatus.NOTIFICATION_ACK_CONFIRMED) {
                            System.out.println("INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - ACK CONFIRMATION VAL");

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
                                    break;
                            }
                            transactionTransmissionManager.confirmReception(record.getTransactionID());

                            if (businessTransactionMetadata.getReceiverType() == PlatformComponentType.ACTOR_CRYPTO_BROKER)
                                raiseNewContractEvent(contractHash);
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
            }
        }

    }

}
