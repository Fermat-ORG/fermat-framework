/*
 * @#IncomingNetworkServiceConnectionRequestEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.events;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;


/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud_server.events.IncomingNetworkServiceConnectionRequestEvent</code> represent the event
 * when a new Incoming Network Service Connection Request is made
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 07/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IncomingNetworkServiceConnectionRequestEvent implements FermatEvent {

    /**
     *  Represent the eventType
     */
    private EventType eventType;

    /**
     *  Represent the eventSource
     */
    private EventSource eventSource;

    /**
     *  Represent the remoteNetworkServicePublicKey
     */
    private String remoteNetworkServicePublicKey;

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
     * @param communicationChannels the communication channels
     * @param eventSource the event source
     * @param eventType   the event type
     * @param remoteNetworkServicePublicKey the remote network service public key
     */
    public IncomingNetworkServiceConnectionRequestEvent(CommunicationChannels communicationChannels, EventSource eventSource, EventType eventType, String remoteNetworkServicePublicKey) {
        this.communicationChannels = communicationChannels;
        this.eventSource = eventSource;
        this.eventType = eventType;
        this.remoteNetworkServicePublicKey = remoteNetworkServicePublicKey;
    }

    /**
     * (non-Javadoc)
     *
     * @see FermatEvent#getEventType()
     */
    @Override
    public EventType getEventType() {
        return this.eventType;
    }

    /**
     * (non-Javadoc)
     *
     * @see FermatEvent#setSource(EventSource)
     */
    @Override
    public void setSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    /**
     * (non-Javadoc)
     *
     * @see FermatEvent#getSource()
     */
    @Override
    public EventSource getSource() {
        return this.eventSource;
    }

    /**
     * Return the communication channels for the connection
     *
     * @return CommunicationChannels the communication chanel
     */
    public CommunicationChannels getCommunicationChannels() {
        return communicationChannels;
    }

    /**
     * Set the communication channels for the connection
     *
     * @return CommunicationChannels the communication channel
     */
    public void setCommunicationChannels(CommunicationChannels communicationChannels) {
        this.communicationChannels = communicationChannels;
    }

    /**
     * Return the public key of the remote network service
     * @return String the public key
     */
    public String getRemoteNetworkServicePublicKey() {
        return remoteNetworkServicePublicKey;
    }
}
