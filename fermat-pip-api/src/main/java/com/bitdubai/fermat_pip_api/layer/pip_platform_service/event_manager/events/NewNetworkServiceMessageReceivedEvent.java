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
public class NewNetworkServiceMessageReceivedEvent extends AbstractPlatformEvent {

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
        super(eventType);
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
