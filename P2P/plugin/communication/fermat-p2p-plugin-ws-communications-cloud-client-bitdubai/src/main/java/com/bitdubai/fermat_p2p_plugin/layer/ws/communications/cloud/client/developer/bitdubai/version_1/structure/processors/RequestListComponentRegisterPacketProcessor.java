/*
 * @#RequestListComponentRegisterPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteRequestListComponentRegisteredNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.RequestListComponentRegisterPacketProcessor</code> implement
 * the logic to process the packet when a packet type <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType.REQUEST_LIST_COMPONENT_REGISTERED</code> is receive by the server.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RequestListComponentRegisterPacketProcessor extends FermatPacketProcessor {

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#processingPackage(FermatPacket)
     */
    @Override
    public void processingPackage(FermatPacket receiveFermatPacket) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println("RequestListComponentRegisterPacketProcessor - Starting processingPackage");

        /*
         * Get the platformComponentProfile from the message content and decrypt
         */
        String messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsCloudClientChannel().getClientIdentity().getPrivateKey());

        System.out.println("RequestListComponentRegisterPacketProcessor - messageContentJsonStringRepresentation = "+messageContentJsonStringRepresentation);

        /*
         * Construct the json object
         */
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject respond = parser.parse(messageContentJsonStringRepresentation).getAsJsonObject();

        NetworkServiceType networkServiceType       = gson.fromJson(respond.get(JsonAttNamesConstants.NETWORK_SERVICE_TYPE), NetworkServiceType.class);
        PlatformComponentType platformComponentType = gson.fromJson(respond.get(JsonAttNamesConstants.COMPONENT_TYPE), PlatformComponentType.class);

         /*
         * Get the receivedList
         */
        List<PlatformComponentProfile> receivedList = gson.fromJson(respond.get(JsonAttNamesConstants.RESULT_LIST).getAsString(), new TypeToken<List<PlatformComponentProfileCommunication>>() {
        }.getType());

        System.out.println("RequestListComponentRegisterPacketProcessor - receivedList.size() = " + receivedList.size());

         /*
         * Create a new event whit the receivedlist
         */
        FermatEvent event =  P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION.getNewEvent();
        event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);

        /*
         * configure the event
         */
        ((CompleteRequestListComponentRegisteredNotificationEvent)event).setRegisteredComponentList(receivedList);
        ((CompleteRequestListComponentRegisteredNotificationEvent)event).setNetworkServiceTypeApplicant(networkServiceType);
        ((CompleteRequestListComponentRegisteredNotificationEvent)event).setPlatformComponentType(platformComponentType);

        /*
         * Raise the event
         */
        System.out.println("RequestListComponentRegisterPacketProcessor - Raised a event = P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION");
        getWsCommunicationsCloudClientChannel().getEventManager().raiseEvent(event);

    }

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.REQUEST_LIST_COMPONENT_REGISTERED;
    }
}
