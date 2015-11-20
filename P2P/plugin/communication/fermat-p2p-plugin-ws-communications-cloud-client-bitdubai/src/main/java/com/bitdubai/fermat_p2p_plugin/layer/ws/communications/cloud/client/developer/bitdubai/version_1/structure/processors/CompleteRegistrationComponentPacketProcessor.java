/*
 * @#CompleteRegistrationComponentPacketProcessor.java - 2015
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
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.CompleteRegistrationComponentPacketProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 07/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteRegistrationComponentPacketProcessor extends FermatPacketProcessor {

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
    public CompleteRegistrationComponentPacketProcessor(){
        super();
        gson = new Gson();
        jsonParser = new JsonParser();
    }

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#processingPackage(FermatPacket)
     */
    @Override
    public void processingPackage(FermatPacket receiveFermatPacket) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println("CompleteRegistrationComponentPacketProcessor - processingPackage");

        String messageContentJsonStringRepresentation = null;

        if (getWsCommunicationsCloudClientChannel().isRegister()){

            /*
            * Get the platformComponentProfile from the message content and decrypt
            */
            System.out.println(" CompleteRegistrationComponentPacketProcessor - decoding fermatPacket with client-identity ");
            messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsCloudClientChannel().getClientIdentity().getPrivateKey());

        }else {

            /*
            * ---------------------------------------------------------------------------------------------------
            * IMPORTANT: This Message Content of this packet come encrypted with the temporal identity public key
            * at this moment the communication cloud client is noT register
            * ---------------------------------------------------------------------------------------------------
            * Get the platformComponentProfile from the message content and decrypt
            */
            System.out.println(" CompleteRegistrationComponentPacketProcessor - decoding fermatPacket with temp-identity ");
            messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsCloudClientChannel().getTemporalIdentity().getPrivateKey());

        }

        System.out.println("CompleteRegistrationComponentPacketProcessor - messageContentJsonStringRepresentation = "+messageContentJsonStringRepresentation);


        /*
         * Construct the json object
         */
        JsonObject contentJsonObject = jsonParser.parse(messageContentJsonStringRepresentation).getAsJsonObject();
        NetworkServiceType networkServiceTypeApplicant = gson.fromJson(contentJsonObject.get(JsonAttNamesConstants.NETWORK_SERVICE_TYPE).getAsString(), NetworkServiceType.class);
        PlatformComponentProfile platformComponentProfile = new PlatformComponentProfileCommunication().fromJson(contentJsonObject.get(JsonAttNamesConstants.PROFILE_TO_REGISTER).getAsString());


        if (platformComponentProfile.getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_CLIENT){

            /*
             * Mark as register
             */
            getWsCommunicationsCloudClientChannel().setIsRegister(Boolean.TRUE);
            // getWsCommunicationsCloudClientChannel().launchCompleteClientComponentRegistrationNotificationEvent();
            // System.out.println("CompleteRegistrationComponentPacketProcessor - Raised a event = P2pEventType.COMPLETE_CLIENT_COMPONENT_REGISTRATION_NOTIFICATION");
            System.out.println("CompleteRegistrationComponentPacketProcessor - getWsCommunicationsCloudClientChannel().isRegister() = "+ getWsCommunicationsCloudClientChannel().isRegister());
        }

        /*
         * Create a raise a new event whit the platformComponentProfile registered
         */
        FermatEvent event = P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION.getNewEvent();
        event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);

        /*
         * Set the component already register
         */
        ((CompleteComponentRegistrationNotificationEvent)event).setPlatformComponentProfileRegistered(platformComponentProfile);
        ((CompleteComponentRegistrationNotificationEvent)event).setNetworkServiceTypeApplicant(networkServiceTypeApplicant);

        /*
         * Raise the event
         */
        System.out.println("CompleteRegistrationComponentPacketProcessor - Raised a event = P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION");
        getWsCommunicationsCloudClientChannel().getEventManager().raiseEvent(event);

    }

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.COMPLETE_COMPONENT_REGISTRATION;
    }




}
