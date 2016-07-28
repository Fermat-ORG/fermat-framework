package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces.DepositManager;
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
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.events.BrokerAckPaymentConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetBankTransactionParametersRecordException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetCashTransactionParameterException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.AbstractBusinessTransactionAgent;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BankTransactionParametersRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.CashTransactionParametersRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
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
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.BrokerAckOfflinePaymentPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database.BrokerAckOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransactionManager;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/07/16.
 */
public class BrokerAckOfflinePaymentMonitorAgent2
        extends AbstractBusinessTransactionAgent<BrokerAckOfflinePaymentPluginRoot> {

    private final BrokerAckOfflinePaymentBusinessTransactionDao brokerAckOfflinePaymentBusinessTransactionDao;
    private final TransactionTransmissionManager transactionTransmissionManager;
    private final CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    private final CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    private final CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;
    private final DepositManager bankDepositTransactionManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final CashDepositTransactionManager cashDepositTransactionManager;
    private final UUID pluginId;

    /**
     * Default constructor with parameters
     *
     * @param sleepTime
     * @param timeUnit
     * @param initDelayTime
     * @param pluginRoot
     */
    public BrokerAckOfflinePaymentMonitorAgent2(
            long sleepTime,
            TimeUnit timeUnit,
            long initDelayTime,
            BrokerAckOfflinePaymentPluginRoot pluginRoot,
            BrokerAckOfflinePaymentBusinessTransactionDao brokerAckOfflinePaymentBusinessTransactionDao,
            EventManager eventManager,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
            DepositManager bankDepositTransactionManager,
            CryptoBrokerWalletManager cryptoBrokerWalletManager,
            CashDepositTransactionManager cashDepositTransactionManager,
            UUID pluginId) {
        super(sleepTime, timeUnit, initDelayTime, pluginRoot, eventManager);
        this.brokerAckOfflinePaymentBusinessTransactionDao = brokerAckOfflinePaymentBusinessTransactionDao;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
        this.bankDepositTransactionManager = bankDepositTransactionManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.cashDepositTransactionManager = cashDepositTransactionManager;
        this.pluginId = pluginId;
    }

    @Override
    protected void doTheMainTask() {
        try {
            String contractHash;
            String cryptoWalletPublicKey;
            String customerAlias;
            UUID externalTransactionId;

            /**
             * Check pending bank transactions to credit - Broker Side
             * The status to verify is PENDING_CREDIT_BANK_WALLET, it represents that the payment
             * is "physically" acknowledge by the broker.
             */
            List<BusinessTransactionRecord> pendingToBankCreditList =
                    brokerAckOfflinePaymentBusinessTransactionDao.getPendingToBankCreditList();
            for (BusinessTransactionRecord pendingToBankCreditRecord : pendingToBankCreditList) {
                try {
                    System.out.println("ACK_OFFLINE_PAYMENT - [Broker] Enter Pending Bank Credit");

                    contractHash = pendingToBankCreditRecord.getContractHash();
                    cryptoWalletPublicKey = pendingToBankCreditRecord.getCBPWalletPublicKey();
                    customerAlias = pendingToBankCreditRecord.getCustomerAlias();
                    BankTransactionParametersRecord bankDepositParameters;

                    bankDepositParameters = getBankDepositParametersFromContractId(
                            contractHash,
                            cryptoWalletPublicKey,
                            customerAlias);

                    final BankTransaction bankTransaction =
                            bankDepositTransactionManager.makeDeposit(bankDepositParameters);
                    System.out.println("ACK_OFFLINE_PAYMENT - [Broker] Make Bank Deposit");

                    externalTransactionId = bankTransaction.getTransactionId();
                    pendingToBankCreditRecord.setExternalTransactionId(externalTransactionId);
                    pendingToBankCreditRecord.setContractTransactionStatus(
                            ContractTransactionStatus.PENDING_ACK_OFFLINE_PAYMENT_NOTIFICATION);
                    pendingToBankCreditRecord.setPaymentType(MoneyType.BANK);

                    brokerAckOfflinePaymentBusinessTransactionDao.updateBusinessTransactionRecord(
                            pendingToBankCreditRecord);
                    System.out.println("ACK_OFFLINE_PAYMENT - [Broker] Update Business Transaction Status: PENDING_ACK_OFFLINE_PAYMENT_NOTIFICATION");
                } catch (Exception e) {
                    reportError(e);
                }

            }

            /**
             * Check pending bank transactions to credit - Broker Side
             * The status to verify is PENDING_CREDIT_CASH_WALLET, it represents that the payment
             * is "physically" acknowledge by the broker.
             */
            List<BusinessTransactionRecord> pendingToCashCreditList =
                    brokerAckOfflinePaymentBusinessTransactionDao.getPendingToCashCreditList();
            for (BusinessTransactionRecord pendingToCashCreditRecord : pendingToCashCreditList) {
                try {
                    System.out.println("ACK_OFFLINE_PAYMENT - [Broker] Enter Pending Cash Credit");

                    contractHash = pendingToCashCreditRecord.getContractHash();
                    cryptoWalletPublicKey = pendingToCashCreditRecord.getCBPWalletPublicKey();
                    customerAlias = pendingToCashCreditRecord.getCustomerAlias();
                    MoneyType paymentType = pendingToCashCreditRecord.getPaymentType();
                    CashTransactionParametersRecord cashDepositParameters;
                    CashDepositTransaction cashDepositTransaction;

                    cashDepositParameters = getCashDepositParametersFromContractId(
                            contractHash,
                            cryptoWalletPublicKey,
                            paymentType,
                            customerAlias);

                    cashDepositTransaction =
                            cashDepositTransactionManager.createCashDepositTransaction(
                                    cashDepositParameters);
                    System.out.println("ACK_OFFLINE_PAYMENT - [Broker] Make Cash Deposit");

                    externalTransactionId = cashDepositTransaction.getTransactionId();
                    pendingToCashCreditRecord.setExternalTransactionId(externalTransactionId);
                    pendingToCashCreditRecord.setContractTransactionStatus(
                            ContractTransactionStatus.PENDING_ACK_OFFLINE_PAYMENT_NOTIFICATION);

                    brokerAckOfflinePaymentBusinessTransactionDao.updateBusinessTransactionRecord(
                            pendingToCashCreditRecord);
                    System.out.println("ACK_OFFLINE_PAYMENT - [Broker] Update Business Transaction Status: PENDING_ACK_OFFLINE_PAYMENT_NOTIFICATION");
                } catch (Exception e) {
                    reportError(e);
                }

            }

            /**
             * Check contract status to send. - Broker Side
             * The status to verify is PENDING_ACK_OFFLINE_PAYMENT_NOTIFICATION, it represents that the payment is fully
             * acknowledge by the broker.
             */
            List<BusinessTransactionRecord> pendingToSubmitNotificationList =
                    brokerAckOfflinePaymentBusinessTransactionDao.getPendingToSubmitNotificationList();
            for (BusinessTransactionRecord pendingToSubmitNotificationRecord : pendingToSubmitNotificationList) {
                try {
                    contractHash = pendingToSubmitNotificationRecord.getTransactionHash();

                    System.out.println("ACK_OFFLINE_PAYMENT - [Broker] Sending notification: OFFLINE_PAYMENT_ACK");
                    transactionTransmissionManager.sendContractStatusNotification(
                            pendingToSubmitNotificationRecord.getBrokerPublicKey(),
                            pendingToSubmitNotificationRecord.getCustomerPublicKey(),
                            contractHash,
                            pendingToSubmitNotificationRecord.getTransactionId(),
                            ContractTransactionStatus.OFFLINE_PAYMENT_ACK,
                            Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER
                    );

                    brokerAckOfflinePaymentBusinessTransactionDao.updateContractTransactionStatus(
                            contractHash,
                            ContractTransactionStatus.OFFLINE_PAYMENT_ACK);
                    System.out.println("ACK_OFFLINE_PAYMENT - [Broker] Update Business Transaction Status: OFFLINE_PAYMENT_ACK");
                } catch (Exception e) {
                    reportError(e);
                }
            }

            /**
             * Check pending notifications - Customer side
             */
            List<BusinessTransactionRecord> pendingToSubmitConfirmationList =
                    brokerAckOfflinePaymentBusinessTransactionDao.getPendingToSubmitConfirmationList();
            for (BusinessTransactionRecord pendingToSubmitConfirmationRecord : pendingToSubmitConfirmationList) {

                try {
                    contractHash = pendingToSubmitConfirmationRecord.getTransactionHash();

                    System.out.println("ACK_OFFLINE_PAYMENT - [Customer] Sending Confirmation");
                    transactionTransmissionManager.confirmNotificationReception(
                            pendingToSubmitConfirmationRecord.getCustomerPublicKey(),
                            pendingToSubmitConfirmationRecord.getBrokerPublicKey(),
                            contractHash,
                            pendingToSubmitConfirmationRecord.getTransactionId(),
                            Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER);

                    brokerAckOfflinePaymentBusinessTransactionDao.updateContractTransactionStatus(
                            contractHash,
                            ContractTransactionStatus.CONFIRM_OFFLINE_ACK_PAYMENT);
                    System.out.println("ACK_OFFLINE_PAYMENT - [Customer] Update Business Transaction Status: CONFIRM_OFFLINE_ACK_PAYMENT");
                } catch (Exception e) {
                    reportError(e);
                }
            }

            /**
             * Check if pending events
             */
            List<String> pendingEventsIdList =
                    brokerAckOfflinePaymentBusinessTransactionDao.getPendingEvents();
            for (String eventId : pendingEventsIdList) {
                try {
                    checkPendingEvent(eventId);
                } catch (Exception e) {
                    reportError(e);
                }
            }

        } catch (
                Exception e) {
            reportError(e);
        }
    }

    @Override
    protected void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            String eventTypeCode = brokerAckOfflinePaymentBusinessTransactionDao.getEventType(eventId);
            String contractHash;
            BusinessTransactionMetadata businessTransactionMetadata;
            ContractTransactionStatus contractTransactionStatus;
            BusinessTransactionRecord businessTransactionRecord;

            //This will happen in customer side
            if (eventTypeCode.equals(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE.getCode())) {

                System.out.println("ACK_OFFLINE_PAYMENT - INCOMING_NEW_CONTRACT_STATUS_UPDATE");

                List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                    businessTransactionMetadata = record.getInformation();
                    contractHash = businessTransactionMetadata.getContractHash();
                    Plugins remoteBusinessTransaction = businessTransactionMetadata.getRemoteBusinessTransaction();

                    System.out.println("ACK_OFFLINE_PAYMENT - remoteBusinessTransaction = " + remoteBusinessTransaction);
                    if (remoteBusinessTransaction != Plugins.BROKER_ACK_OFFLINE_PAYMENT)
                        continue;

                    System.out.println("ACK_OFFLINE_PAYMENT - PASS remoteBusinessTransaction = " + remoteBusinessTransaction);

                    if (brokerAckOfflinePaymentBusinessTransactionDao.isContractHashInDatabase(contractHash)) {
                        contractTransactionStatus = brokerAckOfflinePaymentBusinessTransactionDao.getContractTransactionStatus(contractHash);
                        System.out.println("ACK_OFFLINE_PAYMENT - INCOMING_NEW_CONTRACT_STATUS_UPDATE - The Contract Hash is in Database");
                        //TODO: analyze what we need to do here.

                    } else {
                        CustomerBrokerContractPurchase customerBrokerContractPurchase = customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(contractHash);
                        ObjectChecker.checkArgument(customerBrokerContractPurchase); //If the contract is null, I cannot handle with this situation
                        brokerAckOfflinePaymentBusinessTransactionDao.persistContractInDatabase(customerBrokerContractPurchase);

                        customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(contractHash, ContractStatus.PENDING_MERCHANDISE);
                        brokerAckOfflinePaymentBusinessTransactionDao.setCompletionDateByContractHash(contractHash, (new Date()).getTime());
                        raiseAckConfirmationEvent(contractHash);

                        System.out.println("ACK_OFFLINE_PAYMENT - INCOMING_NEW_CONTRACT_STATUS_UPDATE - Update Contract Status: PENDING_MERCHANDISE");
                        System.out.println("ACK_OFFLINE_PAYMENT - INCOMING_NEW_CONTRACT_STATUS_UPDATE - New Business Transaction Status: PENDING_ACK_OFFLINE_PAYMENT_CONFIRMATION");
                    }
                    transactionTransmissionManager.confirmReception(record.getTransactionID());
                }
                brokerAckOfflinePaymentBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
            }

            //This will happen in broker side
            if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {

                System.out.println("ACK_OFFLINE_PAYMENT - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE");

                List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                    businessTransactionMetadata = record.getInformation();
                    contractHash = businessTransactionMetadata.getContractHash();
                    Plugins remoteBusinessTransaction = businessTransactionMetadata.getRemoteBusinessTransaction();

                    System.out.println("ACK_OFFLINE_PAYMENT - remoteBusinessTransaction = " + remoteBusinessTransaction);
                    if (remoteBusinessTransaction != Plugins.BROKER_ACK_OFFLINE_PAYMENT)
                        continue;

                    System.out.println("ACK_OFFLINE_PAYMENT - PASS remoteBusinessTransaction = " + remoteBusinessTransaction);

                    if (brokerAckOfflinePaymentBusinessTransactionDao.isContractHashInDatabase(contractHash)) {
                        businessTransactionRecord = brokerAckOfflinePaymentBusinessTransactionDao.getBrokerBusinessTransactionRecordByContractHash(contractHash);
                        contractTransactionStatus = businessTransactionRecord.getContractTransactionStatus();

                        if (contractTransactionStatus == ContractTransactionStatus.OFFLINE_PAYMENT_ACK) {
                            customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(contractHash, ContractStatus.PENDING_MERCHANDISE);
                            brokerAckOfflinePaymentBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONFIRM_OFFLINE_ACK_PAYMENT);
                            brokerAckOfflinePaymentBusinessTransactionDao.setCompletionDateByContractHash(contractHash, (new Date()).getTime());
                            raiseAckConfirmationEvent(contractHash);

                            System.out.println("ACK_OFFLINE_PAYMENT - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - Update Contract Status: PENDING_MERCHANDISE");
                            System.out.println("ACK_OFFLINE_PAYMENT - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - Update Business Transaction Status: CONFIRM_OFFLINE_ACK_PAYMENT");
                        }
                    }
                    transactionTransmissionManager.confirmReception(record.getTransactionID());
                }
                brokerAckOfflinePaymentBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
            }

            //the eventId from this event is the contractId - Broker side
            if (eventTypeCode.equals(EventType.NEW_CONTRACT_OPENED.getCode())) {
                System.out.println("ACK_OFFLINE_PAYMENT - NEW_CONTRACT_OPENED");

                CustomerBrokerContractSale customerBrokerContractSale = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(eventId);
                ObjectChecker.checkArgument(customerBrokerContractSale, "The customerBrokerContractSale is null");
                String negotiationId = customerBrokerContractSale.getNegotiatiotId();
                MoneyType paymentType = getMoneyTypeFromContract(customerBrokerContractSale);
                FiatCurrency currencyType = getCurrencyTypeFromContract(customerBrokerContractSale);


                CustomerBrokerSaleNegotiation customerBrokerPurchaseNegotiation = customerBrokerSaleNegotiationManager.
                        getNegotiationsByNegotiationId(UUID.fromString(negotiationId));

                Collection<Clause> negotiationClauses = customerBrokerPurchaseNegotiation.getClauses();
                String clauseValue = NegotiationClauseHelper.getNegotiationClauseValue(negotiationClauses, ClauseType.CUSTOMER_PAYMENT_METHOD);
                if (!MoneyType.CRYPTO.getCode().equals(clauseValue)) {
                    brokerAckOfflinePaymentBusinessTransactionDao.persistContractInDatabase(
                            customerBrokerContractSale,
                            paymentType,
                            customerBrokerContractSale.getPublicKeyBroker(),
                            ContractTransactionStatus.PENDING_ACK_OFFLINE_PAYMENT,
                            currencyType);
                }


                System.out.println("ACK_OFFLINE_PAYMENT - NEW_CONTRACT_OPENED - New Business Transaction Status: PENDING_ACK_OFFLINE_PAYMENT");

                brokerAckOfflinePaymentBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
            }

        } catch (CantGetListClauseException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending events",
                    "Cannot update the database");
        } catch (CantUpdateRecordException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e, "Checking pending events",
                    "Cannot update the database");
        } catch (CantConfirmTransactionException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending events",
                    "Cannot confirm the transaction");
        } catch (CantUpdateCustomerBrokerContractSaleException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending events",
                    "Cannot update the contract sale status");
        } catch (CantDeliverPendingTransactionsException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending events",
                    "Cannot get the pending transactions from transaction transmission plugin");
        } catch (CantInsertRecordException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending events",
                    "Cannot insert a record in database");
        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending events",
                    "Cannot get the purchase contract");
        } catch (CantGetListCustomerBrokerContractSaleException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending events",
                    "Cannot get the sale contract");
        } catch (CantUpdateCustomerBrokerContractPurchaseException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending events",
                    "Cannot update the contract purchase status");
        } catch (ObjectNotSetException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending events",
                    "The customerBrokerContractSale is null");
        } catch (CantGetListSaleNegotiationsException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending events",
                    "The Cant get the sale negotiation list");
        }
    }

    /**
     * This method returns a BankTransactionParametersRecord from a given ContractHash/Id
     *
     * @param contractHash
     * @return
     */
    private BankTransactionParametersRecord getBankDepositParametersFromContractId(
            String contractHash,
            String cryptoBrokerWalletPublicKey,
            String customerAlias) throws CantGetBankTransactionParametersRecordException {
        try {
            CustomerBrokerContractSale customerBrokerContractSale =
                    customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(
                            contractHash);
            ObjectChecker.checkArgument(
                    customerBrokerContractSale, "The customerBrokerContractSale is null");

            String negotiationId = customerBrokerContractSale.getNegotiatiotId();
            String actorPublicKey = customerBrokerContractSale.getPublicKeyBroker();
            ObjectChecker.checkArgument(
                    negotiationId, new StringBuilder().append("The negotiationId for contractHash ").append(contractHash).append(" is null").toString());

            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation =
                    customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(
                            UUID.fromString(negotiationId));
            ObjectChecker.checkArgument(
                    customerBrokerSaleNegotiation, new StringBuilder().append("The customerBrokerSaleNegotiation by Id ").append(negotiationId).append(" is null").toString());

            Collection<Clause> clauses = customerBrokerSaleNegotiation.getClauses();
            ClauseType clauseType;
            FiatCurrency brokerCurrency = FiatCurrency.US_DOLLAR;
            BigDecimal brokerAmount = BigDecimal.ZERO;
            double brokerAmountDouble;
            String account = "bankAccount";

            for (Clause clause : clauses) {
                clauseType = clause.getType();

                if (clauseType == ClauseType.BROKER_CURRENCY)
                    brokerCurrency = FiatCurrency.getByCode(clause.getValue());

                if (clauseType == ClauseType.BROKER_BANK_ACCOUNT)
                    account = NegotiationClauseHelper.getAccountNumberFromClause(clause);

                if (clauseType == ClauseType.BROKER_CURRENCY_QUANTITY) {
                    brokerAmountDouble = parseToDouble(clause.getValue());
                    brokerAmount = BigDecimal.valueOf(brokerAmountDouble);
                }
            }

            //Get the Bank wallet public key
            String bankWalletPublicKey = "bankWalletPublicKey";
            CryptoBrokerWallet cryptoBrokerWallet =
                    cryptoBrokerWalletManager.loadCryptoBrokerWallet(cryptoBrokerWalletPublicKey);
            CryptoBrokerWalletSetting cryptoBrokerWalletSetting =
                    cryptoBrokerWallet.getCryptoWalletSetting();
            List<CryptoBrokerWalletAssociatedSetting> associatedWallets = cryptoBrokerWalletSetting.getCryptoBrokerWalletAssociatedSettings();

            for (CryptoBrokerWalletAssociatedSetting associatedWallet : associatedWallets) {
                MoneyType moneyType = associatedWallet.getMoneyType();
                if (moneyType == MoneyType.BANK) {
                    Currency walletBankCurrency = associatedWallet.getMerchandise();
                    if (brokerCurrency.getCode().equals(walletBankCurrency.getCode()))
                        bankWalletPublicKey = associatedWallet.getWalletPublicKey();
                }
            }

            //Create the BankTransactionParametersRecord
            return new BankTransactionParametersRecord(
                    pluginId.toString(),
                    bankWalletPublicKey,
                    actorPublicKey,
                    brokerAmount,
                    account,
                    brokerCurrency,
                    new StringBuilder().append("Payment from Customer ").append(customerAlias).toString());

        } catch (CantGetListCustomerBrokerContractSaleException e) {
            throw new CantGetBankTransactionParametersRecordException(
                    e,
                    "Getting the BankTransactionParametersRecord",
                    new StringBuilder().append("Cannot get the CustomerBrokerContractSale by contractHash/Id:\n").append(contractHash).toString());
        } catch (ObjectNotSetException e) {
            throw new CantGetBankTransactionParametersRecordException(
                    e,
                    "Getting the BankTransactionParametersRecord",
                    "An object to set is null");
        } catch (CantGetListSaleNegotiationsException e) {
            throw new CantGetBankTransactionParametersRecordException(
                    e,
                    "Getting the BankTransactionParametersRecord",
                    "Cannot get the negotiation list");
        } catch (CryptoBrokerWalletNotFoundException e) {
            throw new CantGetBankTransactionParametersRecordException(
                    e,
                    "Getting the BankTransactionParametersRecord",
                    "Cannot get the crypto wallet");
        } catch (CantGetCryptoBrokerWalletSettingException e) {
            throw new CantGetBankTransactionParametersRecordException(
                    e,
                    "Getting the BankTransactionParametersRecord",
                    "Cannot get the wallet setting");
        } catch (InvalidParameterException e) {
            throw new CantGetBankTransactionParametersRecordException(
                    e,
                    "Getting the BankTransactionParametersRecord",
                    "An invalid parameter is detected");
        } catch (CantGetListClauseException e) {
            throw new CantGetBankTransactionParametersRecordException(
                    e,
                    "Getting the BankTransactionParametersRecord",
                    "Cannot get the clauses");
        } catch (Exception e) {
            throw new CantGetBankTransactionParametersRecordException(
                    e,
                    "Getting the BankTransactionParametersRecord",
                    "Unexpected exception");
        }
    }

    /**
     * This method returns a CashTransactionParametersRecord from a given ContractHash/Id
     *
     * @param contractHash
     * @return
     */
    private CashTransactionParametersRecord getCashDepositParametersFromContractId(String contractHash, String cryptoBrokerWalletPublicKey, MoneyType paymentType, String customerAlias) throws CantGetCashTransactionParameterException {
        try {
            CustomerBrokerContractSale customerBrokerContractSale = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
            ObjectChecker.checkArgument(customerBrokerContractSale, "The customerBrokerContractSale is null");

            String negotiationId = customerBrokerContractSale.getNegotiatiotId();
            String brokerPublicKey = customerBrokerContractSale.getPublicKeyBroker();
            ObjectChecker.checkArgument(negotiationId, new StringBuilder().append("The negotiationId for contractHash ").append(contractHash).append(" is null").toString());

            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(negotiationId));
            ObjectChecker.checkArgument(customerBrokerSaleNegotiation, new StringBuilder().append("The customerBrokerSaleNegotiation by Id").append(negotiationId).append(" is null").toString());

            Collection<Clause> clauses = customerBrokerSaleNegotiation.getClauses();
            ClauseType clauseType;
            FiatCurrency brokerCurrency = FiatCurrency.US_DOLLAR;
            BigDecimal brokerAmount = BigDecimal.ZERO;
            double brokerAmountDouble;

            for (Clause clause : clauses) {
                clauseType = clause.getType();

                if (clauseType == ClauseType.BROKER_CURRENCY)
                    brokerCurrency = FiatCurrency.getByCode(clause.getValue());

                if (clauseType == ClauseType.BROKER_CURRENCY_QUANTITY) {
                    brokerAmountDouble = parseToDouble(clause.getValue());
                    brokerAmount = BigDecimal.valueOf(brokerAmountDouble);
                }
            }

            //Get the Cash Wallet public key
            String cashWalletPublicKey = "cash_wallet";
            CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(cryptoBrokerWalletPublicKey);
            CryptoBrokerWalletSetting cryptoBrokerWalletSetting = cryptoBrokerWallet.getCryptoWalletSetting();
            List<CryptoBrokerWalletAssociatedSetting> associatedSettingList = cryptoBrokerWalletSetting.getCryptoBrokerWalletAssociatedSettings();

            for (CryptoBrokerWalletAssociatedSetting associatedSetting : associatedSettingList) {
                MoneyType moneyType = associatedSetting.getMoneyType();
                if (moneyType.equals(paymentType)) {
                    Currency walletCurrency = associatedSetting.getMerchandise();
                    if (brokerCurrency.getCode().equals(walletCurrency.getCode()))
                        cashWalletPublicKey = associatedSetting.getWalletPublicKey();
                }
            }

            //Create the BankTransactionParametersRecord
            return new CashTransactionParametersRecord(
                    cashWalletPublicKey,
                    brokerPublicKey,
                    pluginId.toString(),
                    brokerAmount,
                    brokerCurrency,
                    new StringBuilder().append("Payment from Customer ").append(customerAlias).toString(),
                    TransactionType.CREDIT);

        } catch (CantGetListCustomerBrokerContractSaleException e) {
            throw new CantGetCashTransactionParameterException(e, "Getting the CashTransactionParametersRecord", new StringBuilder().append("Cannot get the CustomerBrokerContractSale by contractHash/Id:\n").append(contractHash).toString());
        } catch (ObjectNotSetException e) {
            throw new CantGetCashTransactionParameterException(e, "Getting the CashTransactionParametersRecord", "An object to set is null");
        } catch (CantGetListSaleNegotiationsException e) {
            throw new CantGetCashTransactionParameterException(e, "Getting the CashTransactionParametersRecord", "Cannot get the negotiation list");
        } catch (CryptoBrokerWalletNotFoundException e) {
            throw new CantGetCashTransactionParameterException(e, "Getting the CashTransactionParametersRecord", "Cannot get the crypto wallet");
        } catch (CantGetCryptoBrokerWalletSettingException e) {
            throw new CantGetCashTransactionParameterException(e, "Getting the CashTransactionParametersRecord", "Cannot get the wallet setting");
        } catch (InvalidParameterException e) {
            throw new CantGetCashTransactionParameterException(e, "Getting the CashTransactionParametersRecord", "An invalid parameter is detected");
        } catch (CantGetListClauseException e) {
            throw new CantGetCashTransactionParameterException(e, "Getting the CashTransactionParametersRecord", "Cannot get the clauses");
        } catch (Exception e) {
            throw new CantGetCashTransactionParameterException(e, "Getting the CashTransactionParametersRecord", "Unexpected exception");
        }
    }

    protected void raiseAckConfirmationEvent(String contractHash) {
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.BROKER_ACK_PAYMENT_CONFIRMED);
        BrokerAckPaymentConfirmed brokerAckPaymentConfirmed = (BrokerAckPaymentConfirmed) fermatEvent;
        brokerAckPaymentConfirmed.setSource(EventSource.BROKER_ACK_OFFLINE_PAYMENT);
        brokerAckPaymentConfirmed.setContractHash(contractHash);
        brokerAckPaymentConfirmed.setPaymentType(PaymentType.FIAT_MONEY);
        eventManager.raiseEvent(brokerAckPaymentConfirmed);
    }

    /**
     * This method returns the currency type from a contract
     *
     * @param contractSale
     * @return
     * @throws CantGetListSaleNegotiationsException
     */
    private MoneyType getMoneyTypeFromContract(CustomerBrokerContractSale contractSale) throws CantGetListSaleNegotiationsException {
        try {
            String negotiationId = contractSale.getNegotiatiotId();
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(negotiationId));
            ObjectChecker.checkArgument(customerBrokerSaleNegotiation, "The customerBrokerSaleNegotiation is null");

            Collection<Clause> clauses = customerBrokerSaleNegotiation.getClauses();
            ClauseType clauseType;

            for (Clause clause : clauses) {
                clauseType = clause.getType();
                if (clauseType.getCode().equals(ClauseType.CUSTOMER_PAYMENT_METHOD.getCode())) {
                    return MoneyType.getByCode(clause.getValue());
                }
            }

            throw new CantGetListSaleNegotiationsException("Cannot find the proper clause");
        } catch (InvalidParameterException e) {
            throw new CantGetListSaleNegotiationsException("Cannot get the negotiation list", e);
        } catch (CantGetListClauseException e) {
            throw new CantGetListSaleNegotiationsException("Cannot find clauses list");
        } catch (ObjectNotSetException e) {
            throw new CantGetListSaleNegotiationsException("The customerBrokerSaleNegotiation is null", e);
        }
    }

    /**
     * This method returns the currency type from a contract
     *
     * @param contractSale
     * @return
     * @throws CantGetListSaleNegotiationsException
     */
    private FiatCurrency getCurrencyTypeFromContract(CustomerBrokerContractSale contractSale) throws CantGetListSaleNegotiationsException {
        try {
            String negotiationId = contractSale.getNegotiatiotId();
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(negotiationId));
            ObjectChecker.checkArgument(customerBrokerSaleNegotiation, "The customerBrokerSaleNegotiation is null");

            Collection<Clause> clauses = customerBrokerSaleNegotiation.getClauses();
            ClauseType clauseType;

            for (Clause clause : clauses) {
                clauseType = clause.getType();
                if (clauseType.getCode().equals(ClauseType.BROKER_CURRENCY.getCode())) {
                    if (FiatCurrency.codeExists(clause.getValue())) {
                        return FiatCurrency.getByCode(clause.getValue());
                    } else {
                        return FiatCurrency.US_DOLLAR;
                    }

                }
            }

            throw new CantGetListSaleNegotiationsException("Cannot find the proper clause");
        } catch (InvalidParameterException e) {
            throw new CantGetListSaleNegotiationsException("Cannot get the negotiation list", e);
        } catch (CantGetListClauseException e) {
            throw new CantGetListSaleNegotiationsException("Cannot find clauses list");
        } catch (ObjectNotSetException e) {
            throw new CantGetListSaleNegotiationsException("The customerBrokerSaleNegotiation is null", e);
        }

    }

}
