package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.events.CBPService;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.events.IncomingNegotiationTransactionEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.events.IncomingNegotiationTransmissionConfirmNegotiationEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.events.IncomingNegotiationTransmissionConfirmResponseEvent;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Yordin Alayn on 10.12.15.
 * Based On OpenContractRecorderService Create by Manuel Perez.
 */
public class CustomerBrokerNewServiceEventHandler implements CBPService {

    private EventManager                                        eventManager;

    private CustomerBrokerNewNegotiationTransactionDatabaseDao customerBrokerNewNegotiationTransactionDatabaseDao;

    private ServiceStatus               serviceStatus   = ServiceStatus.CREATED;

    private List<FermatEventListener>   listenersAdded  = new ArrayList<>();

    public CustomerBrokerNewServiceEventHandler(
        CustomerBrokerNewNegotiationTransactionDatabaseDao customerBrokerNewNegotiationTransactionDatabaseDao,
        EventManager eventManager
    ){
        this.customerBrokerNewNegotiationTransactionDatabaseDao = customerBrokerNewNegotiationTransactionDatabaseDao;
        this.eventManager                                       = eventManager;
    }

    /*SERVICE*/
    public void start() throws CantStartServiceException {
        try {
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */
            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION);
            fermatEventHandler = new IncomingNegotiationTransactionEventHandler();
            ((IncomingNegotiationTransactionEventHandler) fermatEventHandler).setCustomerBrokerNewService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM);
            fermatEventHandler = new IncomingNegotiationTransmissionConfirmEventHandler();
            ((IncomingNegotiationTransmissionConfirmEventHandler) fermatEventHandler).setCustomerBrokerNewService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_RESPONSE);
            fermatEventHandler = new IncomingNegotiationTransmissionConfirmResponseEventHandler();
            ((IncomingNegotiationTransmissionConfirmResponseEventHandler) fermatEventHandler).setCustomerBrokerNewService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantSetObjectException exception){
            throw new CantStartServiceException(exception,"Starting the AssetDistributionRecorderService", "The AssetDistributionRecorderService is probably null");
        }
    }

    @Override
    public void stop() {
        removeRegisteredListeners();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }
    /*END SERVICE*/

    /*PUBLIC METHOD*/
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void incomingNegotiationTransactionEventHandler(IncomingNegotiationTransactionEvent event) throws CantSaveEventException {
        Logger LOG = Logger.getGlobal();
        LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.customerBrokerNewNegotiationTransactionDatabaseDao.saveNewEventTansaction(event.getEventType().getCode(), event.getSource().getCode());
        LOG.info("CHECK THE DATABASE");
    }

    public void incomingNegotiationTransactionConfirmEventHandler(IncomingNegotiationTransmissionConfirmNegotiationEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.customerBrokerNewNegotiationTransactionDatabaseDao.saveNewEventTansaction(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingNegotiationTransactionConfirmResponseEventHandler(IncomingNegotiationTransmissionConfirmResponseEvent event) throws CantSaveEventException {
        Logger LOG = Logger.getGlobal();
        LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.customerBrokerNewNegotiationTransactionDatabaseDao.saveNewEventTansaction(event.getEventType().getCode(), event.getSource().getCode());
        LOG.info("CHECK THE DATABASE");
    }

    /*END PUBLIC METHOD*/

    /*PRIVATE METHOD*/
    private void removeRegisteredListeners(){
        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }
        listenersAdded.clear();
    }
    /*END PRIVATE METHOD*/

}
