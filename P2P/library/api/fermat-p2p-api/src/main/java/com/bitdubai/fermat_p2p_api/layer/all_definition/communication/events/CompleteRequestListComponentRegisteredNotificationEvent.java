/*
 * @#CompleteRequestListComponentRegisterNotificationEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.interfaces.CommunicationBaseEvent;


import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteRequestListComponentRegisteredNotificationEvent</code> is
 * the  representation of the event for the <code>com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION</code>.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 20/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteRequestListComponentRegisteredNotificationEvent extends AbstractP2PFermatEvent implements CommunicationBaseEvent {

    /**
     * Represent the list of registered components
     */
    private List<PlatformComponentProfile> registeredComponentList;

    /**
     * Represent the networkServiceTypeApplicant
     */
    private NetworkServiceType networkServiceTypeApplicant;

    /**
     * Represent the platformComponentType
     */
    private PlatformComponentType platformComponentType;

    /**
     * Constructor with parameter
     *
     * @param p2pEventType
     */
    public CompleteRequestListComponentRegisteredNotificationEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    /**
     * Get the Registered Component List
     * @return List<PlatformComponentProfile>
     */
    public List<PlatformComponentProfile> getRegisteredComponentList() {
        return registeredComponentList;
    }

    /**
     * Set the Registered Component List
     * @param registeredComponentList
     */
    public void setRegisteredComponentList(List<PlatformComponentProfile> registeredComponentList) {
        this.registeredComponentList = registeredComponentList;
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
     * Get the PlatformComponentType
     * @return PlatformComponentType
     */
    public PlatformComponentType getPlatformComponentType() {
        return platformComponentType;
    }

    /**
     * Set the platformComponentType
     * @param platformComponentType
     */
    public void setPlatformComponentType(PlatformComponentType platformComponentType) {
        this.platformComponentType = platformComponentType;
    }

}
