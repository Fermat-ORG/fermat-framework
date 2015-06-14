/*
 * @#IntraUserLoggedInEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventType;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingNetworkServiceConnectionRequestEvent</code> represent the event
 * when a Intra User Logged in
 * <p/>
 *
 * Created by loui on 22/02/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 07/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraUserLoggedInEvent implements PlatformEvent {

    /**
     * Represent the eventType
     */
    private EventType eventType;

    /**
     * Represent the eventSource
     */
    private EventSource eventSource;

    /**
     * Represent the intraUserId
     */
    private UUID intraUserId;

    /**
     * Constructor with parameter
     *
     * @param eventType
     */
    public IntraUserLoggedInEvent(EventType eventType){
        this.eventType = eventType;
    }

    /**
     * Constructor with parameters
     *
     * @param eventSource
     * @param eventType
     * @param intraUserId
     */
    public IntraUserLoggedInEvent(EventSource eventSource, EventType eventType, UUID intraUserId) {
        this.eventSource = eventSource;
        this.eventType = eventType;
        this.intraUserId = intraUserId;
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformEvent#getEventType()
     */
    @Override
    public EventType getEventType() {
        return this.eventType;
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformEvent#setSource(EventSource)
     */
    @Override
    public void setSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformEvent#getSource()
     */
    @Override
    public EventSource getSource() {
        return this.eventSource;
    }

    /**
     * Get the id of the intra user logged in
     *
     * @return UUID
     */
    public UUID getIntraUserId() {
        return intraUserId;
    }

    /**
     * Set the id of the intra user logged in
     *
     * @return UUID
     */
    public void setIntraUserId(UUID intraUserId) {
        this.intraUserId = intraUserId;
    }
}
