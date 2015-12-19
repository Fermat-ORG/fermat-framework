package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.events.CBPService;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.events.IncomingNegotiationTransactionEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.events.IncomingNegotiationTransmissionConfirmNegotiationEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.events.IncomingNegotiationTransmissionConfirmResponseEvent;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Yordin Alayn 18.12.15
 */
public class CustomerBrokerUpdateServiceEventHandler implements CBPService {

    private EventManager eventManager;

    private CustomerBrokerUpdateNegotiationTransactionDatabaseDao customerBrokerUpdateNegotiationTransactionDatabaseDao;

    private ServiceStatus serviceStatus   = ServiceStatus.CREATED;

    private List<FermatEventListener> listenersAdded  = new ArrayList<>();

    public CustomerBrokerUpdateServiceEventHandler(
        CustomerBrokerUpdateNegotiationTransactionDatabaseDao customerBrokerUpdateNegotiationTransactionDatabaseDao,
        EventManager eventManager
    ){
        this.customerBrokerUpdateNegotiationTransactionDatabaseDao = customerBrokerUpdateNegotiationTransactionDatabaseDao;
        this.eventManager = eventManager;
    }

    /*SERVICE*/
    public void start() throws CantStartServiceException {

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
        this.customerBrokerUpdateNegotiationTransactionDatabaseDao.saveNewEventTansaction(event.getEventType().getCode(), event.getSource().getCode());
        LOG.info("CHECK THE DATABASE");
    }

    public void incomingNegotiationTransactionConfirmEventHandler(IncomingNegotiationTransmissionConfirmNegotiationEvent event) throws CantSaveEventException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.customerBrokerUpdateNegotiationTransactionDatabaseDao.saveNewEventTansaction(event.getEventType().getCode(), event.getSource().getCode());
        //LOG.info("CHECK THE DATABASE");
    }

    public void incomingNegotiationTransactionConfirmResponseEventHandler(IncomingNegotiationTransmissionConfirmResponseEvent event) throws CantSaveEventException {
        Logger LOG = Logger.getGlobal();
        LOG.info("EVENT TEST, I GOT AN EVENT:\n"+event);
        this.customerBrokerUpdateNegotiationTransactionDatabaseDao.saveNewEventTansaction(event.getEventType().getCode(), event.getSource().getCode());
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
