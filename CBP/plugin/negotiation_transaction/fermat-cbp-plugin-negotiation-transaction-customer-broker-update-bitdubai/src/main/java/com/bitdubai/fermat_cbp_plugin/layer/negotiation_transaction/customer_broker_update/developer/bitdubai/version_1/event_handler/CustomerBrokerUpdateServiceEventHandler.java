package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.event_handler;

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
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yordin Alayn 18.12.15
 */
public class CustomerBrokerUpdateServiceEventHandler implements CBPService {

    /*Represent the Event Manager*/
    private EventManager                                        eventManager;

    /*Represent the Error Manager*/
    private ErrorManager                                        errorManager;

    /*Represent the Plugins Version*/
    private PluginVersionReference                              pluginVersionReference;

    /*Represent the Dao*/
    private CustomerBrokerUpdateNegotiationTransactionDatabaseDao customerBrokerUpdateNegotiationTransactionDatabaseDao;

    private ServiceStatus serviceStatus   = ServiceStatus.CREATED;

    private List<FermatEventListener> listenersAdded  = new ArrayList<>();

    public CustomerBrokerUpdateServiceEventHandler(
        CustomerBrokerUpdateNegotiationTransactionDatabaseDao   customerBrokerUpdateNegotiationTransactionDatabaseDao,
        EventManager                                            eventManager,
        ErrorManager                                            errorManager,
        PluginVersionReference                                  pluginVersionReference
    ){
        this.customerBrokerUpdateNegotiationTransactionDatabaseDao  = customerBrokerUpdateNegotiationTransactionDatabaseDao;
        this.eventManager                                           = eventManager;
        this.errorManager                                           = errorManager;
        this.pluginVersionReference                                 = pluginVersionReference;
    }

    /*SERVICE*/
    public void start() throws CantStartServiceException {

        try{

            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_UPDATE);
            fermatEventHandler = new IncomingNegotiationTransactionEventHandler();
            ((IncomingNegotiationTransactionEventHandler) fermatEventHandler).setCustomerBrokerUpdateService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_UPDATE);
            fermatEventHandler = new IncomingNegotiationTransmissionConfirmEventHandler();
            ((IncomingNegotiationTransmissionConfirmEventHandler) fermatEventHandler).setCustomerBrokerUpdateService(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (CantSetObjectException e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantStartServiceException(e,"Starting the CustomerBrokerUpdateServiceEventHandler", "The CustomerBrokerUpdateServiceEventHandler is probably null");
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

        System.out.print("\n**** 16) MOCK CUSTOMER BROKER UPDATE - EVENT HANDLER - SAVE UPDATE EVENT  " +
                "\n - EventType = "+event.getEventType().getCode()+
                "\n - Source = "+event.getSource().getCode()+
                "****\n");

        this.customerBrokerUpdateNegotiationTransactionDatabaseDao.saveNewEventTansaction(event.getEventType().getCode(), event.getSource().getCode());

    }

    public void incomingNegotiationTransactionConfirmEventHandler(IncomingNegotiationTransmissionConfirmNegotiationEvent event) throws CantSaveEventException {

        this.customerBrokerUpdateNegotiationTransactionDatabaseDao.saveNewEventTansaction(event.getEventType().getCode(), event.getSource().getCode());

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
