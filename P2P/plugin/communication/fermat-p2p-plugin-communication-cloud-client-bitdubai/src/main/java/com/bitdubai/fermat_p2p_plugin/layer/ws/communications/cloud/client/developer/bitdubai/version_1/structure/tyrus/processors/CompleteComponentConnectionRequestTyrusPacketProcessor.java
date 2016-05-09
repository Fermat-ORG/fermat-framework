/*
 * @#CompleteComponentConnectionRequestPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.WsCommunicationsTyrusCloudClientChannel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.CompleteComponentConnectionRequestTyrusPacketProcessor</code> implement
 * the logic to process the packet when a packet type <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType.COMPLETE_COMPONENT_CONNECTION_REQUEST</code> is receive by the server.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 14/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteComponentConnectionRequestTyrusPacketProcessor extends FermatTyrusPacketProcessor {

    /**
     * Constructor
     *
     * @param wsCommunicationsTyrusCloudClientChannel
     */
    public CompleteComponentConnectionRequestTyrusPacketProcessor(WsCommunicationsTyrusCloudClientChannel wsCommunicationsTyrusCloudClientChannel) {
        super(wsCommunicationsTyrusCloudClientChannel);
    }

    /**
     * (no-javadoc)
     * @see FermatTyrusPacketProcessor#processingPackage(FermatPacket)
     */
    @Override
    public void processingPackage(FermatPacket receiveFermatPacket){

        System.out.println("CompleteComponentConnectionRequestTyrusPacketProcessor - processingPackage = ");

        /*
         * Get the filters from the message content and decrypt
         */
        String messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsTyrusCloudClientChannel().getClientIdentity().getPrivateKey());

       // System.out.println("CompleteComponentConnectionRequestTyrusPacketProcessor - messageContentJsonStringRepresentation = "+messageContentJsonStringRepresentation);

        /*
         * Construct the json object
         */
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject respond = parser.parse(messageContentJsonStringRepresentation).getAsJsonObject();

        NetworkServiceType networkServiceType       = gson.fromJson(respond.get(JsonAttNamesConstants.NETWORK_SERVICE_TYPE), NetworkServiceType.class);
        PlatformComponentProfile applicantComponent = gson.fromJson(respond.get(JsonAttNamesConstants.APPLICANT_PARTICIPANT_VPN).getAsString(), PlatformComponentProfileCommunication.class);
        PlatformComponentProfile remoteComponent = gson.fromJson(respond.get(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN).getAsString(), PlatformComponentProfileCommunication.class);

        System.out.println("CompleteComponentConnectionRequestTyrusPacketProcessor - networkServiceType = "+networkServiceType);
        System.out.println("CompleteComponentConnectionRequestTyrusPacketProcessor - applicantComponent = "+applicantComponent.getAlias() + "["+applicantComponent.getIdentityPublicKey() +"]");
        System.out.println("CompleteComponentConnectionRequestTyrusPacketProcessor - remoteComponent    = "+remoteComponent.getAlias()    + "["+remoteComponent.getIdentityPublicKey()    +"]");

        /*
         * Create a new event whit the networkServiceType and remoteIdentity
         */
        FermatEvent event = P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION.getNewEvent();
        event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);

        /*
         * Configure the values
         */
        ((CompleteComponentConnectionRequestNotificationEvent)event).setNetworkServiceTypeApplicant(networkServiceType);
        ((CompleteComponentConnectionRequestNotificationEvent)event).setApplicantComponent(applicantComponent);
        ((CompleteComponentConnectionRequestNotificationEvent)event).setRemoteComponent(remoteComponent);

        /*
         * Raise the event
         */
        System.out.println("CompleteComponentConnectionRequestTyrusPacketProcessor - Raised a event = P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION");
        getWsCommunicationsTyrusCloudClientChannel().getEventManager().raiseEvent(event);

    }

    /**
     * (no-javadoc)
     * @see FermatTyrusPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.COMPLETE_COMPONENT_CONNECTION_REQUEST;
    }
}
