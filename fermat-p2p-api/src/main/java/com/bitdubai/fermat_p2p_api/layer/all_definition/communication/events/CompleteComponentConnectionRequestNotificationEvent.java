/*
 * @#CompleteComponentConnectionRequestNotificationEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.EventType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.PlatformComponentType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 20/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteComponentConnectionRequestNotificationEvent extends AbstractP2PFermatEvent {


    /**
     * Represent the remoteComponent
     */
    private PlatformComponentProfile remoteComponent;


    /**
     * Constructor with parameter
     * @param eventType
     */
    public CompleteComponentConnectionRequestNotificationEvent(EventType eventType) {
        super(eventType);
    }

    /**
     * Get the RemoteComponent
     * @return PlatformComponentProfile
     */
    public PlatformComponentProfile getRemoteComponent() {
        return remoteComponent;
    }

    /**
     * Set the RemoteComponent
     * @param remoteComponent
     */
    public void setRemoteComponent(PlatformComponentProfile remoteComponent) {
        this.remoteComponent = remoteComponent;
    }
}
