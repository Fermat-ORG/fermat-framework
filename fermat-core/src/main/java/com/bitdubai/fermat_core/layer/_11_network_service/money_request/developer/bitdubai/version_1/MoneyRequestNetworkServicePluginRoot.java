package com.bitdubai.fermat_core.layer._11_network_service.money_request.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._11_network_service.NetworkService;
import com.bitdubai.fermat_api.layer._11_network_service.money_request.MoneyRequestManager;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingMoneyRequestReceivedEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.OutgoingMoneyRequestApprovedEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.OutgoingMoneyRequestDeliveredEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.OutgoingMoneyRequestRejectedEvent;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 23/02/15.
 */
public class MoneyRequestNetworkServicePluginRoot implements Service, NetworkService, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin, MoneyRequestManager {


    /**
     * Service Interface menber variables. 
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * PluginFileSystem interface member variables. 
     */

    PluginFileSystem pluginFileSystem;


    /**
     * DealsWithEvents Interface menber variables.
     */

    EventManager eventManager;

    /**
     * Plugin interface menber varuiables. 
     */
    UUID pluginId;


    /**
     *  MoneyRequestNetworkServicePluginRoot methods implementation.
     */
    
    public void sendMoneyRequest(){
        
        
    }
    
    
    public void events(){
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INCOMING_MONEY_REQUEST_RECEIVED);
        ((IncomingMoneyRequestReceivedEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_MONEY_REQUEST_PLUGIN);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_1 = eventManager.getNewEvent(EventType.OUTGOING_MONEY_REQUEST_DELIVERED);
        ((OutgoingMoneyRequestDeliveredEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_MONEY_REQUEST_PLUGIN);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_2 = eventManager.getNewEvent(EventType.OUTGOING_MONEY_REQUEST_APPROVED);
        ((OutgoingMoneyRequestApprovedEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_MONEY_REQUEST_PLUGIN);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_3 = eventManager.getNewEvent(EventType.OUTGOING_MONEY_REQUEST_REJECTED);
        ((OutgoingMoneyRequestRejectedEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_MONEY_REQUEST_PLUGIN);
        eventManager.raiseEvent(platformEvent);
        
    }

    /**
     * Service Interface implementation. 
     */
    
    @Override
    public void start() {
        EventListener eventListener;
        EventHandler eventHandler;
        
        
        this.serviceStatus = ServiceStatus.STARTED;
        
    }
    
    @Override
    public void pause(){
        
        this.serviceStatus = ServiceStatus.PAUSED;
        
    }
    
    @Override
    public void resume() {
        
        this.serviceStatus = ServiceStatus.STARTED;
        
    }
    
    @Override
    public void stop(){

        /**
         * I will remove the evnt listeners registered with the event manager. 
         */
        
        for (EventListener eventListener : listenersAdded){
            eventManager.removeListener(eventListener);
        }
        
        listenersAdded.clear();
        
        this.serviceStatus = ServiceStatus.STOPPED;
    }
    
    @Override
    public ServiceStatus getStatus(){
        return this.serviceStatus;
    }

    /**
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem){
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager){
        this.eventManager = eventManager;
    }

    /**
     * DealsWithErrors interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager){

    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId){
        this.pluginId = pluginId;
    }

    @Override
    public UUID getId() {
        return null;
    }
}
