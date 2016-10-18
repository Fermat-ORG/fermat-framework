package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.PaymentType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.util.NegotiationClauseHelper;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.events.BrokerAckPaymentConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.AbstractBusinessTransactionAgent;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.IncomingMoneyEventWrapper;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantUpdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.interfaces.CryptoMoneyRestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.BrokerAckOnlinePaymentPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.exceptions.IncomingOnlinePaymentException;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/07/16.
 */
public class BrokerAckOnlinePaymentMonitorAgent2
        extends AbstractBusinessTransactionAgent<BrokerAckOnlinePaymentPluginRoot> {

    private final BrokerAckOnlinePaymentBusinessTransactionDao dao;
    private final TransactionTransmissionManager transactionTransmissionManager;
    private final CustomerBrokerContractPurchaseManager contractPurchaseManager;
    private final CustomerBrokerContractSaleManager contractSaleManager;
    private final CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;
    private final CryptoMoneyRestockManager cryptoMoneyRestockManager;

    /**
     * Default constructor with parameters
     *  @param sleepTime
     * @param timeUnit
     * @param initDelayTime
     * @param pluginRoot
     * @param eventManager
     * @param transactionTransmissionManager
     * @param contractPurchaseManager
     * @param contractSaleManager
     * @param customerBrokerSaleNegotiationManager
     * @param cryptoMoneyRestockManager
     */
    public BrokerAckOnlinePaymentMonitorAgent2(long sleepTime,
                                               TimeUnit timeUnit,
                                               long initDelayTime,
                                               BrokerAckOnlinePaymentPluginRoot pluginRoot,
                                               BrokerAckOnlinePaymentBusinessTransactionDao dao,
                                               EventManager eventManager,
                                               TransactionTransmissionManager transactionTransmissionManager,
                                               CustomerBrokerContractPurchaseManager contractPurchaseManager,
                                               CustomerBrokerContractSaleManager contractSaleManager,
                                               CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
                                               CryptoMoneyRestockManager cryptoMoneyRestockManager) {

        super(sleepTime, timeUnit, initDelayTime, pluginRoot, eventManager);
        this.dao = dao;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.contractPurchaseManager = contractPurchaseManager;
        this.contractSaleManager = contractSaleManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
        this.cryptoMoneyRestockManager = cryptoMoneyRestockManager;
    }

    @Override
    protected void doTheMainTask() {
        try {

            String contractHash;
            /**
             * Check if pending incoming money events
             */
            List<String> pendingMoneyEventIdList = dao.getPendingIncomingMoneyEvents();
            for (String eventId : pendingMoneyEventIdList) {
                try {
                    checkPendingIncomingMoneyEvents(eventId);
                } catch (Exception e) {
                    reportError(e);
                }

            }

            /**
             * Check contract status to send. - Broker Side
             * The status to verify is PENDING_ACK_ONLINE_PAYMENT_NOTIFICATION, it represents that the payment is
             * acknowledge by the broker.
             */
            List<BusinessTransactionRecord> pendingToSubmitNotificationList = dao.getPendingToSubmitNotificationList();
            for (BusinessTransactionRecord pendingToSubmitNotificationRecord : pendingToSubmitNotificationList) {
                try {
                    System.out.println("ACK_ONLINE_PAYMENT [Broker] - getting Pending To Submit Notification Record");

                    contractHash = pendingToSubmitNotificationRecord.getContractHash();
                    transactionTransmissionManager.sendContractStatusNotification(
                            pendingToSubmitNotificationRecord.getBrokerPublicKey(),
                            pendingToSubmitNotificationRecord.getCustomerPublicKey(),
                            contractHash,
                            pendingToSubmitNotificationRecord.getTransactionId(),
                            ContractTransactionStatus.ONLINE_PAYMENT_ACK,
                            Plugins.BROKER_ACK_ONLINE_PAYMENT,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);

                    System.out.println("ACK_ONLINE_PAYMENT [Broker] - sent Contract Status Notification Message");

                    final CustomerBrokerContractSale saleContract = contractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
                    applySalePaymentCredit(WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode(), saleContract, saleContract.getNegotiatiotId());
                    System.out.println("ACK_ONLINE_PAYMENT - [Broker] Make Crypto Restock");

                    dao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.ONLINE_PAYMENT_ACK);
                    System.out.println("ACK_ONLINE_PAYMENT [Broker] - ContractTransactionStatus updated to ONLINE_PAYMENT_ACK");
                } catch (Exception e) {
                    reportError(e);
                }

            }

            /**
             * Check pending notifications - Customer side
             */
            List<BusinessTransactionRecord> pendingToSubmitConfirmationList = dao.getPendingToSubmitConfirmList();
            for (BusinessTransactionRecord pendingToSubmitConfirmationRecord : pendingToSubmitConfirmationList) {
                try {
                    System.out.println("ACK_ONLINE_PAYMENT [Customer] - getting Pending To Submit Confirmation Record");
                    contractHash = pendingToSubmitConfirmationRecord.getTransactionHash();

                    transactionTransmissionManager.confirmNotificationReception(
                            pendingToSubmitConfirmationRecord.getCustomerPublicKey(),
                            pendingToSubmitConfirmationRecord.getBrokerPublicKey(),
                            contractHash,
                            pendingToSubmitConfirmationRecord.getTransactionId(),
                            Plugins.BROKER_ACK_ONLINE_PAYMENT,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER);

                    System.out.println("ACK_ONLINE_PAYMENT [Customer] - sent Confirm Notification Reception Message");

                    dao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONFIRM_ONLINE_ACK_PAYMENT);
                    System.out.println("ACK_ONLINE_PAYMENT [Customer] - ContractTransactionStatus updated to CONFIRM_ONLINE_ACK_PAYMENT");
                } catch (Exception e) {
                    reportError(e);
                }

            }

            /**
             * Check if pending events
             */
            List<String> pendingEventsIdList = dao.getPendingEvents();
            for (String eventId : pendingEventsIdList) {
                checkPendingEvent(eventId);
            }

        } catch (
                Exception e) {
            reportError(e);
        }
    }

    @Override
    protected void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            String eventTypeCode = dao.getEventType(eventId);
            String contractHash;
            BusinessTransactionMetadata businessTransactionMetadata;
            ContractTransactionStatus contractTransactionStatus;
            BusinessTransactionRecord businessTransactionRecord;

            //This will happen in customer side
            if (eventTypeCode.equals(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE.getCode())) {
                System.out.println("BROKER_ACK_ONLINE_PAYMENT [Customer] - INCOMING_NEW_CONTRACT_STATUS_UPDATE");
                List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);

                for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                    businessTransactionMetadata = record.getInformation();
                    contractHash = businessTransactionMetadata.getContractHash();
                    Plugins remoteBusinessTransaction = businessTransactionMetadata.getRemoteBusinessTransaction();

                    System.out.println("BROKER_ACK_ONLINE_PAYMENT - remoteBusinessTransaction = " + remoteBusinessTransaction);
                    if (remoteBusinessTransaction != Plugins.BROKER_ACK_ONLINE_PAYMENT)
                        continue;

                    System.out.println("BROKER_ACK_ONLINE_PAYMENT - PASS remoteBusinessTransaction = " + remoteBusinessTransaction);

                    if (!dao.isContractHashInDatabase(contractHash)) {
                        CustomerBrokerContractPurchase contractPurchase = contractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(contractHash);
                        ObjectChecker.checkArgument(contractPurchase); //If the contract is null, I cannot handle with this situation

                        if (contractPurchase.getStatus() != ContractStatus.COMPLETED) {
                            dao.persistContractInDatabase(contractPurchase);
                            System.out.println("BROKER_ACK_ONLINE_PAYMENT [Customer] - INCOMING_NEW_CONTRACT_STATUS_UPDATE - persisted contractPurchase");

                            dao.setCompletionDateByContractHash(contractHash, (new Date()).getTime());
                            System.out.println("BROKER_ACK_ONLINE_PAYMENT [Customer] - INCOMING_NEW_CONTRACT_STATUS_UPDATE - settled completion date");

                            contractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(contractHash, ContractStatus.PENDING_MERCHANDISE);
                            System.out.println("BROKER_ACK_ONLINE_PAYMENT [Customer] - INCOMING_NEW_CONTRACT_STATUS_UPDATE - ContractStatus Updated to PENDING_MERCHANDISE");

                            raiseAckConfirmationEvent(contractHash);
                            System.out.println("BROKER_ACK_ONLINE_PAYMENT [Customer] - INCOMING_NEW_CONTRACT_STATUS_UPDATE - raise Ack Confirmation Event");
                        }
                    }
                    transactionTransmissionManager.confirmReception(record.getTransactionID());
                }
                dao.updateEventStatus(eventId, EventStatus.NOTIFIED);
            }

            //This will happen in broker side
            if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {
                System.out.println("BROKER_ACK_ONLINE_PAYMENT [Broker] - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE");
                List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);

                for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                    businessTransactionMetadata = record.getInformation();
                    contractHash = businessTransactionMetadata.getContractHash();
                    Plugins remoteBusinessTransaction = businessTransactionMetadata.getRemoteBusinessTransaction();

                    System.out.println("BROKER_ACK_ONLINE_PAYMENT - remoteBusinessTransaction = " + remoteBusinessTransaction);
                    if (remoteBusinessTransaction != Plugins.BROKER_ACK_ONLINE_PAYMENT)
                        continue;

                    System.out.println("BROKER_ACK_ONLINE_PAYMENT - PASS remoteBusinessTransaction = " + remoteBusinessTransaction);

                    if (dao.isContractHashInDatabase(contractHash)) {
                        businessTransactionRecord = dao.getBusinessTransactionRecordByContractHash(contractHash);
                        contractTransactionStatus = businessTransactionRecord.getContractTransactionStatus();

                        if (contractTransactionStatus.getCode().equals(ContractTransactionStatus.ONLINE_PAYMENT_ACK.getCode())) {
                            CustomerBrokerContractSale customerBrokerContractSale = contractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
                            ObjectChecker.checkArgument(customerBrokerContractSale);

                            if (customerBrokerContractSale.getStatus() != ContractStatus.COMPLETED) {
                                contractSaleManager.updateStatusCustomerBrokerSaleContractStatus(contractHash, ContractStatus.PENDING_MERCHANDISE);
                                System.out.println("BROKER_ACK_ONLINE_PAYMENT [Broker] - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - ContractStatus updated to PENDING_MERCHANDISE");

                                dao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONFIRM_ONLINE_ACK_PAYMENT);
                                System.out.println("BROKER_ACK_ONLINE_PAYMENT [Broker] - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - ContractTransactionStatus updated to CONFIRM_ONLINE_ACK_PAYMENT");

                                dao.setCompletionDateByContractHash(contractHash, new Date().getTime());
                                System.out.println("BROKER_ACK_ONLINE_PAYMENT [Broker] - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - settled Completion Date");

                                raiseAckConfirmationEvent(contractHash);
                                System.out.println("BROKER_ACK_ONLINE_PAYMENT [Broker] - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - raise Ack Confirmation Event");
                            }
                        }
                    }
                    transactionTransmissionManager.confirmReception(record.getTransactionID());
                    System.out.println("BROKER_ACK_ONLINE_PAYMENT [Broker] - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - Reception Confirmed");
                }
                dao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                System.out.println("BROKER_ACK_ONLINE_PAYMENT [Broker] - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - EventStatus updated to NOTIFIED");
            }

            if (eventTypeCode.equals(EventType.NEW_CONTRACT_OPENED.getCode())) {
                System.out.println("BROKER_ACK_ONLINE_PAYMENT [Broker] - NEW_CONTRACT_OPENED");

                try {
                    //the eventId from this event is the contractId - Broker side
                    CustomerBrokerContractSale saleContract = contractSaleManager.getCustomerBrokerContractSaleForContractId(eventId);

                    String negotiationId = saleContract.getNegotiatiotId();
                    CustomerBrokerSaleNegotiation saleNegotiation = customerBrokerSaleNegotiationManager.
                            getNegotiationsByNegotiationId(UUID.fromString(negotiationId));

                    Collection<Clause> clauses = saleNegotiation.getClauses();

                    String clauseValue = NegotiationClauseHelper.getNegotiationClauseValue(clauses, ClauseType.CUSTOMER_PAYMENT_METHOD);

                    if (MoneyType.CRYPTO.getCode().equals(clauseValue)) {
                        String paymentCurrencyCode = NegotiationClauseHelper.getNegotiationClauseValue(clauses, ClauseType.BROKER_CURRENCY);
                        CryptoCurrency paymentCurrency = CryptoCurrency.getByCode(paymentCurrencyCode);

                        String amountStr = NegotiationClauseHelper.getNegotiationClauseValue(clauses, ClauseType.BROKER_CURRENCY_QUANTITY);
                        long cryptoAmount = getCryptoAmount(amountStr, paymentCurrencyCode);

                        dao.persistContractInDatabase(saleContract, cryptoAmount, paymentCurrency);
                        System.out.println("BROKER_ACK_ONLINE_PAYMENT [Broker] - NEW_CONTRACT_OPENED - persisted sale contract. cryptoAmount = " + cryptoAmount + " - paymentCurrency = " + paymentCurrency);
                    }
                } catch (Exception e) {
                    System.out.println("BROKER_ACK_ONLINE_PAYMENT - NEW_CONTRACT_OPENED - EXCEPTION!! Probably this is been executed in the Customer Side");
                }

                dao.updateEventStatus(eventId, EventStatus.NOTIFIED);
            }

        } catch (CantUpdateRecordException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Checking pending events", "Cannot update the database");
        } catch (CantConfirmTransactionException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Checking pending events", "Cannot confirm the transaction");
        } catch (CantUpdateCustomerBrokerContractSaleException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Checking pending events", "Cannot update the contract sale status");
        } catch (CantDeliverPendingTransactionsException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Checking pending events", "Cannot get the pending transactions from transaction transmission plugin");
        } catch (CantInsertRecordException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Checking pending events", "Cannot insert a record in database");
        } catch (CantGetListCustomerBrokerContractPurchaseException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Checking pending events", "Cannot get the purchase contract");
        } catch (CantGetListCustomerBrokerContractSaleException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Checking pending events", "Cannot get the sale contract");
        } catch (CantUpdateCustomerBrokerContractPurchaseException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Checking pending events", "Cannot update the contract purchase status");
        } catch (ObjectNotSetException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Checking pending events", "The customerBrokerContractPurchase is null");
        }
    }

    protected void raiseAckConfirmationEvent(String contractHash) {
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.BROKER_ACK_PAYMENT_CONFIRMED);
        BrokerAckPaymentConfirmed brokerAckPaymentConfirmed = (BrokerAckPaymentConfirmed) fermatEvent;
        brokerAckPaymentConfirmed.setSource(EventSource.BROKER_ACK_ONLINE_PAYMENT);
        brokerAckPaymentConfirmed.setContractHash(contractHash);
        brokerAckPaymentConfirmed.setPaymentType(PaymentType.CRYPTO_MONEY);

        eventManager.raiseEvent(brokerAckPaymentConfirmed);
    }

    private void checkPendingIncomingMoneyEvents(String eventId) throws IncomingOnlinePaymentException, CantUpdateRecordException {
        System.out.println("ACK_ONLINE_PAYMENT - checkPendingIncomingMoneyEvents");

        try {
            IncomingMoneyEventWrapper incomingMoneyEventWrapper = dao.getIncomingMoneyEventWrapper(eventId);
            String contractHash = incomingMoneyEventWrapper.getTransactionHash();
            BusinessTransactionRecord businessTransactionRecord = dao.getBusinessTransactionRecordByContractHash(contractHash);

            if (businessTransactionRecord == null)
                return; //Case: the contract event is not processed or the incoming money is not link to a contract.

            //TODO probar esto
            long incomingCryptoAmount = incomingMoneyEventWrapper.getCryptoAmount();
            long contractCryptoAmount = businessTransactionRecord.getCryptoAmount();
            if (incomingCryptoAmount != contractCryptoAmount) {
                throw new IncomingOnlinePaymentException("The incoming crypto amount received is " + incomingCryptoAmount + "\nThe amount excepted in contract " + contractHash + "\nis " + contractCryptoAmount);
            }

            //TODO probar esto
            CryptoCurrency incomingCryptoCurrency = incomingMoneyEventWrapper.getCryptoCurrency();
            CryptoCurrency contractCryptoCurrency = businessTransactionRecord.getCryptoCurrency();
            if (incomingCryptoCurrency != contractCryptoCurrency) {
                throw new IncomingOnlinePaymentException("The incoming crypto currency received is " + incomingCryptoCurrency + "\nThe crypto currency excepted in contract " + contractHash + "\nis " + contractCryptoCurrency);
            }

            String receiverActorPublicKey = incomingMoneyEventWrapper.getReceiverPublicKey();
            String expectedActorPublicKey = businessTransactionRecord.getCustomerPublicKey();
            if (!receiverActorPublicKey.equals(expectedActorPublicKey)) {
                throw new IncomingOnlinePaymentException("The actor public key that receive the money is " + receiverActorPublicKey + "\nThe broker public key in contract " + contractHash + "\nis " + expectedActorPublicKey);
            }

            businessTransactionRecord.setContractTransactionStatus(ContractTransactionStatus.PENDING_ACK_ONLINE_PAYMENT_NOTIFICATION);
            dao.updateBusinessTransactionRecord(businessTransactionRecord);
            System.out.println("ACK_ONLINE_PAYMENT - checkPendingIncomingMoneyEvents - ContractTransactionStatus updated to PENDING_ACK_ONLINE_PAYMENT_NOTIFICATION");

            dao.updateIncomingMoneyEventStatus(eventId, EventStatus.NOTIFIED);
            System.out.println("ACK_ONLINE_PAYMENT - checkPendingIncomingMoneyEvents - Incoming Money EventStatus updated to NOTIFIED");

        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            throw new IncomingOnlinePaymentException(e, "Checking the incoming payment", "The database return an unexpected result");
        }
    }

    private void applySalePaymentCredit(String brokerWalletPublicKey, CustomerBrokerContractSale contractSale, String negotiationId) throws FermatException, ParseException {

        final NumberFormat numberFormat = NumberFormat.getInstance();
        final CustomerBrokerSaleNegotiation saleNegotiation = customerBrokerSaleNegotiationManager.
                getNegotiationsByNegotiationId(UUID.fromString(negotiationId));

        // Obtengo info de las clausulas de la negociacion
        final Collection<Clause> saleNegotiationClauses = saleNegotiation.getClauses();

        String clauseValue;

        clauseValue = NegotiationClauseHelper.getNegotiationClauseValue(saleNegotiationClauses, ClauseType.EXCHANGE_RATE);
        assert clauseValue != null;
        final BigDecimal priceReference = new BigDecimal(Double.valueOf(clauseValue));

        clauseValue = NegotiationClauseHelper.getNegotiationClauseValue(saleNegotiationClauses, ClauseType.BROKER_CURRENCY_QUANTITY);
        assert clauseValue != null;
        BigDecimal amount = new BigDecimal(Double.valueOf(clauseValue));

        clauseValue = NegotiationClauseHelper.getNegotiationClauseValue(saleNegotiationClauses, ClauseType.CUSTOMER_PAYMENT_METHOD);
        final MoneyType paymentMethod = MoneyType.getByCode(clauseValue);

        final String currencyCode = NegotiationClauseHelper.getNegotiationClauseValue(saleNegotiationClauses, ClauseType.BROKER_CURRENCY);

        //Ejecuto el restock dependiendo del tipo de transferencia a realizar
        switch (paymentMethod) {
            case CRYPTO:
                CryptoCurrency cryptoCurrency = CryptoCurrency.getByCode(currencyCode);
                String cryptoWalletPublicKey;

                switch (cryptoCurrency) {
                    case BITCOIN:
                        amount = new BigDecimal(BitcoinConverter.convert(amount.doubleValue(), BitcoinConverter.Currency.BITCOIN, BitcoinConverter.Currency.SATOSHI));
                        cryptoWalletPublicKey = WalletsPublicKeys.CCP_REFERENCE_WALLET.getCode();
                        break;
                    case FERMAT:
                        amount = new BigDecimal(BitcoinConverter.convert(amount.doubleValue(), BitcoinConverter.Currency.FERMAT, BitcoinConverter.Currency.SATOSHI));
                        cryptoWalletPublicKey = WalletsPublicKeys.CCP_FERMAT_WALLET.getCode();
                        break;
                    default:
                        throw new UnsupportedOperationException("The Crypto Restock operation is not supported for the currency " + cryptoCurrency.getFriendlyName());
                }

                cryptoMoneyRestockManager.createTransactionRestock(
                        contractSale.getPublicKeyBroker(),
                        cryptoCurrency,
                        brokerWalletPublicKey,
                        cryptoWalletPublicKey,
                        amount,
                        "Payment from a Customer",
                        priceReference,
                        OriginTransaction.SALE,
                        contractSale.getContractId(),
                        BlockchainNetworkType.REG_TEST,
                        BitcoinFee.SLOW.getFee(),
                        FeeOrigin.SUBSTRACT_FEE_FROM_AMOUNT); //TODO: Revisar de donde saco esto
                break;
        }
    }
}
