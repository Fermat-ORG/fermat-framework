/*
* @#NetworkClientConnectionSuccessEvent.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientConnectionSuccessEvent</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 13/05/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkClientConnectionSuccessEvent extends AbstractEvent<P2pEventType> {

    /*
     * Represent the uriToNode
     */
    private String uriToNode;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public NetworkClientConnectionSuccessEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    public String getUriToNode() {
        return uriToNode;
    }

    public void setUriToNode(String uriToNode) {
        this.uriToNode = uriToNode;
    }
}
