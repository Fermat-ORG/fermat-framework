package com.bitdubai.smartwallet.platform.layer._2_event.manager.developer;

import com.bitdubai.smartwallet.platform.layer._1_definition.event.PlatformEvent;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.EventSource;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.EventType;

import java.util.UUID;

/**
 * Created by ciencias on 26.01.15.
 */
public class UserCreatedEvent implements PlatformEvent {

    private UUID userId;
    private EventType eventType;
    private EventSource eventSource;

    public void setUserId (UUID userId){
        this.userId = userId;
    }

    public UUID getUserId() {
        return this.userId;
    }


    public UserCreatedEvent (EventType eventType){
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
