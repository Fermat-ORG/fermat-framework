package com.bitdubai.smartwallet.platform.layer._2_event;

/**
 * Created by ciencias on 24.01.15.
 */
public class UserLoggedOutEventListener implements EventListener {

    private Event eventType;
    private EventHandler eventHandler;

    public UserLoggedOutEventListener (Event eventType){
        this.eventType = eventType;
    }

    @Override
    public Event getEventType() {
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
        this.eventHandler.raiseEvent(platformEvent);
    }
}
