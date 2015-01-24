package com.bitdubai.smartwallet.platform.layer._2_event;

/**
 * Created by ciencias on 24.01.15.
 */
public class UserLoggedInEventListener implements EventListener {

    private Event eventType;

    public UserLoggedInEventListener (Event eventType){
        this.eventType = eventType;
    }

    @Override
    public Event getEventType() {
        return eventType;
    }

    @Override
    public void setEventHandler() {

    }

    @Override
    public void getEventHandler() {

    }

    @Override
    public void raiseEvent(PlatformEvent platformEvent) {

    }
}
