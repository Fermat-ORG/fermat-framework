package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
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
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantGetIntraUserIdentityException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/12/15.
 */
public class BrokerSubmitOnlineMerchandiseMonitorAgent implements
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
    IntraActorCryptoTransactionManager intraActorCryptoTransactionManager;
    OutgoingIntraActorManager outgoingIntraActorManager;
    CryptoMoneyDestockManager cryptoMoneyDeStockManager;
    IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    public BrokerSubmitOnlineMerchandiseMonitorAgent(
            PluginDatabaseSystem pluginDatabaseSystem,
            LogManager logManager,
            ErrorManager errorManager,
            EventManager eventManager,
            UUID pluginId,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            OutgoingIntraActorManager outgoingIntraActorManager,
            CryptoMoneyDestockManager cryptoMoneyDeStockManager,IntraWalletUserIdentityManager intraWalletUserIdentityManager) throws CantSetObjectException {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.logManager = logManager;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.outgoingIntraActorManager = outgoingIntraActorManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.cryptoMoneyDeStockManager = cryptoMoneyDeStockManager;
        setIntraActorCryptoTransactionManager(outgoingIntraActorManager);
        this.intraWalletUserIdentityManager=intraWalletUserIdentityManager;
    }

    private void setIntraActorCryptoTransactionManager(
            OutgoingIntraActorManager outgoingIntraActorManager) throws CantSetObjectException {
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
        monitorAgent = new MonitorAgent();

        this.monitorAgent.setPluginDatabaseSystem(this.pluginDatabaseSystem);
        this.monitorAgent.setErrorManager(this.errorManager);

        try {
            this.monitorAgent.Initialize();
        } catch (CantInitializeCBPAgent exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
        } catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
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
                    Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
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
        BrokerSubmitOnlineMerchandiseBusinessTransactionDao brokerSubmitOnlineMerchandiseBusinessTransactionDao;
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

                    logManager.log(BrokerSubmitOnlineMerchandisePluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (Exception e) {
                    errorManager.reportUnexpectedPluginException(
                            Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                            UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
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
                    errorManager.reportUnexpectedPluginException(
                            Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                            UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                            cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException,
                            "Initialize Monitor Agent - trying to create the plugin database",
                            "Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                errorManager.reportUnexpectedPluginException(
                        Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        exception);
                throw new CantInitializeCBPAgent(exception,
                        "Initialize Monitor Agent - trying to open the plugin database",
                        "Please, check the cause");
            }
        }

        private void doTheMainTask() throws CannotSendContractHashException, CantUpdateRecordException, CantSendContractNewStatusNotificationException, CantCreateCryptoMoneyDestockException, CantConfirmNotificationReceptionException {

            try {
                brokerSubmitOnlineMerchandiseBusinessTransactionDao = new BrokerSubmitOnlineMerchandiseBusinessTransactionDao(pluginDatabaseSystem, pluginId, database, errorManager);

                UUID outgoingCryptoTransactionId;
                BusinessTransactionRecord businessTransactionRecord;
                String contractHash;
                CryptoStatus cryptoStatus;

                /**
                 * Check if there is some transaction to crypto De-stock
                 * The de-stock condition is reading the ContractTransactionStatus in PENDING_ONLINE_DE_STOCK
                 */
                List<BusinessTransactionRecord> pendingToDeStockTransactionList = brokerSubmitOnlineMerchandiseBusinessTransactionDao.getPendingDeStockTransactionList();
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
                            cryptoMoneyDeStockRecord.getBlockchainNetworkType()); //TODO: Manuel debemos de ver como nos llega esto desde android

                    pendingToDeStockTransaction.setContractTransactionStatus(ContractTransactionStatus.PENDING_SUBMIT_ONLINE_MERCHANDISE);
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(pendingToDeStockTransaction);
                }

                /**
                 * Check if there is some crypto to send
                 */
                List<String> pendingToSubmitCrypto = brokerSubmitOnlineMerchandiseBusinessTransactionDao.getPendingToSubmitCryptoList();
                for (String pendingContractHash : pendingToSubmitCrypto) {
                    businessTransactionRecord = brokerSubmitOnlineMerchandiseBusinessTransactionDao.getBrokerBusinessTransactionRecord(pendingContractHash);

                    System.out.println("*************************************************************************************************");
                    System.out.println("BROKER_SUBMIT_ONLINE_MERCHANDISE - SENDING CRYPTO TRANSFER USING INTRA_ACTOR_TRANSACTION_MANAGER");
                    System.out.println("*************************************************************************************************");
                    outgoingCryptoTransactionId = intraActorCryptoTransactionManager.sendCrypto(
                            businessTransactionRecord.getExternalWalletPublicKey(),
                            businessTransactionRecord.getCryptoAddress(),
                            businessTransactionRecord.getCryptoAmount(),
                            "Payment from Crypto Customer contract " + pendingContractHash,
                            intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser().get(0).getPublicKey(),
                            businessTransactionRecord.getActorPublicKey(),
                            Actors.CBP_CRYPTO_BROKER,
                            Actors.INTRA_USER,
                            ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET,
                            businessTransactionRecord.getBlockchainNetworkType()
                            );

                    //Updating the business transaction record
                    businessTransactionRecord.setTransactionId(outgoingCryptoTransactionId.toString());
                    businessTransactionRecord.setContractTransactionStatus(ContractTransactionStatus.CRYPTO_MERCHANDISE_SUBMITTED);
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(businessTransactionRecord);
                }

                /**
                 * Check contract status to send - Broker side.
                 */
                List<BusinessTransactionRecord> pendingToSubmitNotificationList = brokerSubmitOnlineMerchandiseBusinessTransactionDao.getPendingToSubmitNotificationList();
                for (BusinessTransactionRecord pendingToSubmitNotificationRecord : pendingToSubmitNotificationList) {
                    contractHash = pendingToSubmitNotificationRecord.getContractHash();
                    transactionTransmissionManager.sendContractStatusNotification(
                            pendingToSubmitNotificationRecord.getBrokerPublicKey(),
                            pendingToSubmitNotificationRecord.getCustomerPublicKey(),
                            contractHash,
                            pendingToSubmitNotificationRecord.getTransactionId(),
                            ContractTransactionStatus.ONLINE_MERCHANDISE_SUBMITTED,
                            Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);

                    //Updating the business transaction record
                    pendingToSubmitNotificationRecord.setContractTransactionStatus(ContractTransactionStatus.ONLINE_MERCHANDISE_SUBMITTED);
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(pendingToSubmitNotificationRecord);
                }

                /**
                 * Check pending notifications - Customer side
                 */
                List<BusinessTransactionRecord> pendingToSubmitConfirmationList = brokerSubmitOnlineMerchandiseBusinessTransactionDao.getPendingToSubmitConfirmationList();
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
                    pendingToSubmitConfirmationRecord.setContractTransactionStatus(ContractTransactionStatus.CONFIRM_ONLINE_CONSIGNMENT);
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(pendingToSubmitConfirmationRecord);
                    raiseIncomingMoneyNotificationEvent(pendingToSubmitConfirmationRecord);
                }

                // from here on, all this code checks the crypto status of a transaction
                // and updates that crypto status into the contract.

                /**
                 * Check pending Crypto transactions
                 */
                List<BusinessTransactionRecord> pendingTransactions = brokerSubmitOnlineMerchandiseBusinessTransactionDao.getPendingCryptoTransactionList();
                for (BusinessTransactionRecord onlinePaymentRecord : pendingTransactions) {
                    checkPendingTransaction(onlinePaymentRecord);
                }

                /**
                 * Check if PENDING_SUBMIT crypto status
                 */
                List<BusinessTransactionRecord> pendingSubmitContractList = brokerSubmitOnlineMerchandiseBusinessTransactionDao.getPendingToSubmitCryptoStatusList();
                for (BusinessTransactionRecord pendingSubmitContractRecord : pendingSubmitContractList) {
                    cryptoStatus = outgoingIntraActorManager.getTransactionStatus(pendingSubmitContractRecord.getTransactionHash());
                    pendingSubmitContractRecord.setCryptoStatus(cryptoStatus);
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(pendingSubmitContractRecord);
                }

                /**
                 * Check if ON_CRYPTO_NETWORK crypto status
                 */
                List<BusinessTransactionRecord> pendingOnCryptoNetworkContractList = brokerSubmitOnlineMerchandiseBusinessTransactionDao.getOnCryptoNetworkCryptoStatusList();
                for (BusinessTransactionRecord onCryptoNetworkContractRecord : pendingOnCryptoNetworkContractList) {
                    cryptoStatus = outgoingIntraActorManager.getTransactionStatus(onCryptoNetworkContractRecord.getTransactionHash());
                    onCryptoNetworkContractRecord.setCryptoStatus(cryptoStatus);
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(onCryptoNetworkContractRecord);
                }

                /**
                 * Check if ON_BLOCKCHAIN crypto status (last crypto status)
                 */
                List<BusinessTransactionRecord> pendingOnBlockchainContractList = brokerSubmitOnlineMerchandiseBusinessTransactionDao.getOnBlockchainkCryptoStatusList();
                for (BusinessTransactionRecord onBlockchainContractRecord : pendingOnBlockchainContractList) {
                    cryptoStatus = outgoingIntraActorManager.getTransactionStatus(onBlockchainContractRecord.getTransactionHash());
                    onBlockchainContractRecord.setCryptoStatus(CryptoStatus.IRREVERSIBLE);
                    onBlockchainContractRecord.setContractTransactionStatus(ContractTransactionStatus.PENDING_SUBMIT_ONLINE_MERCHANDISE_NOTIFICATION);
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(onBlockchainContractRecord);
                }

                //END of the checking process for the crypto status

                /**
                 * Check if pending events
                 */
                List<String> pendingEventsIdList = brokerSubmitOnlineMerchandiseBusinessTransactionDao.getPendingEvents();
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
                //TODO: I want to get a better handler for this exception
                errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

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

        private void raisePaymentConfirmationEvent(String contractHash) {
            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.BROKER_SUBMIT_MERCHANDISE_CONFIRMED);
            BrokerSubmitMerchandiseConfirmed brokerSubmitMerchandiseConfirmed = (BrokerSubmitMerchandiseConfirmed) fermatEvent;
            brokerSubmitMerchandiseConfirmed.setSource(EventSource.BROKER_SUBMIT_ONLINE_MERCHANDISE);
            brokerSubmitMerchandiseConfirmed.setContractHash(contractHash);
            brokerSubmitMerchandiseConfirmed.setMerchandiseType(MoneyType.CRYPTO);
            eventManager.raiseEvent(brokerSubmitMerchandiseConfirmed);
        }
        
        private void raiseIncomingMoneyNotificationEvent(BusinessTransactionRecord record){
            FermatEvent fermatEvent = eventManager.getNewEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType.INCOMING_MONEY_NOTIFICATION);
            com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent event = (com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent) fermatEvent;
            //TODO: quitar el hardcodeo del cryptocurrency para poder negociar con otras monedas.
            event.setCryptoCurrency(CryptoCurrency.BITCOIN);
            event.setAmount(record.getCryptoAmount());
            event.setIntraUserIdentityPublicKey(record.getBrokerPublicKey());
            event.setWalletPublicKey(record.getCBPWalletPublicKey());
            event.setActorId(record.getCustomerPublicKey());
            event.setSource(EventSource.BROKER_SUBMIT_ONLINE_MERCHANDISE);
            event.setActorType(Actors.CBP_CRYPTO_CUSTOMER);
            eventManager.raiseEvent(event);
        }

        //TODO: raise an event only in broker side, notifying the incoming online payment. Create the event.
        //TODO: raise an event only in customer side, notifying the reception of an online payment. Create the event.
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
                brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(businessTransactionRecord);

            } catch (OutgoingIntraActorCantGetSendCryptoTransactionHashException | OutgoingIntraActorCantGetCryptoStatusException e) {
                //I want to know a better way to handle with this exception, for now I going to print the exception and return this method.
                errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                throw new CantUpdateRecordException(UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE, e,
                        "Checking the crypto status", "I get an unexpected results in database");
            }
        }

        private void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {

            try {
                String eventTypeCode = brokerSubmitOnlineMerchandiseBusinessTransactionDao.getEventType(eventId);
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
                        if(businessTransactionMetadata.getRemoteBusinessTransaction()==Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE){
                            if (brokerSubmitOnlineMerchandiseBusinessTransactionDao.isContractHashInDatabase(contractHash)) {
                                contractTransactionStatus = brokerSubmitOnlineMerchandiseBusinessTransactionDao.getContractTransactionStatus(contractHash);
                                //TODO: analyze what we need to do here.

                            } else {
                                CustomerBrokerContractPurchase contractPurchase = customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(contractHash);
                                ObjectChecker.checkArgument(contractPurchase); //If the contract is null, I cannot handle with this situation
                                if(!contractPurchase.getStatus().getCode().equals(ContractStatus.COMPLETED)){
                                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.persistContractInDatabase(contractPurchase);
                                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.setCompletionDateByContractHash(contractHash, (new Date()).getTime());
                                    customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(contractHash, ContractStatus.MERCHANDISE_SUBMIT);
                                    raisePaymentConfirmationEvent(contractHash);
                                }
                            }
                            transactionTransmissionManager.confirmReception(record.getTransactionID());
                        }
                    }
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                }

                //This will happen in broker side
                if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {

                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                        businessTransactionMetadata = record.getInformation();
                        contractHash = businessTransactionMetadata.getContractHash();
                        if(businessTransactionMetadata.getRemoteBusinessTransaction()==Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE){
                            if (brokerSubmitOnlineMerchandiseBusinessTransactionDao.isContractHashInDatabase(contractHash)) {
                                businessTransactionRecord = brokerSubmitOnlineMerchandiseBusinessTransactionDao.getBrokerBusinessTransactionRecord(contractHash);
                                contractTransactionStatus = businessTransactionRecord.getContractTransactionStatus();

                                if (contractTransactionStatus.getCode().equals(ContractTransactionStatus.ONLINE_MERCHANDISE_SUBMITTED.getCode())) {
                                    CustomerBrokerContractSale contractSale= customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
                                    ObjectChecker.checkArgument(contractSale);
                                    if(!contractSale.getStatus().getCode().equals(ContractStatus.COMPLETED)){
                                        businessTransactionRecord.setContractTransactionStatus(ContractTransactionStatus.CONFIRM_ONLINE_CONSIGNMENT);
                                        brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateBusinessTransactionRecord(businessTransactionRecord);
                                        brokerSubmitOnlineMerchandiseBusinessTransactionDao.setCompletionDateByContractHash(contractHash, (new Date()).getTime());
                                        customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(contractHash, ContractStatus.MERCHANDISE_SUBMIT);
                                        raisePaymentConfirmationEvent(contractHash);
                                    }
                                }
                            }
                            transactionTransmissionManager.confirmReception(record.getTransactionID());
                        }
                    }
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                }

                //TODO: look a better way to deal with this exceptions
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
            }
        }
    }
}

