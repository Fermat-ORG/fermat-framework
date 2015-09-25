/*
 * @#CompleteComponentConnectionRequestNotificationEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.EventType;
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
     * Represent the platformComponentType
     */
    private PlatformComponentType platformComponentType;

    /**
     * Represent the networkServiceType
     */
    private NetworkServiceType networkServiceType;

    /**
     * Represent the remoteIdentity
     */
    private String remoteIdentity;


    /**
     * Constructor with parameter
     * @param eventType
     */
    public CompleteComponentConnectionRequestNotificationEvent(EventType eventType) {
        super(eventType);
    }

    /**
     * Get the PlatformComponentType
     * @return PlatformComponentType
     */
    public PlatformComponentType getPlatformComponentType() {
        return platformComponentType;
    }

    /**
     * Set the PlatformComponentType
     * @param platformComponentType
     */
    public void setPlatformComponentType(PlatformComponentType platformComponentType) {
        this.platformComponentType = platformComponentType;
    }

    /**
     * Get the NetworkServiceType
     * @return NetworkServiceType
     */
    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    /**
     * Set the NetworkServiceType
     * @param networkServiceType
     */
    public void setNetworkServiceType(NetworkServiceType networkServiceType) {
        this.networkServiceType = networkServiceType;
    }

    /**
     * Get the RemoteIdentity
     * @return String
     */
    public String getRemoteIdentity() {
        return remoteIdentity;
    }

    /**
     * Set the RemoteIdentity
     * @param remoteIdentity
     */
    public void setRemoteIdentity(String remoteIdentity) {
        this.remoteIdentity = remoteIdentity;
    }
}
