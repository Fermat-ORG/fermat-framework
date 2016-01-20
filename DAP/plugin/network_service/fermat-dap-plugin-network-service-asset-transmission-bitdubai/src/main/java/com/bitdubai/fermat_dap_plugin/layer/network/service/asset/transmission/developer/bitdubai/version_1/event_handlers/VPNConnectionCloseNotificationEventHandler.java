/*
* @#VPNConnectionCloseNotificationEventHandler.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.event_handlers.VPNConnectionCloseNotificationEventHandler</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class VPNConnectionCloseNotificationEventHandler implements FermatEventHandler {

    NetworkService networkServiceRecieved;

    public VPNConnectionCloseNotificationEventHandler(NetworkService networkServiceRecieved){
        this.networkServiceRecieved = networkServiceRecieved;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        this.networkServiceRecieved.handleVpnConnectionCloseNotificationEvent(fermatEvent);

    }
}
