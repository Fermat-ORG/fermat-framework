/*
 * @#FailureRequestedListNotAvailableNotificationEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureRequestedListNotAvailableNotificationEvent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FailureRequestedListNotAvailableNotificationEvent extends AbstractP2PFermatEvent {

    /**
     * Represent the remoteComponent
     */
    private NetworkServiceType networkServiceApplicant;

    /**
     * Represent the discoveryQueryParameters
     */
    private DiscoveryQueryParameters discoveryQueryParameters;

    /**
     * Constructor with parameter
     * @param p2pEventType
     */
    public FailureRequestedListNotAvailableNotificationEvent(P2pEventType p2pEventType) {
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
     * Get the DiscoveryQueryParameters
     * @return DiscoveryQueryParameters
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
