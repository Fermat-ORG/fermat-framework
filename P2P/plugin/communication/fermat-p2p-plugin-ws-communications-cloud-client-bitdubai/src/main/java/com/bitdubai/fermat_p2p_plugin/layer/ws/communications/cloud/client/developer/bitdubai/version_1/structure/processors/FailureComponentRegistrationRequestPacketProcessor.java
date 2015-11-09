/*
 * @#FailureComponentRegistrationRequestPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.FailureComponentRegistrationRequestPacketProcessor</code> implement
 * the logic to process the packet when a packet type <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType.FAILURE_COMPONENT_CONNECTION_REQUEST</code> is receive by the server.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FailureComponentRegistrationRequestPacketProcessor extends FermatPacketProcessor {

    /**
     * Represent the jsonParser
     */
    private JsonParser jsonParser;

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Constructor
     */
    public FailureComponentRegistrationRequestPacketProcessor() {
        jsonParser = new JsonParser();
        gson = new Gson();
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

        System.out.println("FailureComponentRegistrationRequestPacketProcessor - messageContentJsonStringRepresentation = "+messageContentJsonStringRepresentation);

        /*
         * Construct the json object
         */
        JsonObject packetContent = jsonParser.parse(messageContentJsonStringRepresentation).getAsJsonObject();
        PlatformComponentProfile networkServiceApplicant  = gson.fromJson(packetContent.get(JsonAttNamesConstants.APPLICANT_PARTICIPANT_NS_VPN).getAsString(), PlatformComponentProfile.class);
        PlatformComponentProfile platformComponentProfile = new PlatformComponentProfileCommunication().fromJson(packetContent.get(JsonAttNamesConstants.PROFILE_TO_REGISTER).getAsString());

        System.out.println("FailureComponentRegistrationRequestPacketProcessor - networkServiceApplicant "+networkServiceApplicant);

        /*
         * Create a new event whit the networkServiceType and remoteIdentity
         */
        FermatEvent event = P2pEventType.FAILURE_COMPONENT_REGISTRATION_REQUEST_NOTIFICATION.getNewEvent();
        event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);

        /*
         * Configure the values
         */
        ((FailureComponentRegistrationNotificationEvent)event).setNetworkServiceApplicant(networkServiceApplicant.getNetworkServiceType());
        ((FailureComponentRegistrationNotificationEvent)event).setPlatformComponentProfile(platformComponentProfile);

        /*
         * Raise the event
         */
        System.out.println("FailureComponentRegistrationRequestPacketProcessor - Raised a event = P2pEventType.FAILURE_COMPONENT_REGISTRATION_REQUEST_NOTIFICATION");
        getWsCommunicationsCloudClientChannel().getEventManager().raiseEvent(event);

    }

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.FAILURE_COMPONENT_REGISTRATION_REQUEST;
    }
}
