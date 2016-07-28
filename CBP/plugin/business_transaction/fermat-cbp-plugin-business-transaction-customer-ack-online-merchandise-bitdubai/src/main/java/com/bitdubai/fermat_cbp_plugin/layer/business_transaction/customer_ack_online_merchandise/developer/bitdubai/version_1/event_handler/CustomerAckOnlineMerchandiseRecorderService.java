package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.events.CBPService;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.events.BrokerAckPaymentConfirmed;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingConfirmBusinessTransactionResponse;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingNewContractStatusUpdate;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.CustomerAckOnlineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/12/15.
 */
public class CustomerAckOnlineMerchandiseRecorderService implements CBPService {
    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();
    CustomerAckOnlineMerchandiseBusinessTransactionDao customerAckOnlineMerchandiseBusinessTransactionDao;
    private CustomerAckOnlineMerchandisePluginRoot pluginRoot;

    /**
     * TransactionService Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public CustomerAckOnlineMerchandiseRecorderService(
            CustomerAckOnlineMerchandiseBusinessTransactionDao customerAckOnlineMerchandiseBusinessTransactionDao,
            EventManager eventManager,
            CustomerAckOnlineMerchandisePluginRoot pluginRoot) throws CantStartServiceException {
        try {
            this.pluginRoot = pluginRoot;
            setDatabaseDao(customerAckOnlineMerchandiseBusinessTransactionDao);
            setEventManager(eventManager);
        } catch (CantSetObjectException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartServiceException(exception,
                    "Cannot set the customer ack offline merchandise database handler",
                    "The database handler is null");
        } catch (Exception exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartServiceException(CantStartServiceException.DEFAULT_MESSAGE, FermatException.wrapException(exception),
                    "Cannot set the customer ack offline merchandise database handler",
                    "Unexpected error");
        }
    }

    private void setDatabaseDao(CustomerAckOnlineMerchandiseBusinessTransactionDao customerAckOnlineMerchandiseBusinessTransactionDao)
            throws CantSetObjectException {
        if (customerAckOnlineMerchandiseBusinessTransactionDao == null) {
            throw new CantSetObjectException("The CustomerAckOnlineMerchandiseBusinessTransactionDao is null");
        }
        this.customerAckOnlineMerchandiseBusinessTransactionDao = customerAckOnlineMerchandiseBusinessTransactionDao;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void incomingNewContractStatusUpdateEventHandler(IncomingNewContractStatusUpdate event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        if (event.getRemoteBusinessTransaction().getCode().equals(Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE.getCode())) {
            this.customerAckOnlineMerchandiseBusinessTransactionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
            //LOG.info("CHECK THE DATABASE");
        }
    }

    public void incomingConfirmBusinessTransactionResponseEventHandler(IncomingConfirmBusinessTransactionResponse event) throws CantSaveEventException {
        try {
            //Logger LOG = Logger.getGlobal();
            //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
            if (event.getRemoteBusinessTransaction().getCode().equals(Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE.getCode())) {
                this.customerAckOnlineMerchandiseBusinessTransactionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
                //LOG.info("CHECK THE DATABASE");
            }
        } catch (CantSaveEventException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, exception, "incoming Confirm Business Transaction Response EventHandler CantSaveException", "");
        } catch (Exception exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    public void incomingMoneyNotification(IncomingMoneyNotificationEvent event) throws CantSaveEventException {
        try {
            //Logger LOG = Logger.getGlobal();
            //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
            if (event.getActorType().getCode().equals(Actors.CBP_CRYPTO_CUSTOMER.getCode())) {

                this.customerAckOnlineMerchandiseBusinessTransactionDao.saveIncomingMoneyEvent(event);
            }
            //LOG.info("CHECK THE DATABASE");
        } catch (CantSaveEventException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, exception, "incoming Money Notification CantSaveException", "");
        } catch (Exception exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    public void brokerAckPaymentConfirmedEventHandler(BrokerAckPaymentConfirmed event) throws CantSaveEventException {
        try {
            //Logger LOG = Logger.getGlobal();
            //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
            this.customerAckOnlineMerchandiseBusinessTransactionDao.saveNewEvent(
                    event.getEventType().getCode(),
                    event.getSource().getCode(),
                    event.getContractHash());
            //LOG.info("CHECK THE DATABASE");
        } catch (CantSaveEventException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE,
                    exception, "broker Ack Payment Confirmed EventHandler CantSaveException", "");
        } catch (Exception exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    @Override
    public void start() throws CantStartServiceException {
        try {
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */
            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE);
            fermatEventHandler = new IncomingNewContractStatusUpdateEventHandler();
            ((IncomingNewContractStatusUpdateEventHandler) fermatEventHandler).setCustomerAckOnlineMerchandiseRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE);
            fermatEventHandler = new IncomingConfirmBusinessTransactionResponseEventHandler();
            ((IncomingConfirmBusinessTransactionResponseEventHandler) fermatEventHandler).setCustomerAckOnlineMerchandiseRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            /*fermatEventListener = eventManager.getNewListener(EventType.BROKER_SUBMIT_MERCHANDISE_CONFIRMED);
            fermatEventHandler = new BrokerAckPaymentConfirmedEventHandler();
            ((BrokerAckPaymentConfirmedEventHandler) fermatEventHandler).setCustomerAckOnlineMerchandiseRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);*/

            fermatEventListener = eventManager.getNewListener(
                    com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType.INCOMING_MONEY_NOTIFICATION);
            fermatEventHandler = new IncomingMoneyNotificationEventHandler();
            ((IncomingMoneyNotificationEventHandler) fermatEventHandler).setCustomerAckOnlineMerchandiseRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.BROKER_ACK_PAYMENT_CONFIRMED);
            fermatEventHandler = new BrokerAckPaymentConfirmedEventHandler();
            ((BrokerAckPaymentConfirmedEventHandler) fermatEventHandler).setCustomerAckOnlineMerchandiseRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantSetObjectException exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartServiceException(
                    exception,
                    "Starting the CustomerAckOnlineMerchandiseRecorderService",
                    "The CustomerAckOnlineMerchandiseRecorderService is probably null");
        } catch (Exception exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartServiceException(CantStartServiceException.DEFAULT_MESSAGE, exception,
                    "Starting the CustomerAckOnlineMerchandiseRecorderService",
                    "Unexpected error");
        }

    }

    @Override
    public void stop() {
        try {
            removeRegisteredListeners();
            this.serviceStatus = ServiceStatus.STOPPED;
        } catch (Exception exception) {
            this.pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
        }
    }

    private void removeRegisteredListeners() {
        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }
        listenersAdded.clear();
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }
}

