/*
 * @#CompleteRequestListComponentRegisterNotificationEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.EventType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.PlatformComponentProfile;

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
    List<PlatformComponentProfile> registeredComponentList;

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
}
