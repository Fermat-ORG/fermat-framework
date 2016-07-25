package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.events.NewContractClosed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.interfaces.ContractPurchaseRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.interfaces.ContractSaleRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.AbstractBusinessTransactionAgent;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantUpdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantConfirmNotificationReceptionException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.CantSendContractNewStatusNotificationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.CloseContractPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.database.CloseContractBusinessTransactionDao;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/07/16.
 */
public class CloseContractMonitorAgent2
        extends AbstractBusinessTransactionAgent<CloseContractPluginRoot> {

    private final TransactionTransmissionManager transactionTransmissionManager;
    private final CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    private final CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    private final CloseContractBusinessTransactionDao closeContractBusinessTransactionDao;

    public CloseContractMonitorAgent2(
            long sleepTime,
            TimeUnit timeUnit,
            long initDelayTime,
            CloseContractPluginRoot pluginRoot,
            EventManager eventManager,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            CloseContractBusinessTransactionDao closeContractBusinessTransactionDao) {
        super(sleepTime, timeUnit, initDelayTime, pluginRoot, eventManager);
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.closeContractBusinessTransactionDao = closeContractBusinessTransactionDao;
    }

    @Override
    protected void doTheMainTask() {
        try {
            checkCloseContractsToSend();

            checkCloseContractsToConfirm();

            /**
             * Check if pending events
             */
            List<String> pendingEventsIdList = closeContractBusinessTransactionDao.getPendingEvents();
            for (String eventId : pendingEventsIdList) {
                try {
                    checkPendingEvent(eventId);
                } catch (Exception e) {
                    reportError(e);
                }

            }
        } catch (Exception e) {
            reportError(e);
        }
    }

    @Override
    protected void checkPendingEvent(String eventId) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            String eventTypeCode = closeContractBusinessTransactionDao.getEventType(eventId);
            BusinessTransactionMetadata businessTransactionMetadata;
            String contractHash;
            ContractTransactionStatus contractTransactionStatus;
            ContractType contractType;

            if (eventTypeCode.equals(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE.getCode())) {

                System.out.println("CLOSE_CONTRACT - INCOMING_NEW_CONTRACT_STATUS_UPDATE");

                List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                    businessTransactionMetadata = record.getInformation();
                    contractHash = businessTransactionMetadata.getContractHash();
                    Plugins remoteBusinessTransaction = businessTransactionMetadata.getRemoteBusinessTransaction();

                    System.out.println(new StringBuilder().append("CLOSE_CONTRACT - remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());
                    if (remoteBusinessTransaction != Plugins.CLOSE_CONTRACT)
                        continue;

                    System.out.println(new StringBuilder().append("CLOSE_CONTRACT - PASS remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());

                    try {
                        contractType = closeContractBusinessTransactionDao.getContractType(contractHash);
                    } catch (Exception e) {
                        System.out.println("CLOSE_CONTRACT - INCOMING_NEW_CONTRACT_STATUS_UPDATE - contractType NOT FOUND. Maybe the contract is not yet in Database");
                        return;
                    }

                    try {
                        contractTransactionStatus = closeContractBusinessTransactionDao.getContractTransactionStatus(contractHash);
                    } catch (Exception e) {
                        System.out.println("CLOSE_CONTRACT - INCOMING_NEW_CONTRACT_STATUS_UPDATE - contractTransactionStatus NOT FOUND. Maybe the contract is not yet in Database");
                        return;
                    }

                    System.out.println(new StringBuilder().append("CLOSE_CONTRACT - INCOMING_NEW_CONTRACT_STATUS_UPDATE - contractTransactionStatus = ").append(contractTransactionStatus).toString());
                    if (contractTransactionStatus == ContractTransactionStatus.CHECKING_CLOSING_CONTRACT) {

                        switch (contractType) {
                            case PURCHASE:
                                customerBrokerContractPurchaseManager.updateStatusCustomerBrokerPurchaseContractStatus(contractHash, ContractStatus.COMPLETED);

                                System.out.println("CLOSE_CONTRACT - INCOMING_NEW_CONTRACT_STATUS_UPDATE - contractType = PURCHASE");
                                System.out.println("CLOSE_CONTRACT - INCOMING_NEW_CONTRACT_STATUS_UPDATE - Updated Contract Status to: COMPLETED");
                                break;
                            case SALE:
                                customerBrokerContractSaleManager.updateStatusCustomerBrokerSaleContractStatus(contractHash, ContractStatus.COMPLETED);

                                System.out.println("CLOSE_CONTRACT - INCOMING_NEW_CONTRACT_STATUS_UPDATE - contractType = SALE");
                                System.out.println("CLOSE_CONTRACT - INCOMING_NEW_CONTRACT_STATUS_UPDATE - Updated Contract Status to: COMPLETED");
                                break;
                        }
                        closeContractBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONFIRM_CLOSED_CONTRACT);
                        closeContractBusinessTransactionDao.setCompletionDateByContractHash(contractHash, (new Date()).getTime());
                        closeContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                        transactionTransmissionManager.confirmReception(record.getTransactionID());

                        System.out.println("CLOSE_CONTRACT - INCOMING_NEW_CONTRACT_STATUS_UPDATE - Updated Transaction Status to: CONFIRM_CLOSED_CONTRACT");
                        System.out.println("CLOSE_CONTRACT - INCOMING_NEW_CONTRACT_STATUS_UPDATE - Reception Confirmed");
                        System.out.println("CLOSE_CONTRACT - INCOMING_NEW_CONTRACT_STATUS_UPDATE - Updated Event Status to: NOTIFIED");
                    }
                }
            }

            //TODO: check new confirmed closed contract... raise an event.
            if (eventTypeCode.equals(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE.getCode())) {

                System.out.print("\nCLOSE_CONTRACT - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE\n");

                List<Transaction<BusinessTransactionMetadata>> pendingTransactionList = transactionTransmissionManager.getPendingTransactions(Specialist.UNKNOWN_SPECIALIST);
                for (Transaction<BusinessTransactionMetadata> record : pendingTransactionList) {
                    businessTransactionMetadata = record.getInformation();
                    contractHash = businessTransactionMetadata.getContractHash();
                    Plugins remoteBusinessTransaction = businessTransactionMetadata.getRemoteBusinessTransaction();

                    System.out.println(new StringBuilder().append("CLOSE_CONTRACT - remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());
                    if (remoteBusinessTransaction != Plugins.CLOSE_CONTRACT)
                        continue;

                    System.out.println(new StringBuilder().append("CLOSE_CONTRACT - PASS remoteBusinessTransaction = ").append(remoteBusinessTransaction).toString());

                    try {
                        contractTransactionStatus = closeContractBusinessTransactionDao.getContractTransactionStatus(contractHash);
                    } catch (Exception e) {
                        System.out.println("CLOSE_CONTRACT - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - contractTransactionStatus NOT FOUND. Maybe the contract is not yet in Database");
                        return;
                    }

                    System.out.println(new StringBuilder().append("CLOSE_CONTRACT - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - contractTransactionStatus = ").append(contractTransactionStatus).toString());
                    if (contractTransactionStatus == ContractTransactionStatus.SUBMIT_CLOSING_CONTRACT_CONFIRMATION) {
                        closeContractBusinessTransactionDao.updateContractTransactionStatus(contractHash, ContractTransactionStatus.CONTRACT_COMPLETED);
                        raiseNewContractClosedEvent();

                        System.out.println("CLOSE_CONTRACT - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - Updated Transaction Status to: CONTRACT_COMPLETED");
                        System.out.println("CLOSE_CONTRACT - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - raised NewContractClosed Event");


                        transactionTransmissionManager.confirmReception(record.getTransactionID());
                        System.out.println("CLOSE_CONTRACT - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - Reception Confirmed");
                        closeContractBusinessTransactionDao.updateEventStatus(eventId, EventStatus.NOTIFIED);
                        System.out.println("CLOSE_CONTRACT - INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE - Updated Event Status to: NOTIFIED");
                    }
                }


            }

        } catch (CantUpdateRecordException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot update the database");
        } catch (CantConfirmTransactionException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot confirm the transaction");
        } catch (CantUpdateCustomerBrokerContractSaleException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot update the contract sale status");
        } catch (CantDeliverPendingTransactionsException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot get the pending transactions from transaction transmission plugin");
        } catch (CantUpdateCustomerBrokerContractPurchaseException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Checking pending events", "Cannot update the contract purchase status");
        }
    }

    /**
     * Check if exists a new closed contract to confirm
     */
    private void checkCloseContractsToConfirm() throws CantGetContractListException, UnexpectedResultReturnedFromDatabaseException,
            CantConfirmNotificationReceptionException, CantUpdateRecordException, CantConfirmTransactionException {

        ContractType contractType;
        String contractXML;
        ContractPurchaseRecord purchaseContract = new ContractPurchaseRecord();
        ContractSaleRecord saleContract = new ContractSaleRecord();

        List<String> contractToConfirmList = closeContractBusinessTransactionDao.getClosingConfirmContractToCloseList();
        for (String hashToSubmit : contractToConfirmList) {

            contractType = closeContractBusinessTransactionDao.getContractType(hashToSubmit);
            contractXML = closeContractBusinessTransactionDao.getContractXML(hashToSubmit);
            UUID transmissionId = UUID.randomUUID();

            System.out.println("CLOSE_CONTRACT - checkCloseContractsToConfirm()");

            switch (contractType) {
                case PURCHASE:
                    purchaseContract = (ContractPurchaseRecord) XMLParser.parseXML(contractXML, purchaseContract);
                    transactionTransmissionManager.confirmNotificationReception(
                            purchaseContract.getPublicKeyCustomer(),
                            purchaseContract.getPublicKeyBroker(),
                            hashToSubmit,
                            transmissionId.toString(),
                            Plugins.CLOSE_CONTRACT,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER);

                    System.out.println("CLOSE_CONTRACT - [Customer] checkCloseContractsToConfirm() - Sending Confirmation Notification");

                    break;
                case SALE:
                    saleContract = (ContractSaleRecord) XMLParser.parseXML(contractXML, saleContract);
                    transactionTransmissionManager.confirmNotificationReception(
                            saleContract.getPublicKeyBroker(),
                            saleContract.getPublicKeyCustomer(),
                            hashToSubmit,
                            transmissionId.toString(),
                            Plugins.CLOSE_CONTRACT,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);

                    System.out.println("CLOSE_CONTRACT - [Broker] checkCloseContractsToConfirm() - Sending Confirmation Notification");

                    break;
            }

            closeContractBusinessTransactionDao.updateContractTransactionStatus(hashToSubmit, ContractTransactionStatus.SUBMIT_CLOSING_CONTRACT_CONFIRMATION);

            System.out.println("CLOSE_CONTRACT - checkCloseContractsToConfirm() - Updated Transaction Status to: SUBMIT_CLOSING_CONTRACT_CONFIRMATION");
        }
    }

    /**
     * Check if exist in database new close contracts to send
     */
    private void checkCloseContractsToSend()
            throws CantGetContractListException, UnexpectedResultReturnedFromDatabaseException, CantSendContractNewStatusNotificationException,
            CantUpdateRecordException, CantConfirmTransactionException {

        ContractType contractType;
        String contractXML;
        ContractPurchaseRecord purchaseContract = new ContractPurchaseRecord();
        ContractSaleRecord saleContract = new ContractSaleRecord();

        List<String> contractToCloseList = closeContractBusinessTransactionDao.getNewContractToCloseList();
        for (String hashToSubmit : contractToCloseList) {

            contractType = closeContractBusinessTransactionDao.getContractType(hashToSubmit);
            contractXML = closeContractBusinessTransactionDao.getContractXML(hashToSubmit);
            UUID transmissionId = UUID.randomUUID();

            System.out.println("CLOSE_CONTRACT - checkCloseContractsToSend()");

            switch (contractType) {
                case PURCHASE:
                    purchaseContract = (ContractPurchaseRecord) XMLParser.parseXML(contractXML, purchaseContract);
                    transactionTransmissionManager.sendContractStatusNotification(
                            purchaseContract.getPublicKeyCustomer(),
                            purchaseContract.getPublicKeyBroker(),
                            hashToSubmit,
                            transmissionId.toString(),
                            ContractTransactionStatus.CHECKING_CLOSING_CONTRACT,
                            Plugins.CLOSE_CONTRACT,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER);

                    System.out.println("CLOSE_CONTRACT - [Customer] checkCloseContractsToSend() - Sending Transaction Status: CHECKING_CLOSING_CONTRACT");

                    break;
                case SALE:
                    saleContract = (ContractSaleRecord) XMLParser.parseXML(contractXML, saleContract);
                    transactionTransmissionManager.sendContractStatusNotification(
                            saleContract.getPublicKeyBroker(),
                            saleContract.getPublicKeyCustomer(),
                            hashToSubmit,
                            transmissionId.toString(),
                            ContractTransactionStatus.CHECKING_CLOSING_CONTRACT,
                            Plugins.CLOSE_CONTRACT,
                            PlatformComponentType.ACTOR_CRYPTO_BROKER,
                            PlatformComponentType.ACTOR_CRYPTO_CUSTOMER);

                    System.out.println("CLOSE_CONTRACT - [Broker] checkCloseContractsToSend() - Sending Transaction Status: CHECKING_CLOSING_CONTRACT");

                    break;
            }

            closeContractBusinessTransactionDao.updateContractTransactionStatus(hashToSubmit, ContractTransactionStatus.CHECKING_CLOSING_CONTRACT);

            System.out.println("CLOSE_CONTRACT - checkCloseContractsToSend() - Updated Transaction Status to: CHECKING_CLOSING_CONTRACT");
        }
    }

    private void raiseNewContractClosedEvent() {
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.NEW_CONTRACT_CLOSED);
        NewContractClosed newContractOpened = (NewContractClosed) fermatEvent;
        newContractOpened.setSource(EventSource.BUSINESS_TRANSACTION_CLOSE_CONTRACT);
        eventManager.raiseEvent(newContractOpened);
    }

}
