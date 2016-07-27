package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_ACTIVITY_TO_OPEN_CODE;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_NOTIFICATION_PAINTER_FROM;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_TO_OPEN_PUBLIC_KEY;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.NOTIFICATION_ID;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.SOURCE_PLUGIN;

/**
 * Created by Yordin Alayn on 05.07.16.
 */
public class CustomerBrokerNewAgent2 extends AbstractAgent {

    private Database database;
    private Thread agentThread;
    private LogManager logManager;
    private EventManager eventManager;
    private NegotiationTransactionCustomerBrokerNewPluginRoot pluginRoot;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private NegotiationTransmissionManager negotiationTransmissionManager;
    private CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation;
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;
    private CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation;
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;
    private Broadcaster broadcaster;
    private CustomerBrokerNewNegotiationTransactionDatabaseDao dao;

    private int iterationConfirmSend = 0;
    private Map<UUID, Integer> transactionSend = new HashMap<>();

    public CustomerBrokerNewAgent2(
            long sleepTime,
            TimeUnit timeUnit,
            long initDelayTime,
            PluginDatabaseSystem pluginDatabaseSystem,
            LogManager logManager,
            NegotiationTransactionCustomerBrokerNewPluginRoot pluginRoot,
            EventManager eventManager,
            UUID pluginId,
            CustomerBrokerNewNegotiationTransactionDatabaseDao dao,
            NegotiationTransmissionManager negotiationTransmissionManager,
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation,
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation,
            CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
            Broadcaster broadcaster
    ) {

        super(sleepTime, timeUnit, initDelayTime);

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.logManager = logManager;
        this.pluginRoot = pluginRoot;
        this.eventManager = eventManager;
        this.pluginId = pluginId;
        this.dao = dao;
        this.negotiationTransmissionManager = negotiationTransmissionManager;
        this.customerBrokerPurchaseNegotiation = customerBrokerPurchaseNegotiation;
        this.customerBrokerSaleNegotiation = customerBrokerSaleNegotiation;
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
        this.broadcaster = broadcaster;
    }

    @Override
    protected Runnable agentJob() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                try {

                    doTheMainTask();

                } catch (CantSendCustomerBrokerNewNegotiationTransactionException |
                        CantSendCustomerBrokerNewConfirmationNegotiationTransactionException |
                        CantUpdateRecordException e) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        };
        return runnable;
    }

    @Override
    protected void onErrorOccur() {
        pluginRoot.reportError(
                UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                new Exception("CustomerBrokerNewAgent2 Error"));
    }

    private void doTheMainTask() throws
            CantSendCustomerBrokerNewNegotiationTransactionException,
            CantSendCustomerBrokerNewConfirmationNegotiationTransactionException,
            CantUpdateRecordException {
        try {

            String negotiationXML;
            NegotiationType negotiationType;
            UUID transactionId;
            List<CustomerBrokerNew> negotiationPendingToSubmitList;
            CustomerBrokerPurchaseNegotiation purchaseNegotiation = new NegotiationPurchaseRecord();
            CustomerBrokerSaleNegotiation saleNegotiation = new NegotiationSaleRecord();
            int timeConfirmSend = 20;

            //SEND NEGOTIATION PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_SUBMIT)
            negotiationPendingToSubmitList = dao.getPendingToSubmitNegotiation();
            if (!negotiationPendingToSubmitList.isEmpty()) {
                for (CustomerBrokerNew negotiationTransaction : negotiationPendingToSubmitList) {

                    System.out.print("\n\n**** 5) NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - PURCHASE NEGOTIATION SEND ****\n");
                    negotiationXML = negotiationTransaction.getNegotiationXML();
                    negotiationType = negotiationTransaction.getNegotiationType();
                    transactionId = negotiationTransaction.getTransactionId();

                    switch (negotiationType) {
                        case PURCHASE:
                            purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                            System.out.print(new StringBuilder().append("\n\n**** 6) NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - PURCHASE NEGOTIATION SEND negotiationId(XML): ").append(purchaseNegotiation.getNegotiationId()).append(" ****\n").toString());
                            //SEND NEGOTIATION TO BROKER
                            negotiationTransmissionManager.sendNegotiationToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_NEW);
                            break;
                    }

                    //Update the Negotiation Transaction
                    dao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(
                            transactionId,
                            NegotiationTransactionStatus.SENDING_NEGOTIATION);

                }
            }

            //SEND CONFIRM PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_CONFIRMATION)
            negotiationPendingToSubmitList = dao.getPendingToConfirmtNegotiation();
            if (!negotiationPendingToSubmitList.isEmpty()) {
                for (CustomerBrokerNew negotiationTransaction : negotiationPendingToSubmitList) {

                    negotiationXML = negotiationTransaction.getNegotiationXML();
                    negotiationType = negotiationTransaction.getNegotiationType();
                    transactionId = negotiationTransaction.getTransactionId();

                    System.out.print(new StringBuilder().append("\n\n**** 22) NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - CONFIRMATION FOR SEND transactionId: ").append(transactionId).append(" ****\n").toString());

                    switch (negotiationType) {
                        case SALE:
                            saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);
                            System.out.print(new StringBuilder().append("\n\n**** 23) NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - CONFIRMATION SEND negotiationId(XML): ").append(transactionId).append(" ****\n").toString());
                            negotiationTransmissionManager.sendConfirmNegotiationToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_NEW);
                            break;
                    }

                    //UPDATE STATUS NEGOTIATION TRANSACTION
                    dao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(
                            transactionId,
                            NegotiationTransactionStatus.CONFIRM_NEGOTIATION);

                    //CONFIRM TRANSACTION IS DONE
                    dao.confirmTransaction(transactionId);
                }

            }

            //PROCES PENDING EVENT
            List<UUID> pendingEventsIdList = dao.getPendingEvents();
            for (UUID eventId : pendingEventsIdList) {
                checkPendingEvent(eventId);
            }

            //SEND TRNSACTION AGAIN IF NOT IS CONFIRM
            /*iterationConfirmSend++;
            if(timeConfirmSend == iterationConfirmSend){

                CustomerBrokerNewForwardTransaction forwardTransaction = new CustomerBrokerNewForwardTransaction(
                        dao,
                        pluginRoot,
                        transactionSend
                );

                forwardTransaction.pendingToConfirmtTransaction();
                transactionSend = forwardTransaction.getTransactionSend();

                iterationConfirmSend = 0;
            }*/

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

    private void checkPendingEvent(UUID eventId) throws UnexpectedResultReturnedFromDatabaseException {

        try {

            UUID transactionId;
            UUID transmissionId;
            NegotiationTransmission negotiationTransmission;
            NegotiationTransaction negotiationTransaction;
            NegotiationType negotiationType;
            String negotiationXML;
            CustomerBrokerNewSaleNegotiationTransaction customerBrokerNewSaleNegotiationTransaction;

            CustomerBrokerPurchaseNegotiation purchaseNegotiation = new NegotiationPurchaseRecord();
            CustomerBrokerSaleNegotiation saleNegotiation = new NegotiationSaleRecord();

            String eventTypeCode = dao.getEventType(eventId);

            //EVENT - RECEIVE NEGOTIATION
            if (eventTypeCode.equals(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_NEW.getCode())) {
                List<Transaction<NegotiationTransmission>> pendingTransactionList = negotiationTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                for (Transaction<NegotiationTransmission> record : pendingTransactionList) {

                    negotiationTransmission = record.getInformation();

                    final NegotiationTransactionType negotiationTransactionType = negotiationTransmission.getNegotiationTransactionType();
                    if (negotiationTransactionType == NegotiationTransactionType.CUSTOMER_BROKER_NEW) {

                        negotiationXML = negotiationTransmission.getNegotiationXML();
                        transmissionId = negotiationTransmission.getTransmissionId();
                        transactionId = negotiationTransmission.getTransactionId();
                        negotiationType = negotiationTransmission.getNegotiationType();

                        if (negotiationXML != null) {

                            negotiationTransaction = dao.getRegisterCustomerBrokerNewNegotiationTranasction(transactionId);

                            if (negotiationTransmission.getTransmissionType().equals(NegotiationTransmissionType.TRANSMISSION_NEGOTIATION)) {

                                switch (negotiationType) {
                                    case SALE:

                                        //CREATE SALE NEGOTIATION
                                        System.out.print("\n**** 21) NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - CREATE SALE NEGOTIATION TRANSACTION  ****\n");
                                        saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);

                                        if (negotiationTransaction == null) {

                                            System.out.print("\n**** 21.1) NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - CREATE SALE NEGOTIATION TRANSACTION NEW ****\n");
                                            customerBrokerNewSaleNegotiationTransaction = new CustomerBrokerNewSaleNegotiationTransaction(
                                                    customerBrokerSaleNegotiationManager,
                                                    dao,
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

                                            System.out.print("\n**** 21.1) NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - CREATE SALE NEGOTIATION TRANSACTION REPEAT SEND ****\n");
                                            //CONFIRM TRANSACTION
                                            dao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(
                                                    transactionId,
                                                    NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM);

                                        }
                                        break;
                                }

                            } else if (negotiationTransmission.getTransmissionType().equals(NegotiationTransmissionType.TRANSMISSION_CONFIRM)) {

                                System.out.print("\n**** 25.1) NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - NEW NEGOTIATION TRANSACTION CONFIRM ****\n");

                                if (negotiationTransaction == null) {

                                    switch (negotiationType) {
                                        case PURCHASE:

                                            if (!negotiationTransaction.getStatusTransaction().getCode().equals(NegotiationTransactionStatus.CONFIRM_NEGOTIATION.getCode())) {

                                                //CREATE CONFIRM NEGOTIATION
                                                System.out.print("\n**** 25.2) NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - NEW PURCHASE NEGOTIATION TRANSACTION CONFIRM ****\n");
                                                purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                                                customerBrokerPurchaseNegotiationManager.waitForBroker(purchaseNegotiation);

                                                //CONFIRM TRANSACTION
                                                dao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(
                                                        transactionId,
                                                        NegotiationTransactionStatus.CONFIRM_NEGOTIATION);
                                            }

                                            //CONFIRM TRANSACTION IS DONE
                                            dao.confirmTransaction(transactionId);

                                            break;
                                    }

                                }

                            }

                            //NOTIFIED EVENT
                            dao.updateEventTansactionStatus(eventId, EventStatus.NOTIFIED);
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
}