/*
 * @#ServerHandshakeRespondPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.AttNamesConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
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
    public void processingPackage(FermatPacket fermatPacket) {

        /* -----------------------------------------------------------------------------------------
         * This Message Content of this packet come encrypted with the temporal identity public key
         * and contain the server identity whit the communications cloud client that
         * have to use to talk with the server.
         * -----------------------------------------------------------------------------------------
         */

        /*
         * Decrypt the message content
         */
        String jsonRepresentation = AsymmectricCryptography.decryptMessagePrivateKey(fermatPacket.getMessageContent(), getWsCommunicationsCloudClient().getTemporalIdentity().getPrivateKey());

        /*
         * Construct the json object
         */
        JsonParser parser = new JsonParser();
        JsonObject serverIdentity = parser.parse(jsonRepresentation).getAsJsonObject();

        /*
         * Get the server identity and set into the communication cloud client
         */
        getWsCommunicationsCloudClient().setServerIdentity(serverIdentity.get(AttNamesConstants.JSON_ATT_NAME_SERVER_IDENTITY).getAsString());


        /*
         * TODO: Construct a client profile and send and fermat packet type FermatPacketType.COMPONENT_REGISTRATION_REQUEST
         */

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
