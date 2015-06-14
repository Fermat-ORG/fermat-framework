/*
 * @#IncomingNetworkServiceConnectionRequestEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer._10_communication.CommunicationChannels;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventType;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IncomingNetworkServiceConnectionRequestEvent</code> represent the event
 * when a new Incoming Network Service Connection Request is made
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 07/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IncomingNetworkServiceConnectionRequestEvent implements PlatformEvent {

    /**
     *  Represent the eventType
     */
    private EventType eventType;

    /**
     *  Represent the eventSource
     */
    private EventSource eventSource;

    /**
     *  Represent the remoteNetworkService
     */
    private UUID remoteNetworkService;

    /**
     *  Represent the communicationChannels
     */
    private CommunicationChannels communicationChannels;

    /**
     * Constructor with parameter
     *
     * @param eventType
     */
    public IncomingNetworkServiceConnectionRequestEvent(EventType eventType){
        this.eventType = eventType;
    }

    /**
     * Constructor with parameters
     *
     * @param communicationChannels
     * @param eventSource
     * @param eventType
     * @param remoteNetworkService
     */
    public IncomingNetworkServiceConnectionRequestEvent(CommunicationChannels communicationChannels, EventSource eventSource, EventType eventType, UUID remoteNetworkService) {
        this.communicationChannels = communicationChannels;
        this.eventSource = eventSource;
        this.eventType = eventType;
        this.remoteNetworkService = remoteNetworkService;
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
     * Return the communication channels for the connection
     *
     * @return CommunicationChannels
     */
    public CommunicationChannels getCommunicationChannels() {
        return communicationChannels;
    }

    /**
     * Set the communication channels for the connection
     *
     * @return CommunicationChannels
     */
    public void setCommunicationChannels(CommunicationChannels communicationChannels) {
        this.communicationChannels = communicationChannels;
    }

    /**
     * Get the id of the remote network service
     *
     * @return UUID
     */
    public UUID getRemoteNetworkService() {
        return remoteNetworkService;
    }

    /**
     * Set the id of the remote network service
     *
     * @return UUID
     */
    public void setRemoteNetworkService(UUID remoteNetworkService) {
        this.remoteNetworkService = remoteNetworkService;
    }
}
