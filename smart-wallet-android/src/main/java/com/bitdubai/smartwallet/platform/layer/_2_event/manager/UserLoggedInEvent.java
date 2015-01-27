package com.bitdubai.smartwallet.platform.layer._2_event.manager;

import com.bitdubai.smartwallet.platform.layer._1_definition.event.PlatformEvent;

import java.util.UUID;

/**
 * Created by ciencias on 24.01.15.
 */
public class UserLoggedInEvent implements PlatformEvent {

    private UUID userId;
    private EventType eventType;
    private EventSource eventSource;

    public void setUserId (UUID userId){
        this.userId = userId;
    }

    public UUID getUserId() {
        return this.userId;
    }


    public UserLoggedInEvent (EventType eventType){
        this.eventType = eventType;
    }


    @Override
    public EventType getEventType() {
        return this.eventType;
    }

    @Override
    public void setSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    @Override
    public EventSource getSource() {
        return this.eventSource;
    }
}
