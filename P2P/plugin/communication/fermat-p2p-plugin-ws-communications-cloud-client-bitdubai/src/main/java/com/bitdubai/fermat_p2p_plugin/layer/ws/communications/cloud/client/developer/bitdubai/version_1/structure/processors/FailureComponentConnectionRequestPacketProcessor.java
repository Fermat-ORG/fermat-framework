/*
 * @#FailureComponentConnectionRequestPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.FailureComponentConnectionRequestPacketProcessor</code> implement
 * the logic to process the packet when a packet type <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType.FAILURE_COMPONENT_CONNECTION_REQUEST</code> is receive by the server.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 14/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FailureComponentConnectionRequestPacketProcessor extends FermatPacketProcessor {

    /**
     * Represent the jsonParser
     */
    private JsonParser jsonParser;

    /**
     * Constructor
     */
    public FailureComponentConnectionRequestPacketProcessor() {
        jsonParser = new JsonParser();
    }

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#processingPackage(FermatPacket)
     */
    @Override
    public void processingPackage(FermatPacket receiveFermatPacket){

        /*
         * Get the filters from the message content and decrypt
         */
        String messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsCloudClientChannel().getClientIdentity().getPrivateKey());

        System.out.println("FailureComponentConnectionRequestPacketProcessor - messageContentJsonStringRepresentation = "+messageContentJsonStringRepresentation);

        /*
         * Construct the json object
         */
        JsonObject packetContent = jsonParser.parse(messageContentJsonStringRepresentation).getAsJsonObject();
        PlatformComponentProfile networkServiceApplicant = new PlatformComponentProfileCommunication().fromJson(packetContent.get(JsonAttNamesConstants.APPLICANT_PARTICIPANT_NS_VPN).getAsString());
        PlatformComponentProfile remoteParticipant       = new PlatformComponentProfileCommunication().fromJson(packetContent.get(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN).getAsString());
        System.out.println("FailureComponentConnectionRequestPacketProcessor - networkServiceApplicant "+networkServiceApplicant.toJson());

        /*
         * Create a new event whit the networkServiceType and remoteIdentity
         */
        FermatEvent event = P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION.getNewEvent();
        event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);

        /*
         * Configure the values
         */
        ((FailureComponentConnectionRequestNotificationEvent)event).setNetworkServiceApplicant(networkServiceApplicant);
        ((FailureComponentConnectionRequestNotificationEvent)event).setRemoteParticipant(remoteParticipant);

        /*
         * Raise the event
         */
        System.out.println("FailureComponentConnectionRequestPacketProcessor - Raised a event = P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION");
        getWsCommunicationsCloudClientChannel().getEventManager().raiseEvent(event);

    }

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.FAILURE_COMPONENT_CONNECTION_REQUEST;
    }
}
