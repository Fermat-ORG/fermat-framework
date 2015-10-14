/*
* @#CompleteClientAssetUserActorRegistrationNotificationEventListener.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_api.layer.all_definition.dap_actor_network_service.asset_user.enums.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_dap_api.layer.all_definition.dap_actor_network_service.asset_user.enums.DapEvenType;

/**
 * The Class <code>CompleteClientAssetUserActorRegistrationNotificationEventListener</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 12/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteClientAssetUserActorRegistrationNotificationEventListener extends BasicFermatEventListener {
    /**
     * Constructor with parameters
     *
     * @param eventType
     * @param eventMonitor
     */
    public CompleteClientAssetUserActorRegistrationNotificationEventListener(DapEvenType eventType, FermatEventMonitor eventMonitor) {
        super(eventType, eventMonitor);
    }
}
