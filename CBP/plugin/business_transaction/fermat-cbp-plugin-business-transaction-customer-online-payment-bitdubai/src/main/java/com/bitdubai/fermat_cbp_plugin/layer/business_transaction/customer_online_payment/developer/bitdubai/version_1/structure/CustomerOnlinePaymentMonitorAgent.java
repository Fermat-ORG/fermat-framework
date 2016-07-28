package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.structure;

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
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.util.NegotiationClauseHelper;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CannotSendContractHashException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.events.CustomerOnlinePaymentConfirmed;
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
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.CustomerOnlinePaymentPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetCryptoStatusException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetSendCryptoTransactionHashException;
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
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus.PAYMENT_SUBMIT;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.CONFIRM_ONLINE_PAYMENT;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.CRYPTO_PAYMENT_SUBMITTED;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.ONLINE_PAYMENT_SUBMITTED;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_ONLINE_PAYMENT_CONFIRMATION;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_ONLINE_PAYMENT_NOTIFICATION;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class CustomerOnlinePaymentMonitorAgent implements
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
    CustomerOnlinePaymentPluginRoot pluginRoot;
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;
    TransactionTransmissionManager transactionTransmissionManager;
    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    CustomerBrokerSaleNegotiationManager saleNegotiationManager;
    IntraActorCryptoTransactionManager intraActorCryptoTransactionManager;
    OutgoingIntraActorManager outgoingIntraActorManager;
    IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    public CustomerOnlinePaymentMonitorAgent(
            PluginDatabaseSystem pluginDatabaseSystem,
            LogManager logManager,
            CustomerOnlinePaymentPluginRoot pluginRoot,
            EventManager eventManager,
            UUID pluginId,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            OutgoingIntraActorManager outgoingIntraActorManager,
            IntraWalletUserIdentityManager intraWalletUserIdentityManager,
            CustomerBrokerSaleNegotiationManager saleNegotiationManager) throws CantSetObjectException {

        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginRoot = pluginRoot;
        this.pluginId = pluginId;
        this.logManager = logManager;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.outgoingIntraActorManager = outgoingIntraActorManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.intraWalletUserIdentityManager = intraWalletUserIdentityManager;
        this.saleNegotiationManager = saleNegotiationManager;

        setIntraActorCryptoTransactionManager(outgoingIntraActorManager);
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
        monitorAgent = new MonitorAgent(pluginRoot);

        this.monitorAgent.setPluginDatabaseSystem(this.pluginDatabaseSystem);

        try {
            this.monitorAgent.Initialize();
        } catch (CantInitializeCBPAgent exception) {
            pluginRoot.reportError(
                    DISABLES_THIS_PLUGIN, exception);
        } catch (Exception exception) {
            this.pluginRoot.reportError(
                    DISABLES_THIS_PLUGIN, FermatException.wrapException(exception));
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

        CustomerOnlinePaymentPluginRoot pluginRoot;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = 5000;
        int iterationNumber = 0;
        CustomerOnlinePaymentBusinessTransactionDao dao;
        boolean threadWorking;

        public MonitorAgent(CustomerOnlinePaymentPluginRoot pluginRoot) {
            this.pluginRoot = pluginRoot;
        }


        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            try {
                this.pluginDatabaseSystem = pluginDatabaseSystem;
            } catch (Exception exception) {
                this.pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            }
        }

        @Override
        public void run() {

            threadWorking = true;
            logManager.log(CustomerOnlinePaymentPluginRoot.getLogLevelByClass(this.getClass().getName()),
                    "Customer Online Payment Monitor Agent: running...", null, null);
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

                    logManager.log(CustomerOnlinePaymentPluginRoot.getLogLevelByClass(this.getClass().getName()), new StringBuilder().append("Iteration number ").append(iterationNumber).toString(), null, null);
                    doTheMainTask();
                } catch (CannotSendContractHashException | CantUpdateRecordException | CantSendContractNewStatusNotificationException e) {
                    pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }

            }

        }

        public void Initialize() throws CantInitializeCBPAgent {
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId,
                        CustomerOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME);
            } catch (DatabaseNotFoundException databaseNotFoundException) {

                //Logger LOG = Logger.getGlobal();
                //LOG.info("Database in Open Contract monitor agent doesn't exists");
                CustomerOnlinePaymentBusinessTransactionDatabaseFactory CustomerOnlinePaymentBusinessTransactionDatabaseFactory =
                        new CustomerOnlinePaymentBusinessTransactionDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = CustomerOnlinePaymentBusinessTransactionDatabaseFactory.createDatabase(pluginId,
                            CustomerOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    pluginRoot.reportError(DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
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

        private void doTheMainTask() throws CannotSendContractHashException, CantUpdateRecordException, CantSendContractNewStatusNotificationException {

            try {
                dao = new CustomerOnlinePaymentBusinessTransactionDao(pluginDatabaseSystem, pluginId, database, pluginRoot);
                String contractHash;
                ContractTransactionStatus contractTransactionStatus;

                /**
                 * Check if there is some crypto to send - Customer Side
                 */
                List<String> pendingToSubmitCrypto = dao.getPendingToSubmitCryptoList();
                for (String pendingContractHash : pendingToSubmitCrypto) {
                    BusinessTransactionRecord businessTransactionRecord = dao.getCustomerOnlinePaymentRecord(pendingContractHash);

                    //I'll check if the payment was sent in a previous loop
                    contractTransactionStatus = businessTransactionRecord
                            .getContractTransactionStatus();
                    if (contractTransactionStatus != ContractTransactionStatus.PENDING_PAYMENT) {
                        /**
                         * If the contractTransactionStatus is different to PENDING_PAYMENT means
                         * that tha payment through the Crypto* Wallet was done.
                         * We don't want to send multiple payments, we will ignore this transaction.
                         */
                        continue;
                    }

                    ArrayList<IntraWalletUserIdentity> intraUsers = intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser();
                    IntraWalletUserIdentity intraUser = intraUsers.get(0);

                    System.out.println("***************************************************************************************");
                    System.out.println("CUSTOMER_ONLINE_PAYMENT - SENDING CRYPTO TRANSFER USING INTRA_ACTOR_TRANSACTION_MANAGER");
                    System.out.println("***************************************************************************************");

                    UUID outgoingCryptoTransactionId = intraActorCryptoTransactionManager.sendCrypto(
                            businessTransactionRecord.getExternalWalletPublicKey(),
                            businessTransactionRecord.getCryptoAddress(),
                            businessTransactionRecord.getCryptoAmount(),
                            "Payment sent from a Customer",
                            intraUser.getPublicKey(),
                            businessTransactionRecord.getActorPublicKey(),
                            Actors.CBP_CRYPTO_CUSTOMER,
                            Actors.INTRA_USER,
                            getReferenceWallet(businessTransactionRecord.getCryptoCurrency()),
                            businessTransactionRecord.getBlockchainNetworkType(), //TODO de Manuel: crear un setting para configuar esto
                            businessTransactionRecord.getCryptoCurrency(),
                            businessTransactionRecord.getFee(),
                            businessTransactionRecord.getFeeOrigin());

                    dao.persistsCryptoTransactionUUID(pendingContractHash, outgoingCryptoTransactionId);
                    dao.updateContractTransactionStatus(pendingContractHash, ONLINE_PAYMENT_SUBMITTED);
                }

                /**
                 * Check contract status to send - Customer Side
                 */
                List<BusinessTransactionRecord> pendingToSubmitNotificationList = dao.getPendingToSubmitNotificationList();
                for (BusinessTransactionRecord pendingToSubmitNotificationRecord : pendingToSubmitNotificationList) {
                    contractHash = pendingToSubmitNotificationRecord.getTransactionHash();

                    transactionTransmissionManager.sendContractStatusNotification(
                            pendingToSubmitNotificationRecord.getCustomerPublicKey(),
                            pendingToSubmitNotificationRecord.getBrokerPublicKey(),
                            contractHash,
                            pendingToSubmitNotificationRecord.getTransactionId(),
                            CRYPTO_PAYMENT_SUBMITTED,
                            Plugins.CUSTOMER_ONLINE_PAYMENT,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER);

                    dao.updateContractTransactionStatus(contractHash, CRYPTO_PAYMENT_SUBMITTED);
                }

                /**
                 * Check pending notifications - Broker side
                 */
                List<BusinessTransactionRecord> pendingToSubmitConfirmationList = dao.getPendingToSubmitConfirmList();
                for (BusinessTransactionRecord pendingToSubmitConfirmationRecord : pendingToSubmitConfirmationList) {
                    contractHash = pendingToSubmitConfirmationRecord.getTransactionHash();

                    transactionTransmissionManager.confirmNotificationReception(
                            pendingToSubmitConfirmationRecord.getBrokerPublicKey(),
                            pendingToSubmitConfirmationRecord.getCustomerPublicKey(),
                            contractHash,
                            pendingToSubmitConfirmationRecord.getTransactionId(),
                            Plugins.CUSTOMER_ONLINE_PAYMENT,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);

                    dao.updateContractTransactionStatus(contractHash, CONFIRM_ONLINE_PAYMENT);

                    raiseIncomingMoneyNotificationEvent(pendingToSubmitConfirmationRecord);
                }


                // from here on, all this code checks the crypto status of a transaction and updates that crypto status into the contract.
                /**
                 * Check pending transactions
                 */
                List<BusinessTransactionRecord> pendingTransactions = dao.getPendingCryptoTransactionList();
                for (BusinessTransactionRecord onlinePaymentRecord : pendingTransactions) {
                    checkPendingTransaction(onlinePaymentRecord);
                }

                /**
                 * Check if pending to submit crypto status
                 */
                List<BusinessTransactionRecord> pendingSubmitContractCryptoStatusList = dao.getPendingToSubmitCryptoStatusList();
                for (BusinessTransactionRecord pendingSubmitContractRecord : pendingSubmitContractCryptoStatusList) {
                    final String transactionId = pendingSubmitContractRecord.getTransactionId();
                    final String cryptoTransactionHash = intraActorCryptoTransactionManager.getSendCryptoTransactionHash(UUID.fromString(transactionId));

                    final CryptoStatus cryptoStatus = outgoingIntraActorManager.getTransactionStatus(cryptoTransactionHash);
                    pendingSubmitContractRecord.setCryptoStatus(cryptoStatus);
                    dao.updateBusinessTransactionRecord(pendingSubmitContractRecord);
                }

                /**
                 * Check if on crypto network crypto status
                 */
                List<BusinessTransactionRecord> pendingOnCryptoNetworkContractList = dao.getOnCryptoNetworkCryptoStatusList();
                for (BusinessTransactionRecord onCryptoNetworkContractRecord : pendingOnCryptoNetworkContractList) {
                    final String transactionId = onCryptoNetworkContractRecord.getTransactionId();
                    final String cryptoTransactionHash = intraActorCryptoTransactionManager.getSendCryptoTransactionHash(UUID.fromString(transactionId));

                    final CryptoStatus cryptoStatus = outgoingIntraActorManager.getTransactionStatus(cryptoTransactionHash);
                    onCryptoNetworkContractRecord.setCryptoStatus(cryptoStatus);
                    dao.updateBusinessTransactionRecord(onCryptoNetworkContractRecord);
                }

                /**
                 * Check if on blockchain crypto status
                 */
                List<BusinessTransactionRecord> pendingOnBlockchainContractList = dao.getOnBlockchainCryptoStatusList();
                for (BusinessTransactionRecord onBlockchainContractRecord : pendingOnBlockchainContractList) {
                    onBlockchainContractRecord.setCryptoStatus(CryptoStatus.IRREVERSIBLE);
                    onBlockchainContractRecord.setContractTransactionStatus(PENDING_ONLINE_PAYMENT_NOTIFICATION);
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
                throw new CannotSendContractHashException(e, "Sending Crypto Payment", "Cannot get list of intra users");
            } catch (CantGetContractListException e) {
                throw new CannotSendContractHashException(e, "Sending Crypto Payment", "Cannot get the contract list from database");
            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                throw new CannotSendContractHashException(e, "Sending Crypto Payment", "Unexpected result in database");
            } catch (Exception e) {
                pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }

        /**
         * Rise an event indicating that the submitted online payment has been confirmed
         */
        private void raisePaymentConfirmationEvent() {
            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.CUSTOMER_ONLINE_PAYMENT_CONFIRMED);
            CustomerOnlinePaymentConfirmed customerOnlinePaymentConfirmed = (CustomerOnlinePaymentConfirmed) fermatEvent;
            customerOnlinePaymentConfirmed.setSource(EventSource.CUSTOMER_ONLINE_PAYMENT);
            eventManager.raiseEvent(customerOnlinePaymentConfirmed);
        }

        /**
         * Rise an event indicating that the incoming money is in the crypto wallet.
         * Call this method when the crypto status is ON_BLOCKCHAIN and beyond
         *
         * @param record the Business Transaction record associated with the crypto transaction
         */
        private void raiseIncomingMoneyNotificationEvent(BusinessTransactionRecord record) {
            System.out.println(new StringBuilder().append("SUBMIT_ONLINE_PAYMENT - raiseIncomingMoneyNotificationEvent - record.getCryptoCurrency() = ").append(record.getCryptoCurrency()).toString());
            System.out.println(new StringBuilder().append("SUBMIT_ONLINE_PAYMENT - raiseIncomingMoneyNotificationEvent - record.getCryptoAmount() = ").append(record.getCryptoAmount()).toString());

            FermatEvent fermatEvent = eventManager.getNewEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType.INCOMING_MONEY_NOTIFICATION);
            IncomingMoneyNotificationEvent event = (IncomingMoneyNotificationEvent) fermatEvent;

            event.setCryptoCurrency(record.getCryptoCurrency());
            event.setAmount(record.getCryptoAmount());
            event.setIntraUserIdentityPublicKey(record.getBrokerPublicKey());
            event.setWalletPublicKey(record.getCBPWalletPublicKey());
            event.setActorId(record.getCustomerPublicKey());
            event.setSource(EventSource.CUSTOMER_ONLINE_PAYMENT);
            event.setActorType(Actors.CBP_CRYPTO_BROKER);
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
                if (transactionHash == null) {
                    return; //If transactionHash is null the crypto amount is not sent yet, I'll check for this later.
                }
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

                //This will happen in broker side
                if (eventTypeCode.equals(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE.getCode())) {

                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                        businessTransactionMetadata = record.getInformation();
                        contractHash = businessTransactionMetadata.getContractHash();

                        if (businessTransactionMetadata.getRemoteBusinessTransaction() == Plugins.CUSTOMER_ONLINE_PAYMENT) {
                            if (!dao.isContractHashInDatabase(contractHash)) {
                                CustomerBrokerContractSale saleContract = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
                                ObjectChecker.checkArgument(saleContract); //If the contract is null, I cannot handle with this situation

                                CustomerBrokerSaleNegotiation saleNegotiation = saleNegotiationManager.getNegotiationsByNegotiationId(UUID.fromString(saleContract.getNegotiatiotId()));
                                String paymentCurrencyCode = NegotiationClauseHelper.getNegotiationClauseValue(saleNegotiation.getClauses(), ClauseType.BROKER_CURRENCY);

                                String amountStr = NegotiationClauseHelper.getNegotiationClauseValue(saleNegotiation.getClauses(), ClauseType.BROKER_CURRENCY_QUANTITY);
                                long cryptoAmount = getCryptoAmount(amountStr, paymentCurrencyCode);

                                if (saleContract.getStatus() != COMPLETED) {
                                    dao.persistContractInDatabase(saleContract, paymentCurrencyCode, cryptoAmount);
                                    dao.setCompletionDateByContractHash(contractHash, (new Date()).getTime());
                                    dao.updateContractTransactionStatus(contractHash, PENDING_ONLINE_PAYMENT_CONFIRMATION);
                                    customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(contractHash, PAYMENT_SUBMIT);

                                    raisePaymentConfirmationEvent();
                                }
                            }
                            transactionTransmissionManager.confirmReception(record.getTransactionID());
                        }
                    }
                    dao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                }

                //This will happen in customer side
                if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {

                    List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                        businessTransactionMetadata = record.getInformation();
                        contractHash = businessTransactionMetadata.getContractHash();

                        if (businessTransactionMetadata.getRemoteBusinessTransaction() == Plugins.CUSTOMER_ONLINE_PAYMENT) {
                            if (dao.isContractHashInDatabase(contractHash)) {
                                businessTransactionRecord = dao.getCustomerOnlinePaymentRecord(contractHash);
                                contractTransactionStatus = businessTransactionRecord.getContractTransactionStatus();

                                if (contractTransactionStatus == CRYPTO_PAYMENT_SUBMITTED) {
                                    CustomerBrokerContractPurchase contractPurchase = customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(contractHash);
                                    ObjectChecker.checkArgument(contractPurchase);

                                    if (contractPurchase.getStatus() != COMPLETED) {
                                        dao.setCompletionDateByContractHash(contractHash, (new Date()).getTime());
                                        dao.updateContractTransactionStatus(contractHash, CONFIRM_ONLINE_PAYMENT);
                                        customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(contractHash, PAYMENT_SUBMIT);

                                        raisePaymentConfirmationEvent();
                                    }
                                }
                            }
                            transactionTransmissionManager.confirmReception(record.getTransactionID());
                        }
                    }
                    dao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                }

            } catch (CantGetListCustomerBrokerContractPurchaseException | CantUpdateRecordException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot update the database");
            } catch (CantConfirmTransactionException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot confirm the transaction");
            } catch (CantUpdateCustomerBrokerContractSaleException | CantGetListCustomerBrokerContractSaleException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot update the contract sale status");
            } catch (CantDeliverPendingTransactionsException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot get the pending transactions from transaction transmission plugin");
            } catch (CantInsertRecordException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot insert a record in database");
            } catch (CantUpdateCustomerBrokerContractPurchaseException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot update the contract purchase status");
            } catch (ObjectNotSetException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "The customerBrokerContractSale is null");
            } catch (CantGetListSaleNegotiationsException exception) {
                throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cant get the sale negotiation reference");
            } catch (CantGetListClauseException exception) {
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

