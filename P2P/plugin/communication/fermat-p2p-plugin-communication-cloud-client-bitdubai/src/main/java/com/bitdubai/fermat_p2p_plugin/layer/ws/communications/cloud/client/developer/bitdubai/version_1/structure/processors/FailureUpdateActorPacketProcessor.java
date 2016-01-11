/*
* @#FailureUpdateActorPacketProcessor.java - 2016
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
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureUpdateActorNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.FailureUpdateActorPacketProcessor</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 06/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FailureUpdateActorPacketProcessor extends FermatPacketProcessor {


    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Represent the jsonParser
     */
    private JsonParser jsonParser;

    /**
     * Constructor
     */
    public FailureUpdateActorPacketProcessor(){
        super();
        gson = new Gson();
        jsonParser = new JsonParser();
    }

    @Override
    public void processingPackage(FermatPacket receiveFermatPacket) {

          /*
         * Get the filters from the message content and decrypt
         */
        String messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsCloudClientChannel().getClientIdentity().getPrivateKey());

        //System.out.println("FailureComponentRegistrationRequestPacketProcessor - messageContentJsonStringRepresentation = "+messageContentJsonStringRepresentation);

        /*
         * Construct the json object
         */
        JsonObject packetContent = jsonParser.parse(messageContentJsonStringRepresentation).getAsJsonObject();
        NetworkServiceType networkServiceTypeApplicant = gson.fromJson(packetContent.get(JsonAttNamesConstants.NETWORK_SERVICE_TYPE).getAsString(), NetworkServiceType.class);
        PlatformComponentProfile platformComponentProfileUpdate = new PlatformComponentProfileCommunication().fromJson(packetContent.get(JsonAttNamesConstants.PROFILE_TO_UPDATE).getAsString());
        String message = gson.fromJson(packetContent.get(JsonAttNamesConstants.FAILURE_VPN_MSJ).getAsString(), String.class);

        /*
         * Create a new event whit the networkServiceType and remoteIdentity
         */
        FermatEvent event = P2pEventType.FAILURE_UPDATE_ACTOR_REQUEST_NOTIFICATION.getNewEvent();
        event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);

        /*
         * Configure the values
         */
        ((FailureUpdateActorNotificationEvent)event).setNetworkServiceApplicant(networkServiceTypeApplicant);
        ((FailureUpdateActorNotificationEvent)event).setPlatformComponentProfile(platformComponentProfileUpdate);
        ((FailureUpdateActorNotificationEvent) event).setCause(message);

        /*
         * Raise the event
         */
        getWsCommunicationsCloudClientChannel().getEventManager().raiseEvent(event);

    }

    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.FAILURE_UPDATE_ACTOR_REQUEST;
    }

}
