/*
* @#NewReceiveMessagesNotificationEventHandler.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.NewReceiveMessageActorNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessageGson;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.AssetIssuerActorNetworkServicePluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.event_handlers.NewReceiveMessagesNotificationEventHandler</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 28/10/15.
 * Modified by Franklin on 03/11/2015
 * Modified by Victor on 04/12/2015 to fit new DAPMessage protocol.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NewReceiveMessagesNotificationEventHandler implements FermatEventHandler {

    private EventManager eventManager;
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;


    public NewReceiveMessagesNotificationEventHandler(CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager, EventManager eventManager) {
        this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
        this.eventManager = eventManager;
    }

    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {
        if (platformEvent.getSource() != AssetIssuerActorNetworkServicePluginRoot.EVENT_SOURCE) {
            return; //This is not for me.
        }
        if (!(platformEvent instanceof NewNetworkServiceMessageReceivedNotificationEvent)) {
            return; //This is not for me.
        }
        try {

            System.out.println("ACTOR NETWORK SERVICE ASSET ISSUER - NOTIFICACION EVENTO MENSAJE RECIBIDO!!!!");

            NewNetworkServiceMessageReceivedNotificationEvent newNetworkServiceMessageReceivedNotificationEvent = (NewNetworkServiceMessageReceivedNotificationEvent) platformEvent;
            FermatMessage fermatMessageReceive = (FermatMessage) newNetworkServiceMessageReceivedNotificationEvent.getData();

            Gson gson = DAPMessageGson.getGson();
            String messageContent = fermatMessageReceive.getContent();

            DAPMessage message = gson.fromJson(messageContent, DAPMessage.class);

            System.out.println("ACTOR NETWORK SERVICE ASSET ISSUER - SE LANZARA EVENTO PARA REQUEST PUBLIC KEY EXTENDED");

            communicationNetworkServiceConnectionManager.getIncomingMessageDao().create(fermatMessageReceive);
            FermatEvent event = eventManager.getNewEvent(EventType.NEW_RECEIVE_MESSAGE_ACTOR);
            event.setSource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_ISSUER);
            ((NewReceiveMessageActorNotificationEvent) event).setNewReceiveMessage(message);
            eventManager.raiseEvent(event);
        } catch (Exception jsonEx) {
            throw new DAPException("Cannot read the message, it have a wrong class.", jsonEx, null, null);
        }
    }
}
