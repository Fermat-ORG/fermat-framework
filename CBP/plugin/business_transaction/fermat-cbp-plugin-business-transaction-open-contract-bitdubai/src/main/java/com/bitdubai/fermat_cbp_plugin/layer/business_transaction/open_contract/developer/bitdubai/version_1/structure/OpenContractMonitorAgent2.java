package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.AbstractBusinessTransactionAgent;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.OpenContract;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.OpenContractPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDao;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/07/16.
 */
public class OpenContractMonitorAgent2
        extends AbstractBusinessTransactionAgent<OpenContractPluginRoot> {

    private final OpenContractBusinessTransactionDao openContractBusinessTransactionDao;
    private final TransactionTransmissionManager transactionTransmissionManager;
    private final CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    private final CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    private final CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;
    private final CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;
    private final Broadcaster broadcaster;

    /**
     * Default constructor with parameters
     *
     * @param sleepTime
     * @param timeUnit
     * @param initDelayTime
     * @param pluginRoot
     * @param eventManager
     * @param openContractBusinessTransactionDao
     * @param transactionTransmissionManager
     * @param customerBrokerContractPurchaseManager
     * @param customerBrokerContractSaleManager
     */
    public OpenContractMonitorAgent2(
            long sleepTime,
            TimeUnit timeUnit,
            long initDelayTime,
            OpenContractPluginRoot pluginRoot,
            EventManager eventManager,
            OpenContractBusinessTransactionDao openContractBusinessTransactionDao,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
            Broadcaster broadcaster) {
        super(sleepTime, timeUnit, initDelayTime, pluginRoot, eventManager);
        this.openContractBusinessTransactionDao = openContractBusinessTransactionDao;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
        this.broadcaster = broadcaster;

    }

    @Override
    protected void doTheMainTask() {
        try {

            UUID transmissionId;

            // Check if exist in database new contracts to send
            List<OpenContract> contractPendingToSubmitList = openContractBusinessTransactionDao.getPendingToSubmitOpenContract();
            if (!contractPendingToSubmitList.isEmpty()) {

                for (OpenContract openContract : contractPendingToSubmitList) {

                    transmissionId  = UUID.randomUUID();
                    OpenContractProcess openProcess = new OpenContractProcess(
                            openContractBusinessTransactionDao,
                            transactionTransmissionManager
                    );

                    if(openProcess.processPendingToSubmit(openContract, transmissionId)){

                        //CONFIRM RECEPTION OF TRANSMISSION
                        transactionTransmissionManager.confirmReception(transmissionId);

                    }
                }
            }



            // Check if pending events
            List<String> pendingEventsIdList = openContractBusinessTransactionDao.getPendingEvents();
            for (String eventId : pendingEventsIdList) {
                checkPendingEvent(eventId);
            }

        } catch (Exception e) {
            reportError(e);
        }
    }

    @Override
    protected void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {

        try {

            UUID transmissionId;
            BusinessTransactionMetadata businessTransactionMetadata;

            String eventTypeCode = openContractBusinessTransactionDao.getEventType(eventId);

            List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);

            for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {

                businessTransactionMetadata = record.getInformation();
                transmissionId              = record.getTransactionID();
                Plugins businessTransaction = businessTransactionMetadata.getRemoteBusinessTransaction();

                System.out.println("OPEN_CONTRACT - remoteBusinessTransaction = " + businessTransaction);
                if (businessTransaction != Plugins.OPEN_CONTRACT)
                    continue;
                System.out.println("OPEN_CONTRACT - PASS remoteBusinessTransaction = " + businessTransaction);

                OpenContractProcess openProcess = new OpenContractProcess(
                        businessTransactionMetadata,
                        openContractBusinessTransactionDao,
                        transactionTransmissionManager,
                        customerBrokerContractPurchaseManager,
                        customerBrokerContractSaleManager,
                        customerBrokerPurchaseNegotiationManager,
                        customerBrokerSaleNegotiationManager,
                        broadcaster,
                        eventManager
                );

                //************** CONTRACT HASH
                if (eventTypeCode.equals(EventType.INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH.getCode())) {

                    if (openProcess.processContractHash()) {

                        //CONFIRM RECEPTION OF TRANSMISSION
                        transactionTransmissionManager.confirmReception(transmissionId);
                        System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() [processContractHash] - Transaction transmission marked as CONFIRMED\n");
                        break;

                    }

                }

                //************** CONFIRM CONTRACT HASH
                if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {

                    if (openProcess.processConfirmContractHash()) {

                        //CONFIRM RECEPTION OF TRANSMISSION
                        transactionTransmissionManager.confirmReception(transmissionId);
                        System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() [processConfirmContractHash] - Transaction transmission marked as CONFIRMED\n");
                        break;

                    }

                }

                //************** ACK CONFIRM CONTRACT HASH
                if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT.getCode())) {

                    if (openProcess.processAckContractHash()) {

                        //CONFIRM RECEPTION OF TRANSMISSION
                        transactionTransmissionManager.confirmReception(transmissionId);
                        System.out.println("OPEN_CONTRACT - AGENT - checkPendingEvent() [processAckContractHash] - Transaction transmission marked as CONFIRMED\n");
                        break;

                    }

                }
            }

            //CONFIRM RECEPTION OF NOTIFICATION EVENT
            openContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);

        } catch (CantDeliverPendingTransactionsException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot deliver pending transaction");
        } catch (CantConfirmTransactionException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot confirm transaction");
        } catch (CantUpdateRecordException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Checking pending transactions",
                    "Cannot update the database record");
        }
    }

}