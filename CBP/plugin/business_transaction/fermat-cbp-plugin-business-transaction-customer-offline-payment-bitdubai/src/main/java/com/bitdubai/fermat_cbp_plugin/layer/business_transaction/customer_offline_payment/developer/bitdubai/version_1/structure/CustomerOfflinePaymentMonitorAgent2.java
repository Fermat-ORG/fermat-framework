package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.structure;

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
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.util.NegotiationClauseHelper;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.AbstractBusinessTransactionAgent;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_offline_payment.events.CustomerOfflinePaymentConfirmed;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantUpdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.CustomerOfflinePaymentPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/07/16.
 */
public class CustomerOfflinePaymentMonitorAgent2
        extends AbstractBusinessTransactionAgent<CustomerOfflinePaymentPluginRoot> {

    private final CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao;
    private final TransactionTransmissionManager transactionTransmissionManager;
    private final CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    private final CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    private final CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    /**
     * Default constructor with parameters
     *
     * @param sleepTime
     * @param timeUnit
     * @param initDelayTime
     * @param pluginRoot
     * @param eventManager
     * @param customerOfflinePaymentBusinessTransactionDao
     * @param transactionTransmissionManager
     * @param customerBrokerContractPurchaseManager
     * @param customerBrokerContractSaleManager
     * @param customerBrokerSaleNegotiationManager
     */
    public CustomerOfflinePaymentMonitorAgent2(
            long sleepTime,
            TimeUnit timeUnit,
            long initDelayTime,
            CustomerOfflinePaymentPluginRoot pluginRoot,
            EventManager eventManager,
            CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager) {
        super(sleepTime, timeUnit, initDelayTime, pluginRoot, eventManager);
        this.customerOfflinePaymentBusinessTransactionDao = customerOfflinePaymentBusinessTransactionDao;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
    }

    @Override
    protected void doTheMainTask() {
        try {
            String contractHash;

            // Check contract status to send.
            List<CustomerOfflinePaymentRecord> pendingToSubmitNotificationList = customerOfflinePaymentBusinessTransactionDao.getPendingToSubmitNotificationList();
            for (CustomerOfflinePaymentRecord pendingToSubmitNotificationRecord : pendingToSubmitNotificationList) {
                try {
                    contractHash = pendingToSubmitNotificationRecord.getTransactionHash();

                    System.out.println("OFFLINE_PAYMENT - [Customer] Sending notification: OFFLINE_PAYMENT_SUBMITTED");
                    transactionTransmissionManager.sendContractStatusNotification(
                            pendingToSubmitNotificationRecord.getCustomerPublicKey(),
                            pendingToSubmitNotificationRecord.getBrokerPublicKey(),
                            contractHash,
                            pendingToSubmitNotificationRecord.getTransactionId(),
                            ContractTransactionStatus.OFFLINE_PAYMENT_SUBMITTED,
                            Plugins.CUSTOMER_OFFLINE_PAYMENT,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER);

                    customerOfflinePaymentBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.OFFLINE_PAYMENT_SUBMITTED);
                    System.out.println("OFFLINE_PAYMENT - [Customer] Update Business Transaction Status: OFFLINE_PAYMENT_SUBMITTED");
                } catch (Exception e) {
                    reportError(e);
                }

            }

            // Check pending notifications - Broker side
            List<CustomerOfflinePaymentRecord> pendingToSubmitConfirmationList = customerOfflinePaymentBusinessTransactionDao.getPendingToSubmitConfirmList();
            for (CustomerOfflinePaymentRecord pendingToSubmitConfirmationRecord : pendingToSubmitConfirmationList) {
                try {
                    contractHash = pendingToSubmitConfirmationRecord.getTransactionHash();

                    System.out.println("OFFLINE_PAYMENT - [Broker] Sending Confirmation");
                    transactionTransmissionManager.confirmNotificationReception(
                            pendingToSubmitConfirmationRecord.getBrokerPublicKey(),
                            pendingToSubmitConfirmationRecord.getCustomerPublicKey(),
                            contractHash,
                            pendingToSubmitConfirmationRecord.getTransactionId(),
                            Plugins.CUSTOMER_OFFLINE_PAYMENT,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);

                    customerOfflinePaymentBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONFIRM_OFFLINE_PAYMENT);
                    System.out.println("OFFLINE_PAYMENT - [Broker] Update Business Transaction Status: CONFIRM_OFFLINE_PAYMENT");
                } catch (Exception e) {
                    reportError(e);
                }
            }

            // Check if pending events
            List<String> pendingEventsIdList = customerOfflinePaymentBusinessTransactionDao.getPendingEvents();
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
            String eventTypeCode = customerOfflinePaymentBusinessTransactionDao.getEventType(eventId);
            String contractHash;
            BusinessTransactionMetadata businessTransactionMetadata;
            ContractTransactionStatus contractTransactionStatus;
            CustomerOfflinePaymentRecord customerOnlinePaymentRecord;

            //This will happen in broker side
            if (eventTypeCode.equals(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE.getCode())) {

                System.out.println("OFFLINE_PAYMENT - INCOMING_NEW_CONTRACT_STATUS_UPDATE");

                List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                    businessTransactionMetadata = record.getInformation();
                    contractHash = businessTransactionMetadata.getContractHash();
                    Plugins remoteBusinessTransaction = businessTransactionMetadata.getRemoteBusinessTransaction();

                    System.out.println(new StringBuilder().append("CUSTOMER_OFFLINE_PAYMENT - remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());
                    if (remoteBusinessTransaction != Plugins.CUSTOMER_OFFLINE_PAYMENT)
                        continue;

                    System.out.println(new StringBuilder().append("CUSTOMER_OFFLINE_PAYMENT - PASS remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());

                    if (customerOfflinePaymentBusinessTransactionDao.isContractHashInDatabase(contractHash)) {
                        contractTransactionStatus = customerOfflinePaymentBusinessTransactionDao.getContractTransactionStatus(contractHash);
                        //TODO: analyze what we need to do here.

                    } else {
                        CustomerBrokerContractSale saleContract = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);

                        //If the contract is null, I cannot handle with this situation
                        ObjectChecker.checkArgument(saleContract);
                        String negotiationId = saleContract.getNegotiatiotId();
                        CustomerBrokerSaleNegotiation customerBrokerPurchaseNegotiation = customerBrokerSaleNegotiationManager.
                                getNegotiationsByNegotiationId(UUID.fromString(negotiationId));
                        Collection<Clause> negotiationClauses = customerBrokerPurchaseNegotiation.getClauses();
                        String clauseValue = NegotiationClauseHelper.getNegotiationClauseValue(negotiationClauses, ClauseType.CUSTOMER_PAYMENT_METHOD);
                        if (!MoneyType.CRYPTO.getCode().equals(clauseValue)) {
                            customerOfflinePaymentBusinessTransactionDao.persistContractInDatabase(saleContract);
                            customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(contractHash, ContractStatus.PAYMENT_SUBMIT);
                            customerOfflinePaymentBusinessTransactionDao.setCompletionDateByContractHash(contractHash, (new Date()).getTime());
                            raisePaymentConfirmationEvent();

                            System.out.println("OFFLINE_PAYMENT - INCOMING_NEW_CONTRACT_STATUS_UPDATE - Update Contract Status: PAYMENT_SUBMIT");
                            System.out.println("OFFLINE_PAYMENT - INCOMING_NEW_CONTRACT_STATUS_UPDATE - New Business Transaction Status: PENDING_OFFLINE_PAYMENT_CONFIRMATION");
                        }
                    }

                    transactionTransmissionManager.confirmReception(record.getTransactionID());
                }
                customerOfflinePaymentBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
            }

            //This will happen in customer side
            if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {

                System.out.println("OFFLINE_PAYMENT - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE");

                List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                    businessTransactionMetadata = record.getInformation();
                    contractHash = businessTransactionMetadata.getContractHash();
                    Plugins remoteBusinessTransaction = businessTransactionMetadata.getRemoteBusinessTransaction();

                    System.out.println(new StringBuilder().append("CUSTOMER_OFFLINE_PAYMENT - remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());
                    if (remoteBusinessTransaction != Plugins.CUSTOMER_OFFLINE_PAYMENT)
                        continue;

                    System.out.println(new StringBuilder().append("CUSTOMER_OFFLINE_PAYMENT - PASS remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());

                    if (customerOfflinePaymentBusinessTransactionDao.isContractHashInDatabase(contractHash)) {
                        customerOnlinePaymentRecord = customerOfflinePaymentBusinessTransactionDao.getCustomerOfflinePaymentRecord(contractHash);
                        contractTransactionStatus = customerOnlinePaymentRecord.getContractTransactionStatus();

                        if (contractTransactionStatus == ContractTransactionStatus.OFFLINE_PAYMENT_SUBMITTED) {
                            customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(contractHash, ContractStatus.PAYMENT_SUBMIT);
                            customerOfflinePaymentBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONFIRM_OFFLINE_PAYMENT);
                            customerOfflinePaymentBusinessTransactionDao.setCompletionDateByContractHash(contractHash, (new Date()).getTime());
                            raisePaymentConfirmationEvent();

                            System.out.println("OFFLINE_PAYMENT - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - Update Contract Status: PAYMENT_SUBMIT");
                            System.out.println("OFFLINE_PAYMENT - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - Update Business Transaction Status: CONFIRM_OFFLINE_PAYMENT");
                        }
                    }

                    transactionTransmissionManager.confirmReception(record.getTransactionID());
                }
                customerOfflinePaymentBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
            }

        } catch (CantGetListClauseException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Checking pending events",
                    "Cannot update the database");
        } catch (CantGetListSaleNegotiationsException exception) {
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
        } catch (CantUpdateCustomerBrokerContractPurchaseException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Checking pending events",
                    "Cannot update the contract purchase status");
        } catch (CantGetListCustomerBrokerContractSaleException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Checking pending events",
                    "Cannot get the list of contract sale status");
        } catch (ObjectNotSetException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Checking pending events",
                    "The customerBrokerContractSale is null");
        }
    }

    private void raisePaymentConfirmationEvent() {
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.CUSTOMER_OFFLINE_PAYMENT_CONFIRMED);
        CustomerOfflinePaymentConfirmed customerOnlinePaymentConfirmed = (CustomerOfflinePaymentConfirmed) fermatEvent;
        customerOnlinePaymentConfirmed.setSource(EventSource.CUSTOMER_OFFLINE_PAYMENT);
        eventManager.raiseEvent(customerOnlinePaymentConfirmed);
    }
}
