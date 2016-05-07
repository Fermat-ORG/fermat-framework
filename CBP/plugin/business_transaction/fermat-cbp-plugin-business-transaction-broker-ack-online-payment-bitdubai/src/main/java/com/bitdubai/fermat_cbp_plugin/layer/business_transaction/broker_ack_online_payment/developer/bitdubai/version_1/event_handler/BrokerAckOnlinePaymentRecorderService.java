package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.events.NewContractOpened;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingConfirmBusinessTransactionResponse;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingNewContractStatusUpdate;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
    private ErrorManager errorManager;
    /**
     * TransactionService Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public BrokerAckOnlinePaymentRecorderService(
            BrokerAckOnlinePaymentBusinessTransactionDao brokerAckOnlinePaymentBusinessTransactionDao,
            EventManager eventManager,
            ErrorManager errorManager) throws CantStartServiceException {
        try {
            this.errorManager = errorManager;
            setDatabaseDao(brokerAckOnlinePaymentBusinessTransactionDao);
            setEventManager(eventManager);
        } catch (CantSetObjectException exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartServiceException(exception,
                    "Cannot set the broker ack online payment database handler",
                    "The database handler is null");
        }catch (Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartServiceException(CantStartServiceException.DEFAULT_MESSAGE, FermatException.wrapException(exception),
                    "Cannot set the broker ack online payment database handler",
                    "Unexpected error");
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
        try{
            //Logger LOG = Logger.getGlobal();
            //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
            if(event.getRemoteBusinessTransaction().getCode().equals(Plugins.BROKER_ACK_ONLINE_PAYMENT.getCode())) {
                this.brokerAckOnlinePaymentBusinessTransactionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
                //LOG.info("CHECK THE DATABASE");
            }
        }catch (CantSaveEventException exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE,exception,"incoming new Contract Status Update Event Handler CantSaveException","");
        }catch(Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE,exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    public void incomingConfirmBusinessTransactionResponseEventHandler(IncomingConfirmBusinessTransactionResponse event) throws CantSaveEventException {
        try{
            //Logger LOG = Logger.getGlobal();
            //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
            if(event.getRemoteBusinessTransaction().getCode().equals(Plugins.BROKER_ACK_ONLINE_PAYMENT.getCode())) {
                this.brokerAckOnlinePaymentBusinessTransactionDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
                //LOG.info("CHECK THE DATABASE");
            }
        }catch(CantSaveEventException exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE,exception,"incoming Confirm Business Transaction Response CantSaveException","");
        }catch (Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE,exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    public void incomingMoneyNotification(IncomingMoneyNotificationEvent event) throws CantSaveEventException {
        try{
            Logger LOG = Logger.getGlobal();
            LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
            if(event.getActorType().getCode().equals(Actors.CBP_CRYPTO_BROKER.getCode())){
                this.brokerAckOnlinePaymentBusinessTransactionDao.saveIncomingMoneyEvent(event);
            }

            LOG.info("CHECK THE DATABASE");
        }catch(CantSaveEventException exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE,exception,"incoming Money Notification CantSaveException","");
        }catch (Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE,exception,
                    "Unexpected error",
                    "Check the cause");
        }
    }

    public void newContractOpenedEvenHandler(NewContractOpened event)throws CantSaveEventException {
        try{
            //Logger LOG = Logger.getGlobal();
            //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
            this.brokerAckOnlinePaymentBusinessTransactionDao.saveNewEvent(
                    event.getEventType().getCode(),
                    event.getSource().getCode(),
                    event.getContractHash());
            //LOG.info("CHECK THE DATABASE");
        }catch(CantSaveEventException exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE,exception,"new Contract Opened Event Handler CantSaveException","");
        }catch (Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE,exception,
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
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartServiceException(
                    exception,
                    "Starting the BrokerAckOnlinePaymentRecorderService",
                    "The BrokerAckOnlinePaymentRecorderService is probably null");
        }catch(Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartServiceException(CantStartServiceException.DEFAULT_MESSAGE,exception,
                    "Starting the BrokerAckOnlinePaymentRecorderService",
                    "Unexpected error");
        }

    }

    @Override
    public void stop() {
        try{
            removeRegisteredListeners();
            this.serviceStatus = ServiceStatus.STOPPED;
        }catch (Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
        }
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

