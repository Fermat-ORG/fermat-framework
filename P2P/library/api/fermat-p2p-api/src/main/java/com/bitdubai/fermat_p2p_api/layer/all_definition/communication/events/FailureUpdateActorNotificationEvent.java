/*
* @#FailureUpdateActorNotificationEvent.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureUpdateActorNotificationEvent</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 06/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FailureUpdateActorNotificationEvent extends AbstractP2PFermatEvent {

    /**
     * Represent the remoteComponent
     */
    private NetworkServiceType networkServiceApplicant;

    /**
     * Represent the remoteParticipant
     */
    private PlatformComponentProfile platformComponentProfile;

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        cause = cause;
    }

    /**
     * Represent the Cause of the Failure
     */
    private String cause;

    /**
     * Constructor with parameter
     * @param p2pEventType
     */
    public FailureUpdateActorNotificationEvent(P2pEventType p2pEventType) {
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
     * Get the PlatformComponentProfile
     * @return PlatformComponentProfile
     */
    public PlatformComponentProfile getPlatformComponentProfile() {
        return platformComponentProfile;
    }

    /**
     * Set the PlatformComponentProfile
     * @param platformComponentProfile
     */
    public void setPlatformComponentProfile(PlatformComponentProfile platformComponentProfile) {
        this.platformComponentProfile = platformComponentProfile;
    }
}
