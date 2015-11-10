/*
 * @#ClientConnectionCloseNotificationEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionCloseNotificationEvent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientConnectionCloseNotificationEvent extends AbstractP2PFermatEvent {

    /**
     * Constructor with parameter
     * @param p2pEventType
     */
    public ClientConnectionCloseNotificationEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }
}
