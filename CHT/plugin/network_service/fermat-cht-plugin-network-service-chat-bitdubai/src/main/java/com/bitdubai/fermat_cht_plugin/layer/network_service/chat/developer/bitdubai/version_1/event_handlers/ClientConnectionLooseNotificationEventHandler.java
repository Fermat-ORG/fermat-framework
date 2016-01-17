/*
* @#ClientConnectionLooseNotificationEventHandler.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.communication.ClientConnectionLooseNotificationEventHandler</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 08/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientConnectionLooseNotificationEventHandler implements FermatEventHandler {

    NetworkService networkServiceRecieved;

    public ClientConnectionLooseNotificationEventHandler(NetworkService networkServiceRecieved){

        this.networkServiceRecieved = networkServiceRecieved;

    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        this.networkServiceRecieved.handleClientConnectionLooseNotificationEvent(fermatEvent);

    }
}
