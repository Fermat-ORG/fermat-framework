/*
 * @#EncodeMsjContent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_art_api.layer.actor_network_service.util.ArtistActorNetworkServiceRecord;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.EncodeMsjContent</code> is
 * responsible of encode the content of the message by type of content
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class EncodeMsjContent {

    private static JsonParser parser = new JsonParser();

    /**
     *  Construct the content of the message fot the type <code>ChatMessageTransactionType.CHAT_METADATA_TRASMIT</code>
     *
     * @param artistActorNetworkServiceRecord
     * @return String message content
     */
    public static String encodeMSjContentChatMetadataTransmit(ArtistActorNetworkServiceRecord artistActorNetworkServiceRecord){

        String contemnt = "";

        /*
         * Create the json object
         */
        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(TransmissionJsonAttNames.METADATA, artistActorNetworkServiceRecord.toXML());

        return gson.toJson(jsonObjectContent);
    }

    /**
     * Decode a FermatMessage
     * @param fermatMessage
     * @return
     */
    public static JsonObject decodeMsjContent(FermatMessage fermatMessage){
        return parser.parse(fermatMessage.getContent()).getAsJsonObject();
    }
}
