package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
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
import com.bitdubai.fermat_cbp_api.all_definition.agent.CBPTransactionAgent;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.util.NegotiationClauseHelper;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.events.BrokerSubmitMerchandiseConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CannotSendContractHashException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.CryptoMoneyDeStockRecord;
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
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantConfirmNotificationReceptionException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.exceptions.CantCreateCryptoMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.interfaces.CryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.BrokerSubmitOnlineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetCryptoStatusException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetSendCryptoTransactionHashException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.IntraActorCryptoTransactionManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN;
import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus.COMPLETED;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus.MERCHANDISE_SUBMIT;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.CONFIRM_ONLINE_CONSIGNMENT;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.CRYPTO_MERCHANDISE_SUBMITTED;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.ONLINE_MERCHANDISE_SUBMITTED;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_SUBMIT_ONLINE_MERCHANDISE;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_SUBMIT_ONLINE_MERCHANDISE_NOTIFICATION;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/12/15.
 */
public class BrokerSubmitOnlineMerchandiseMonitorAgent implements
        CBPTransactionAgent,
        DealsWithLogger,
        DealsWithEvents,
        DealsWithPluginDatabaseSystem,
        DealsWithPluginIdentity {

    Database database;
    MonitorAgent monitorAgent;
    Thread agentThread;
    LogManager logManager;
    EventManager eventManager;
    BrokerSubmitOnlineMerchandisePluginRoot pluginRoot;
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;
    TransactionTransmissionManager transactionTransmissionManager;
    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    CustomerBrokerPurchaseNegotiationManager purchaseNegotiationManager;
    IntraActorCryptoTransactionManager intraActorCryptoTransactionManager;
    OutgoingIntraActorManager outgoingIntraActorManager;
    CryptoMoneyDestockManager cryptoMoneyDeStockManager;
    IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    public BrokerSubmitOnlineMerchandiseMonitorAgent(
            PluginDatabaseSystem pluginDatabaseSystem,
            LogManager logManager,
            BrokerSubmitOnlineMerchandisePluginRoot pluginRoot,
            EventManager eventManager,
            UUID pluginId,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            OutgoingIntraActorManager outgoingIntraActorManager,
            CryptoMoneyDestockManager cryptoMoneyDeStockManager,
            IntraWalletUserIdentityManager intraWalletUserIdentityManager,
            CustomerBrokerPurchaseNegotiationManager purchaseNegotiationManager) throws CantSetObjectException {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginRoot = pluginRoot;
        this.pluginId = pluginId;
        this.logManager = logManager;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.outgoingIntraActorManager = outgoingIntraActorManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.cryptoMoneyDeStockManager = cryptoMoneyDeStockManager;
        this.intraWalletUserIdentityManager = intraWalletUserIdentityManager;
        this.purchaseNegotiationManager = purchaseNegotiationManager;

        setIntraActorCryptoTransactionManager(outgoingIntraActorManager);
    }

    private void setIntraActorCryptoTransactionManager(OutgoingIntraActorManager outgoingIntraActorManager) throws CantSetObjectException {
        if (outgoingIntraActorManager == null) {
            throw new CantSetObjectException("outgoingIntraActorManager is null");
        }
        try {
            this.intraActorCryptoTransactionManager = outgoingIntraActorManager.getTransactionManager();
        } catch (CantGetOutgoingIntraActorTransactionManagerException e) {
            throw new CantSetObjectException(
                    e,
                    "Setting the intraActorCryptoTransactionManager",
                    "Cannot get the intraActorCryptoTransactionManager from outgoingIntraActorManager");
        }
    }

    @Override
    public void start() throws CantStartAgentException {

        //Logger LOG = Logger.getGlobal();
        //LOG.info("Customer online payment monitor agent starting");
        monitorAgent = new MonitorAgent(pluginRoot);

        this.monitorAgent.setPluginDatabaseSystem(this.pluginDatabaseSystem);

        try {
            this.monitorAgent.Initialize();
        } catch (CantInitializeCBPAgent exception) {
            pluginRoot.reportError(
                    DISABLES_THIS_PLUGIN,
                    exception);
        } catch (Exception exception) {
            this.pluginRoot.reportError(
                    DISABLES_THIS_PLUGIN,
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
            this.pluginRoot.reportError(
                    DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
        }
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
    private class MonitorAgent implements DealsWithPluginDatabaseSystem, Runnable {

        BrokerSubmitOnlineMerchandisePluginRoot pluginRoot;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = 5000;
        int iterationNumber = 0;
        BrokerSubmitOnlineMerchandiseBusinessTransactionDao dao;
        boolean threadWorking;

        public MonitorAgent(BrokerSubmitOnlineMerchandisePluginRoot pluginRoot) {
            this.pluginRoot = pluginRoot;
        }

        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        @Override
        public void run() {

            threadWorking = true;
            logManager.log(BrokerSubmitOnlineMerchandisePluginRoot.getLogLevelByClass(this.getClass().getName()),
                    "Broker Submit Online Merchandise Monitor Agent: running...", null, null);
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

                    logManager.log(BrokerSubmitOnlineMerchandisePluginRoot.getLogLevelByClass(this.getClass().getName()), new StringBuilder().append("Iteration number ").append(iterationNumber).toString(), null, null);
                    doTheMainTask();
                } catch (Exception e) {
                    pluginRoot.reportError(

                            DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                            e);
                }

            }

        }

        public void Initialize() throws CantInitializeCBPAgent {
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId,
                        BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME);
            } catch (DatabaseNotFoundException databaseNotFoundException) {

                //Logger LOG = Logger.getGlobal();
                //LOG.info("Database in Open Contract monitor agent doesn't exists");
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory brokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory =
                        new BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = brokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory.createDatabase(pluginId,
                            BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    pluginRoot.reportError(

                            DISABLES_THIS_PLUGIN,
                            cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException,
                            "Initialize Monitor Agent - trying to create the plugin database",
                            "Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                pluginRoot.reportError(

                        DISABLES_THIS_PLUGIN,
                        exception);
                throw new CantInitializeCBPAgent(exception,
                        "Initialize Monitor Agent - trying to open the plugin database",
                        "Please, check the cause");
            }
        }

        private void doTheMainTask() throws CannotSendContractHashException, CantUpdateRecordException, CantSendContractNewStatusNotificationException, CantCreateCryptoMoneyDestockException, CantConfirmNotificationReceptionException {

            try {
                dao = new BrokerSubmitOnlineMerchandiseBusinessTransactionDao(pluginDatabaseSystem, pluginId, database, pluginRoot);
                String contractHash;
                ContractTransactionStatus contractTransactionStatus;

                /**
                 * Check if there is some transaction to crypto De-stock
                 * The de-stock condition is reading the ContractTransactionStatus in PENDING_ONLINE_DE_STOCK
                 */
                List<BusinessTransactionRecord> pendingToDeStockTransactionList = dao.getPendingDeStockTransactionList();
                for (BusinessTransactionRecord pendingToDeStockTransaction : pendingToDeStockTransactionList) {
                    CryptoMoneyDeStockRecord cryptoMoneyDeStockRecord = new CryptoMoneyDeStockRecord(pendingToDeStockTransaction);

                    //Execute the de stock transaction.
                    cryptoMoneyDeStockManager.createTransactionDestock(
                            cryptoMoneyDeStockRecord.getPublicKeyActor(),
                            cryptoMoneyDeStockRecord.getCryptoCurrency(),
                            cryptoMoneyDeStockRecord.getCbpWalletPublicKey(),
                            cryptoMoneyDeStockRecord.getCryptoWalletPublicKey(),
                            cryptoMoneyDeStockRecord.getAmount(),
                            cryptoMoneyDeStockRecord.getMemo(),
                            cryptoMoneyDeStockRecord.getPriceReference(),
                            cryptoMoneyDeStockRecord.getOriginTransaction(),
                            pendingToDeStockTransaction.getContractHash(),
                            cryptoMoneyDeStockRecord.getBlockchainNetworkType(),
                            cryptoMoneyDeStockRecord.getFee(),
                            cryptoMoneyDeStockRecord.getFeeOrigin()); //TODO de Manuel: crear un setting para configuar esto

                    pendingToDeStockTransaction.setContractTransactionStatus(PENDING_SUBMIT_ONLINE_MERCHANDISE);
                    dao.updateBusinessTransactionRecord(pendingToDeStockTransaction);
                }

                /**
                 * Check if there is some crypto to send
                 */
                List<String> pendingToSubmitCrypto = dao.getPendingToSubmitCryptoList();
                for (String pendingContractHash : pendingToSubmitCrypto) {
                    BusinessTransactionRecord businessTransactionRecord = dao.getBrokerBusinessTransactionRecord(pendingContractHash);

                    //I'll check if the merchandise was sent in a previous loop
                    contractTransactionStatus = businessTransactionRecord
                            .getContractTransactionStatus();
                    if (contractTransactionStatus != ContractTransactionStatus.PENDING_SUBMIT_ONLINE_MERCHANDISE) {
                        /**
                         * If the contractTransactionStatus is different to PENDING_SUBMIT_ONLINE_MERCHANDISE means
                         * that the merchandise submit through the Crypto* Wallet was done.
                         * We don't want to send multiple payments, we will ignore this transaction.
                         */
                        continue;
                    }

                    ArrayList<IntraWalletUserIdentity> intraUsers = intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser();
                    IntraWalletUserIdentity intraUser = intraUsers.get(0);

                    System.out.println("*************************************************************************************************");
                    System.out.println("BROKER_SUBMIT_ONLINE_MERCHANDISE - SENDING CRYPTO TRANSFER USING INTRA_ACTOR_TRANSACTION_MANAGER");
                    System.out.println("*************************************************************************************************");
                    UUID outgoingCryptoTransactionId = intraActorCryptoTransactionManager.sendCrypto(
                            businessTransactionRecord.getExternalWalletPublicKey(),
                            businessTransactionRecord.getCryptoAddress(),
                            businessTransactionRecord.getCryptoAmount(),
                            "Merchandise sent from a Broker",
                            intraUser.getPublicKey(),
                            businessTransactionRecord.getActorPublicKey(),
                            Actors.CBP_CRYPTO_BROKER,
                            Actors.INTRA_USER,
                            getReferenceWallet(businessTransactionRecord.getCryptoCurrency()),
                            businessTransactionRecord.getBlockchainNetworkType(), //TODO de Manuel: crear un setting para configuar esto
                            businessTransactionRecord.getCryptoCurrency(),
                            businessTransactionRecord.getFee(),
                            businessTransactionRecord.getFeeOrigin());

                    //Updating the business transaction record
                    businessTransactionRecord.setTransactionId(outgoingCryptoTransactionId.toString());
                    businessTransactionRecord.setContractTransactionStatus(CRYPTO_MERCHANDISE_SUBMITTED);
                    dao.updateBusinessTransactionRecord(businessTransactionRecord);
                }

                /**
                 * Check contract status to send - Broker side.
                 */
                List<BusinessTransactionRecord> pendingToSubmitNotificationList = dao.getPendingToSubmitNotificationList();
                for (BusinessTransactionRecord pendingToSubmitNotificationRecord : pendingToSubmitNotificationList) {
                    contractHash = pendingToSubmitNotificationRecord.getContractHash();
                    transactionTransmissionManager.sendContractStatusNotification(
                            pendingToSubmitNotificationRecord.getBrokerPublicKey(),
                            pendingToSubmitNotificationRecord.getCustomerPublicKey(),
                            contractHash,
                            pendingToSubmitNotificationRecord.getTransactionId(),
                            ONLINE_MERCHANDISE_SUBMITTED,
                            Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);

                    //Updating the business transaction record
                    pendingToSubmitNotificationRecord.setContractTransactionStatus(ONLINE_MERCHANDISE_SUBMITTED);
                    dao.updateBusinessTransactionRecord(pendingToSubmitNotificationRecord);
                }

                /**
                 * Check pending notifications - Customer side
                 */
                List<BusinessTransactionRecord> pendingToSubmitConfirmationList = dao.getPendingToSubmitConfirmationList();
                for (BusinessTransactionRecord pendingToSubmitConfirmationRecord : pendingToSubmitConfirmationList) {
                    contractHash = pendingToSubmitConfirmationRecord.getContractHash();

                    transactionTransmissionManager.confirmNotificationReception(
                            pendingToSubmitConfirmationRecord.getCustomerPublicKey(),
                            pendingToSubmitConfirmationRecord.getBrokerPublicKey(),
                            contractHash,
                            pendingToSubmitConfirmationRecord.getTransactionId(),
                            Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER);

                    //Updating the business transaction record
                    pendingToSubmitConfirmationRecord.setContractTransactionStatus(CONFIRM_ONLINE_CONSIGNMENT);
                    dao.updateBusinessTransactionRecord(pendingToSubmitConfirmationRecord);

                    raiseIncomingMoneyNotificationEvent(pendingToSubmitConfirmationRecord);
                }

                // from here on, all this code checks the crypto status of a transaction and updates that crypto status into the contract.

                /**
                 * Check pending Crypto transactions
                 */
                List<BusinessTransactionRecord> pendingTransactions = dao.getPendingCryptoTransactionList();
                for (BusinessTransactionRecord onlinePaymentRecord : pendingTransactions) {
                    checkPendingTransaction(onlinePaymentRecord);
                }

                /**
                 * Check if PENDING_SUBMIT crypto status
                 */
                List<BusinessTransactionRecord> pendingSubmitContractList = dao.getPendingToSubmitCryptoStatusList();
                for (BusinessTransactionRecord pendingSubmitContractRecord : pendingSubmitContractList) {
                    CryptoStatus cryptoStatus = outgoingIntraActorManager.getTransactionStatus(pendingSubmitContractRecord.getTransactionHash());
                    pendingSubmitContractRecord.setCryptoStatus(cryptoStatus);
                    dao.updateBusinessTransactionRecord(pendingSubmitContractRecord);
                }

                /**
                 * Check if ON_CRYPTO_NETWORK crypto status
                 */
                List<BusinessTransactionRecord> pendingOnCryptoNetworkContractList = dao.getOnCryptoNetworkCryptoStatusList();
                for (BusinessTransactionRecord onCryptoNetworkContractRecord : pendingOnCryptoNetworkContractList) {
                    CryptoStatus cryptoStatus = outgoingIntraActorManager.getTransactionStatus(onCryptoNetworkContractRecord.getTransactionHash());
                    onCryptoNetworkContractRecord.setCryptoStatus(cryptoStatus);
                    dao.updateBusinessTransactionRecord(onCryptoNetworkContractRecord);
                }

                /**
                 * Check if ON_BLOCKCHAIN crypto status (last crypto status)
                 */
                List<BusinessTransactionRecord> pendingOnBlockchainContractList = dao.getOnBlockchainCryptoStatusList();
                for (BusinessTransactionRecord onBlockchainContractRecord : pendingOnBlockchainContractList) {
                    onBlockchainContractRecord.setCryptoStatus(CryptoStatus.IRREVERSIBLE);
                    onBlockchainContractRecord.setContractTransactionStatus(PENDING_SUBMIT_ONLINE_MERCHANDISE_NOTIFICATION);
                    dao.updateBusinessTransactionRecord(onBlockchainContractRecord);
                }

                //END of the checking process for the crypto status

                /**
                 * Check if pending events
                 */
                List<String> pendingEventsIdList = dao.getPendingEvents();
                for (String eventId : pendingEventsIdList) {
                    checkPendingEvent(eventId);
                }

            } catch (CantListIntraWalletUsersException e) {
                throw new CannotSendContractHashException(e, "Sending contract hash", "cannot get list of intra users");

            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                throw new CannotSendContractHashException(e, "Sending contract hash", "Unexpected result in database");

            } catch (CantSendContractNewStatusNotificationException e) {
                throw new CantSendContractNewStatusNotificationException(CantSendContractNewStatusNotificationException.DEFAULT_MESSAGE,
                        e, "Sending contract hash", "Error in Transaction Transmission Network Service");

            } catch (OutgoingIntraActorInsufficientFundsException | OutgoingIntraActorCantSendFundsExceptions e) {
                pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

            } catch (OutgoingIntraActorCantGetCryptoStatusException e) {
                throw new CantUpdateRecordException(OutgoingIntraActorCantGetCryptoStatusException.DEFAULT_MESSAGE,
                        e, "Getting the crypto status", "Cannot get the crypto status");

            } catch (CantGetContractListException e) {
                throw new CantUpdateRecordException(OutgoingIntraActorCantGetCryptoStatusException.DEFAULT_MESSAGE,
                        e, "Getting the Contract", "Cannot get the contract list");

            } catch (CantConfirmNotificationReceptionException e) {
                throw new CantConfirmNotificationReceptionException(CantSendContractNewStatusNotificationException.DEFAULT_MESSAGE,
                        e, "Sending Confirmation Notification to the Broker", "Error Sending Notification Confirmation Message");
            }
        }

        /**
         * Rise an event indicating the submitted online merchandise has been confirmed
         */
        private void raiseMerchandiseSubmittedConfirmationEvent(String contractHash) {
            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.BROKER_SUBMIT_MERCHANDISE_CONFIRMED);
            BrokerSubmitMerchandiseConfirmed brokerSubmitMerchandiseConfirmed = (BrokerSubmitMerchandiseConfirmed) fermatEvent;
            brokerSubmitMerchandiseConfirmed.setSource(EventSource.BROKER_SUBMIT_ONLINE_MERCHANDISE);
            brokerSubmitMerchandiseConfirmed.setContractHash(contractHash);
            brokerSubmitMerchandiseConfirmed.setMerchandiseType(MoneyType.CRYPTO);
            eventManager.raiseEvent(brokerSubmitMerchandiseConfirmed);
        }

        /**
         * Rise an event indicating that the incoming money is in the crypto wallet.
         * Call this method when the crypto status is ON_BLOCKCHAIN and beyond
         *
         * @param record the Business Transaction record associated with the crypto transaction
         */
        private void raiseIncomingMoneyNotificationEvent(BusinessTransactionRecord record) {
            System.out.println(new StringBuilder().append("SUBMIT_ONLINE_MERCHANDISE - raiseIncomingMoneyNotificationEvent - record.getCryptoCurrency() = ").append(record.getCryptoCurrency()).toString());
            System.out.println(new StringBuilder().append("SUBMIT_ONLINE_MERCHANDISE - raiseIncomingMoneyNotificationEvent - record.getCryptoAmount() = ").append(record.getCryptoAmount()).toString());

            FermatEvent fermatEvent = eventManager.getNewEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType.INCOMING_MONEY_NOTIFICATION);
            IncomingMoneyNotificationEvent event = (IncomingMoneyNotificationEvent) fermatEvent;

            event.setCryptoCurrency(record.getCryptoCurrency());
            event.setAmount(record.getCryptoAmount());
            event.setIntraUserIdentityPublicKey(record.getBrokerPublicKey());
            event.setWalletPublicKey(record.getCBPWalletPublicKey());
            event.setActorId(record.getCustomerPublicKey());
            event.setSource(EventSource.BROKER_SUBMIT_ONLINE_MERCHANDISE);
            event.setActorType(Actors.CBP_CRYPTO_CUSTOMER);
            event.setTransactionHash(record.getContractHash());

            eventManager.raiseEvent(event);
        }

        /**
         * check the status of the crypto transaction and update the Business Transaction accordingly
         *
         * @param businessTransactionRecord the business transaction record associated with the crypto transaction
         * @throws CantUpdateRecordException
         */
        private void checkPendingTransaction(BusinessTransactionRecord businessTransactionRecord) throws CantUpdateRecordException {
            UUID transactionUUID = UUID.fromString(businessTransactionRecord.getTransactionId());

            //Get transaction hash from IntraActorCryptoTransactionManager
            try {
                String transactionHash = intraActorCryptoTransactionManager.getSendCryptoTransactionHash(transactionUUID);
                if (transactionHash == null)
                    return; //If transactionHash is null the crypto amount is not sent yet, I'll check for this later.

                CryptoStatus cryptoStatus = outgoingIntraActorManager.getTransactionStatus(transactionHash);
                businessTransactionRecord.setTransactionHash(transactionHash);
                businessTransactionRecord.setCryptoStatus(cryptoStatus);
                dao.updateBusinessTransactionRecord(businessTransactionRecord);

            } catch (OutgoingIntraActorCantGetSendCryptoTransactionHashException | OutgoingIntraActorCantGetCryptoStatusException e) {
                //I want to know a better way to handle with this exception, for now I going to print the exception and return this method.
                pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                throw new CantUpdateRecordException(UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE, e,
                        "Checking the crypto status", "I get an unexpected results in database");
            }
        }

        /**
         * Check the pending Business Transaction events and act accordingly
         *
         * @param eventId the event ID
         * @throws UnexpectedResultReturnedFromDatabaseException
         */
        private void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {

            try {
                String eventTypeCode = dao.getEventType(eventId);
                String contractHash;
                BusinessTransactionMetadata businessTransactionMetadata;
                ContractTransactionStatus contractTransactionStatus;
                BusinessTransactionRecord businessTransactionRecord;

                //This will happen in customer side
                if (eventTypeCode.equals(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE.getCode())) {

                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                        businessTransactionMetadata = record.getInformation();
                        contractHash = businessTransactionMetadata.getContractHash();

                        if (businessTransactionMetadata.getRemoteBusinessTransaction() == Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE) {
                            if (!dao.isContractHashInDatabase(contractHash)) {
                                CustomerBrokerContractPurchase purchaseContract = customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(contractHash);
                                ObjectChecker.checkArgument(purchaseContract); //If the contract is null, I cannot handle with this situation

                                CustomerBrokerPurchaseNegotiation purchaseNegotiation = purchaseNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(purchaseContract.getNegotiatiotId()));
                                String merchandiseCurrencyCode = NegotiationClauseHelper.getNegotiationClauseValue(purchaseNegotiation.getClauses(), ClauseType.CUSTOMER_CURRENCY);

                                String amountStr = NegotiationClauseHelper.getNegotiationClauseValue(purchaseNegotiation.getClauses(), ClauseType.CUSTOMER_CURRENCY_QUANTITY);
                                long cryptoAmount = getCryptoAmount(amountStr, merchandiseCurrencyCode);

                                if (purchaseContract.getStatus() != COMPLETED) {
                                    dao.persistContractInDatabase(purchaseContract, merchandiseCurrencyCode, cryptoAmount);
                                    dao.setCompletionDateByContractHash(contractHash, (new Date()).getTime());
                                    customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(contractHash, MERCHANDISE_SUBMIT);

                                    raiseMerchandiseSubmittedConfirmationEvent(contractHash);
                                }
                            }
                            transactionTransmissionManager.confirmReception(record.getTransactionID());
                        }
                    }
                    dao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                }

                //This will happen in broker side
                if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {

                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                        businessTransactionMetadata = record.getInformation();
                        contractHash = businessTransactionMetadata.getContractHash();

                        if (businessTransactionMetadata.getRemoteBusinessTransaction() == Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE) {
                            if (dao.isContractHashInDatabase(contractHash)) {
                                businessTransactionRecord = dao.getBrokerBusinessTransactionRecord(contractHash);
                                contractTransactionStatus = businessTransactionRecord.getContractTransactionStatus();

                                if (contractTransactionStatus == ONLINE_MERCHANDISE_SUBMITTED) {
                                    CustomerBrokerContractSale contractSale = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
                                    ObjectChecker.checkArgument(contractSale);

                                    if (contractSale.getStatus() != COMPLETED) {
                                        businessTransactionRecord.setContractTransactionStatus(CONFIRM_ONLINE_CONSIGNMENT);
                                        dao.updateBusinessTransactionRecord(businessTransactionRecord);
                                        dao.setCompletionDateByContractHash(contractHash, (new Date()).getTime());
                                        customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(contractHash, MERCHANDISE_SUBMIT);

                                        raiseMerchandiseSubmittedConfirmationEvent(contractHash);
                                    }
                                }
                            }
                            transactionTransmissionManager.confirmReception(record.getTransactionID());
                        }
                    }
                    dao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                }

            } catch (CantGetListCustomerBrokerContractSaleException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot get sale contract");
            } catch (CantUpdateRecordException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot update the database");
            } catch (CantConfirmTransactionException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot confirm the transaction");
            } catch (CantUpdateCustomerBrokerContractSaleException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot update the contract sale status");
            } catch (CantDeliverPendingTransactionsException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot get the pending transactions from transaction transmission plugin");
            } catch (CantInsertRecordException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot insert a record in database");
            } catch (CantGetListCustomerBrokerContractPurchaseException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot get the purchase contract");
            } catch (CantUpdateCustomerBrokerContractPurchaseException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot update the contract purchase status");
            } catch (ObjectNotSetException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "The customerBrokerContractPurchase is null");
            } catch (CantGetListClauseException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cant get the purchase negotiation reference");
            } catch (CantGetListPurchaseNegotiationsException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cant get the negotiation clauses");
            }
        }

        /**
         * Return a Satoshi representation of the given String amount for the given currency
         *
         * @param cryptoAmountString the crypto amount in String
         * @param currencyCode       the crypto currency code
         * @return the crypto amount in satoshi
         */
        private long getCryptoAmount(String cryptoAmountString, String currencyCode) {
            try {
                Number number = DecimalFormat.getInstance().parse(cryptoAmountString);

                if (CryptoCurrency.BITCOIN.getCode().equals(currencyCode))
                    return (long) BitcoinConverter.convert(number.doubleValue(), BitcoinConverter.Currency.BITCOIN, BitcoinConverter.Currency.SATOSHI);
                if (CryptoCurrency.FERMAT.getCode().equals(currencyCode))
                    return (long) BitcoinConverter.convert(number.doubleValue(), BitcoinConverter.Currency.FERMAT, BitcoinConverter.Currency.SATOSHI);

            } catch (ParseException ignore) {
            }

            return 0;
        }

        /**
         * Return the reference wallet associated with the crypto currency
         *
         * @param cryptoCurrency the crypto currency
         * @return the reference wallet or null
         */
        private ReferenceWallet getReferenceWallet(CryptoCurrency cryptoCurrency) {
            if (cryptoCurrency == CryptoCurrency.BITCOIN)
                return ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET;
            if (cryptoCurrency == CryptoCurrency.FERMAT)
                return ReferenceWallet.BASIC_WALLET_FERMAT_WALLET;

            return null;
        }
    }
}

