/*
 * @#EncodeMsjContent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.entities.NetworkServiceMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.UUID;

/**
 * Created by Gabriel Araujo
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class EncodeMsjContent {

    private static JsonParser parser = new JsonParser();

    /**
     * Construct the content of the message fot the type <code>ChatMessageTransactionType.CHAT_METADATA_TRASMIT</code>
     *
     * @param messageMetadataRecord
     * @return String message content
     */
    public static String encodeMSjContentMessageMetadataTransmit(MessageMetadataRecord messageMetadataRecord) {

        /*         * Create the json object
         */
        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE, ChatMessageTransactionType.MESSAGE_METADATA_TRANSMIT.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MESSAGE_METADATA, messageMetadataRecord.toJson());

        return gson.toJson(jsonObjectContent);
    }

    /**
     * Construct the content of the message fot the type <code>ChatMessageTransactionType.TRANSACTION_STATUS_UPDATE</code>
     *
     * @param messageId
     * @param messageStatus
     * @return
     */
    public static String encodeMSjContentTransactionNewStatusNotification(String messageId, MessageStatus messageStatus) {


        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE, ChatMessageTransactionType.TRANSACTION_STATUS_UPDATE.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MESSAGE_ID, messageId);
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MESSAGE_STATUS, gson.toJson(messageStatus));

        return gson.toJson(jsonObjectContent);
    }

    public static String encodeMSjContentTransactionWritingNotification( String localPublicKey) {


        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE, ChatMessageTransactionType.TRANSACTION_WRITING_STATUS.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.SENDER_PUBLIC_KEY, localPublicKey);

        return gson.toJson(jsonObjectContent);
    }

    public static JsonObject decodeMsjContent(NetworkServiceMessage fermatMessage) {
        return parser.parse(fermatMessage.getContent()).getAsJsonObject();
    }
}
