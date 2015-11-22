/*
 * @#CompleteComponentConnectionRequestNotificationEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.interfaces.CommunicationBaseEvent;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 20/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteComponentConnectionRequestNotificationEvent extends AbstractP2PFermatEvent implements CommunicationBaseEvent {

    /**
     * Represent the networkServiceTypeApplicant
     */
    private NetworkServiceType networkServiceTypeApplicant;

    /**
     * Represent the remoteComponent
     */
    private PlatformComponentProfile remoteComponent;

    /**
     * Represent the remoteComponent
     */
    private PlatformComponentProfile applicantComponent;


    /**
     * Constructor with parameter
     * @param p2pEventType
     */
    public CompleteComponentConnectionRequestNotificationEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
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

    /**
     * Get the applicantComponent
     * @return applicantComponent
     */
    public PlatformComponentProfile getApplicantComponent() {
        return applicantComponent;
    }

    /**
     * Set the applicantComponent
     * @param applicantComponent
     */
    public void setApplicantComponent(PlatformComponentProfile applicantComponent) {
        this.applicantComponent = applicantComponent;
    }

    @Override
    public String toString() {
        return "CompleteComponentConnectionRequestNotificationEvent{" +
                "networkServiceTypeApplicant=" + networkServiceTypeApplicant +
                ", remoteComponent=" + remoteComponent +
                ", applicantComponent=" + applicantComponent +
                '}';
    }
}
