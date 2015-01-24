package com.bitdubai.smartwallet.platform.layer._2_event;

import java.util.UUID;

/**
 * Created by ciencias on 24.01.15.
 */
public class UserLoggedOutEvent implements PlatformEvent {

    private UUID userId;
    private Event eventType;
    private EventSource eventSource;

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UserLoggedOutEvent(Event eventType) {
        this.eventType = eventType;
    }


    @Override
    public Event getEventType() {
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