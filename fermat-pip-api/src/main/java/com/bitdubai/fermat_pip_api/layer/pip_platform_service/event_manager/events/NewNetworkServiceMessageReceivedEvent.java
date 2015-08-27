/*
 * @#NewNetworkServiceMessageReceivedEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud_server.events.NewNetworkServiceMessageReceivedEvent</code> represent the event
 * when a new Network Service Message is received
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 26/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NewNetworkServiceMessageReceivedEvent implements PlatformEvent {

    /**
     *  Represent the eventType
     */
    private EventType eventType;

    /**
     *  Represent the eventSource
     */
    private EventSource eventSource;

    /**
     *  Represent the data
     */
    private Object data;

    /**
     * Constructor with parameter
     *
     * @param eventType type of the event
     */
    public NewNetworkServiceMessageReceivedEvent(EventType eventType){
        this.eventType = eventType;
    }

    /**
     * Constructor with parameters
     *
     * @param eventSource the event source
     * @param eventType   the event type
     */
    public NewNetworkServiceMessageReceivedEvent(EventSource eventSource, EventType eventType) {

        this.eventSource = eventSource;
        this.eventType   = eventType;
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
     * Return the data object that contains message received
     *
     * @return message received
     */
    public Object getData() {
        return data;
    }

    /**
     * Set data object that contains the message received
     */
    public void setData(Object data) {
        this.data = data;
    }
}
