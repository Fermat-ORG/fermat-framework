package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Owner;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
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
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationPurchaseRecord;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationSaleRecord;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdate;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.NegotiationTransactionCustomerBrokerUpdatePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantGetNegotiationTransactionListException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantSendCustomerBrokerUpdateConfirmationNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantSendCustomerBrokerUpdateNegotiationTransactionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType.UPDATE_VIEW;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_ACTIVITY_TO_OPEN_CODE;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_NOTIFICATION_PAINTER_FROM;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_TO_OPEN_PUBLIC_KEY;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.NOTIFICATION_ID;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.SOURCE_PLUGIN;


/**
 * Created by Yordin Alayn on 16.12.15.
 */
public class CustomerBrokerUpdateAgent implements
        CBPTransactionAgent,
        DealsWithLogger,
        DealsWithEvents,
        DealsWithPluginDatabaseSystem,
        DealsWithPluginIdentity {

    private Database database;

    private Thread agentThread;

    private LogManager logManager;

    private EventManager eventManager;

    private NegotiationTransactionCustomerBrokerUpdatePluginRoot pluginRoot;

    private PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    /*Represent the Network Service*/
    private NegotiationTransmissionManager negotiationTransmissionManager;

    /*Represent the Negotiation Purchase*/

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    /**
     * Let me send a fire a broadcast to show a notification or update a view
     */
    private Broadcaster broadcaster;

    public CustomerBrokerUpdateAgent(
            PluginDatabaseSystem pluginDatabaseSystem,
            LogManager logManager,
            NegotiationTransactionCustomerBrokerUpdatePluginRoot pluginRoot,
            UUID pluginId,
            NegotiationTransmissionManager negotiationTransmissionManager,
            CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
            Broadcaster broadcaster

    ) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.logManager = logManager;
        this.pluginRoot = pluginRoot;
        this.pluginId = pluginId;
        this.negotiationTransmissionManager = negotiationTransmissionManager;
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
        this.broadcaster = broadcaster;
    }

    /*IMPLEMENTATION CBPTransactionAgent*/
    @Override
    public void start() throws CantStartAgentException {

//        Logger LOG = Logger.getGlobal();
//        LOG.info("CUSTMER BROKER NEW AGENT STARTING...");
        MonitorAgentTransaction monitorAgentTransaction = new MonitorAgentTransaction();

        monitorAgentTransaction.setPluginDatabaseSystem(this.pluginDatabaseSystem);

        try {
            monitorAgentTransaction.Initialize();
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }

        this.agentThread = new Thread(monitorAgentTransaction);
        this.agentThread.start();
//        System.out.print("-----------------------\n CUSTOMER BROKER UPDATE AGENT: SUCCESSFUL START \n-----------------------\n");
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

    /*INNER CLASS*/
    private class MonitorAgentTransaction implements DealsWithPluginDatabaseSystem, Runnable {

        private CustomerBrokerUpdateSaleNegotiationTransaction customerBrokerUpdateSaleNegotiationTransaction;

        private CustomerBrokerUpdatePurchaseNegotiationTransaction customerBrokerUpdatePurchaseNegotiationTransaction;

        PluginDatabaseSystem pluginDatabaseSystem;

        CustomerBrokerUpdateNegotiationTransactionDatabaseDao customerBrokerUpdateNegotiationTransactionDatabaseDao;

        boolean threadWorking;

        public final int SLEEP_TIME = 5000;

        int iterationNumber = 0;

        int iterationConfirmSend = 0;

        Map<UUID, Integer> transactionSend = new HashMap<>();

        /*IMPLEMENTATION DealsWithPluginIdentity*/
        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        /*IMPLEMENTATION Runnable*/
        @Override
        public void run() {

            threadWorking = true;
            logManager.log(NegotiationTransactionCustomerBrokerUpdatePluginRoot.getLogLevelByClass(this.getClass().getName()), "Customer Broker Update Monitor Agent: running...", null, null);

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

                    logManager.log(NegotiationTransactionCustomerBrokerUpdatePluginRoot.getLogLevelByClass(this.getClass().getName()), new StringBuilder().append("Iteration number ").append(iterationNumber).toString(), null, null);
                    doTheMainTask();

                } catch (CantSendCustomerBrokerUpdateNegotiationTransactionException | CantSendCustomerBrokerUpdateConfirmationNegotiationTransactionException | CantUpdateRecordException e) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        }

        /*INNER CLASS PUBLIC METHOD*/
        public void Initialize() throws CantInitializeCBPAgent {

            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.DATABASE_NAME);

            } catch (DatabaseNotFoundException databaseNotFoundException) {

                try {
                    CustomerBrokerUpdateNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerUpdateNegotiationTransactionDatabaseFactory(this.pluginDatabaseSystem);
                    database = databaseFactory.createDatabase(pluginId, CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.DATABASE_NAME);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                    throw new CantInitializeCBPAgent(cantCreateDatabaseException, "Customer Broker Update Initialize Monitor Agent - trying to create the plugin database", "Please, check the cause");
                }

            } catch (CantOpenDatabaseException exception) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantInitializeCBPAgent(exception, "Customer Broker Update Initialize Monitor Agent - trying to open the plugin database", "Please, check the cause");
            }

        }

        /*INNER CLASS PRIVATE METHOD*/
        private void doTheMainTask() throws
                CantSendCustomerBrokerUpdateNegotiationTransactionException,
                CantSendCustomerBrokerUpdateConfirmationNegotiationTransactionException,
                CantUpdateRecordException {

            try {

                customerBrokerUpdateNegotiationTransactionDatabaseDao = new CustomerBrokerUpdateNegotiationTransactionDatabaseDao(pluginDatabaseSystem, pluginId, database);

                String negotiationXML;
                NegotiationType negotiationType;
                UUID transactionId;
                List<CustomerBrokerUpdate> negotiationPendingToSubmitList;
                CustomerBrokerPurchaseNegotiation purchaseNegotiation = new NegotiationPurchaseRecord();
                CustomerBrokerSaleNegotiation saleNegotiation = new NegotiationSaleRecord();
                int timeConfirmSend = 20;

                //SEND NEGOTIATION PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_SUBMIT)
                negotiationPendingToSubmitList = customerBrokerUpdateNegotiationTransactionDatabaseDao.getPendingToSubmitNegotiation();
                if (!negotiationPendingToSubmitList.isEmpty()) {
                    for (CustomerBrokerUpdate negotiationTransaction : negotiationPendingToSubmitList) {

                        System.out.print(new StringBuilder().append("\n\n**** 5) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - NEGOTIATION FOR SEND ").append("\n - TransactionId: ").append(negotiationTransaction.getTransactionId()).append("\n - Status: ").append(negotiationTransaction.getStatusTransaction()).append(" ****\n").toString());

                        negotiationXML = negotiationTransaction.getNegotiationXML();
                        negotiationType = negotiationTransaction.getNegotiationType();
                        transactionId = negotiationTransaction.getTransactionId();

                        switch (negotiationType) {
                            case PURCHASE:
                                purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                                System.out.print(new StringBuilder().append("\n\n**** 6) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - PURCHASE NEGOTIATION SEND negotiationId(XML): ").append(purchaseNegotiation.getNegotiationId()).append(" ****\n").append("\n - Status :").append(purchaseNegotiation.getStatus().getCode()).toString());
                                //SEND NEGOTIATION TO BROKER
                                negotiationTransmissionManager.sendNegotiationToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_UPDATE);

                                break;

                            case SALE:
                                saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);
                                System.out.print(new StringBuilder().append("\n\n**** 6) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - SALE NEGOTIATION SEND negotiationId(XML): ").append(saleNegotiation.getNegotiationId()).append(" ****\n").append("\n - Status :").append(saleNegotiation.getStatus().getCode()).toString());
                                //SEND NEGOTIATION TO CUSTOMER
                                negotiationTransmissionManager.sendNegotiationToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_UPDATE);

                                break;
                        }

                        //Update the Negotiation Transaction
//                        System.out.print("\n\n**** 7) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - UPDATE STATUS SALE NEGOTIATION STATUS : " + NegotiationTransactionStatus.SENDING_NEGOTIATION.getCode() + " ****\n");
                        customerBrokerUpdateNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerUpdateNegotiationTranasction(transactionId, NegotiationTransactionStatus.SENDING_NEGOTIATION);
                        CustomerBrokerUpdate transactionDao = customerBrokerUpdateNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerUpdateNegotiationTranasction(transactionId);
                        System.out.print(new StringBuilder().append("\n\n**** 6.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - STATUS TRANSACTION: ").append(transactionDao.getStatusTransaction().getCode()).append(" ****\n").toString());

                    }
                }

                //SEND CONFIRM PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_CONFIRMATION)
                negotiationPendingToSubmitList = customerBrokerUpdateNegotiationTransactionDatabaseDao.getPendingToConfirmtNegotiation();
                if (!negotiationPendingToSubmitList.isEmpty()) {
                    for (CustomerBrokerUpdate negotiationTransaction : negotiationPendingToSubmitList) {

                        System.out.print("\n\n**** 22) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CONFIRMATION FOR SEND ****\n");

                        transactionId = negotiationTransaction.getTransactionId();
                        negotiationType = negotiationTransaction.getNegotiationType();

                        switch (negotiationType) {
                            case PURCHASE:
                                System.out.print(new StringBuilder().append("\n**** 23) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CONFIRMATION SEND PURCHASE NEGOTIATION negotiationId(XML): ").append(negotiationTransaction.getTransactionId()).append(" ****\n").toString());
                                //SEND CONFIRM NEGOTIATION TO BROKER
                                negotiationTransmissionManager.sendConfirmNegotiationToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_UPDATE);
                                break;
                            case SALE:
                                System.out.print(new StringBuilder().append("\n**** 23) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CONFIRMATION SEND SALE NEGOTIATION negotiationId(XML): ").append(negotiationTransaction.getTransactionId()).append(" ****\n").toString());
                                //SEND NEGOTIATION TO CUSTOMER
                                negotiationTransmissionManager.sendConfirmNegotiationToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_UPDATE);
                                break;
                        }

                        //Update the Negotiation Transaction
                        customerBrokerUpdateNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerUpdateNegotiationTranasction(transactionId, NegotiationTransactionStatus.CONFIRM_NEGOTIATION);

                        //CONFIRM TRANSACTION IS DONE
                        customerBrokerUpdateNegotiationTransactionDatabaseDao.confirmTransaction(transactionId);

                    }
                }

                //Check if pending events
                List<UUID> pendingEventsIdList = customerBrokerUpdateNegotiationTransactionDatabaseDao.getPendingEvents();
                for (UUID eventId : pendingEventsIdList) {
                    checkPendingEvent(eventId);
                }

                //SEND TRNSACTION AGAIN IF NOT IS CONFIRM
                if (timeConfirmSend == iterationConfirmSend) {

                    CustomerBrokerUpdateForwardTransaction forwardTransaction = new CustomerBrokerUpdateForwardTransaction(
                            customerBrokerUpdateNegotiationTransactionDatabaseDao,
                            pluginRoot,
                            transactionSend
                    );

                    forwardTransaction.pendingToConfirmtTransaction();
                    transactionSend = forwardTransaction.getTransactionSend();

                    iterationConfirmSend = 0;
                }

            } catch (CantSendNegotiationToCryptoBrokerException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerUpdateNegotiationTransactionException(CantSendCustomerBrokerUpdateNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Purchase Negotiation", "Error in Negotiation Transmission Network Service");
            } catch (CantSendNegotiationToCryptoCustomerException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerUpdateNegotiationTransactionException(CantSendCustomerBrokerUpdateNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Sale Negotiation", "Error in Negotiation Transmission Network Service");
            } catch (CantSendConfirmToCryptoBrokerException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerUpdateConfirmationNegotiationTransactionException(CantSendCustomerBrokerUpdateConfirmationNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Confirm Purchase Negotiation", "Error in Negotiation Transmission Network Service");
            } catch (CantSendConfirmToCryptoCustomerException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerUpdateConfirmationNegotiationTransactionException(CantSendCustomerBrokerUpdateConfirmationNegotiationTransactionException.DEFAULT_MESSAGE, e, "Sending Confirm Sale Negotiation", "Error in Negotiation Transmission Network Service");
            } catch (CantGetNegotiationTransactionListException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Sending Negotiation", "Cannot get the Negotiation list from database");
            } catch (Exception e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSendCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Sending Negotiation", "UNKNOWN FAILURE.");
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

                String eventTypeCode = customerBrokerUpdateNegotiationTransactionDatabaseDao.getEventType(eventId);

                //EVENT - RECEIVE NEGOTIATION
                if (eventTypeCode.equals(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_UPDATE.getCode())) {

                    List<Transaction<NegotiationTransmission>> pendingTransactionList = negotiationTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                    for (Transaction<NegotiationTransmission> record : pendingTransactionList) {

                        negotiationTransmission = record.getInformation();

                        if (negotiationTransmission.getNegotiationTransactionType().getCode().equals(NegotiationTransactionType.CUSTOMER_BROKER_UPDATE.getCode())) {

                            negotiationXML = negotiationTransmission.getNegotiationXML();
                            transmissionId = negotiationTransmission.getTransmissionId();
                            transactionId = negotiationTransmission.getTransactionId();
                            negotiationType = negotiationTransmission.getNegotiationType();

                            if (negotiationXML != null) {

                                negotiationTransaction = customerBrokerUpdateNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerUpdateNegotiationTranasction(transactionId);

                                if (negotiationTransmission.getTransmissionType().equals(NegotiationTransmissionType.TRANSMISSION_NEGOTIATION)) {

                                    if (negotiationTransaction == null) {

                                        switch (negotiationType) {
                                            case PURCHASE:

                                                System.out.print("\n**** 19) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CREATE PURCHASE NEGOTIATION TRANSACTION  ****\n");
                                                //UPDATE PURCHASE NEGOTIATION
                                                purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);

                                                customerBrokerUpdatePurchaseNegotiationTransaction = new CustomerBrokerUpdatePurchaseNegotiationTransaction(
                                                        customerBrokerPurchaseNegotiationManager,
                                                        customerBrokerUpdateNegotiationTransactionDatabaseDao,
                                                        pluginRoot
                                                );

                                                final String purchaseCancelReason = purchaseNegotiation.getCancelReason();
                                                System.out.println(new StringBuilder().append("CancelReason: ").append(purchaseCancelReason).toString());

                                                if (purchaseCancelReason != null && !purchaseCancelReason.isEmpty() && !purchaseCancelReason.equalsIgnoreCase("null")) {
                                                    System.out.print("\n**** 20) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CANCEL PURCHASE NEGOTIATION TRANSACTION  ****\n");
                                                    //CANCEL NEGOTIATION
                                                    customerBrokerUpdatePurchaseNegotiationTransaction.receiveCancelPurchaseNegotiationTranasction(transactionId, purchaseNegotiation);

                                                    FermatBundle fermatBundle = new FermatBundle();
                                                    fermatBundle.put(SOURCE_PLUGIN, Plugins.CUSTOMER_BROKER_UPDATE.getCode());
                                                    fermatBundle.put(APP_NOTIFICATION_PAINTER_FROM, new Owner(WalletsPublicKeys.CBP_CRYPTO_CUSTOMER_WALLET.getCode()));
                                                    fermatBundle.put(APP_TO_OPEN_PUBLIC_KEY, WalletsPublicKeys.CBP_CRYPTO_CUSTOMER_WALLET.getCode());
                                                    fermatBundle.put(NOTIFICATION_ID, CBPBroadcasterConstants.CCW_CANCEL_NEGOTIATION_NOTIFICATION);
                                                    fermatBundle.put(APP_ACTIVITY_TO_OPEN_CODE, Activities.CBP_CRYPTO_CUSTOMER_WALLET_CONTRACTS_HISTORY.getCode());

                                                    broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, fermatBundle);

                                                    fermatBundle = new FermatBundle();
                                                    fermatBundle.put(Broadcaster.PUBLISH_ID, WalletsPublicKeys.CBP_CRYPTO_CUSTOMER_WALLET.getCode());
                                                    fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CCW_NEGOTIATION_UPDATE_VIEW);
                                                    broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle);
                                                } else {
                                                    System.out.print("\n**** 20) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - UPDATE PURCHASE NEGOTIATION TRANSACTION  ****\n");
                                                    //UPDATE NEGOTIATION
                                                    customerBrokerUpdatePurchaseNegotiationTransaction.receivePurchaseNegotiationTranasction(transactionId, purchaseNegotiation);

                                                    FermatBundle fermatBundle = new FermatBundle();
                                                    fermatBundle.put(SOURCE_PLUGIN, Plugins.CUSTOMER_BROKER_UPDATE.getCode());
                                                    fermatBundle.put(APP_NOTIFICATION_PAINTER_FROM, new Owner(WalletsPublicKeys.CBP_CRYPTO_CUSTOMER_WALLET.getCode()));
                                                    fermatBundle.put(APP_TO_OPEN_PUBLIC_KEY, WalletsPublicKeys.CBP_CRYPTO_CUSTOMER_WALLET.getCode());
                                                    fermatBundle.put(NOTIFICATION_ID, CBPBroadcasterConstants.CCW_WAITING_FOR_CUSTOMER_NOTIFICATION);
                                                    fermatBundle.put(APP_ACTIVITY_TO_OPEN_CODE, Activities.CBP_CRYPTO_CUSTOMER_WALLET_HOME.getCode());
                                                    broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, fermatBundle);

                                                    fermatBundle = new FermatBundle();
                                                    fermatBundle.put(Broadcaster.PUBLISH_ID, WalletsPublicKeys.CBP_CRYPTO_CUSTOMER_WALLET.getCode());
                                                    fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CCW_NEGOTIATION_UPDATE_VIEW);
                                                    broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle);
                                                }

                                                break;

                                            case SALE:
                                                System.out.print("\n**** 19) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CREATE SALE NEGOTIATION TRANSACTION  ****\n");
                                                //UPDATE SALE NEGOTIATION
                                                saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);


                                                customerBrokerUpdateSaleNegotiationTransaction = new CustomerBrokerUpdateSaleNegotiationTransaction(
                                                        customerBrokerSaleNegotiationManager,
                                                        customerBrokerUpdateNegotiationTransactionDatabaseDao,
                                                        pluginRoot
                                                );

                                                final String saleCancelReason = saleNegotiation.getCancelReason();
                                                System.out.println(new StringBuilder().append("CancelReason: ").append(saleCancelReason).toString());

                                                if (saleCancelReason != null && !saleCancelReason.isEmpty() && !saleCancelReason.equalsIgnoreCase("null")) {
                                                    System.out.print("\n**** 20) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CANCEL SALE NEGOTIATION TRANSACTION  ****\n");
                                                    //CANCEL NEGOTIATION
                                                    customerBrokerUpdateSaleNegotiationTransaction.receiveCancelSaleNegotiationTranasction(transactionId, saleNegotiation);

                                                    FermatBundle fermatBundle = new FermatBundle();
                                                    fermatBundle.put(SOURCE_PLUGIN, Plugins.CUSTOMER_BROKER_UPDATE.getCode());
                                                    fermatBundle.put(APP_NOTIFICATION_PAINTER_FROM, new Owner(WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode()));
                                                    fermatBundle.put(APP_TO_OPEN_PUBLIC_KEY, WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode());
                                                    fermatBundle.put(NOTIFICATION_ID, CBPBroadcasterConstants.CBW_CANCEL_NEGOTIATION_NOTIFICATION);
                                                    fermatBundle.put(APP_ACTIVITY_TO_OPEN_CODE, Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY.getCode());

                                                    broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, fermatBundle);

                                                    fermatBundle = new FermatBundle();
                                                    fermatBundle.put(Broadcaster.PUBLISH_ID, WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode());
                                                    fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CBW_NEGOTIATION_UPDATE_VIEW);
                                                    broadcaster.publish(UPDATE_VIEW, fermatBundle);
                                                } else {
                                                    System.out.print("\n**** 20) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - UPDATE SALE NEGOTIATION TRANSACTION  ****\n");
                                                    //UPDATE NEGOTIATION
                                                    customerBrokerUpdateSaleNegotiationTransaction.receiveSaleNegotiationTranasction(transactionId, saleNegotiation);

                                                    FermatBundle fermatBundle = new FermatBundle();
                                                    fermatBundle.put(SOURCE_PLUGIN, Plugins.CUSTOMER_BROKER_UPDATE.getCode());
                                                    fermatBundle.put(APP_NOTIFICATION_PAINTER_FROM, new Owner(WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode()));
                                                    fermatBundle.put(APP_TO_OPEN_PUBLIC_KEY, WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode());
                                                    fermatBundle.put(NOTIFICATION_ID, CBPBroadcasterConstants.CBW_WAITING_FOR_BROKER_NOTIFICATION);
                                                    fermatBundle.put(APP_ACTIVITY_TO_OPEN_CODE, Activities.CBP_CRYPTO_BROKER_WALLET_HOME.getCode());
                                                    broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, fermatBundle);

                                                    fermatBundle = new FermatBundle();
                                                    fermatBundle.put(Broadcaster.PUBLISH_ID, WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode());
                                                    fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CBW_NEGOTIATION_UPDATE_VIEW);
                                                    broadcaster.publish(UPDATE_VIEW, fermatBundle);
                                                }

                                                break;
                                        }

                                    } else {

                                        System.out.print("\n**** 20) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CREATE PURCHASE NEGOTIATION TRANSACTION REPEAT SEND ****\n");
                                        //CONFIRM TRANSACTION
                                        customerBrokerUpdateNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerUpdateNegotiationTranasction(
                                                transactionId,
                                                NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM);

                                    }


                                } else if (negotiationTransmission.getTransmissionType().equals(NegotiationTransmissionType.TRANSMISSION_CONFIRM)) {

                                    System.out.print("\n**** 25.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - UPDATE NEGOTIATION TRANSACTION CONFIRM ****\n");

                                    if (!negotiationTransaction.getStatusTransaction().getCode().equals(NegotiationTransactionStatus.CONFIRM_NEGOTIATION.getCode())) {
                                        FermatBundle fermatBundle;

                                        switch (negotiationType) {
                                            case PURCHASE:
                                                System.out.print("\n**** 25.2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - UPDATE PURCHASE NEGOTIATION TRANSACTION CONFIRM ****\n");

                                                purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                                                final String purchaseCancelReason = purchaseNegotiation.getCancelReason();
                                                if (purchaseCancelReason == null || purchaseCancelReason.isEmpty() || purchaseCancelReason.equalsIgnoreCase("null"))
                                                    customerBrokerPurchaseNegotiationManager.waitForBroker(purchaseNegotiation);

                                                fermatBundle = new FermatBundle();
                                                fermatBundle.put(Broadcaster.PUBLISH_ID, WalletsPublicKeys.CBP_CRYPTO_CUSTOMER_WALLET.getCode());
                                                fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CCW_NEGOTIATION_UPDATE_VIEW);
                                                broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle);

                                                break;
                                            case SALE:
                                                System.out.print("\n**** 25.2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - UPDATE SALE NEGOTIATION TRANSACTION CONFIRM ****\n");

                                                saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);
                                                final String saleCancelReason = saleNegotiation.getCancelReason();
                                                if (saleCancelReason == null || saleCancelReason.isEmpty() || saleCancelReason.equalsIgnoreCase("null"))
                                                    customerBrokerSaleNegotiationManager.waitForCustomer(saleNegotiation);

                                                fermatBundle = new FermatBundle();
                                                fermatBundle.put(Broadcaster.PUBLISH_ID, WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode());
                                                fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CBW_NEGOTIATION_UPDATE_VIEW);
                                                broadcaster.publish(UPDATE_VIEW, fermatBundle);

                                                break;
                                        }

                                        //CONFIRM TRANSACTION
                                        customerBrokerUpdateNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerUpdateNegotiationTranasction(transactionId, NegotiationTransactionStatus.CONFIRM_NEGOTIATION);

                                    }

                                    //CONFIRM TRANSACTION IS DONE
                                    customerBrokerUpdateNegotiationTransactionDatabaseDao.confirmTransaction(transactionId);

                                }

                                //NOTIFIED EVENT
                                customerBrokerUpdateNegotiationTransactionDatabaseDao.updateEventTansactionStatus(eventId, EventStatus.NOTIFIED);
                                //CONFIRM TRANSMISSION
                                negotiationTransmissionManager.confirmReception(transmissionId);

                            }
                        }
                    }
                }

            } catch (Exception e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                e.printStackTrace();
            }
        }
        /*END INNER CLASS PRIVATE METHOD*/
    }
    /*END INNER CLASS*/
}
