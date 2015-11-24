/*
 * @#CompleteComponentRegistrationNotificationEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;


import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 * The Class <code>com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.CompleteComponentRegistrationNotificationEvent</code> is
 * the  representation of the event for the <code>com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.P2pEventType.COMPONENT_REGISTRATION_COMPLETE_NOTIFICATION</code>.
 * <p/>
 *
 * Created by Roberto Requena - (rrequena) on 14/09/15.
 *
 * @version 1.0
 */
public class CompleteComponentRegistrationNotificationEvent extends AbstractP2PFermatEvent {

    /**
     * Represent the networkServiceTypeApplicant
     */
    private NetworkServiceType networkServiceTypeApplicant;


    /**
     * Represent the platformComponentProfileRegistered
     */
    private PlatformComponentProfile platformComponentProfileRegistered;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public CompleteComponentRegistrationNotificationEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    /**
     * Get the PlatformComponentProfileRegistered
     * @return PlatformComponentProfile
     */
    public PlatformComponentProfile getPlatformComponentProfileRegistered() {
        return platformComponentProfileRegistered;
    }

    /**
     * Set the PlatformComponentProfileRegistered
     * @param platformComponentProfileRegistered
     */
    public void setPlatformComponentProfileRegistered(PlatformComponentProfile platformComponentProfileRegistered) {
        this.platformComponentProfileRegistered = platformComponentProfileRegistered;
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
                ", platformComponentProfileRegistered=" + platformComponentProfileRegistered +
                '}';
    }
}
