package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util;

import java.util.UUID;

/**
 * The class <code>EventWrapper</code>
 * let us manipulate an event information.
 */
public class EventWrapper {

    private final UUID   eventId;
    private final String eventType;
    private final String eventSource;
    private final String eventStatus;
    private final long   eventTimeStamp;

    public EventWrapper(UUID eventId, String eventType, String eventSource, String eventStatus, long eventTimeStamp) {
        this.eventId        = eventId;
        this.eventType      = eventType;
        this.eventSource    = eventSource;
        this.eventStatus    = eventStatus;
        this.eventTimeStamp = eventTimeStamp;
    }

    public UUID getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventSource() {
        return eventSource;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public long getEventTimeStamp() {
        return eventTimeStamp;
    }
}
