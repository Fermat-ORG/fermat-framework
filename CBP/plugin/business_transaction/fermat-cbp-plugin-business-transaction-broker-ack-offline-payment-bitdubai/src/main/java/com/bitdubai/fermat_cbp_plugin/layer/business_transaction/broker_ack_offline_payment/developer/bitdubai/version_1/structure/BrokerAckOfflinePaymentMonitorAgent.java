package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.exceptions.CantMakeDepositTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces.DepositManager;
import com.bitdubai.fermat_cbp_api.all_definition.agent.CBPTransactionAgent;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.PaymentType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.util.NegotiationClauseHelper;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.events.BrokerAckPaymentConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CannotSendContractHashException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetCashTransactionParameterException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
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
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetBankTransactionParametersRecordException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantConfirmNotificationReceptionException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;
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
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database.BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database.BrokerAckOfflinePaymentBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.exceptions.CantCreateDepositTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransactionManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/12/15.
 */
public class BrokerAckOfflinePaymentMonitorAgent implements
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
    CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;
    DepositManager bankDepositTransactionManager;
    CryptoBrokerWalletManager cryptoBrokerWalletManager;
    CashDepositTransactionManager cashDepositTransactionManager;

    public BrokerAckOfflinePaymentMonitorAgent(
            PluginDatabaseSystem pluginDatabaseSystem,
            LogManager logManager,
            ErrorManager errorManager,
            EventManager eventManager,
            UUID pluginId,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
            DepositManager depositManager,
            CryptoBrokerWalletManager cryptoBrokerWalletManager,
            CashDepositTransactionManager cashDepositTransactionManager) {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.logManager = logManager;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
        this.bankDepositTransactionManager = depositManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.cashDepositTransactionManager = cashDepositTransactionManager;
    }

    @Override
    public void start() throws CantStartAgentException {

        Logger LOG = Logger.getGlobal();
        LOG.info("Customer online payment monitor agent starting");
        monitorAgent = new MonitorAgent();

        this.monitorAgent.setPluginDatabaseSystem(this.pluginDatabaseSystem);
        this.monitorAgent.setErrorManager(this.errorManager);

        try {
            this.monitorAgent.Initialize();
        } catch (CantInitializeCBPAgent exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(exception));
        }

        this.agentThread = new Thread(monitorAgent, this.getClass().getSimpleName());
        this.agentThread.start();

    }

    @Override
    public void stop() {
        try {
            this.agentThread.interrupt();
        } catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
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
        BrokerAckOfflinePaymentBusinessTransactionDao brokerAckOfflinePaymentBusinessTransactionDao;
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

            threadWorking = true;
            logManager.log(BrokerAckOfflinePaymentPluginRoot.getLogLevelByClass(this.getClass().getName()),
                    "Broker Ack Offline Payment Monitor Agent: running...", null, null);
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

                    logManager.log(BrokerAckOfflinePaymentPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CantCreateDepositTransactionException | CantMakeDepositTransactionException | CannotSendContractHashException | CantUpdateRecordException | CantSendContractNewStatusNotificationException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                            UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        }

        public void Initialize() throws CantInitializeCBPAgent {
            try {
                database = this.pluginDatabaseSystem.openDatabase(pluginId, BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME);

            } catch (DatabaseNotFoundException databaseNotFoundException) {

                BrokerAckOfflinePaymentBusinessTransactionDatabaseFactory brokerAckOfflinePaymentBusinessTransactionDatabaseFactory =
                        new BrokerAckOfflinePaymentBusinessTransactionDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = brokerAckOfflinePaymentBusinessTransactionDatabaseFactory.createDatabase(pluginId,
                            BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME);

                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                            UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);

                    throw new CantInitializeCBPAgent(cantCreateDatabaseException,
                            "Initialize Monitor Agent - trying to create the plugin database", "Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);

                throw new CantInitializeCBPAgent(exception, "Initialize Monitor Agent - trying to open the plugin database", "Please, check the cause");
            }
        }

        private void doTheMainTask() throws CannotSendContractHashException, CantUpdateRecordException, CantSendContractNewStatusNotificationException, CantMakeDepositTransactionException, CantCreateDepositTransactionException {

            try {
                brokerAckOfflinePaymentBusinessTransactionDao = new BrokerAckOfflinePaymentBusinessTransactionDao(pluginDatabaseSystem, pluginId, database, errorManager);

                String contractHash;
                String cryptoWalletPublicKey;
                String customerAlias;
                UUID externalTransactionId;

                /**
                 * Check pending bank transactions to credit - Broker Side
                 * The status to verify is PENDING_CREDIT_BANK_WALLET, it represents that the payment
                 * is "physically" acknowledge by the broker.
                 */
                List<BusinessTransactionRecord> pendingToBankCreditList = brokerAckOfflinePaymentBusinessTransactionDao.getPendingToBankCreditList();
                for (BusinessTransactionRecord pendingToBankCreditRecord : pendingToBankCreditList) {
                    System.out.println("ACK_OFFLINE_PAYMENT - [Broker] Enter Pending Bank Credit");

                    contractHash = pendingToBankCreditRecord.getContractHash();
                    cryptoWalletPublicKey = pendingToBankCreditRecord.getCBPWalletPublicKey();
                    customerAlias = pendingToBankCreditRecord.getCustomerAlias();
                    BankTransactionParametersRecord bankDepositParameters;

                    bankDepositParameters = getBankDepositParametersFromContractId(contractHash, cryptoWalletPublicKey, customerAlias);

                    final BankTransaction bankTransaction = bankDepositTransactionManager.makeDeposit(bankDepositParameters);
                    System.out.println("ACK_OFFLINE_PAYMENT - [Broker] Make Bank Deposit");

                    externalTransactionId = bankTransaction.getTransactionId();
                    pendingToBankCreditRecord.setExternalTransactionId(externalTransactionId);
                    pendingToBankCreditRecord.setContractTransactionStatus(ContractTransactionStatus.PENDING_ACK_OFFLINE_PAYMENT_NOTIFICATION);
                    pendingToBankCreditRecord.setPaymentType(MoneyType.BANK);

                    brokerAckOfflinePaymentBusinessTransactionDao.updateBusinessTransactionRecord(pendingToBankCreditRecord);
                    System.out.println("ACK_OFFLINE_PAYMENT - [Broker] Update Business Transaction Status: PENDING_ACK_OFFLINE_PAYMENT_NOTIFICATION");
                }

                /**
                 * Check pending bank transactions to credit - Broker Side
                 * The status to verify is PENDING_CREDIT_CASH_WALLET, it represents that the payment
                 * is "physically" acknowledge by the broker.
                 */
                List<BusinessTransactionRecord> pendingToCashCreditList = brokerAckOfflinePaymentBusinessTransactionDao.getPendingToCashCreditList();
                for (BusinessTransactionRecord pendingToCashCreditRecord : pendingToCashCreditList) {
                    System.out.println("ACK_OFFLINE_PAYMENT - [Broker] Enter Pending Cash Credit");

                    contractHash = pendingToCashCreditRecord.getContractHash();
                    cryptoWalletPublicKey = pendingToCashCreditRecord.getCBPWalletPublicKey();
                    customerAlias = pendingToCashCreditRecord.getCustomerAlias();
                    MoneyType paymentType = pendingToCashCreditRecord.getPaymentType();
                    CashTransactionParametersRecord cashDepositParameters;
                    CashDepositTransaction cashDepositTransaction;

                    cashDepositParameters = getCashDepositParametersFromContractId(contractHash, cryptoWalletPublicKey, paymentType, customerAlias);

                    cashDepositTransaction = cashDepositTransactionManager.createCashDepositTransaction(cashDepositParameters);
                    System.out.println("ACK_OFFLINE_PAYMENT - [Broker] Make Cash Deposit");

                    externalTransactionId = cashDepositTransaction.getTransactionId();
                    pendingToCashCreditRecord.setExternalTransactionId(externalTransactionId);
                    pendingToCashCreditRecord.setContractTransactionStatus(ContractTransactionStatus.PENDING_ACK_OFFLINE_PAYMENT_NOTIFICATION);

                    brokerAckOfflinePaymentBusinessTransactionDao.updateBusinessTransactionRecord(pendingToCashCreditRecord);
                    System.out.println("ACK_OFFLINE_PAYMENT - [Broker] Update Business Transaction Status: PENDING_ACK_OFFLINE_PAYMENT_NOTIFICATION");
                }

                /**
                 * Check contract status to send. - Broker Side
                 * The status to verify is PENDING_ACK_OFFLINE_PAYMENT_NOTIFICATION, it represents that the payment is fully
                 * acknowledge by the broker.
                 */
                List<BusinessTransactionRecord> pendingToSubmitNotificationList = brokerAckOfflinePaymentBusinessTransactionDao.getPendingToSubmitNotificationList();
                for (BusinessTransactionRecord pendingToSubmitNotificationRecord : pendingToSubmitNotificationList) {
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

                    brokerAckOfflinePaymentBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.OFFLINE_PAYMENT_ACK);
                    System.out.println("ACK_OFFLINE_PAYMENT - [Broker] Update Business Transaction Status: OFFLINE_PAYMENT_ACK");
                }

                /**
                 * Check pending notifications - Customer side
                 */
                List<BusinessTransactionRecord> pendingToSubmitConfirmationList = brokerAckOfflinePaymentBusinessTransactionDao.getPendingToSubmitConfirmationList();
                for (BusinessTransactionRecord pendingToSubmitConfirmationRecord : pendingToSubmitConfirmationList) {
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

                    brokerAckOfflinePaymentBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONFIRM_OFFLINE_ACK_PAYMENT);
                    System.out.println("ACK_OFFLINE_PAYMENT - [Customer] Update Business Transaction Status: CONFIRM_OFFLINE_ACK_PAYMENT");
                }

                /**
                 * Check if pending events
                 */
                List<String> pendingEventsIdList = brokerAckOfflinePaymentBusinessTransactionDao.getPendingEvents();
                for (String eventId : pendingEventsIdList) {
                    checkPendingEvent(eventId);
                }

            } catch (CantGetContractListException e) {
                throw new CannotSendContractHashException(e, "Sending contract hash", "Cannot get the contract list from database");
            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                throw new CannotSendContractHashException(e, "Sending contract hash", "Unexpected result in database");
            } catch (CantGetBankTransactionParametersRecordException e) {
                throw new CannotSendContractHashException(e, "Sending contract hash", "Cannot get Bank Transaction Parameter Exception");
            } catch (CantGetCashTransactionParameterException e) {
                throw new CannotSendContractHashException(e, "Sending contract hash", "Cannot get Cash Transaction Parameter Exception");
            } catch (CantConfirmNotificationReceptionException e) {
                throw new CannotSendContractHashException(e, "Confirm Reception contract", "Error in Transaction Transmission Network Service");
            }
        }

        private void raiseAckConfirmationEvent(String contractHash) {
            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.BROKER_ACK_PAYMENT_CONFIRMED);
            BrokerAckPaymentConfirmed brokerAckPaymentConfirmed = (BrokerAckPaymentConfirmed) fermatEvent;
            brokerAckPaymentConfirmed.setSource(EventSource.BROKER_ACK_OFFLINE_PAYMENT);
            brokerAckPaymentConfirmed.setContractHash(contractHash);
            brokerAckPaymentConfirmed.setPaymentType(PaymentType.FIAT_MONEY);
            eventManager.raiseEvent(brokerAckPaymentConfirmed);
        }

        private void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {
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
                    MoneyType paymentType = getMoneyTypeFromContract(customerBrokerContractSale);
                    FiatCurrency currencyType = getCurrencyTypeFromContract(customerBrokerContractSale);

                    brokerAckOfflinePaymentBusinessTransactionDao.persistContractInDatabase(
                            customerBrokerContractSale,
                            paymentType,
                            customerBrokerContractSale.getPublicKeyBroker(),
                            ContractTransactionStatus.PENDING_ACK_OFFLINE_PAYMENT,
                            currencyType);

                    System.out.println("ACK_OFFLINE_PAYMENT - NEW_CONTRACT_OPENED - New Business Transaction Status: PENDING_ACK_OFFLINE_PAYMENT");

                    brokerAckOfflinePaymentBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                }

            } catch (CantUpdateRecordException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Checking pending events", "Cannot update the database");
            } catch (CantConfirmTransactionException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Checking pending events", "Cannot confirm the transaction");
            } catch (CantUpdateCustomerBrokerContractSaleException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Checking pending events", "Cannot update the contract sale status");
            } catch (CantDeliverPendingTransactionsException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Checking pending events", "Cannot get the pending transactions from transaction transmission plugin");
            } catch (CantInsertRecordException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Checking pending events", "Cannot insert a record in database");
            } catch (CantGetListCustomerBrokerContractPurchaseException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Checking pending events", "Cannot get the purchase contract");
            } catch (CantGetListCustomerBrokerContractSaleException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Checking pending events", "Cannot get the sale contract");
            } catch (CantUpdateCustomerBrokerContractPurchaseException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Checking pending events", "Cannot update the contract purchase status");
            } catch (ObjectNotSetException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Checking pending events", "The customerBrokerContractSale is null");
            } catch (CantGetListSaleNegotiationsException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Checking pending events", "The Cant get the sale negotiation list");
            }
        }

        /**
         * This method returns a CashTransactionParametersRecord from a given ContractHash/Id
         *
         * @param contractHash
         *
         * @return
         */
        private CashTransactionParametersRecord getCashDepositParametersFromContractId(String contractHash, String cryptoBrokerWalletPublicKey, MoneyType paymentType, String customerAlias) throws CantGetCashTransactionParameterException {
            try {
                CustomerBrokerContractSale customerBrokerContractSale = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
                ObjectChecker.checkArgument(customerBrokerContractSale, "The customerBrokerContractSale is null");

                String negotiationId = customerBrokerContractSale.getNegotiatiotId();
                String brokerPublicKey = customerBrokerContractSale.getPublicKeyBroker();
                ObjectChecker.checkArgument(negotiationId, "The negotiationId for contractHash " + contractHash + " is null");

                CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(negotiationId));
                ObjectChecker.checkArgument(customerBrokerSaleNegotiation, "The customerBrokerSaleNegotiation by Id" + negotiationId + " is null");

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
                        "Payment from Customer " + customerAlias,
                        TransactionType.CREDIT);

            } catch (CantGetListCustomerBrokerContractSaleException e) {
                throw new CantGetCashTransactionParameterException(e, "Getting the CashTransactionParametersRecord", "Cannot get the CustomerBrokerContractSale by contractHash/Id:\n" + contractHash);
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


        /**
         * This method returns a BankTransactionParametersRecord from a given ContractHash/Id
         *
         * @param contractHash
         *
         * @return
         */
        private BankTransactionParametersRecord getBankDepositParametersFromContractId(String contractHash, String cryptoBrokerWalletPublicKey, String customerAlias) throws CantGetBankTransactionParametersRecordException {
            try {
                CustomerBrokerContractSale customerBrokerContractSale = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
                ObjectChecker.checkArgument(customerBrokerContractSale, "The customerBrokerContractSale is null");

                String negotiationId = customerBrokerContractSale.getNegotiatiotId();
                String actorPublicKey = customerBrokerContractSale.getPublicKeyBroker();
                ObjectChecker.checkArgument(negotiationId, "The negotiationId for contractHash " + contractHash + " is null");

                CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(negotiationId));
                ObjectChecker.checkArgument(customerBrokerSaleNegotiation, "The customerBrokerSaleNegotiation by Id " + negotiationId + " is null");

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
                CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(cryptoBrokerWalletPublicKey);
                CryptoBrokerWalletSetting cryptoBrokerWalletSetting = cryptoBrokerWallet.getCryptoWalletSetting();
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
                        "Payment from Customer " + customerAlias);

            } catch (CantGetListCustomerBrokerContractSaleException e) {
                throw new CantGetBankTransactionParametersRecordException(e, "Getting the BankTransactionParametersRecord", "Cannot get the CustomerBrokerContractSale by contractHash/Id:\n" + contractHash);
            } catch (ObjectNotSetException e) {
                throw new CantGetBankTransactionParametersRecordException(e, "Getting the BankTransactionParametersRecord", "An object to set is null");
            } catch (CantGetListSaleNegotiationsException e) {
                throw new CantGetBankTransactionParametersRecordException(e, "Getting the BankTransactionParametersRecord", "Cannot get the negotiation list");
            } catch (CryptoBrokerWalletNotFoundException e) {
                throw new CantGetBankTransactionParametersRecordException(e, "Getting the BankTransactionParametersRecord", "Cannot get the crypto wallet");
            } catch (CantGetCryptoBrokerWalletSettingException e) {
                throw new CantGetBankTransactionParametersRecordException(e, "Getting the BankTransactionParametersRecord", "Cannot get the wallet setting");
            } catch (InvalidParameterException e) {
                throw new CantGetBankTransactionParametersRecordException(e, "Getting the BankTransactionParametersRecord", "An invalid parameter is detected");
            } catch (CantGetListClauseException e) {
                throw new CantGetBankTransactionParametersRecordException(e, "Getting the BankTransactionParametersRecord", "Cannot get the clauses");
            } catch (Exception e) {
                throw new CantGetBankTransactionParametersRecordException(e, "Getting the BankTransactionParametersRecord", "Unexpected exception");
            }
        }

        /**
         * This method parse a String object to a long object
         *
         * @param stringValue
         *
         * @return
         *
         * @throws InvalidParameterException
         */
        public double parseToDouble(String stringValue) throws InvalidParameterException {
            if (stringValue == null) {
                throw new InvalidParameterException("Cannot parse a null string value to long");
            } else {
                try {
                    return NumberFormat.getInstance().parse(stringValue).doubleValue();
                } catch (Exception exception) {
                    throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, FermatException.wrapException(exception),
                            "Parsing String object to long", "Cannot parse " + stringValue + " string value to long");
                }
            }
        }

        /**
         * This method returns the currency type from a contract
         *
         * @param contractSale
         *
         * @return
         *
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
                        return FiatCurrency.getByCode(clause.getValue());
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
         *
         * @return
         *
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
    }
}


