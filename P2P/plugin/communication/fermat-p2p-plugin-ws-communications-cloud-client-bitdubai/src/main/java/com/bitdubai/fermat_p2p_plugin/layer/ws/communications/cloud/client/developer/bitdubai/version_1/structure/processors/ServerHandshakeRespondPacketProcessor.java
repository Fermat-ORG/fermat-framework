/*
 * @#ServerHandshakeRespondPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.ServerHandshakeRespondPacketProcessor</code> this
 * class process the FermatPacket of type <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType.SERVER_HANDSHAKE_RESPOND</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ServerHandshakeRespondPacketProcessor extends FermatPacketProcessor {

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#processingPackage(FermatPacket)
     */
    @Override
    public void processingPackage(final FermatPacket receiveFermatPacket) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println("ServerHandshakeRespondPacketProcessor - processingPackage");

        /* -----------------------------------------------------------------------------------------
         * IMPORTANT: This Message Content of this packet come encrypted with the temporal identity public key
         * and contain the server identity whit the communications cloud client that
         * have to use to talk with the server.
         * -----------------------------------------------------------------------------------------
         */

        /*
         * Decrypt the message content
         */
        String jsonRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsCloudClientChannel().getTemporalIdentity().getPrivateKey());

        /*
         * Construct the json object
         */
        JsonParser parser = new JsonParser();
        JsonObject serverIdentity = parser.parse(jsonRepresentation).getAsJsonObject();

        /*
         * Get the server identity and set into the communication cloud client
         */
        getWsCommunicationsCloudClientChannel().setServerIdentity(serverIdentity.get(JsonAttNamesConstants.SERVER_IDENTITY).getAsString());


        System.out.println("ServerHandshakeRespondPacketProcessor - ServerIdentity = "+ getWsCommunicationsCloudClientChannel().getServerIdentity());

        /*
         * Construct a Communications Cloud Client Profile for this component and send and fermat packet type FermatPacketType.COMPONENT_REGISTRATION_REQUEST
         */
        PlatformComponentProfile communicationsCloudClientProfile = getWsCommunicationsCloudClientChannel().getWsCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory(getWsCommunicationsCloudClientChannel().getClientIdentity().getPublicKey(), "WsCommunicationsCloudClientChannel",  "Web Socket Communications Cloud Client", NetworkServiceType.UNDEFINED, PlatformComponentType.COMMUNICATION_CLOUD_CLIENT, null);
        getWsCommunicationsCloudClientChannel().setPlatformComponentProfile(communicationsCloudClientProfile);

        /* ------------------------------------
         * IMPORTANT: At this moment the server only
         * know the temporal identity of the client
         * the packet has construct with this identity
         * --------------------------------------
         */

        /*
         * Construc the jsonObject
         */
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, NetworkServiceType.UNDEFINED.toString());
        jsonObject.addProperty(JsonAttNamesConstants.PROFILE_TO_REGISTER, communicationsCloudClientProfile.toJson());

        /*
         * Construct a fermat packet whit the server identity
         */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(getWsCommunicationsCloudClientChannel().getServerIdentity(),                    //Destination
                                                                                                                    getWsCommunicationsCloudClientChannel().getTemporalIdentity().getPublicKey(),   //Sender
                                                                                                                    gson.toJson(jsonObject),                                      //Message Content
                                                                                                                    FermatPacketType.COMPONENT_REGISTRATION_REQUEST,                                //Packet type
                                                                                                                    getWsCommunicationsCloudClientChannel().getTemporalIdentity().getPrivateKey()); //Sender private key


        /*
         * Send the encode packet to the server
         */
        getWsCommunicationsCloudClientChannel().send(FermatPacketEncoder.encode(fermatPacketRespond));

    }

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.SERVER_HANDSHAKE_RESPOND;
    }
}
