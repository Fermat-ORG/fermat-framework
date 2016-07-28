package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Owner;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.AbstractBusinessTransactionAgent;
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
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantConfirmNotificationReceptionException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.OpenContractPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDao;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_ACTIVITY_TO_OPEN_CODE;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_NOTIFICATION_PAINTER_FROM;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_TO_OPEN_PUBLIC_KEY;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.NOTIFICATION_ID;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.SOURCE_PLUGIN;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/07/16.
 */
public class OpenContractMonitorAgent2
        extends AbstractBusinessTransactionAgent<OpenContractPluginRoot> {

    private final OpenContractBusinessTransactionDao openContractBusinessTransactionDao;
    private final TransactionTransmissionManager transactionTransmissionManager;
    private final CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    private final CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    private final CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;
    private final CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;
    private final Broadcaster broadcaster;

    /**
     * Default constructor with parameters
     *
     * @param sleepTime
     * @param timeUnit
     * @param initDelayTime
     * @param pluginRoot
     * @param eventManager
     * @param openContractBusinessTransactionDao
     * @param transactionTransmissionManager
     * @param customerBrokerContractPurchaseManager
     * @param customerBrokerContractSaleManager
     */
    public OpenContractMonitorAgent2(
            long sleepTime,
            TimeUnit timeUnit,
            long initDelayTime,
            OpenContractPluginRoot pluginRoot,
            EventManager eventManager,
            OpenContractBusinessTransactionDao openContractBusinessTransactionDao,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
            Broadcaster broadcaster) {
        super(sleepTime, timeUnit, initDelayTime, pluginRoot, eventManager);
        this.openContractBusinessTransactionDao = openContractBusinessTransactionDao;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
        this.broadcaster = broadcaster;

    }

    @Override
    protected void doTheMainTask() {
        try {
            String contractXML;
            ContractPurchaseRecord purchaseContract = new ContractPurchaseRecord();
            ContractSaleRecord saleContract = new ContractSaleRecord();
            ContractType contractType;
            UUID transmissionId;
            UUID transactionContractId;

            // Check if exist in database new contracts to send
            List<String> contractPendingToSubmitList = openContractBusinessTransactionDao.getPendingToSubmitContractHash();
            if (!contractPendingToSubmitList.isEmpty()) {
                for (String hashToSubmit : contractPendingToSubmitList) {
                    try {
                        contractXML             = openContractBusinessTransactionDao.getContractXML(hashToSubmit);
                        contractType            = openContractBusinessTransactionDao.getContractType(hashToSubmit);
                        transactionContractId   = openContractBusinessTransactionDao.getTransactionId(hashToSubmit);
                        transmissionId          = UUID.randomUUID();

                        System.out.println(new StringBuilder().append("OPEN_CONTRACT - AGENT - doTheMainTask() - getPendingToSubmitContractHash() - contractType: ").append(contractType).append("\n").toString());

                        switch (contractType) {
                            case PURCHASE:
                                System.out.println(new StringBuilder()
                                        .append("OPEN_CONTRACT - AGENT - doTheMainTask() - getPendingToSubmitContractHash() - PURCHASE\n")
                                        .append("\n - contractHash: ").append(hashToSubmit));

                                purchaseContract = (ContractPurchaseRecord) XMLParser.parseXML(contractXML, purchaseContract);
                                transactionTransmissionManager.sendContractHash(
                                        transmissionId,
                                        transactionContractId,
                                        purchaseContract.getPublicKeyCustomer(),
                                        purchaseContract.getPublicKeyBroker(),
                                        hashToSubmit,
                                        purchaseContract.getNegotiationId(),
                                        Plugins.OPEN_CONTRACT,
                                        PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                                        PlatformComponentType.ACTOR_CRYPTO_BROKER);
                                break;
                            case SALE:
                                System.out.println(new StringBuilder()
                                        .append("OPEN_CONTRACT - AGENT - doTheMainTask() - getPendingToSubmitContractHash() - SALE\n")
                                        .append("\n - contractHash: ").append(hashToSubmit));

                                saleContract = (ContractSaleRecord) XMLParser.parseXML(contractXML, saleContract);
                                transactionTransmissionManager.sendContractHash(
                                        transmissionId,
                                        transactionContractId,
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

                    } catch (Exception e) {
                        reportError(e);
                    }
                }
            }

            // Check if pending events
            List<String> pendingEventsIdList = openContractBusinessTransactionDao.getPendingEvents();
            for (String eventId : pendingEventsIdList) {
                checkPendingEvent(eventId);
            }

        } catch (Exception e) {
            reportError(e);
        }
    }

    /*@Override
    protected void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {

        try {

            UUID transmissionId;
            BusinessTransactionMetadata businessTransactionMetadata;

            String eventTypeCode = openContractBusinessTransactionDao.getEventType(eventId);

            //******************    CONTRACT HASH
            if (eventTypeCode.equals(EventType.INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH.getCode())) {

                List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);

                for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {

                    businessTransactionMetadata         = record.getInformation();
                    Plugins remoteBusinessTransaction   = businessTransactionMetadata.getRemoteBusinessTransaction();
                    transmissionId                      = record.getTransactionID();

                    System.out.println(new StringBuilder().append("OPEN_CONTRACT - remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());
                    if (remoteBusinessTransaction != Plugins.OPEN_CONTRACT)
                        continue;

                    System.out.println(new StringBuilder().append("OPEN_CONTRACT - PASS remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());

                    if(processContractHash(businessTransactionMetadata)){

                        //CONFIRM RECEPTION OF TRANSMISSION
                        transactionTransmissionManager.confirmReception(transmissionId);
                        break;

                    }

                }

                //CONFIRM RECEPTION OF NOTIFICATION EVENT
                openContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);

            }

            //******************  CONFIRM CONTRACT HASH
            if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {

                List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);

                for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {

                    businessTransactionMetadata         = record.getInformation();
                    Plugins remoteBusinessTransaction   = businessTransactionMetadata.getRemoteBusinessTransaction();
                    transmissionId                      = record.getTransactionID();

                    System.out.println(new StringBuilder().append("OPEN_CONTRACT - remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());
                    if (remoteBusinessTransaction != Plugins.OPEN_CONTRACT)
                        continue;

                    System.out.println(new StringBuilder().append("OPEN_CONTRACT - PASS remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());

                    if(processConfirmContractHash(businessTransactionMetadata)){

                        //CONFIRM RECEPTION OF TRANSMISSION
                        transactionTransmissionManager.confirmReception(transmissionId);
                        break;

                    }

                }

                //CONFIRM RECEPTION OF NOTIFICATION EVENT
                openContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);

            }

            //*****************************ACK CONFIRM CONTRACT HASH
            if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT.getCode())) {

                List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);

                for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {

                    businessTransactionMetadata         = record.getInformation();
                    Plugins remoteBusinessTransaction   = businessTransactionMetadata.getRemoteBusinessTransaction();
                    transmissionId                      = record.getTransactionID();

                    System.out.println(new StringBuilder().append("OPEN_CONTRACT - remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());
                    if (remoteBusinessTransaction != Plugins.OPEN_CONTRACT)
                        continue;

                    System.out.println(new StringBuilder().append("OPEN_CONTRACT - PASS remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());

                    if(processAckContractHash(businessTransactionMetadata)){

                        //CONFIRM RECEPTION OF TRANSMISSION
                        transactionTransmissionManager.confirmReception(transmissionId);
                        break;

                    }

                }

                //CONFIRM RECEPTION OF NOTIFICATION EVENT
                openContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);

            }

        } catch (CantDeliverPendingTransactionsException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot deliver pending transaction");
        } catch (CantConfirmTransactionException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot confirm transaction");
        } catch (CantUpdateRecordException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot update the database record");
        }
    }*/

    @Override
    protected void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {

        try {

            UUID transmissionId;
            BusinessTransactionMetadata businessTransactionMetadata;

            String eventTypeCode = openContractBusinessTransactionDao.getEventType(eventId);

            List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);

            for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {

                businessTransactionMetadata = record.getInformation();
                transmissionId              = record.getTransactionID();
                Plugins businessTransaction = businessTransactionMetadata.getRemoteBusinessTransaction();

                System.out.println(new StringBuilder().append("OPEN_CONTRACT - remoteBusinessTransaction = ").append(businessTransaction).toString());
                if (businessTransaction != Plugins.OPEN_CONTRACT)
                    continue;
                System.out.println(new StringBuilder().append("OPEN_CONTRACT - PASS remoteBusinessTransaction = ").append(businessTransaction).toString());


                //************** CONTRACT HASH
                if (eventTypeCode.equals(EventType.INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH.getCode())) {

                    if (processContractHash(businessTransactionMetadata)) {

                        //CONFIRM RECEPTION OF TRANSMISSION
                        transactionTransmissionManager.confirmReception(transmissionId);
                        System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() [processContractHash] - Transaction transmission marked as CONFIRMED\n");
                        break;

                    }

                }

                //************** CONFIRM CONTRACT HASH
                if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {

                    if (processConfirmContractHash(businessTransactionMetadata)) {

                        //CONFIRM RECEPTION OF TRANSMISSION
                        transactionTransmissionManager.confirmReception(transmissionId);
                        System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() [processConfirmContractHash] - Transaction transmission marked as CONFIRMED\n");
                        break;

                    }

                }

                //************** ACK CONFIRM CONTRACT HASH
                if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT.getCode())) {

                    if (processAckContractHash(businessTransactionMetadata)) {

                        //CONFIRM RECEPTION OF TRANSMISSION
                        transactionTransmissionManager.confirmReception(transmissionId);
                        System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() [processAckContractHash] - Transaction transmission marked as CONFIRMED\n");
                        break;

                    }

                }
            }

            //CONFIRM RECEPTION OF NOTIFICATION EVENT
            openContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);

        } catch (CantDeliverPendingTransactionsException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot deliver pending transaction");
        } catch (CantConfirmTransactionException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot confirm transaction");
        } catch (CantUpdateRecordException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot update the database record");
        }
    }

    private boolean processContractHash(BusinessTransactionMetadata businessTransactionMetadata) throws UnexpectedResultReturnedFromDatabaseException{

        try {

            String contractHash                     = businessTransactionMetadata.getContractHash();
            UUID transactionContractId              = businessTransactionMetadata.getTransactionContractId();
            ContractTransactionStatus statusRemote  = businessTransactionMetadata.getContractTransactionStatus();
            UUID transmissionIdNew                  = UUID.randomUUID();

            if (statusRemote.getCode().equals(ContractTransactionStatus.PENDING_REMOTE_CONFIRMATION.getCode())) {

                System.out.println(new StringBuilder()
                        .append("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH ")
                        .append("\n - contractHash: ").append(contractHash)
                        .append("\n - Exist Contract: ").append(Boolean.toString(openContractBusinessTransactionDao.isContractHashExists(contractHash))));

                if (openContractBusinessTransactionDao.isContractHashExists(contractHash)) {

                    System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH - HASH - PASS\n");

                    //SEND CONFIRM RECEPTION HASH
                    transactionTransmissionManager.confirmNotificationReception(
                            businessTransactionMetadata.getReceiverId(),
                            businessTransactionMetadata.getSenderId(),
                            contractHash,
                            transmissionIdNew.toString(),
                            transactionContractId,
                            Plugins.OPEN_CONTRACT,
                            businessTransactionMetadata.getReceiverType(),
                            businessTransactionMetadata.getSenderType()
                    );
                    System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH - Sent message confirmNotificationReception\n");

                    //CHANGE STATUS TRANSACTION
                    openContractBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONTRACT_ACK_CONFIRMED);
                    System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH - update contract transaction status to CONTRACT_ACK_CONFIRMED\n");

                    //CONFIRM TRANSMISSION OF SEND
                    transactionTransmissionManager.confirmReception(transmissionIdNew);
                    System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH - transaction transmission marked has CONFIRMED\n");

                } else {

                    throw new UnexpectedResultReturnedFromDatabaseException(
                            "Checking pending transactions.",
                            "Contract Hash Not exist, Cannot Send The Confirm Notification Reception.");

                }

                return true;
            }

        } catch (CantConfirmNotificationReceptionException e){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Sending Confirm contract",
                    "Error in Transaction Transmission Network Service");
        } catch (CantUpdateRecordException e){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot update the database record");
        } catch (CantConfirmTransactionException e){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot confirm transaction");
        }

        return false;

    }

    private boolean processConfirmContractHash(BusinessTransactionMetadata businessTransactionMetadata) throws UnexpectedResultReturnedFromDatabaseException {

        try {
            String contractHash = businessTransactionMetadata.getContractHash();
            UUID transactionContractId = businessTransactionMetadata.getTransactionContractId();
            ContractTransactionStatus statusRemote = businessTransactionMetadata.getContractTransactionStatus();
            UUID transmissionIdNew = UUID.randomUUID();

            System.out.println(new StringBuilder()
                    .append("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - CONFIRMATION\n")
                    .append("\n - contractHash: ").append(contractHash));

            if (statusRemote.getCode().equals(ContractTransactionStatus.NOTIFICATION_CONFIRMED.getCode())) {

                if (openContractBusinessTransactionDao.isContractHashExists(contractHash)) {

                    System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - CONFIRMATION - PASS\n");

                    //SEND CONFIRM RECEPTION HASH
                    transactionTransmissionManager.ackConfirmNotificationReception(
                            businessTransactionMetadata.getReceiverId(),
                            businessTransactionMetadata.getSenderId(),
                            contractHash,
                            transmissionIdNew.toString(),
                            transactionContractId,
                            Plugins.OPEN_CONTRACT,
                            businessTransactionMetadata.getReceiverType(),
                            businessTransactionMetadata.getSenderType()
                    );
                    System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - Sent message ackConfirmNotificationReception\n");

                    //CHANGE STATUS TRANSACTION
                    openContractBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONTRACT_CONFIRMED);
                    System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - update contract transaction status to CONTRACT_CONFIRMED\n");

                    //CONFIRM SEND OF TRANSMISSION
                    transactionTransmissionManager.confirmReception(transmissionIdNew);
                    System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - transaction transmission marked has CONFIRMED\n");

                } else {

                    throw new UnexpectedResultReturnedFromDatabaseException(
                            "Checking pending transactions.",
                            "Contract Hash Not exist, Cannot Send The Ack Confirm Notification Reception.");

                }

                return true;
            }

        } catch (CantConfirmNotificationReceptionException e){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Sending Confirm contract",
                    "Error in Transaction Transmission Network Service");
        } catch (CantUpdateRecordException e){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot update the database record");
        } catch (CantConfirmTransactionException e){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot confirm transaction");
        }

        return false;

    }

    private boolean processAckContractHash(BusinessTransactionMetadata businessTransactionMetadata) throws UnexpectedResultReturnedFromDatabaseException{

        try {

            ContractType contractType;

            String contractHash                     = businessTransactionMetadata.getContractHash();
            ContractTransactionStatus statusRemote  = businessTransactionMetadata.getContractTransactionStatus();

            System.out.println(new StringBuilder()
                    .append("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - ACK CONFIRMATION\n")
                    .append("\n - contractHash: ").append(contractHash));

            if (statusRemote.getCode().equals(ContractTransactionStatus.NOTIFICATION_ACK_CONFIRMED.getCode())) {

                System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - ACK CONFIRMATION - PASS\n");

                contractType = openContractBusinessTransactionDao.getContractType(contractHash);
                switch (contractType) {
                    case PURCHASE:
                        CustomerBrokerContractPurchase contractPurchase = customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(contractHash);
                        if (!contractPurchase.getStatus().getCode().equals(ContractStatus.CANCELLED.getCode()) &&
                                !contractPurchase.getStatus().getCode().equals(ContractStatus.COMPLETED.getCode())) {

                            customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(contractHash, ContractStatus.PENDING_PAYMENT);
                            System.out.println("OPEN_CONTRACT [PURCHASE] - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - updated contract status to PENDING_PAYMENT\n");

                            //CLOSE NEGOTIATION
                            closeNegotiation(contractType, contractPurchase.getNegotiatiotId());
                            System.out.println("OPEN_CONTRACT [PURCHASE] - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - closed negotiation\n");

                            //NOTIFICATIONS NEW OPEN CONTRACT
                            notificationNewOpenContract(contractType);
                            System.out.println("OPEN_CONTRACT [PURCHASE] - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - raised Contract Opened Event\n");
                        }
                        break;
                    case SALE:
                        CustomerBrokerContractSale contractSale = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
                        if (!contractSale.getStatus().getCode().equals(ContractStatus.CANCELLED.getCode()) &&
                                !contractSale.getStatus().getCode().equals(ContractStatus.COMPLETED.getCode())) {

                            customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(contractHash, ContractStatus.PENDING_PAYMENT);
                            System.out.println("OPEN_CONTRACT [SALE] - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - updated contract status to PENDING_PAYMENT\n");

                            //CLOSE NEGOTIATION
                            closeNegotiation(contractType, contractSale.getNegotiatiotId());
                            System.out.println("OPEN_CONTRACT [SALE] - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - Negotiation Closed\n");

                            //NOTIFICATIONS NEW OPEN CONTRAC
                            notificationNewOpenContract(contractType);
                            System.out.println("OPEN_CONTRACT [SALE] - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - send notification to Android\n");

                            //RAISE EVENT NEW_CONTRACT_OPENED
                            raiseNewContractEvent(contractHash);
                            System.out.println("OPEN_CONTRACT [SALE] - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - raised Contract Opened Event\n");
                        }
                        break;
                }

                //CHANGE STATUS TRANSACTION
                openContractBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONTRACT_OPENED);
                System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - updated contract transaction status to CONTRACT_OPENED\n");


                return true;

            }

        } catch (CantGetListCustomerBrokerContractPurchaseException e){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot update the purchase contract");
        } catch (CantUpdateCustomerBrokerContractPurchaseException e){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot update the purchase contract");
        } catch (CantGetListCustomerBrokerContractSaleException e){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot update the sale contract");
        } catch (CantUpdateCustomerBrokerContractSaleException e){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot update the sale contract");
        } catch (CantUpdateRecordException e){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot update the database record");
        }

        return false;
    }

    private void raiseNewContractEvent(String contractHash) {
        System.out.println(new StringBuilder().append("OPEN_CONTRACT - AGENT - raiseNewContractEvent() - NEW_CONTRACT_OPENED \n - Contract Hash: ").append(contractHash).append("\n").toString());
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.NEW_CONTRACT_OPENED);
        NewContractOpened newContractOpened = (NewContractOpened) fermatEvent;
        newContractOpened.setSource(EventSource.BUSINESS_TRANSACTION_OPEN_CONTRACT);
        newContractOpened.setContractHash(contractHash);
        eventManager.raiseEvent(newContractOpened);
    }

    private void closeNegotiation(ContractType contractType, String negotiationId) throws UnexpectedResultReturnedFromDatabaseException {

        UUID uuidNegotiationId = UUID.fromString(negotiationId);
        try {

            System.out.println(new StringBuilder().append("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - ACK CONFIRMATION - VAL").append("\n - closeNegotiation ").append(contractType).append(" : ").append(negotiationId).append("\n").toString());

            if (contractType.equals(ContractType.PURCHASE)) {

                System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - ACK CONFIRMATION - VAL" +
                        "\n - closeNegotiation - PURCHASE\n");
                //CLOSE PURCHASE NEGOTIATION
                customerBrokerPurchaseNegotiationManager.closeNegotiation(uuidNegotiationId);

            } else if (contractType.equals(ContractType.SALE)) {

                System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT - ACK CONFIRMATION - VAL" +
                        "\n - closeNegotiation - SALE\n");
                //CLOSE SALE NEGOTIATION
                customerBrokerSaleNegotiationManager.closeNegotiation(uuidNegotiationId);

            } else {

                throw new UnexpectedResultReturnedFromDatabaseException("Error Close Negotiation Contract Type not exist");

            }

        } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Close Purchase Negotiation",
                    "Error Closing negotiation");
        } catch (CantUpdateCustomerBrokerSaleException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Close Negotiation",
                    "Error Closing negotiation");
        }
    }

    private void notificationNewOpenContract(ContractType contractType) throws UnexpectedResultReturnedFromDatabaseException {

        if (contractType.equals(ContractType.PURCHASE)) {

            FermatBundle fermatBundle = new FermatBundle();
            fermatBundle.put(SOURCE_PLUGIN, Plugins.CUSTOMER_BROKER_PURCHASE.getCode());
            fermatBundle.put(APP_NOTIFICATION_PAINTER_FROM, new Owner(WalletsPublicKeys.CBP_CRYPTO_CUSTOMER_WALLET.getCode()));
            fermatBundle.put(APP_TO_OPEN_PUBLIC_KEY, WalletsPublicKeys.CBP_CRYPTO_CUSTOMER_WALLET.getCode());
            fermatBundle.put(NOTIFICATION_ID, CBPBroadcasterConstants.CCW_NEW_CONTRACT_NOTIFICATION);
            fermatBundle.put(APP_ACTIVITY_TO_OPEN_CODE, Activities.CBP_CRYPTO_CUSTOMER_WALLET_HOME.getCode());
            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, fermatBundle);

            fermatBundle = new FermatBundle();
            fermatBundle.put(Broadcaster.PUBLISH_ID, WalletsPublicKeys.CBP_CRYPTO_CUSTOMER_WALLET.getCode());
            fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CCW_CONTRACT_UPDATE_VIEW);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle);

        } else if (contractType.equals(ContractType.SALE)) {

            FermatBundle fermatBundle = new FermatBundle();
            fermatBundle.put(SOURCE_PLUGIN, Plugins.CUSTOMER_BROKER_SALE.getCode());
            fermatBundle.put(APP_NOTIFICATION_PAINTER_FROM, new Owner(WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode()));
            fermatBundle.put(APP_TO_OPEN_PUBLIC_KEY, WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode());
            fermatBundle.put(NOTIFICATION_ID, CBPBroadcasterConstants.CBW_NEW_CONTRACT_NOTIFICATION);
            fermatBundle.put(APP_ACTIVITY_TO_OPEN_CODE, Activities.CBP_CRYPTO_BROKER_WALLET_HOME.getCode());
            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, fermatBundle);

            fermatBundle = new FermatBundle();
            fermatBundle.put(Broadcaster.PUBLISH_ID, WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode());
            fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CBW_CONTRACT_UPDATE_VIEW);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle);

        } else {

            throw new UnexpectedResultReturnedFromDatabaseException("Error Close Negotiation Contract Type not exist");

        }
    }
}
