/*
 * @#CompleteRequestListComponentRegisterNotificationEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_api.layer.all_definition.components.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.EventType;


import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteRequestListComponentRegisteredNotificationEvent</code> is
 * the  representation of the event for the <code>com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION</code>.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 20/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteRequestListComponentRegisteredNotificationEvent extends AbstractP2PFermatEvent {

    /**
     * Represent the list of registered components
     */
    private List<PlatformComponentProfile> registeredComponentList;

    /**
     * Represent the networkServiceType
     */
    private NetworkServiceType networkServiceType;

    /**
     * Represent the platformComponentType
     */
    private PlatformComponentType platformComponentType;

    /**
     * Constructor with parameter
     *
     * @param eventType
     */
    public CompleteRequestListComponentRegisteredNotificationEvent(EventType eventType) {
        super(eventType);
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
