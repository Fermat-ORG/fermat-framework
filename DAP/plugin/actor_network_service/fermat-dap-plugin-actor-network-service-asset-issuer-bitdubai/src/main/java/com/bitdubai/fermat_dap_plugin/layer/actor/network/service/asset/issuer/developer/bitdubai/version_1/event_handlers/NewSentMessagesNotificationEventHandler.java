/*
 * @#NewSentMessagesNotificationEventHandler.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.AssetIssuerActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageSentNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * The class <code>com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.event_handlers.NewSentMessagesNotificationEventHandler</code> listen
 * and handle the event <code>NewNetworkServiceMessageSentNotificationEvent</code>
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 13/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NewSentMessagesNotificationEventHandler implements FermatEventHandler {

    /**
     * Constructor with parameter
     *
     * @param
     */
    public NewSentMessagesNotificationEventHandler() {

    }

    /**
     * (non-Javadoc)
     *
     * @see FermatEventHandler#handleEvent(FermatEvent)
     *
     * @param platformEvent
     * @throws Exception
     */
    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {

        if (platformEvent.getSource() == AssetIssuerActorNetworkServicePluginRoot.EVENT_SOURCE){

            System.out.println("CompleteComponentConnectionRequestNotificationEventHandler - handleEvent platformEvent =" + platformEvent.toString());
            System.out.println("ACTOR NETWORK SERVICE ASSET ISSUER - NOTIFICACION EVENTO MENSAJE ENVIADO!!!!");
            NewNetworkServiceMessageSentNotificationEvent newNetworkServiceMessageSentNotificationEvent = (NewNetworkServiceMessageSentNotificationEvent) platformEvent;
            FermatMessage fermatMessage = (FermatMessage) newNetworkServiceMessageSentNotificationEvent.getData();

        }
    }
}
