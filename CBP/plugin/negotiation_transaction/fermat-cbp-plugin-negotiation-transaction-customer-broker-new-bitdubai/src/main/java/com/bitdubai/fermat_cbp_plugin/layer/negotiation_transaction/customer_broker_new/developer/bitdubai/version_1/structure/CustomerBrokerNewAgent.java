package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Owner;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.agent.CBPTransactionAgent;
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationPurchaseRecord;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationSaleRecord;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.NegotiationTransactionCustomerBrokerNewPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantGetNegotiationTransactionListException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantNewSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantSendCustomerBrokerNewConfirmationNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantSendCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_ACTIVITY_TO_OPEN_CODE;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_NOTIFICATION_PAINTER_FROM;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_TO_OPEN_PUBLIC_KEY;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.NOTIFICATION_ID;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.SOURCE_PLUGIN;


/**
 * Created by Yordin Alayn on 08.12.15.
 * Based On OpenContractMonitorAgentTransaction Create by Manuel Perez.
 */
public class CustomerBrokerNewAgent implements
        CBPTransactionAgent,
        DealsWithLogger,
        DealsWithEvents,
        DealsWithPluginDatabaseSystem,
        DealsWithPluginIdentity {

    private Database database;

    private Thread agentThread;

    private LogManager logManager;

    private EventManager eventManager;

    private NegotiationTransactionCustomerBrokerNewPluginRoot pluginRoot;

    private PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    /*Represent the Network Service*/
    private NegotiationTransmissionManager negotiationTransmissionManager;

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation;

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    /*Represent the Monitor Agent*/
    private MonitorAgentTransaction monitorAgentTransaction;

    /**
     * Let me send a fire a broadcast to show a notification or update a view
     */
    private Broadcaster broadcaster;

    /*Represent Plugin Version*/
    private PluginVersionReference pluginVersionReference;


    public CustomerBrokerNewAgent(
            PluginDatabaseSystem pluginDatabaseSystem,
            LogManager logManager,
            NegotiationTransactionCustomerBrokerNewPluginRoot pluginRoot,
            EventManager eventManager,
            UUID pluginId,
            NegotiationTransmissionManager negotiationTransmissionManager,
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation,
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation,
            CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
            Broadcaster broadcaster
    ) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.logManager = logManager;
        this.pluginRoot = pluginRoot;
        this.eventManager = eventManager;
        this.pluginId = pluginId;
        this.negotiationTransmissionManager = negotiationTransmissionManager;
        this.customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiation;
        this.customerBrokerSaleNegotiation = customerBrokerSaleNegotiation;
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
        this.broadcaster = broadcaster;
    }

    /*IMPLEMENTATION CBPTransactionAgent*/
    @Override
    public void start() throws CantStartAgentException {

//        Logger LOG = Logger.getGlobal();
//        LOG.info("CUSTMER BROKER NEW AGENT STARTING...");
        monitorAgentTransaction = new MonitorAgentTransaction();

        this.monitorAgentTransaction.setPluginDatabaseSystem(this.pluginDatabaseSystem);

        try {
//            this.monitorAgentTransaction.Initialize();
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }

        this.agentThread = new Thread(monitorAgentTransaction);
        this.agentThread.start();
//        System.out.print("-----------------------\n CUSTOMER BROKER NEW AGENT: SUCCESSFUL START \n-----------------------\n");

    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    /*IMPLEMENTATION DealsWithEvents*/
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /*IMPLEMENTATION DealsWithLogger*/
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /*IMPLEMENTATION DealsWithPluginDatabaseSystem*/
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*IMPLEMENTATION DealsWithPluginIdentity*/
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /*INNER CLASSES*/
    private class MonitorAgentTransaction implements DealsWithPluginDatabaseSystem, Runnable {

        private CustomerBrokerNewSaleNegotiationTransaction customerBrokerNewSaleNegotiationTransaction;

        private volatile boolean agentRunning;

        PluginDatabaseSystem pluginDatabaseSystem;

        CustomerBrokerNewNegotiationTransactionDatabaseDao customerBrokerNewNegotiationTransactionDatabaseDao;

        boolean threadWorking;

        public final int SLEEP_TIME = 5000;

        int iterationNumber = 0;

        int iterationConfirmSend = 0;

        Map<UUID, Integer> transactionSend = new HashMap<>();

        //public MonitorAgentTransaction() { startAgent(); }

        /*IMPLEMENTATION DealsWithPluginIdentity*/
        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        /*IMPLEMENTATION Runnable*/
        @Override
        public void run() {

            threadWorking = true;
            logManager.log(NegotiationTransactionCustomerBrokerNewPluginRoot.getLogLevelByClass(this.getClass().getName()), "Customer Broker New Monitor Agent: running...", null, null);

            while (threadWorking) {
                //Increase the iteration counter
                iterationNumber++;
                iterationConfirmSend++;
                try {

                    Thread.sleep(SLEEP_TIME);

                } catch (InterruptedException interruptedException) {
                    return;
                }

                //now I will check if there are pending transactions to raise the event
                try {

                    logManager.log(NegotiationTransactionCustomerBrokerNewPluginRoot.getLogLevelByClass(this.getClass().getName()), new StringBuilder().append("Iteration number ").append(iterationNumber).toString(), null, null);
                    doTheMainTask();

                } catch (CantSendCustomerBrokerNewNegotiationTransactionException | CantSendCustomerBrokerNewConfirmationNegotiationTransactionException | CantUpdateRecordException e) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }

        }

        /*INNER CLASS PUBLIC METHOD*/
        /*public void Initialize() throws CantInitializeCBPAgent {
//            try {
//
//                customerBrokerNewNegotiationTransactionDatabaseDao.initialize();
//
//            } catch (CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException exception) {
//                errorManager.reportUnexpectedPluginException(pluginVersionReference,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,exception);
//                throw new CantInitializeCBPAgent(exception,"Customer Broker New Initialize Monitor Agent - trying to open the plugin database","Please, check the cause");
//            }

            try {

//                database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
                database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerNewNegotiationTransactionDatabaseConstants.DATABASE_NAME);

            }
            catch (DatabaseNotFoundException databaseNotFoundException) {

                try {
                    CustomerBrokerNewNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerNewNegotiationTransactionDatabaseFactory(this.pluginDatabaseSystem);
//                    database = databaseFactory.createDatabase(pluginId,pluginId.toString());
                    database = databaseFactory.createDatabase(pluginId,CustomerBrokerNewNegotiationTransactionDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException,"Customer Broker New Initialize Monitor Agent - trying to create the plugin database","Please, check the cause");
                }

            } catch (CantOpenDatabaseException exception) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantInitializeCBPAgent(exception,"Customer Broker New Initialize Monitor Agent - trying to open the plugin database","Please, check the cause");
            }


        }*/

        public void stopAgent() {
            agentRunning = false;
        }

        public void startAgent() {
            agentRunning = true;
        }

        public boolean isAgentRunning() {
            return agentRunning;
        }
        /*END INNER CLASS PUBLIC METHOD*/

        /*INNER CLASS PRIVATE METHOD*/
        private void doTheMainTask() throws
                CantSendCustomerBrokerNewNegotiationTransactionException,
                CantSendCustomerBrokerNewConfirmationNegotiationTransactionException,
                CantUpdateRecordException {
            try {

//                customerBrokerNewNegotiationTransactionDatabaseDao = new CustomerBrokerNewNegotiationTransactionDatabaseDao(pluginDatabaseSystem, pluginId);
//                customerBrokerNewNegotiationTransactionDatabaseDao.initialize();
                customerBrokerNewNegotiationTransactionDatabaseDao = new CustomerBrokerNewNegotiationTransactionDatabaseDao(pluginDatabaseSystem, pluginId, database);

                String negotiationXML;
                NegotiationType negotiationType;
                UUID transactionId;
                List<CustomerBrokerNew> negotiationPendingToSubmitList;
                CustomerBrokerPurchaseNegotiation purchaseNegotiation = new NegotiationPurchaseRecord();
                CustomerBrokerSaleNegotiation saleNegotiation = new NegotiationSaleRecord();
                int timeConfirmSend = 20;

                //SEND NEGOTIATION PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_SUBMIT)
                negotiationPendingToSubmitList = customerBrokerNewNegotiationTransactionDatabaseDao.getPendingToSubmitNegotiation();
                if (!negotiationPendingToSubmitList.isEmpty()) {
                    for (CustomerBrokerNew negotiationTransaction : negotiationPendingToSubmitList) {

                        System.out.print("\n\n**** 5) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - PURCHASE NEGOTIATION SEND ****\n");
                        negotiationXML = negotiationTransaction.getNegotiationXML();
                        negotiationType = negotiationTransaction.getNegotiationType();
                        transactionId = negotiationTransaction.getTransactionId();

                        switch (negotiationType) {
                            case PURCHASE:
                                purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                                System.out.print(new StringBuilder().append("\n\n**** 6) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - PURCHASE NEGOTIATION SEND negotiationId(XML): ").append(purchaseNegotiation.getNegotiationId()).append(" ****\n").toString());
                                //SEND NEGOTIATION TO BROKER
                                negotiationTransmissionManager.sendNegotiationToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_NEW);
                                break;
                        }

                        //Update the Negotiation Transaction
                        customerBrokerNewNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(
                                transactionId,
                                NegotiationTransactionStatus.SENDING_NEGOTIATION);

                    }
                }

                //SEND CONFIRM PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_CONFIRMATION)
                negotiationPendingToSubmitList = customerBrokerNewNegotiationTransactionDatabaseDao.getPendingToConfirmtNegotiation();
                if (!negotiationPendingToSubmitList.isEmpty()) {
                    for (CustomerBrokerNew negotiationTransaction : negotiationPendingToSubmitList) {

                        negotiationXML = negotiationTransaction.getNegotiationXML();
                        negotiationType = negotiationTransaction.getNegotiationType();
                        transactionId = negotiationTransaction.getTransactionId();

                        System.out.print(new StringBuilder().append("\n\n**** 22) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - CONFIRMATION FOR SEND transactionId: ").append(transactionId).append(" ****\n").toString());

                        switch (negotiationType) {
                            case SALE:
                                saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);
                                System.out.print(new StringBuilder().append("\n\n**** 23) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - CONFIRMATION SEND negotiationId(XML): ").append(transactionId).append(" ****\n").toString());
                                negotiationTransmissionManager.sendConfirmNegotiationToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_NEW);
                                break;
                        }

                        //UPDATE STATUS NEGOTIATION TRANSACTION
                        customerBrokerNewNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(
                                transactionId,
                                NegotiationTransactionStatus.CONFIRM_NEGOTIATION);

                        //CONFIRM TRANSACTION IS DONE
                        customerBrokerNewNegotiationTransactionDatabaseDao.confirmTransaction(transactionId);
                    }

                }

                //PROCES PENDING EVENT
                List<UUID> pendingEventsIdList = customerBrokerNewNegotiationTransactionDatabaseDao.getPendingEvents();
                for (UUID eventId : pendingEventsIdList) {
                    checkPendingEvent(eventId);
                }

                //SEND TRNSACTION AGAIN IF NOT IS CONFIRM
                if (timeConfirmSend == iterationConfirmSend) {

                    CustomerBrokerNewForwardTransaction forwardTransaction = new CustomerBrokerNewForwardTransaction(
                            customerBrokerNewNegotiationTransactionDatabaseDao,
                            pluginRoot,
                            transactionSend
                    );

                    forwardTransaction.pendingToConfirmtTransaction();
                    transactionSend = forwardTransaction.getTransactionSend();

                    iterationConfirmSend = 0;
                }

            } catch (CantGetNegotiationTransactionListException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerNewNegotiationTransactionException(CantSendCustomerBrokerNewNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Negotiation", "Cannot get the Negotiation list from database");
            } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantUpdateRecordException(CantUpdateRecordException.DEFAULT_MESSAGE, e, "Sending Negotiation", "Cannot Update State the Negotiation from database");
            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantUpdateRecordException(CantUpdateRecordException.DEFAULT_MESSAGE, e, "Sending Negotiation", "Unexpected result in database");
            } catch (CantSendNegotiationToCryptoBrokerException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerNewNegotiationTransactionException(CantSendCustomerBrokerNewNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Sale Negotiation", "Error in Negotiation Transmission Network Service");
            } catch (CantSendConfirmToCryptoCustomerException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerNewConfirmationNegotiationTransactionException(CantSendCustomerBrokerNewConfirmationNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Confirm Purchase Negotiation", "Error in Negotiation Transmission Network Service");
            } catch (Exception e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Sending Negotiation", "UNKNOWN FAILURE.");
            }
        }

        //CHECK PENDING EVEN
        private void checkPendingEvent(UUID eventId) throws UnexpectedResultReturnedFromDatabaseException {

            try {

                UUID transactionId;
                UUID transmissionId;
                NegotiationTransmission negotiationTransmission;
                NegotiationTransaction negotiationTransaction;
                NegotiationType negotiationType;
                String negotiationXML;
                CustomerBrokerPurchaseNegotiation purchaseNegotiation = new NegotiationPurchaseRecord();
                CustomerBrokerSaleNegotiation saleNegotiation = new NegotiationSaleRecord();

                String eventTypeCode = customerBrokerNewNegotiationTransactionDatabaseDao.getEventType(eventId);
                String eventStatus = customerBrokerNewNegotiationTransactionDatabaseDao.getEventStatus(eventId);

                //EVENT - RECEIVE NEGOTIATION
                if (eventTypeCode.equals(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_NEW.getCode())) {
                    List<Transaction<NegotiationTransmission>> pendingTransactionList = negotiationTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<NegotiationTransmission> record : pendingTransactionList) {

                        negotiationTransmission = record.getInformation();

                        if (negotiationTransmission.getNegotiationTransactionType().getCode().equals(NegotiationTransactionType.CUSTOMER_BROKER_NEW.getCode())) {

                            negotiationXML = negotiationTransmission.getNegotiationXML();
                            transmissionId = negotiationTransmission.getTransmissionId();
                            transactionId = negotiationTransmission.getTransactionId();
                            negotiationType = negotiationTransmission.getNegotiationType();

                            if (negotiationXML != null) {

                                negotiationTransaction = customerBrokerNewNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerNewNegotiationTranasction(transactionId);

                                if (negotiationTransmission.getTransmissionType().equals(NegotiationTransmissionType.TRANSMISSION_NEGOTIATION)) {

                                    switch (negotiationType) {
                                        case SALE:

                                            //CREATE SALE NEGOTIATION
                                            System.out.print("\n**** 21) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - CREATE SALE NEGOTIATION TRANSACTION  ****\n");
                                            saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);

                                            if (negotiationTransaction == null) {

                                                System.out.print("\n**** 21.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - CREATE SALE NEGOTIATION TRANSACTION NEW ****\n");
                                                customerBrokerNewSaleNegotiationTransaction = new CustomerBrokerNewSaleNegotiationTransaction(
                                                        customerBrokerSaleNegotiationManager,
                                                        customerBrokerNewNegotiationTransactionDatabaseDao,
                                                        pluginRoot
                                                );
                                                customerBrokerNewSaleNegotiationTransaction.receiveSaleNegotiationTranasction(transactionId, saleNegotiation);

                                                //BROADCASTER FOR UPDATE LAYER ANDROID
                                                FermatBundle fermatBundle = new FermatBundle();
                                                fermatBundle.put(SOURCE_PLUGIN, Plugins.CUSTOMER_BROKER_NEW.getCode());
                                                fermatBundle.put(APP_NOTIFICATION_PAINTER_FROM, new Owner(WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode()));
                                                fermatBundle.put(APP_TO_OPEN_PUBLIC_KEY, WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode());
                                                fermatBundle.put(NOTIFICATION_ID, CBPBroadcasterConstants.CBW_NEW_NEGOTIATION_NOTIFICATION);
                                                fermatBundle.put(APP_ACTIVITY_TO_OPEN_CODE, Activities.CBP_CRYPTO_BROKER_WALLET_HOME.getCode());
                                                broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, fermatBundle);

                                                fermatBundle = new FermatBundle();
                                                fermatBundle.put(Broadcaster.PUBLISH_ID, WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode());
                                                fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CBW_NEGOTIATION_UPDATE_VIEW);
                                                broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle);

                                            } else {

                                                System.out.print("\n**** 21.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - CREATE SALE NEGOTIATION TRANSACTION REPEAT SEND ****\n");
                                                //CONFIRM TRANSACTION
                                                customerBrokerNewNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(
                                                        transactionId,
                                                        NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM);

                                            }
                                            break;
                                    }

                                } else if (negotiationTransmission.getTransmissionType().equals(NegotiationTransmissionType.TRANSMISSION_CONFIRM)) {

                                    System.out.print("\n**** 25.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - NEW NEGOTIATION TRANSACTION CONFIRM ****\n");
                                    switch (negotiationType) {
                                        case PURCHASE:

                                            if (!negotiationTransaction.getStatusTransaction().getCode().equals(NegotiationTransactionStatus.CONFIRM_NEGOTIATION.getCode())) {

                                                //CREATE CONFIRM NEGOTIATION
                                                System.out.print("\n**** 25.2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - NEW PURCHASE NEGOTIATION TRANSACTION CONFIRM ****\n");
                                                purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                                                customerBrokerPurchaseNegotiationManager.waitForBroker(purchaseNegotiation);

                                                //CONFIRM TRANSACTION
                                                customerBrokerNewNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(
                                                        transactionId,
                                                        NegotiationTransactionStatus.CONFIRM_NEGOTIATION);
                                            }

                                            //CONFIRM TRANSACTION IS DONE
                                            customerBrokerNewNegotiationTransactionDatabaseDao.confirmTransaction(transactionId);

                                            break;
                                    }

                                }

                                //NOTIFIED EVENT
                                customerBrokerNewNegotiationTransactionDatabaseDao.updateEventTansactionStatus(eventId, EventStatus.NOTIFIED);
                                //CONFIRM TRANSMISSION
                                negotiationTransmissionManager.confirmReception(transmissionId);

                            }
                        }
                    }
                }

            } catch (CantDeliverPendingTransactionsException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                e.printStackTrace();
            } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                e.printStackTrace();
            } catch (CantNewSaleNegotiationTransactionException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                e.printStackTrace();
            } catch (CantConfirmTransactionException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                e.printStackTrace();
            } catch (CantUpdateRecordException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                e.printStackTrace();
            } catch (Exception e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                e.printStackTrace();
            }
        }
        /*END INNER CLASS PRIVATE METHOD*/

    }
    /*END INNER CLASS*/

}
