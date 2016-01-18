/*
* @#CompleteUpdateActorPacketProcessor.java - 2016
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
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteUpdateActorNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.CompleteUpdateActorPacketProcessor</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 06/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteUpdateActorPacketProcessor extends FermatPacketProcessor {

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
    public CompleteUpdateActorPacketProcessor(){
        super();
        gson = new Gson();
        jsonParser = new JsonParser();
    }

    @Override
    public void processingPackage(FermatPacket receiveFermatPacket) {

        String messageContentJsonStringRepresentation = null;

        if (getWsCommunicationsCloudClientChannel().isRegister()){

            /*
            * Get the platformComponentProfile from the message content and decrypt
            */
            //System.out.println(" CompleteRegistrationComponentPacketProcessor - decoding fermatPacket with client-identity ");
            messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsCloudClientChannel().getClientIdentity().getPrivateKey());

        }else {

            /*
            * ---------------------------------------------------------------------------------------------------
            * IMPORTANT: This Message Content of this packet come encrypted with the temporal identity public key
            * at this moment the communication cloud client is noT register
            * ---------------------------------------------------------------------------------------------------
            * Get the platformComponentProfile from the message content and decrypt
            */
            //System.out.println(" CompleteRegistrationComponentPacketProcessor - decoding fermatPacket with temp-identity ");
            messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsCloudClientChannel().getTemporalIdentity().getPrivateKey());

        }


           /*
         * Construct the json object
         */
        JsonObject contentJsonObject = jsonParser.parse(messageContentJsonStringRepresentation).getAsJsonObject();
        NetworkServiceType networkServiceTypeApplicant = gson.fromJson(contentJsonObject.get(JsonAttNamesConstants.NETWORK_SERVICE_TYPE).getAsString(), NetworkServiceType.class);
        PlatformComponentProfile platformComponentProfileUpdate = new PlatformComponentProfileCommunication().fromJson(contentJsonObject.get(JsonAttNamesConstants.PROFILE_TO_UPDATE).getAsString());


          /*
         * Create a raise a new event whit the platformComponentProfile registered
         */
        FermatEvent event = P2pEventType.COMPLETE_UPDATE_ACTOR_NOTIFICATION.getNewEvent();
        event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);

        /*
         * Set the component already register
         */
        ((CompleteUpdateActorNotificationEvent)event).setPlatformComponentProfileUpdate(platformComponentProfileUpdate);
        ((CompleteUpdateActorNotificationEvent)event).setNetworkServiceTypeApplicant(networkServiceTypeApplicant);

        /*
         * Raise the event
         */
        //System.out.println("CompleteRegistrationComponentPacketProcessor - Raised a event = P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION");
        getWsCommunicationsCloudClientChannel().getEventManager().raiseEvent(event);

    }

    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.COMPLETE_UPDATE_ACTOR;
    }
}
