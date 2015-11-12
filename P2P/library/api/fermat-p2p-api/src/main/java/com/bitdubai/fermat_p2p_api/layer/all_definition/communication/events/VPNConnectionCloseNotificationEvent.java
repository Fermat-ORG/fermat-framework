/*
 * @#VPNConnectionCloseNotificationEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class VPNConnectionCloseNotificationEvent extends AbstractP2PFermatEvent {

    /**
     * Represent the remoteComponent
     */
    private NetworkServiceType networkServiceApplicant;

    /**
     * Represent the remoteParticipant
     */
    private PlatformComponentProfile remoteParticipant;

    /**
     * Constructor with parameter
     * @param p2pEventType
     */
    public VPNConnectionCloseNotificationEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    /**
     * Get the NetworkServiceApplicant
     *
     * @return NetworkServiceType
     */
    public NetworkServiceType getNetworkServiceApplicant() {
        return networkServiceApplicant;
    }

    /**
     * Set the NetworkServiceApplicant
     * @param networkServiceApplicant
     */
    public void setNetworkServiceApplicant(NetworkServiceType networkServiceApplicant) {
        this.networkServiceApplicant = networkServiceApplicant;
    }

    /**
     * Get the RemoteParticipant
     * @return remoteParticipant
     */
    public PlatformComponentProfile getRemoteParticipant() {
        return remoteParticipant;
    }

    /**
     * Set the RemoteParticipant
     * @param remoteParticipant
     */
    public void setRemoteParticipant(PlatformComponentProfile remoteParticipant) {
        this.remoteParticipant = remoteParticipant;
    }
}
