package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.PaymentType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.util.NegotiationClauseHelper;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.events.CustomerAckMerchandiseConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.AbstractBusinessTransactionAgent;
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
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.CustomerAckOfflineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.database.CustomerAckOfflineMerchandiseBusinessTransactionDao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/07/16.
 */
public class CustomerAckOfflineMerchandiseMonitorAgent2
        extends AbstractBusinessTransactionAgent<CustomerAckOfflineMerchandisePluginRoot> {

    private final CustomerAckOfflineMerchandiseBusinessTransactionDao customerAckOfflineMerchandiseBusinessTransactionDao;
    private final TransactionTransmissionManager transactionTransmissionManager;
    private final CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    private final CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    private final CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    public CustomerAckOfflineMerchandiseMonitorAgent2(
            long sleepTime,
            TimeUnit timeUnit,
            long initDelayTime,
            CustomerAckOfflineMerchandisePluginRoot pluginRoot,
            EventManager eventManager,
            CustomerAckOfflineMerchandiseBusinessTransactionDao customerAckOfflineMerchandiseBusinessTransactionDao,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager) {
        super(sleepTime, timeUnit, initDelayTime, pluginRoot, eventManager);
        this.customerAckOfflineMerchandiseBusinessTransactionDao = customerAckOfflineMerchandiseBusinessTransactionDao;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
    }

    @Override
    protected void doTheMainTask() {
        try {

            String contractHash;

            /**
             * Check contract status to send. - Customer Side
             * The status to verify is PENDING_SUBMIT_OFFLINE_MERCHANDISE_NOTIFICATION, it represents that the merchandise is
             * acknowledge by the customer.
             */
            List<BusinessTransactionRecord> pendingToSubmitNotificationList = customerAckOfflineMerchandiseBusinessTransactionDao.getPendingToSubmitNotificationList();
            for (BusinessTransactionRecord pendingToSubmitNotificationRecord : pendingToSubmitNotificationList) {
                try {
                    contractHash = pendingToSubmitNotificationRecord.getTransactionHash();

                    System.out.println(new StringBuilder().append("\nTEST CONTRACT - ACK OFFLINE MERCHANDISE - AGENT - doTheMainTask() - getPendingToSubmitNotificationList(): ").append(contractHash).append("\n").toString());

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
                } catch (Exception e) {
                    reportError(e);
                }

            }

            /**
             * Check pending notifications - Broker side
             */
            List<BusinessTransactionRecord> pendingToSubmitConfirmationList = customerAckOfflineMerchandiseBusinessTransactionDao.getPendingToSubmitConfirmList();
            for (BusinessTransactionRecord pendingToSubmitConfirmationRecord : pendingToSubmitConfirmationList) {
                try {
                    System.out.println("\nTEST CONTRACT - ACK OFFLINE MERCHANDISE - AGENT - doTheMainTask() - getPendingToSubmitNotificationList()\n");
                    contractHash = pendingToSubmitConfirmationRecord.getTransactionHash();

                    transactionTransmissionManager.confirmNotificationReception(
                            pendingToSubmitConfirmationRecord.getBrokerPublicKey(),
                            pendingToSubmitConfirmationRecord.getCustomerPublicKey(),
                            contractHash,
                            pendingToSubmitConfirmationRecord.getTransactionId(),
                            Plugins.CUSTOMER_ACK_OFFLINE_MERCHANDISE,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);

                    customerAckOfflineMerchandiseBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONFIRM_OFFLINE_ACK_MERCHANDISE);
                } catch (Exception e) {
                    reportError(e);
                }
            }

            /**
             * Check if pending events
             */
            List<String> pendingEventsIdList = customerAckOfflineMerchandiseBusinessTransactionDao.getPendingEvents();
            for (String eventId : pendingEventsIdList) {
                try {
                    checkPendingEvent(eventId);
                } catch (Exception e) {
                    reportError(e);
                }
            }

        } catch (Exception e) {
            reportError(e);
        }
    }

    @Override
    protected void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            String eventTypeCode = customerAckOfflineMerchandiseBusinessTransactionDao.getEventType(eventId);
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
                    Plugins remoteBusinessTransaction = businessTransactionMetadata.getRemoteBusinessTransaction();

                    System.out.println(new StringBuilder().append("CUSTOMER_ACK_OFFLINE_MERCHANDISE - remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());
                    if (remoteBusinessTransaction != Plugins.CUSTOMER_ACK_OFFLINE_MERCHANDISE)
                        continue;

                    System.out.println(new StringBuilder().append("CUSTOMER_ACK_OFFLINE_MERCHANDISE - PASS remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());

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
                    Plugins remoteBusinessTransaction = businessTransactionMetadata.getRemoteBusinessTransaction();

                    System.out.println(new StringBuilder().append("CUSTOMER_ACK_OFFLINE_MERCHANDISE - remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());
                    if (remoteBusinessTransaction != Plugins.CUSTOMER_ACK_OFFLINE_MERCHANDISE)
                        continue;

                    System.out.println(new StringBuilder().append("CUSTOMER_ACK_OFFLINE_MERCHANDISE - PASS remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());

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
                            customerAckOfflineMerchandiseBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONFIRM_OFFLINE_ACK_MERCHANDISE);
                            raiseAckConfirmationEvent(contractHash);

                        }
                    }
                    transactionTransmissionManager.confirmReception(record.getTransactionID());

                }
                customerAckOfflineMerchandiseBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);

            }

            //EVENT FOR ACK PAYMENT CONFIRMED
            if (eventTypeCode.equals(EventType.BROKER_ACK_PAYMENT_CONFIRMED.getCode())) {

                System.out.print("\nTEST CONTRACT - ACK OFFLINE MERCHANDISE - AGENT - checkPendingEvent() - BROKER_SUBMIT_MERCHANDISE_CONFIRMED\n");

                //the eventId from this event is the contractId - Customer side
                CustomerBrokerContractPurchase customerBrokerContractPurchase =
                        customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(eventId);
                if (customerBrokerContractPurchase != null) {
                    String negotiationId = customerBrokerContractPurchase.getNegotiatiotId();
                    CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.
                            getNegotiationsByNegotiationId(UUID.fromString(negotiationId));

                    Collection<Clause> negotiationClauses = customerBrokerPurchaseNegotiation.getClauses();
                    String clauseValue = NegotiationClauseHelper.getNegotiationClauseValue(negotiationClauses, ClauseType.BROKER_PAYMENT_METHOD);

                    if (!MoneyType.CRYPTO.getCode().equals(clauseValue)) {
                        customerAckOfflineMerchandiseBusinessTransactionDao.persistContractInDatabase(customerBrokerContractPurchase);
                    }
                }

                customerAckOfflineMerchandiseBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
            }

        } catch (CantGetListPurchaseNegotiationsException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Checking pending events",
                    "Cannot update the database");
        } catch (CantGetListClauseException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Checking pending events",
                    "Cannot update the database");
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

    private void raiseAckConfirmationEvent(String contractHash) {
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.CUSTOMER_ACK_MERCHANDISE_CONFIRMED);
        CustomerAckMerchandiseConfirmed customerAckMerchandiseConfirmed = (CustomerAckMerchandiseConfirmed) fermatEvent;
        customerAckMerchandiseConfirmed.setSource(EventSource.CUSTOMER_ACK_OFFLINE_MERCHANDISE);
        customerAckMerchandiseConfirmed.setContractHash(contractHash);
        customerAckMerchandiseConfirmed.setPaymentType(PaymentType.CRYPTO_MONEY);
        eventManager.raiseEvent(customerAckMerchandiseConfirmed);
    }

}
