package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
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
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantConfirmNotificationReceptionException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendBusinessTransactionHashException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.OpenContractPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDao;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    /**
     * Default constructor with parameters
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
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager) {
        super(sleepTime, timeUnit, initDelayTime, pluginRoot, eventManager);
        this.openContractBusinessTransactionDao = openContractBusinessTransactionDao;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;

    }

    @Override
    protected void doTheMainTask() {
        try {
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

                    System.out.println("\nTEST CONTRACT - OPEN CONTRACT - AGENT - doTheMainTask() - getPendingToSubmitContractHash() - contractType: "+contractType+"\n");

                    switch (contractType) {
                        case PURCHASE:
                            System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - doTheMainTask() - getPendingToSubmitContractHash() - PURCHASE\n");
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
                            System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - doTheMainTask() - getPendingToSubmitContractHash() - SALE\n");
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

        } catch (CantUpdateRecordException |
                CantConfirmTransactionException |
                UnexpectedResultReturnedFromDatabaseException |
                CantGetContractListException |
                CantSendBusinessTransactionHashException e) {
            reportError(e);
        }
    }

    private void raiseNewContractEvent(String contractHash) {
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.NEW_CONTRACT_OPENED);
        NewContractOpened newContractOpened = (NewContractOpened) fermatEvent;
        newContractOpened.setSource(EventSource.BUSINESS_TRANSACTION_OPEN_CONTRACT);
        newContractOpened.setContractHash(contractHash);
        eventManager.raiseEvent(newContractOpened);
    }

    @Override
    protected void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {
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

                    System.out.print("\nTEST CONTRACT - OPEN CONTRACT - AGENT - checkPendingEvent() - EVENT - TYPE: " + eventTypeCode + "\n");

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
                                    if(!contractPurchase.getStatus().getCode().equals(ContractStatus.CANCELLED.getCode())) {
                                        customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(contractHash,
                                                ContractStatus.PENDING_PAYMENT);

                                        //CLOSE NEGOTIATION
                                        closeNegotiation(contractType, contractPurchase.getNegotiatiotId());

                                    }
                                    break;
                                case SALE:
                                    CustomerBrokerContractSale contractSale = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
                                    if(!contractSale.getStatus().getCode().equals(ContractStatus.CANCELLED.getCode())) {
                                        customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(contractHash,
                                                ContractStatus.PENDING_PAYMENT);

                                        //CLOSE NEGOTIATION
                                        closeNegotiation(contractType, contractSale.getNegotiatiotId());
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
        } catch (CantGetListCustomerBrokerContractPurchaseException e){
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
        } catch (CantGetListCustomerBrokerContractSaleException e){
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

    private void closeNegotiation(ContractType contractType, String negotiationId) throws UnexpectedResultReturnedFromDatabaseException{

        try {

            if(contractType.equals(ContractType.PURCHASE)) {

                //CLOSE PURCHASE NEGOTIATION
                CustomerBrokerPurchaseNegotiation purchaseNegotiation = customerBrokerPurchaseNegotiationManager.
                        getNegotiationsByNegotiationId(UUID.fromString(negotiationId));
                customerBrokerPurchaseNegotiationManager.closeNegotiation(purchaseNegotiation);

            } else if(contractType.equals(ContractType.SALE)) {

                //CLOSE SALE NEGOTIATION
                CustomerBrokerSaleNegotiation purchaseNegotiation = customerBrokerSaleNegotiationManager.
                        getNegotiationsByNegotiationId(UUID.fromString(negotiationId));
                customerBrokerSaleNegotiationManager.closeNegotiation(purchaseNegotiation);

            } else {

                throw new UnexpectedResultReturnedFromDatabaseException("Error Close Negotiation Contract Type not exist");

            }



        } catch (CantGetListPurchaseNegotiationsException e){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Close Purchase Negotiation",
                    "Error in get negotiation");
        } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Close Purchase Negotiation",
                    "Error Closing negotiation");
        } catch (CantGetListSaleNegotiationsException e){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Close Purchase Negotiation",
                    "Error in get negotiation");
        } catch (CantUpdateCustomerBrokerSaleException e){
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Close Negotiation",
                    "Error Closing negotiation");
        }
    }
}
