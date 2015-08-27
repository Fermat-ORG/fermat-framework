package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.listeners;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class GenericEventListener implements EventListener {
    private EventMonitor eventMonitor;
    private EventType eventType;
    private EventHandler eventHandler;

    public GenericEventListener(final EventType eventType, final EventMonitor eventMonitor) {
        this.eventMonitor = eventMonitor;
        this.eventType = eventType;
    }

    @Override
    public EventType getEventType() {
        return this.eventType;
    }

    @Override
    public void setEventHandler(final EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public EventHandler getEventHandler() {
        return this.eventHandler;
    }

    @Override
    public void raiseEvent(final PlatformEvent platformEvent) {
        try{
            this.eventHandler.handleEvent(platformEvent);
        } catch (Exception exception){
            eventMonitor.handleEventException(exception, platformEvent);
        }
    }
}
