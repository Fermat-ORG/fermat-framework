/*
 * @#EncodeMsjContent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatProtocolState;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.UUID;

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
     * Construct the content of the message fot the type <code>ChatMessageTransactionType.CHAT_METADATA_TRASMIT</code>
     *
     * @param chatMetadataRecord
     * @return String message content
     */
    public static String encodeMSjContentChatMetadataTransmit(ChatMetadataRecord chatMetadataRecord, PlatformComponentType senderType, PlatformComponentType receiverType) {

        String contemnt = "";

        /*         * Create the json object
         */
        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.ID_CHAT, chatMetadataRecord.getChatId().toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE, ChatMessageTransactionType.CHAT_METADATA_TRASMIT.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.CHAT_METADATA, chatMetadataRecord.toJson());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.SENDER_TYPE, senderType.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RECEIVER_TYPE, receiverType.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.DISTRIBUTION_STATUS, chatMetadataRecord.getDistributionStatus().toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.CHAT_STATUS, chatMetadataRecord.getChatMessageStatus().toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MESSAGE_STATUS, chatMetadataRecord.getMessageStatus().toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.TRANSACTION_ID, chatMetadataRecord.getTransactionId().toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RESPONSE_TO, chatMetadataRecord.getResponseToNotification());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RESPONSE_TO, chatMetadataRecord.getResponseToNotification());

        return gson.toJson(jsonObjectContent);
    }

    /**
     * Construct the content of the message fot the type <code>ChatMessageTransactionType.TRANSACTION_STATUS_UPDATE</code>
     *
     * @param responseTo
     * @param transactionID
     * @param newDistributionStatus
     * @return String message content
     */
    public static String encodeMSjContentTransactionNewStatusNotification(String responseTo, String transactionID, DistributionStatus newDistributionStatus, PlatformComponentType senderType, PlatformComponentType receiverType) {


        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE, ChatMessageTransactionType.TRANSACTION_STATUS_UPDATE.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.TRANSACTION_ID, transactionID);
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RESPONSE_TO, responseTo);
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.DISTRIBUTION_STATUS, gson.toJson(newDistributionStatus));
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.SENDER_TYPE, senderType.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RECEIVER_TYPE, receiverType.toString());

        return gson.toJson(jsonObjectContent);
    }

    /**
     * Construct the content of the message fot the type <code>ChatMessageTransactionType.TRANSACTION_STATUS_UPDATE</code>
     *
     * @param responseTo
     * @param chatProtocolState
     * @param senderType
     * @param receiverType
     * @return
     */
    public static String encodeMSjContentTransactionNewStatusNotification(String responseTo, ChatProtocolState chatProtocolState, PlatformComponentType senderType, PlatformComponentType receiverType) {


        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE, ChatMessageTransactionType.TRANSACTION_STATUS_UPDATE.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RESPONSE_TO, responseTo);
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.PROTOCOL_STATE, gson.toJson(chatProtocolState));
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.DISTRIBUTION_STATUS, gson.toJson(DistributionStatus.DELIVERED));
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.SENDER_TYPE, senderType.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RECEIVER_TYPE, receiverType.toString());

        return gson.toJson(jsonObjectContent);
    }

    /**
     * Construct the content of the message fot the type <code>ChatMessageTransactionType.TRANSACTION_STATUS_UPDATE</code>
     *
     * @param responseTo
     * @param messageStatus
     * @param senderType
     * @param receiverType
     * @return
     */
    public static String encodeMSjContentTransactionNewStatusNotification(String responseTo, MessageStatus messageStatus, PlatformComponentType senderType, PlatformComponentType receiverType, UUID chatId) {


        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE, ChatMessageTransactionType.TRANSACTION_STATUS_UPDATE.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RESPONSE_TO, responseTo);
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MESSAGE_STATUS, gson.toJson(messageStatus));
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.DISTRIBUTION_STATUS, gson.toJson(DistributionStatus.DELIVERED));
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.SENDER_TYPE, senderType.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RECEIVER_TYPE, receiverType.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.ID_CHAT, chatId.toString());

        return gson.toJson(jsonObjectContent);
    }

    public static String encodeMSjContentTransactionWritingNotification(String responseTo, PlatformComponentType senderType, PlatformComponentType receiverType, UUID chatId) {


        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE, ChatMessageTransactionType.TRANSACTION_WRITING_STATUS.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RESPONSE_TO, responseTo);
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.DISTRIBUTION_STATUS, gson.toJson(DistributionStatus.DELIVERED));
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.SENDER_TYPE, senderType.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RECEIVER_TYPE, receiverType.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.ID_CHAT, chatId.toString());

        return gson.toJson(jsonObjectContent);
    }

//    public static String encodeMSjContentTransactionOnlineNotification(String responseTo, PlatformComponentType senderType, PlatformComponentType receiverType, UUID chatId) {
//
//
//        Gson gson = new Gson();
//        JsonObject jsonObjectContent = new JsonObject();
//        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE, ChatMessageTransactionType.TRANSACTION_ONLINE_STATUS.toString());
//        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RESPONSE_TO,responseTo);
//        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.DISTRIBUTION_STATUS,gson.toJson(DistributionStatus.DELIVERED));
//        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.SENDER_TYPE, senderType.toString());
//        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RECEIVER_TYPE, receiverType.toString());
//        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.ID_CHAT, chatId.toString());
//
//        return gson.toJson(jsonObjectContent);
//    }

    /**
     * Decode a FermatMessage
     *
     * @param fermatMessage
     * @return
     */
    public static JsonObject decodeMsjContent(FermatMessage fermatMessage) {
        return parser.parse(fermatMessage.getContent()).getAsJsonObject();
    }

    public static JsonObject decodeMsjContent(NetworkServiceMessage fermatMessage) {
        return parser.parse(fermatMessage.getContent()).getAsJsonObject();
    }
}
