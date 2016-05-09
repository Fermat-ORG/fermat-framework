package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.events.CBPService;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events.IncomingNegotiationTransactionEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events.IncomingNegotiationTransmissionConfirmNegotiationEvent;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yordin Alayn on 22.12.15.
 */
public class CustomerBrokerCloseServiceEventHandler implements CBPService {

    /*Represent the Event Manager*/
    private EventManager                                        eventManager;

    /*Represent the Error Manager*/
    private ErrorManager                                        errorManager;

    /*Represent the Plugins Version*/
    private PluginVersionReference                              pluginVersionReference;

    /*Represent the Dao*/
    private CustomerBrokerCloseNegotiationTransactionDatabaseDao customerBrokerCloseNegotiationTransactionDatabaseDao;

    private ServiceStatus serviceStatus   = ServiceStatus.CREATED;

    private List<FermatEventListener> listenersAdded  = new ArrayList<>();

    public CustomerBrokerCloseServiceEventHandler(
            CustomerBrokerCloseNegotiationTransactionDatabaseDao    customerBrokerCloseNegotiationTransactionDatabaseDao,
            EventManager                                            eventManager,
            ErrorManager                                            errorManager,
            PluginVersionReference                                  pluginVersionReference
    ){
        this.customerBrokerCloseNegotiationTransactionDatabaseDao   = customerBrokerCloseNegotiationTransactionDatabaseDao;
        this.eventManager                                           = eventManager;
        this.errorManager                                           = errorManager;
        this.pluginVersionReference                                 = pluginVersionReference;
    }

    /*SERVICE*/
    public void start() throws CantStartServiceException {
        try {
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */
            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_CLOSE);
            fermatEventHandler = new IncomingNegotiationTransactionEventHandler();
            ((IncomingNegotiationTransactionEventHandler) fermatEventHandler).setCustomerBrokerNewService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_CLOSE);
            fermatEventHandler = new IncomingNegotiationTransmissionConfirmEventHandler();
            ((IncomingNegotiationTransmissionConfirmEventHandler) fermatEventHandler).setCustomerBrokerNewService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (CantSetObjectException e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantStartServiceException(e,"Starting the CustomerBrokerNewServiceEventHandler", "The CustomerBrokerNewServiceEventHandler is probably null");
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

        this.customerBrokerCloseNegotiationTransactionDatabaseDao.saveNewEventTansaction(event.getEventType().getCode(), event.getSource().getCode());

    }

    public void incomingNegotiationTransactionConfirmEventHandler(IncomingNegotiationTransmissionConfirmNegotiationEvent event) throws CantSaveEventException {

        this.customerBrokerCloseNegotiationTransactionDatabaseDao.saveNewEventTansaction(event.getEventType().getCode(), event.getSource().getCode());

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
