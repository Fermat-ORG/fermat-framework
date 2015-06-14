package com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventType;

/**
 * Created by loui on 20/02/15.
 */
public class IncomingCryptoReversedFromDeviceUserEvent implements PlatformEvent {
    private EventType eventType;
    private EventSource eventSource;

    public IncomingCryptoReversedFromDeviceUserEvent(EventType eventType){
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
