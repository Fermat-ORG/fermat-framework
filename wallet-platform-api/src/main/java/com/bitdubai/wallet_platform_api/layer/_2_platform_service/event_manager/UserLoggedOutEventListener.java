package com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager;

import com.bitdubai.wallet_platform_api.layer._1_definition.event.EventMonitor;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;

/**
 * Created by ciencias on 24.01.15.
 */
public class UserLoggedOutEventListener implements EventListener {

    EventMonitor eventMonitor;
    private EventType eventType;
    private EventHandler eventHandler;

    public UserLoggedOutEventListener (EventType eventType, EventMonitor eventMonitor){
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
    public void getEventHandler() {

    }

    @Override
    public void raiseEvent(PlatformEvent platformEvent) {

        try
        {
            this.eventHandler.raiseEvent(platformEvent);
        }
        catch (Exception exception)
        {
            eventMonitor.handleEventException(exception, platformEvent);
        }

    }
}
