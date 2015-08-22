package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.listeners;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by ciencias on 24.01.15.
 */
public class OutgoingMoneyRequestDeliveredEventListener implements EventListener {

    EventMonitor eventMonitor;
    private EventType eventType;
    private EventHandler eventHandler;

    public OutgoingMoneyRequestDeliveredEventListener(EventType eventType, EventMonitor eventMonitor){
        this.eventType = eventType;
        this.eventMonitor = eventMonitor;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }

    @Override
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public EventHandler getEventHandler() {
        return this.eventHandler;
    }

    @Override
    public void raiseEvent(PlatformEvent platformEvent) {

        try
        {
            this.eventHandler.handleEvent(platformEvent);
        }
        catch (Exception exception)
        {
            eventMonitor.handleEventException(exception, platformEvent);
        }

    }
}
