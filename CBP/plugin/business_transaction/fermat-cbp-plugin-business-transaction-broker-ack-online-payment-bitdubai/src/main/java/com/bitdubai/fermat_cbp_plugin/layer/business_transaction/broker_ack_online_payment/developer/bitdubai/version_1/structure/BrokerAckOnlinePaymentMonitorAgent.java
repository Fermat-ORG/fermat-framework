package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
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
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
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
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantConfirmNotificationReceptionException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.BrokerAckOnlinePaymentPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.exceptions.IncomingOnlinePaymentException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/12/15.
 */
public class BrokerAckOnlinePaymentMonitorAgent implements
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
    BrokerAckOnlinePaymentPluginRoot pluginRoot;
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;
    TransactionTransmissionManager transactionTransmissionManager;
    CustomerBrokerContractPurchaseManager contractPurchaseManager;
    CustomerBrokerContractSaleManager contractSaleManager;
    CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    public BrokerAckOnlinePaymentMonitorAgent(
            PluginDatabaseSystem pluginDatabaseSystem,
            LogManager logManager,
            BrokerAckOnlinePaymentPluginRoot pluginRoot,
            EventManager eventManager,
            UUID pluginId,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager contractPurchaseManager,
            CustomerBrokerContractSaleManager contractSaleManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager) {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginRoot = pluginRoot;
        this.pluginId = pluginId;
        this.logManager = logManager;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.contractPurchaseManager = contractPurchaseManager;
        this.contractSaleManager = contractSaleManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
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
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        } catch (Exception exception) {
            this.pluginRoot.reportError(
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
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
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

        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = 5000;
        int iterationNumber = 0;
        BrokerAckOnlinePaymentBusinessTransactionDao dao;
        boolean threadWorking;
        private BrokerAckOnlinePaymentPluginRoot pluginRoot;

        public MonitorAgent(BrokerAckOnlinePaymentPluginRoot pluginRoot) {
            this.pluginRoot = pluginRoot;
        }

        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        @Override
        public void run() {

            threadWorking = true;
            logManager.log(BrokerAckOnlinePaymentPluginRoot.getLogLevelByClass(this.getClass().getName()),
                    "Broker Ack Online Payment Monitor Agent: running...", null, null);
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

                    logManager.log(BrokerAckOnlinePaymentPluginRoot.getLogLevelByClass(this.getClass().getName()), new StringBuilder().append("Iteration number ").append(iterationNumber).toString(), null, null);
                    doTheMainTask();
                } catch (CannotSendContractHashException | CantUpdateRecordException | CantSendContractNewStatusNotificationException e) {
                    pluginRoot.reportError(
                            UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                            e);
                }

            }

        }

        public void Initialize() throws CantInitializeCBPAgent {
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId,
                        BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME);
            } catch (DatabaseNotFoundException databaseNotFoundException) {

                //Logger LOG = Logger.getGlobal();
                //LOG.info("Database in Open Contract monitor agent doesn't exists");
                BrokerAckOnlinePaymentBusinessTransactionDatabaseFactory brokerAckOnlinePaymentBusinessTransactionDatabaseFactory =
                        new BrokerAckOnlinePaymentBusinessTransactionDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = brokerAckOnlinePaymentBusinessTransactionDatabaseFactory.createDatabase(pluginId,
                            BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    pluginRoot.reportError(
                            UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                            cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException,
                            "Initialize Monitor Agent - trying to create the plugin database",
                            "Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                pluginRoot.reportError(
                        UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                        exception);
                throw new CantInitializeCBPAgent(exception,
                        "Initialize Monitor Agent - trying to open the plugin database",
                        "Please, check the cause");
            }
        }

        private void doTheMainTask() throws CannotSendContractHashException, CantUpdateRecordException, CantSendContractNewStatusNotificationException {

            try {
                dao = new BrokerAckOnlinePaymentBusinessTransactionDao(pluginDatabaseSystem, pluginId, database, pluginRoot);

                String contractHash;
                /**
                 * Check if pending incoming money events
                 */
                List<String> pendingMoneyEventIdList = dao.getPendingIncomingMoneyEvents();
                for (String eventId : pendingMoneyEventIdList) {
                    checkPendingIncomingMoneyEvents(eventId);
                }

                /**
                 * Check contract status to send. - Broker Side
                 * The status to verify is PENDING_ACK_ONLINE_PAYMENT_NOTIFICATION, it represents that the payment is
                 * acknowledge by the broker.
                 */
                List<BusinessTransactionRecord> pendingToSubmitNotificationList = dao.getPendingToSubmitNotificationList();
                for (BusinessTransactionRecord pendingToSubmitNotificationRecord : pendingToSubmitNotificationList) {
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

                    dao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.ONLINE_PAYMENT_ACK);
                    System.out.println("ACK_ONLINE_PAYMENT [Broker] - ContractTransactionStatus updated to ONLINE_PAYMENT_ACK");
                }

                /**
                 * Check pending notifications - Customer side
                 */
                List<BusinessTransactionRecord> pendingToSubmitConfirmationList = dao.getPendingToSubmitConfirmList();
                for (BusinessTransactionRecord pendingToSubmitConfirmationRecord : pendingToSubmitConfirmationList) {
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
                }

                /**
                 * Check if pending events
                 */
                List<String> pendingEventsIdList = dao.getPendingEvents();
                for (String eventId : pendingEventsIdList) {
                    checkPendingEvent(eventId);
                }

            } catch (CantGetContractListException e) {
                throw new CannotSendContractHashException(e, "Sending Ack Online Payment", "Cannot get the contract list from database");
            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                throw new CannotSendContractHashException(e, "Sending Ack Online Payment", "Unexpected result in database");
            } catch (CantConfirmNotificationReceptionException e) {
                throw new CannotSendContractHashException(e, "Sending Ack Online Payment", "Cant send Confirmation Notification Reception message");
            } catch (IncomingOnlinePaymentException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }

        private void raiseAckConfirmationEvent(String contractHash) {
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
                    throw new IncomingOnlinePaymentException(new StringBuilder().append("The incoming crypto amount received is ").append(incomingCryptoAmount).append("\nThe amount excepted in contract ").append(contractHash).append("\nis ").append(contractCryptoAmount).toString());
                }

                //TODO probar esto
                CryptoCurrency incomingCryptoCurrency = incomingMoneyEventWrapper.getCryptoCurrency();
                CryptoCurrency contractCryptoCurrency = businessTransactionRecord.getCryptoCurrency();
                if (incomingCryptoCurrency != contractCryptoCurrency) {
                    throw new IncomingOnlinePaymentException(new StringBuilder()
                            .append("The incoming crypto currency received is ").append(incomingCryptoCurrency)
                            .append("\nThe crypto currency excepted in contract: ").append(contractHash)
                            .append("\nis ").append(contractCryptoCurrency).toString());
//                    throw new IncomingOnlinePaymentException(String.format("The incoming crypto currency received is %s\nThe crypto currency excepted in contract %s\nis %s", incomingCryptoCurrency, contractHash, contractCryptoCurrency));
                }

                String receiverActorPublicKey = incomingMoneyEventWrapper.getReceiverPublicKey();
                String expectedActorPublicKey = businessTransactionRecord.getCustomerPublicKey();
                if (!receiverActorPublicKey.equals(expectedActorPublicKey)) {
                    throw new IncomingOnlinePaymentException(new StringBuilder().append("The actor public key that receive the money is ").append(receiverActorPublicKey).append("\nThe broker public key in contract ").append(contractHash).append("\nis ").append(expectedActorPublicKey).toString());
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

        private void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {
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
                            System.out.println(new StringBuilder().append("BROKER_ACK_ONLINE_PAYMENT [Broker] - NEW_CONTRACT_OPENED - persisted sale contract. cryptoAmount = ").append(cryptoAmount).append(" - paymentCurrency = ").append(paymentCurrency).toString());
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
    }
}