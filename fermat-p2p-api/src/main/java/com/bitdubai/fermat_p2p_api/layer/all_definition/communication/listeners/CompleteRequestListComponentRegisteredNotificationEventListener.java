/*
 * @#CompleteRequestListComponentRegisteredNotificationEventListener.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 * The Class <code>com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.listeners.CompleteComponentRegistrationNotificationEventListener</code> is
 * the event listener for the <code>com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION</code>.
 * <p/>
 *
 * Created by Roberto Requena - (rrequena) on 19/09/15.
 *
 * @version 1.0
 */
public class CompleteRequestListComponentRegisteredNotificationEventListener extends BasicFermatEventListener {

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     * @param eventMonitor
     */
    public CompleteRequestListComponentRegisteredNotificationEventListener(P2pEventType p2pEventType, FermatEventMonitor eventMonitor) {
        super(p2pEventType, eventMonitor);
    }
}
