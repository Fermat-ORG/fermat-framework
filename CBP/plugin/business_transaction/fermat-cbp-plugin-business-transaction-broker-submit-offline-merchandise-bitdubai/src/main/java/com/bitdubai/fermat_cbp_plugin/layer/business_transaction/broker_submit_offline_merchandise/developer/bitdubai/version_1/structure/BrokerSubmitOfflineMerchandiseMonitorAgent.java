package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
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
import com.bitdubai.fermat_cbp_api.all_definition.agent.CBPTransactionAgent;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.util.NegotiationClauseHelper;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.events.BrokerSubmitMerchandiseConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CannotSendContractHashException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSubmitMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BankMoneyDeStockRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.CashMoneyDeStockRecord;
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
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantConfirmNotificationReceptionException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.exceptions.CantCreateBankMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.interfaces.BankMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.exceptions.CantCreateCashMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.interfaces.CashMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.exceptions.CantCreateCryptoMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.BrokerSubmitOfflineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOfflineMerchandiseBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseFactory;
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


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 22/12/15.
 */
public class BrokerSubmitOfflineMerchandiseMonitorAgent implements
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
    CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;
    CashMoneyDestockManager cashMoneyDestockManager;
    BankMoneyDestockManager bankMoneyDestockManager;
    CryptoBrokerWalletManager cryptoBrokerWalletManager;

    public BrokerSubmitOfflineMerchandiseMonitorAgent(
            PluginDatabaseSystem pluginDatabaseSystem,
            LogManager logManager,
            ErrorManager errorManager,
            EventManager eventManager,
            UUID pluginId,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
            CashMoneyDestockManager cashMoneyDestockManager,
            BankMoneyDestockManager bankMoneyDestockManager,
            CryptoBrokerWalletManager cryptoBrokerWalletManager,CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager) {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.logManager = logManager;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
        this.bankMoneyDestockManager = bankMoneyDestockManager;
        this.cashMoneyDestockManager = cashMoneyDestockManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.customerBrokerPurchaseNegotiationManager=customerBrokerPurchaseNegotiationManager;
    }

    @Override
    public void start() throws CantStartAgentException {

        //Logger LOG = Logger.getGlobal();
        //LOG.info("Customer online payment monitor agent starting");
        monitorAgent = new MonitorAgent();

        this.monitorAgent.setPluginDatabaseSystem(this.pluginDatabaseSystem);
        this.monitorAgent.setErrorManager(this.errorManager);

        try {
            this.monitorAgent.Initialize();
        } catch (CantInitializeCBPAgent exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_SUBMIT_OFFLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
        } catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_SUBMIT_OFFLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    FermatException.wrapException(exception));
        }

        this.agentThread = new Thread(monitorAgent, this.getClass().getSimpleName());
        this.agentThread.start();

    }

    @Override
    public void stop() {
        try {
            this.agentThread.interrupt();
        } catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_SUBMIT_OFFLINE_MERCHANDISE,
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
        BrokerSubmitOfflineMerchandiseBusinessTransactionDao brokerSubmitOfflineMerchandiseBusinessTransactionDao;
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
            logManager.log(BrokerSubmitOfflineMerchandisePluginRoot.getLogLevelByClass(this.getClass().getName()),
                    "Broker Submit Offline Merchandise Monitor Agent: running...", null, null);
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

                    logManager.log(BrokerSubmitOfflineMerchandisePluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CannotSendContractHashException | CantUpdateRecordException | CantSendContractNewStatusNotificationException | CantCreateBankMoneyDestockException | CantSubmitMerchandiseException | CantCreateCryptoMoneyDestockException | CantCreateCashMoneyDestockException e) {
                    errorManager.reportUnexpectedPluginException(
                            Plugins.BROKER_SUBMIT_OFFLINE_MERCHANDISE,
                            UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                            e);
                }

            }

        }

        public void Initialize() throws CantInitializeCBPAgent {
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId,
                        BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME);
            } catch (DatabaseNotFoundException databaseNotFoundException) {

                //Logger LOG = Logger.getGlobal();
                //LOG.info("Database in Open Contract monitor agent doesn't exists");
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseFactory brokerSubmitOfflineMerchandiseBusinessTransactionDatabaseFactory =
                        new BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = brokerSubmitOfflineMerchandiseBusinessTransactionDatabaseFactory.createDatabase(pluginId,
                            BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    errorManager.reportUnexpectedPluginException(
                            Plugins.BROKER_SUBMIT_OFFLINE_MERCHANDISE,
                            UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                            cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException,
                            "Initialize Monitor Agent - trying to create the plugin database",
                            "Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                errorManager.reportUnexpectedPluginException(
                        Plugins.BROKER_SUBMIT_OFFLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        exception);
                throw new CantInitializeCBPAgent(exception,
                        "Initialize Monitor Agent - trying to open the plugin database",
                        "Please, check the cause");
            }
        }

        private void doTheMainTask()
                throws CannotSendContractHashException,
                CantUpdateRecordException,
                CantSendContractNewStatusNotificationException,
                CantCreateCryptoMoneyDestockException,
                CantCreateBankMoneyDestockException,
                CantCreateCashMoneyDestockException,
                CantSubmitMerchandiseException {

            try {

                brokerSubmitOfflineMerchandiseBusinessTransactionDao = new BrokerSubmitOfflineMerchandiseBusinessTransactionDao(
                        pluginDatabaseSystem,
                        pluginId,
                        database,
                        errorManager);

                String contractHash;

                /**
                 * Check if there is some transaction to crypto de stock
                 * The de stock condition is reading the ContractTransactionStatus in PENDING_ONLINE_DE_STOCK
                 */
                List<BusinessTransactionRecord> pendingToDeStockTransactionList = brokerSubmitOfflineMerchandiseBusinessTransactionDao.getPendingDeStockTransactionList();
                for (BusinessTransactionRecord pendingToDeStockTransaction : pendingToDeStockTransactionList) {

                    System.out.println("\nTEST CONTRACT - SUBMIT OFFLINE MERCHANDISE - AGENT - doTheMainTask() - getPendingDeStockTransactionList()\n");

                    MoneyType moneyType = pendingToDeStockTransaction.getPaymentType();
                    switch (moneyType) {
                        case BANK:
                            System.out.println("\nTEST CONTRACT - SUBMIT OFFLINE MERCHANDISE - AGENT - doTheMainTask() - getPendingDeStockTransactionList() - BANK\n");
                            executeBankDeStock(pendingToDeStockTransaction);
                            break;
                        case CASH_DELIVERY:
                            System.out.println("\nTEST CONTRACT - SUBMIT OFFLINE MERCHANDISE - AGENT - doTheMainTask() - getPendingDeStockTransactionList() - CASH DELIVERY\n");
                            executeCashDeStock(pendingToDeStockTransaction);
                            break;
                        case CASH_ON_HAND:
                            System.out.println("\nTEST CONTRACT - SUBMIT OFFLINE MERCHANDISE - AGENT - doTheMainTask() - getPendingDeStockTransactionList() - CASH ON HAND\n");
                            executeCashDeStock(pendingToDeStockTransaction);
                            break;
                        case CRYPTO:
                            throw new CantSubmitMerchandiseException("The currency type is CRYPTO, can't send crypto money from this plugin");
                    }
                }
                /**
                 * Check contract status to send. Broker Side
                 */
                List<BusinessTransactionRecord> pendingToSubmitNotificationList = brokerSubmitOfflineMerchandiseBusinessTransactionDao.getPendingToSubmitNotificationList();
                for (BusinessTransactionRecord pendingToSubmitNotificationRecord : pendingToSubmitNotificationList) {

                    System.out.println("\nTEST CONTRACT - SUBMIT OFFLINE MERCHANDISE - AGENT - doTheMainTask() - getPendingToSubmitNotificationList()\n");

                    contractHash = pendingToSubmitNotificationRecord.getTransactionHash();
                    transactionTransmissionManager.sendContractStatusNotification(
                            pendingToSubmitNotificationRecord.getBrokerPublicKey(),
                            pendingToSubmitNotificationRecord.getCustomerPublicKey(),
                            contractHash,
                            pendingToSubmitNotificationRecord.getTransactionId(),
                            ContractTransactionStatus.OFFLINE_MERCHANDISE_SUBMITTED,
                            Plugins.BROKER_SUBMIT_OFFLINE_MERCHANDISE,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);

                    //Updating the business transaction record
                    pendingToSubmitNotificationRecord.setContractTransactionStatus(ContractTransactionStatus.OFFLINE_MERCHANDISE_SUBMITTED);
                    brokerSubmitOfflineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(pendingToSubmitNotificationRecord);
                }

                /**
                 * Check pending notifications - Customer side
                 */
                List<BusinessTransactionRecord> pendingToSubmitConfirmationList = brokerSubmitOfflineMerchandiseBusinessTransactionDao.getPendingToSubmitConfirmList();
                for (BusinessTransactionRecord pendingToSubmitConfirmationRecord : pendingToSubmitConfirmationList) {

                    contractHash = pendingToSubmitConfirmationRecord.getTransactionHash();

                    System.out.println("\nTEST CONTRACT - SUBMIT OFFLINE MERCHANDISE - AGENT - doTheMainTask() - getPendingToSubmitConfirmList(): " + contractHash + "\n");

                    transactionTransmissionManager.confirmNotificationReception(
                            pendingToSubmitConfirmationRecord.getCustomerPublicKey(),
                            pendingToSubmitConfirmationRecord.getBrokerPublicKey(),
                            contractHash,
                            pendingToSubmitConfirmationRecord.getTransactionId(),
                            Plugins.BROKER_SUBMIT_OFFLINE_MERCHANDISE,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER);

                    //Updating the business transaction record
                    brokerSubmitOfflineMerchandiseBusinessTransactionDao.updateContractTransactionStatus(contractHash,
                            ContractTransactionStatus.CONFIRM_OFFLINE_CONSIGNMENT);
                }

                /**
                 * Check if pending events
                 */
                List<String> pendingEventsIdList = brokerSubmitOfflineMerchandiseBusinessTransactionDao.getPendingEvents();
                for (String eventId : pendingEventsIdList) {
                    checkPendingEvent(eventId);
                }

            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                throw new CannotSendContractHashException(e, "Sending contract hash", "Unexpected result in database");

            } catch (CantSendContractNewStatusNotificationException e) {
                throw new CantSendContractNewStatusNotificationException(CantSendContractNewStatusNotificationException.DEFAULT_MESSAGE,
                        e, "Sending contract hash", "Error in Transaction Transmission Network Service");

            } catch (CantGetContractListException e) {
                throw new CantUpdateRecordException("", e, "Getting the Contract", "Cannot get the contract list");

            } catch (CantConfirmNotificationReceptionException e) {
                throw new CantSendContractNewStatusNotificationException(CantSendContractNewStatusNotificationException.DEFAULT_MESSAGE,
                        e, "Sending confirm contract hash", "Error in Transaction Transmission Network Service");
            }
        }

        private void executeBankDeStock(BusinessTransactionRecord pendingToDeStockTransaction)
                throws CantCreateBankMoneyDestockException,
                CantUpdateRecordException,
                UnexpectedResultReturnedFromDatabaseException {

            final BankMoneyDeStockRecord destockRecord = new BankMoneyDeStockRecord(pendingToDeStockTransaction);

            final BigDecimal amount = getAmount(pendingToDeStockTransaction.getContractHash());
            destockRecord.setAmount(amount);

            final String accountNumber = getAccountNumber(destockRecord.getCbpWalletPublicKey(), destockRecord.getFiatCurrency());
            destockRecord.setBankAccount(accountNumber);

            System.out.println("TEST CONTRACT - SUBMIT OFFLINE MERCHANDISE - AGENT - doTheMainTask() - executeBankDeStock():" +
                    "\n - deStockRecord.getPublicKeyActor(): " + destockRecord.getPublicKeyActor() +
                    "\n - deStockRecord.getFiatCurrency(): " + destockRecord.getFiatCurrency() +
                    "\n - deStockRecord.getCbpWalletPublicKey(): " + destockRecord.getCbpWalletPublicKey() +
                    "\n - deStockRecord.getBankWalletPublicKey(): " + destockRecord.getBankWalletPublicKey() +
                    "\n - deStockRecord.getBankAccount(): " + destockRecord.getBankAccount() +
                    "\n - deStockRecord.getAmount(): " + destockRecord.getAmount() +
                    "\n - deStockRecord.getMemo(): " + destockRecord.getMemo() +
                    "\n - deStockRecord.getPriceReference(): " + destockRecord.getPriceReference() +
                    "\n - deStockRecord.getOriginTransaction(): " + destockRecord.getOriginTransaction() +
                    "\n - pendingToDeStockTransaction.getContractHash(): " + pendingToDeStockTransaction.getContractHash());

            bankMoneyDestockManager.createTransactionDestock(
                    destockRecord.getPublicKeyActor(),
                    destockRecord.getFiatCurrency(),
                    destockRecord.getCbpWalletPublicKey(),
                    destockRecord.getBankWalletPublicKey(),
                    destockRecord.getBankAccount(),
                    destockRecord.getAmount(),
                    destockRecord.getMemo(),
                    destockRecord.getPriceReference(),
                    destockRecord.getOriginTransaction(),
                    pendingToDeStockTransaction.getContractHash());

            pendingToDeStockTransaction.setContractTransactionStatus(ContractTransactionStatus.PENDING_SUBMIT_OFFLINE_MERCHANDISE_NOTIFICATION);

            brokerSubmitOfflineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(pendingToDeStockTransaction);
        }

        private void executeCashDeStock(BusinessTransactionRecord pendingToDeStockTransaction)
                throws CantUpdateRecordException,
                UnexpectedResultReturnedFromDatabaseException,
                CantCreateCashMoneyDestockException {

            CashMoneyDeStockRecord destockRecord = new CashMoneyDeStockRecord(pendingToDeStockTransaction);

            final BigDecimal amount = getAmount(pendingToDeStockTransaction.getContractHash());
            destockRecord.setAmount(amount);

            System.out.println("\nTEST CONTRACT - SUBMIT OFFLINE MERCHANDISE - AGENT - doTheMainTask() - executeCashDeStock():" +
                    "\n - destockRecord.getPublicKeyActor(): " + destockRecord.getPublicKeyActor() +
                    "\n - destockRecord.getFiatCurrency(): " + destockRecord.getFiatCurrency() +
                    "\n - destockRecord.getCbpWalletPublicKey(): " + destockRecord.getCbpWalletPublicKey() +
                    "\n - destockRecord.getBankWalletPublicKey(): " + destockRecord.getCshWalletPublicKey() +
                    "\n - destockRecord.getCashReference(): " + destockRecord.getCashReference() +
                    "\n - destockRecord.getAmount(): " + destockRecord.getAmount() +
                    "\n - destockRecord.getMemo(): " + destockRecord.getMemo() +
                    "\n - destockRecord.getPriceReference(): " + destockRecord.getPriceReference() +
                    "\n - destockRecord.getOriginTransaction(): " + destockRecord.getOriginTransaction() +
                    "\n - pendingToDeStockTransaction.getContractHash(): " + pendingToDeStockTransaction.getContractHash());

            cashMoneyDestockManager.createTransactionDestock(
                    destockRecord.getPublicKeyActor(),
                    destockRecord.getFiatCurrency(),
                    destockRecord.getCbpWalletPublicKey(),
                    destockRecord.getCshWalletPublicKey(),
                    destockRecord.getCashReference(),
                    destockRecord.getAmount(),
                    destockRecord.getMemo(),
                    destockRecord.getPriceReference(),
                    destockRecord.getOriginTransaction(),
                    pendingToDeStockTransaction.getContractHash());

            pendingToDeStockTransaction.setContractTransactionStatus(ContractTransactionStatus.PENDING_SUBMIT_OFFLINE_MERCHANDISE_NOTIFICATION);
            brokerSubmitOfflineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(pendingToDeStockTransaction);
        }

        private String getAccountNumber(String cbpWalletPublicKey, Currency merchandiseCurrency) throws UnexpectedResultReturnedFromDatabaseException {
            try {

                final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(cbpWalletPublicKey);
                final CryptoBrokerWalletSetting cryptoBrokerWalletSetting = cryptoBrokerWallet.getCryptoWalletSetting();
                List<CryptoBrokerWalletAssociatedSetting> associatedWallets = cryptoBrokerWalletSetting.getCryptoBrokerWalletAssociatedSettings();

                for(CryptoBrokerWalletAssociatedSetting associatedWallet : associatedWallets){
                    Platforms platform = associatedWallet.getPlatform();
                    Currency currency = associatedWallet.getMerchandise();

                    if(platform == Platforms.BANKING_PLATFORM && currency == merchandiseCurrency)
                        return associatedWallet.getBankAccount();
                }

                return "";

            } catch (CantGetCryptoBrokerWalletSettingException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the bank account", "Cant Get the associated wallets");
            } catch (CryptoBrokerWalletNotFoundException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the bank account", "Cant Get the broker wallet settings");
            }
        }

        private BigDecimal getAmount(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {

            try {

                CustomerBrokerContractSale customerBrokerContractSale = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
                ObjectChecker.checkArgument(customerBrokerContractSale, "The customerBrokerContractSale is null");

                String negotiationId = customerBrokerContractSale.getNegotiatiotId();

                CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation = customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(negotiationId));
                ObjectChecker.checkArgument(customerBrokerSaleNegotiation, "The customerBrokerSaleNegotiation by Id " + negotiationId + " is null");

                Collection<Clause> clauses = customerBrokerSaleNegotiation.getClauses();
                ClauseType clauseType;
                BigDecimal amount = BigDecimal.ZERO;
                double brokerAmountDouble;

                for (Clause clause : clauses) {
                    clauseType = clause.getType();

                    if (clauseType == ClauseType.CUSTOMER_CURRENCY_QUANTITY) {
                        brokerAmountDouble = parseToDouble(clause.getValue());
                        amount = BigDecimal.valueOf(brokerAmountDouble);
                    }
                }

                return amount;

            } catch (CantGetListCustomerBrokerContractSaleException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the amount merchandise", "Cant Get ContractHash");
            } catch (CantGetListSaleNegotiationsException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the amount merchandise", "Cant Get Negotiation");
            } catch (CantGetListClauseException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the amount merchandise", "Cant Get Clause");
            } catch (ObjectNotSetException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the amount merchandise", "Cant Get ObjectChecker");
            } catch (InvalidParameterException e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the amount merchandise", "An invalid parameter is detected");
            } catch (Exception e) {
                throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the amount merchandise", "N/A");
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

        private void raisePaymentConfirmationEvent(String contractHash, MoneyType moneyType) {
            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.BROKER_SUBMIT_MERCHANDISE_CONFIRMED);
            BrokerSubmitMerchandiseConfirmed brokerSubmitMerchandiseConfirmed = (BrokerSubmitMerchandiseConfirmed) fermatEvent;
            brokerSubmitMerchandiseConfirmed.setSource(EventSource.BROKER_SUBMIT_OFFLINE_MERCHANDISE);
            brokerSubmitMerchandiseConfirmed.setContractHash(contractHash);
            brokerSubmitMerchandiseConfirmed.setMerchandiseType(moneyType);
            eventManager.raiseEvent(brokerSubmitMerchandiseConfirmed);
        }

        private void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {

            try {

                String eventTypeCode = brokerSubmitOfflineMerchandiseBusinessTransactionDao.getEventType(eventId);
                String contractHash;
                BusinessTransactionMetadata businessTransactionMetadata;
                ContractTransactionStatus contractTransactionStatus;
                BusinessTransactionRecord businessTransactionRecord;

                if (eventTypeCode.equals(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE.getCode())) {

                    System.out.print("\nTEST CONTRACT - SUBMIT OFFLINE MERCHANDISE - AGENT - checkPendingEvent() - INCOMING_NEW_CONTRACT_STATUS_UPDATE\n");

                    //This will happen in customer side
                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {

                        businessTransactionMetadata = record.getInformation();
                        contractHash = businessTransactionMetadata.getContractHash();

                        if (brokerSubmitOfflineMerchandiseBusinessTransactionDao.isContractHashInDatabase(contractHash)) {

                            contractTransactionStatus = brokerSubmitOfflineMerchandiseBusinessTransactionDao.getContractTransactionStatus(contractHash);
                            //TODO: analyze what we need to do here.

                        } else {

                            System.out.print("\nTEST CONTRACT - SUBMIT OFFLINE MERCHANDISE - AGENT - checkPendingEvent() - INCOMING_NEW_CONTRACT_STATUS_UPDATE - VAL\n");

                            CustomerBrokerContractPurchase customerBrokerContractPurchase = customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(contractHash);
                            //If the contract is null, I cannot handle with this situation
                            ObjectChecker.checkArgument(customerBrokerContractPurchase);

                            String negotiationId = customerBrokerContractPurchase.getNegotiatiotId();
                            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiationManager.
                                    getNegotiationsByNegotiationId(UUID.fromString(negotiationId));

                            Collection<Clause> negotiationClauses = customerBrokerPurchaseNegotiation.getClauses();
                            String clauseValue = NegotiationClauseHelper.getNegotiationClauseValue(negotiationClauses, ClauseType.BROKER_PAYMENT_METHOD);
                            if (!MoneyType.CRYPTO.getCode().equals(clauseValue)){
                                brokerSubmitOfflineMerchandiseBusinessTransactionDao.persistContractInDatabase(customerBrokerContractPurchase);
                                customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(contractHash, ContractStatus.MERCHANDISE_SUBMIT);
                                Date date = new Date();
                                brokerSubmitOfflineMerchandiseBusinessTransactionDao.setCompletionDateByContractHash(contractHash, date.getTime());
                                //TODO: I'm going to set BANK, I need to look a better way to set this
                                raisePaymentConfirmationEvent(contractHash, MoneyType.BANK);
                            }
                        }

                        transactionTransmissionManager.confirmReception(record.getTransactionID());
                    }

                    brokerSubmitOfflineMerchandiseBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                }


                if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {

                    System.out.print("\nTEST CONTRACT - SUBMIT OFFLINE MERCHANDISE - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE\n");

                    //This will happen in broker side
                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {

                        businessTransactionMetadata = record.getInformation();
                        contractHash = businessTransactionMetadata.getContractHash();

                        if (brokerSubmitOfflineMerchandiseBusinessTransactionDao.isContractHashInDatabase(contractHash)) {

                            System.out.print("\nTEST CONTRACT - SUBMIT OFFLINE MERCHANDISE - AGENT - checkPendingEvent() - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE\n");

                            businessTransactionRecord = brokerSubmitOfflineMerchandiseBusinessTransactionDao.getBusinessTransactionRecord(contractHash);
                            contractTransactionStatus = businessTransactionRecord.getContractTransactionStatus();

                            if (contractTransactionStatus.getCode().equals(ContractTransactionStatus.OFFLINE_MERCHANDISE_SUBMITTED.getCode())) {
                                businessTransactionRecord.setContractTransactionStatus(ContractTransactionStatus.CONFIRM_OFFLINE_CONSIGNMENT);
                                customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(contractHash, ContractStatus.MERCHANDISE_SUBMIT);
                                Date date = new Date();
                                brokerSubmitOfflineMerchandiseBusinessTransactionDao.setCompletionDateByContractHash(contractHash, date.getTime());
                                raisePaymentConfirmationEvent(contractHash, businessTransactionRecord.getPaymentType());
                            }

                        }

                        transactionTransmissionManager.confirmReception(record.getTransactionID());
                    }

                    brokerSubmitOfflineMerchandiseBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);

                }

                //TODO: look a better way to deal with this exceptions
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
            } catch (ObjectNotSetException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(
                        exception,
                        "Checking pending events",
                        "The customerBrokerContractPurchase is null");
            }

        }

    }

}

