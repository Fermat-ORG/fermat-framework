package com.bitdubai.fermat_dmp_plugin.layer.middleware.money_request.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.dmp_middleware.money_request.MoneyRequestManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingMoneyRequestApprovedEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingMoneyRequestRejectedEvent;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import com.bitdubai.fermat_dmp_plugin.layer.middleware.money_request.developer.bitdubai.version_1.event_handlers.IncomingMoneyRequestReceivedEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.money_request.developer.bitdubai.version_1.event_handlers.IncomingMoneyRequestRejectedEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.money_request.developer.bitdubai.version_1.event_handlers.OutgoingMoneyRequestApprovedEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.money_request.developer.bitdubai.version_1.event_handlers.OutgoingMoneyRequestDeliveredEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.money_request.developer.bitdubai.version_1.event_handlers.OutgoingMoneyRequestRejectedEventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 23/02/15.
 */
public class MoneyRequestMiddlewarePluginRoot implements Service,  DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem,Plugin, MoneyRequestManager {

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
     * MoneyRequestMiddlewarePluginRoot methods implementation. 
     */
    public void createFiatMoneyRequest(){

    }
    
    public void events(){
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INCOMING_MONEY_REQUEST_APPROVED);
        ((IncomingMoneyRequestApprovedEvent) platformEvent).setSource(EventSource.MIDDLEWARE_MONEY_REQUEST_PLUGIN);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_1 = eventManager.getNewEvent(EventType.INCOMING_MONEY_REQUEST_REJECTED);
        ((IncomingMoneyRequestRejectedEvent) platformEvent).setSource(EventSource.MIDDLEWARE_MONEY_REQUEST_PLUGIN);
        eventManager.raiseEvent(platformEvent);
        
    }
    
    
    
    /**
     * Service Interface implementation. 
     */
    
    @Override
    public void start() {
        EventListener eventListener;
        EventHandler eventHandler;

        eventListener = eventManager.getNewListener(EventType.INCOMING_MONEY_REQUEST_RECEIVED);
        eventHandler = new IncomingMoneyRequestReceivedEventHandler();
        ((IncomingMoneyRequestReceivedEventHandler) eventHandler).setMoneyRequestManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.INCOMING_MONEY_REQUEST_REJECTED);
        eventHandler = new IncomingMoneyRequestRejectedEventHandler();
        ((IncomingMoneyRequestRejectedEventHandler) eventHandler).setMoneyRequestManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.OUTGOING_MONEY_REQUEST_APPROVED);
        eventHandler = new OutgoingMoneyRequestApprovedEventHandler();
        ((OutgoingMoneyRequestApprovedEventHandler) eventHandler).setMoneyRequestManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.OUTGOING_MONEY_REQUEST_DELIVERED);
        eventHandler = new OutgoingMoneyRequestDeliveredEventHandler();
        ((OutgoingMoneyRequestDeliveredEventHandler) eventHandler).setMoneyRequestManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.OUTGOING_MONEY_REQUEST_REJECTED);
        eventHandler = new OutgoingMoneyRequestRejectedEventHandler();
        ((OutgoingMoneyRequestRejectedEventHandler) eventHandler).setMoneyRequestManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

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
        return  this.serviceStatus;        
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

//    @Override
//    public void exampleMethod() throws ExampleException {

//    }
}
