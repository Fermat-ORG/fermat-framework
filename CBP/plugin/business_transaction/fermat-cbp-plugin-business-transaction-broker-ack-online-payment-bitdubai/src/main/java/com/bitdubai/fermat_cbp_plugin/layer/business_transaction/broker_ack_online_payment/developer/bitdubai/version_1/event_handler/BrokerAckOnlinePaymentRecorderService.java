package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.events.CBPService;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.events.NewContractOpened;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.events.IncomingConfirmBusinessTransactionResponse;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.events.IncomingNewContractStatusUpdate;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/12/15.
 */
public class BrokerAckOnlinePaymentRecorderService implements CBPService {
    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;
    private List<FermatEventListener> listenersAdded = new ArrayList<>();
    BrokerAckOnlinePaymentBusinessTransactionDao brokerAckOnlinePaymentBusinessTransactionDao;
    /**
     * TransactionService Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public BrokerAckOnlinePaymentRecorderService(
            BrokerAckOnlinePaymentBusinessTransactionDao brokerAckOnlinePaymentBusinessTransactionDao,
            EventManager eventManager) throws CantStartServiceException {
        try {
            setDatabaseDao(brokerAckOnlinePaymentBusinessTransactionDao);
            setEventManager(eventManager);
        } catch (CantSetObjectException exception) {
            throw new CantStartServiceException(exception,
                    "Cannot set the broker ack online payment database handler",
                    "The database handler is null");
        }
    }

    private void setDatabaseDao(BrokerAckOnlinePaymentBusinessTransactionDao brokerAckOnlinePaymentBusinessTransactionDao)
            throws CantSetObjectException {
        if(brokerAckOnlinePaymentBusinessTransactionDao==null){
            throw new CantSetObjectException("The BrokerAckOnlinePaymentBusinessTransactionDao is null");
        }
        this.brokerAckOnlinePaymentBusinessTransactionDao =brokerAckOnlinePaymentBusinessTransactionDao;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void incomingNewContractStatusUpdateEventHandler(IncomingNewContractStatusUpdate event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.brokerAckOnlinePaymentBusinessTransactionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingConfirmBusinessTransactionResponseEventHandler(IncomingConfirmBusinessTransactionResponse event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.brokerAckOnlinePaymentBusinessTransactionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingMoneyNotification(IncomingMoneyNotificationEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);

        this.brokerAckOnlinePaymentBusinessTransactionDao.saveIncomingMoneyEvent(event);
        //LOG.info("CHECK THE DATABASE");
    }

    public void newContractOpenedEvenHandler(NewContractOpened event)throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.brokerAckOnlinePaymentBusinessTransactionDao.saveNewEvent(
                event.getEventType().getCode(),
                event.getSource().getCode(),
                event.getContractHash());
        //LOG.info("CHECK THE DATABASE");
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
            ((IncomingNewContractStatusUpdateEventHandler) fermatEventHandler).setBrokerAckOnlinePaymentRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE);
            fermatEventHandler = new IncomingConfirmBusinessTransactionResponseEventHandler();
            ((IncomingConfirmBusinessTransactionResponseEventHandler) fermatEventHandler).setBrokerAckOnlinePaymentRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.NEW_CONTRACT_OPENED);
            fermatEventHandler = new NewContractOpenedEventHandler();
            ((NewContractOpenedEventHandler) fermatEventHandler).setBrokerAckOnlinePaymentRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(
                    com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType.INCOMING_MONEY_NOTIFICATION);
            fermatEventHandler = new IncomingMoneyNotificationEventHandler();
            ((IncomingMoneyNotificationEventHandler) fermatEventHandler).setBrokerAckOnlinePaymentRecorderService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantSetObjectException exception){
            throw new CantStartServiceException(
                    exception,
                    "Starting the BrokerAckOnlinePaymentRecorderService",
                    "The BrokerAckOnlinePaymentRecorderService is probably null");
        }

    }

    @Override
    public void stop() {
        removeRegisteredListeners();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    private void removeRegisteredListeners(){
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

