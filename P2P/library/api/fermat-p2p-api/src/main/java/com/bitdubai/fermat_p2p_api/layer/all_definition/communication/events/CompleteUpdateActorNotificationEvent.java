/*
* @#CompleteUpdateActorNotificationEvent.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteUpdateActorNotificationEvent</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 06/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteUpdateActorNotificationEvent extends AbstractP2PFermatEvent {

    /**
     * Represent the networkServiceTypeApplicant
     */
    private NetworkServiceType networkServiceTypeApplicant;


    /**
     * Represent the platformComponentProfileRegistered
     */
    private PlatformComponentProfile platformComponentProfileUpdate;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public CompleteUpdateActorNotificationEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    /**
     * Get the PlatformComponentProfileRegistered
     * @return PlatformComponentProfile
     */
    public PlatformComponentProfile getPlatformComponentProfileUpdate() {
        return platformComponentProfileUpdate;
    }

    /**
     * Set the PlatformComponentProfileRegistered
     * @param platformComponentProfileUpdate
     */
    public void setPlatformComponentProfileUpdate(PlatformComponentProfile platformComponentProfileUpdate) {
        this.platformComponentProfileUpdate = platformComponentProfileUpdate;
    }

    /**
     * Get the NetworkServiceTypeApplicant
     * @return NetworkServiceTypeApplicant
     */
    public NetworkServiceType getNetworkServiceTypeApplicant() {
        return networkServiceTypeApplicant;
    }

    /**
     * Set the NetworkServiceTypeApplicant
     * @param networkServiceTypeApplicant
     */
    public void setNetworkServiceTypeApplicant(NetworkServiceType networkServiceTypeApplicant) {
        this.networkServiceTypeApplicant = networkServiceTypeApplicant;
    }

    @Override
    public String toString() {
        return "CompleteComponentRegistrationNotificationEvent{" +
                "networkServiceTypeApplicant=" + networkServiceTypeApplicant +
                ", platformComponentProfileUpdate=" + platformComponentProfileUpdate +
                '}';
    }
}
