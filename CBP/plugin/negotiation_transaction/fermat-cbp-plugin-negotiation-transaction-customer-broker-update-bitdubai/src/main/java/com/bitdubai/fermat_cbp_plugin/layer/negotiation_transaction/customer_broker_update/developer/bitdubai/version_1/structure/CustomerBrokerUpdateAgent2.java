package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
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
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdate;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.NegotiationTransactionCustomerBrokerUpdatePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantGetNegotiationTransactionListException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantSendCustomerBrokerUpdateConfirmationNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantSendCustomerBrokerUpdateNegotiationTransactionException;

import java.util.List;
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
public class CustomerBrokerUpdateAgent2 extends AbstractAgent {

    private NegotiationTransactionCustomerBrokerUpdatePluginRoot pluginRoot;
    private CustomerBrokerUpdateNegotiationTransactionDatabaseDao dao;
    private NegotiationTransmissionManager negotiationTransmissionManager;
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;
    private Broadcaster broadcaster;

    public CustomerBrokerUpdateAgent2(long sleepTime,
                                      TimeUnit timeUnit,
                                      long initDelayTime,
                                      NegotiationTransactionCustomerBrokerUpdatePluginRoot pluginRoot,
                                      CustomerBrokerUpdateNegotiationTransactionDatabaseDao dao,
                                      NegotiationTransmissionManager negotiationTransmissionManager,
                                      CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
                                      CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
                                      Broadcaster broadcaster
    ) {

        super(sleepTime, timeUnit, initDelayTime);

        this.pluginRoot = pluginRoot;
        this.dao = dao;
        this.negotiationTransmissionManager = negotiationTransmissionManager;
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
        this.broadcaster = broadcaster;
    }

    @Override
    protected Runnable agentJob() {
        return new Runnable() {
            @Override
            public void run() {

                try {

                    CustomerBrokerUpdateAgent2.this.doTheMainTask();

                } catch (
                        CantSendCustomerBrokerUpdateNegotiationTransactionException |
                                CantSendCustomerBrokerUpdateConfirmationNegotiationTransactionException |
                                CantUpdateRecordException e) {
                    CustomerBrokerUpdateAgent2.this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        };
    }

    @Override
    protected void onErrorOccur() {
        pluginRoot.reportError(
                UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                new Exception("CustomerBrokerCloseAgent2 Error"));
    }

    private void doTheMainTask() throws
            CantSendCustomerBrokerUpdateNegotiationTransactionException,
            CantSendCustomerBrokerUpdateConfirmationNegotiationTransactionException,
            CantUpdateRecordException {

        try {

            String negotiationXML;
            NegotiationType negotiationType;
            UUID transactionId;
            List<CustomerBrokerUpdate> negotiationPendingToSubmitList;
            CustomerBrokerPurchaseNegotiation purchaseNegotiation = new NegotiationPurchaseRecord();
            CustomerBrokerSaleNegotiation saleNegotiation = new NegotiationSaleRecord();

            //SEND NEGOTIATION PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_SUBMIT)
            negotiationPendingToSubmitList = dao.getPendingToSubmitNegotiation();
            if (!negotiationPendingToSubmitList.isEmpty()) {
                for (CustomerBrokerUpdate negotiationTransaction : negotiationPendingToSubmitList) {

                    System.out.print(new StringBuilder()
                            .append("\n\n**** 5) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - NEGOTIATION FOR SEND ")
                            .append("\n - TransactionId: ").append(negotiationTransaction.getTransactionId())
                            .append("\n - Status: ").append(negotiationTransaction.getStatusTransaction())
                            .append(" ****\n").toString());

                    negotiationXML = negotiationTransaction.getNegotiationXML();
                    negotiationType = negotiationTransaction.getNegotiationType();
                    transactionId = negotiationTransaction.getTransactionId();

                    switch (negotiationType) {
                        case PURCHASE:
                            purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                            System.out.print(new StringBuilder()
                                    .append("\n\n**** 6) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - PURCHASE NEGOTIATION SEND negotiationId(XML): ")
                                    .append(purchaseNegotiation.getNegotiationId())
                                    .append(" ****\n").append("\n - Status :")
                                    .append(purchaseNegotiation.getStatus().getCode()).toString());
                            //SEND NEGOTIATION TO BROKER
                            negotiationTransmissionManager.sendNegotiationToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_UPDATE);

                            break;

                        case SALE:
                            saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);
                            System.out.print(new StringBuilder()
                                    .append("\n\n**** 6) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - SALE NEGOTIATION SEND negotiationId(XML): ")
                                    .append(saleNegotiation.getNegotiationId())
                                    .append(" ****\n")
                                    .append("\n - Status :").append(saleNegotiation.getStatus().getCode()).toString());
                            //SEND NEGOTIATION TO CUSTOMER
                            negotiationTransmissionManager.sendNegotiationToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_UPDATE);

                            break;
                    }

                    //Update the Negotiation Transaction
//                        System.out.print("\n\n**** 7) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - UPDATE STATUS SALE NEGOTIATION STATUS : " + NegotiationTransactionStatus.SENDING_NEGOTIATION.getCode() + " ****\n");
                    dao.updateStatusRegisterCustomerBrokerUpdateNegotiationTranasction(transactionId, NegotiationTransactionStatus.SENDING_NEGOTIATION);
                    CustomerBrokerUpdate transactionDao = dao.getRegisterCustomerBrokerUpdateNegotiationTranasction(transactionId);
                    System.out.print(new StringBuilder().append("\n\n**** 6.1) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - STATUS TRANSACTION: ").append(transactionDao.getStatusTransaction().getCode()).append(" ****\n").toString());

                }
            }

            //SEND CONFIRM PENDING (CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = NegotiationTransactionStatus.PENDING_CONFIRMATION)
            negotiationPendingToSubmitList = dao.getPendingToConfirmtNegotiation();
            if (!negotiationPendingToSubmitList.isEmpty()) {
                for (CustomerBrokerUpdate negotiationTransaction : negotiationPendingToSubmitList) {

                    System.out.print("\n\n**** 22) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CONFIRMATION FOR SEND ****\n");

                    transactionId = negotiationTransaction.getTransactionId();
                    negotiationType = negotiationTransaction.getNegotiationType();

                    switch (negotiationType) {
                        case PURCHASE:
                            System.out.print(new StringBuilder().append("\n**** 23) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CONFIRMATION SEND PURCHASE NEGOTIATION negotiationId(XML): ").append(negotiationTransaction.getTransactionId()).append(" ****\n").toString());
                            //SEND CONFIRM NEGOTIATION TO BROKER
                            negotiationTransmissionManager.sendConfirmNegotiationToCryptoBroker(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_UPDATE);
                            break;
                        case SALE:
                            System.out.print(new StringBuilder().append("\n**** 23) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CONFIRMATION SEND SALE NEGOTIATION negotiationId(XML): ").append(negotiationTransaction.getTransactionId()).append(" ****\n").toString());
                            //SEND NEGOTIATION TO CUSTOMER
                            negotiationTransmissionManager.sendConfirmNegotiationToCryptoCustomer(negotiationTransaction, NegotiationTransactionType.CUSTOMER_BROKER_UPDATE);
                            break;
                    }

                    //Update the Negotiation Transaction
                    dao.updateStatusRegisterCustomerBrokerUpdateNegotiationTranasction(transactionId, NegotiationTransactionStatus.CONFIRM_NEGOTIATION);

                    //CONFIRM TRANSACTION IS DONE
                    dao.confirmTransaction(transactionId);

                }
            }

            //Check if pending events
            List<UUID> pendingEventsIdList = dao.getPendingEvents();
            for (UUID eventId : pendingEventsIdList) {
                checkPendingEvent(eventId);
            }

            //SEND TRNSACTION AGAIN IF NOT IS CONFIRM
            /*iterationConfirmSend++;
            if(timeConfirmSend == iterationConfirmSend){

                CustomerBrokerUpdateForwardTransaction forwardTransaction = new CustomerBrokerUpdateForwardTransaction(
                        dao,
                        pluginRoot,
                        transactionSend
                );

                forwardTransaction.pendingToConfirmtTransaction();
                transactionSend = forwardTransaction.getTransactionSend();

                iterationConfirmSend = 0;
            }*/

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
            CustomerBrokerUpdatePurchaseNegotiationTransaction customerBrokerUpdatePurchaseNegotiationTransaction;
            CustomerBrokerUpdateSaleNegotiationTransaction customerBrokerUpdateSaleNegotiationTransaction;
            CustomerBrokerPurchaseNegotiation purchaseNegotiation = new NegotiationPurchaseRecord();
            CustomerBrokerSaleNegotiation saleNegotiation = new NegotiationSaleRecord();

            String eventTypeCode = dao.getEventType(eventId);

            //EVENT - RECEIVE NEGOTIATION
            if (eventTypeCode.equals(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_UPDATE.getCode())) {
                List<Transaction<NegotiationTransmission>> pendingTransactionList = negotiationTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                for (Transaction<NegotiationTransmission> record : pendingTransactionList) {

                    negotiationTransmission = record.getInformation();

                    final NegotiationTransactionType negotiationTransactionType = negotiationTransmission.getNegotiationTransactionType();
                    if (negotiationTransactionType == NegotiationTransactionType.CUSTOMER_BROKER_UPDATE) {

                        negotiationXML = negotiationTransmission.getNegotiationXML();
                        transmissionId = negotiationTransmission.getTransmissionId();
                        transactionId = negotiationTransmission.getTransactionId();
                        negotiationType = negotiationTransmission.getNegotiationType();

                        if (negotiationXML != null) {

                            negotiationTransaction = dao.getRegisterCustomerBrokerUpdateNegotiationTranasction(transactionId);

                            if (negotiationTransmission.getTransmissionType().equals(NegotiationTransmissionType.TRANSMISSION_NEGOTIATION)) {

                                if (negotiationTransaction == null) {

                                    switch (negotiationType) {
                                        case PURCHASE:

                                            System.out.print("\n**** 19) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CREATE PURCHASE NEGOTIATION TRANSACTION  ****\n");
                                            //UPDATE PURCHASE NEGOTIATION
                                            purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);

                                            customerBrokerUpdatePurchaseNegotiationTransaction = new CustomerBrokerUpdatePurchaseNegotiationTransaction(
                                                    customerBrokerPurchaseNegotiationManager,
                                                    dao,
                                                    pluginRoot
                                            );

                                            final String purchaseCancelReason = purchaseNegotiation.getCancelReason();
                                            System.out.println(new StringBuilder().append("CancelReason: ").append(purchaseCancelReason).toString());

                                            if (purchaseCancelReason != null && !purchaseCancelReason.isEmpty() && !purchaseCancelReason.equalsIgnoreCase("null")) {
                                                System.out.print("\n**** 20) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CANCEL PURCHASE NEGOTIATION TRANSACTION  ****\n");
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
                                                System.out.print("\n**** 20) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - UPDATE PURCHASE NEGOTIATION TRANSACTION  ****\n");
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
                                            System.out.print("\n**** 19) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CREATE SALE NEGOTIATION TRANSACTION  ****\n");
                                            //UPDATE SALE NEGOTIATION
                                            saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);


                                            customerBrokerUpdateSaleNegotiationTransaction = new CustomerBrokerUpdateSaleNegotiationTransaction(
                                                    customerBrokerSaleNegotiationManager,
                                                    dao,
                                                    pluginRoot
                                            );

                                            final String saleCancelReason = saleNegotiation.getCancelReason();
                                            System.out.println(new StringBuilder().append("CancelReason: ").append(saleCancelReason).toString());

                                            if (saleCancelReason != null && !saleCancelReason.isEmpty() && !saleCancelReason.equalsIgnoreCase("null")) {
                                                System.out.print("\n**** 20) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CANCEL SALE NEGOTIATION TRANSACTION  ****\n");
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
                                                fermatBundle.put(Broadcaster.PUBLISH_ID, WalletsPublicKeys.CBP_CRYPTO_CUSTOMER_WALLET.getCode());
                                                fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CBW_NEGOTIATION_UPDATE_VIEW);
                                                broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle);

                                            } else {
                                                System.out.print("\n**** 20) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - UPDATE SALE NEGOTIATION TRANSACTION  ****\n");
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
                                                fermatBundle.put(Broadcaster.PUBLISH_ID, WalletsPublicKeys.CBP_CRYPTO_CUSTOMER_WALLET.getCode());
                                                fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CBW_NEGOTIATION_UPDATE_VIEW);
                                                broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle);
                                            }

                                            break;
                                    }

                                } else {

                                    System.out.print("\n**** 20) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - CREATE PURCHASE NEGOTIATION TRANSACTION REPEAT SEND ****\n");
                                    //CONFIRM TRANSACTION
                                    dao.updateStatusRegisterCustomerBrokerUpdateNegotiationTranasction(transactionId, NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM);

                                }


                            } else if (negotiationTransmission.getTransmissionType().equals(NegotiationTransmissionType.TRANSMISSION_CONFIRM)) {

                                System.out.print("\n**** 25.1) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - UPDATE NEGOTIATION TRANSACTION CONFIRM ****\n");

                                if (!negotiationTransaction.getStatusTransaction().getCode().equals(NegotiationTransactionStatus.CONFIRM_NEGOTIATION.getCode())) {

                                    FermatBundle fermatBundle = new FermatBundle();
                                    switch (negotiationType) {
                                        case PURCHASE:
                                            System.out.print("\n**** 25.2) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - UPDATE PURCHASE NEGOTIATION TRANSACTION CONFIRM ****\n");

                                            purchaseNegotiation = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationXML, purchaseNegotiation);
                                            final String purchaseCancelReason = purchaseNegotiation.getCancelReason();
                                            if (purchaseCancelReason == null || purchaseCancelReason.isEmpty() || purchaseCancelReason.equalsIgnoreCase("null"))
                                                customerBrokerPurchaseNegotiationManager.waitForBroker(purchaseNegotiation);

                                            fermatBundle.put(Broadcaster.PUBLISH_ID, WalletsPublicKeys.CBP_CRYPTO_CUSTOMER_WALLET.getCode());
                                            fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CCW_NEGOTIATION_UPDATE_VIEW);
                                            broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle);

                                            break;
                                        case SALE:
                                            System.out.print("\n**** 25.2) NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - AGENT - UPDATE SALE NEGOTIATION TRANSACTION CONFIRM ****\n");

                                            saleNegotiation = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationXML, saleNegotiation);
                                            final String saleCancelReason = saleNegotiation.getCancelReason();
                                            if (saleCancelReason == null || saleCancelReason.isEmpty() || saleCancelReason.equalsIgnoreCase("null"))
                                                customerBrokerSaleNegotiationManager.waitForCustomer(saleNegotiation);

                                            fermatBundle.put(Broadcaster.PUBLISH_ID, WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode());
                                            fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, CBPBroadcasterConstants.CBW_NEGOTIATION_UPDATE_VIEW);
                                            broadcaster.publish(BroadcasterType.UPDATE_VIEW, fermatBundle);

                                            break;
                                    }

                                    //CONFIRM TRANSACTION
                                    dao.updateStatusRegisterCustomerBrokerUpdateNegotiationTranasction(transactionId, NegotiationTransactionStatus.CONFIRM_NEGOTIATION);

                                }

                                //CONFIRM TRANSACTION IS DONE
                                dao.confirmTransaction(transactionId);

                            }

                            //NOTIFIED EVENT
                            dao.updateEventTansactionStatus(eventId, EventStatus.NOTIFIED);
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

}