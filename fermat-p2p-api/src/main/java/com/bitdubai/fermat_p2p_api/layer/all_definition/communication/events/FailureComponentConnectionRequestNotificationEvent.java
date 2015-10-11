/*
 * @#FailureComponentConnectionRequestNotificationEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.EventType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentConnectionRequestNotificationEvent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FailureComponentConnectionRequestNotificationEvent extends AbstractP2PFermatEvent {

    /**
     * Represent the remoteComponent
     */
    private PlatformComponentProfile networkServiceApplicant;

    /**
     * Represent the discoveryQueryParameters
     */
    private DiscoveryQueryParameters discoveryQueryParameters;

    /**
     * Constructor with parameter
     * @param eventType
     */
    public FailureComponentConnectionRequestNotificationEvent(EventType eventType) {
        super(eventType);
    }

    /**
     * Get the NetworkServiceApplicant
     *
     * @return PlatformComponentProfile
     */
    public PlatformComponentProfile getNetworkServiceApplicant() {
        return networkServiceApplicant;
    }

    /**
     * Set the NetworkServiceApplicant
     * @param networkServiceApplicant
     */
    public void setNetworkServiceApplicant(PlatformComponentProfile networkServiceApplicant) {
        this.networkServiceApplicant = networkServiceApplicant;
    }

    /**
     * Get the DiscoveryQueryParameters
     *
     * @return discoveryQueryParameters
     */
    public DiscoveryQueryParameters getDiscoveryQueryParameters() {
        return discoveryQueryParameters;
    }

    /**
     * Set the DiscoveryQueryParameters
     * @param discoveryQueryParameters
     */
    public void setDiscoveryQueryParameters(DiscoveryQueryParameters discoveryQueryParameters) {
        this.discoveryQueryParameters = discoveryQueryParameters;
    }
}
