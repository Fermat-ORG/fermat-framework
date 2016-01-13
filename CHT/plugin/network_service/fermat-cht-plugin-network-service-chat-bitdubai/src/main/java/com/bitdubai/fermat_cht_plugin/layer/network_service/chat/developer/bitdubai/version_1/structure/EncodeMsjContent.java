/*
 * @#EncodeMsjContent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

    /**
     *  Construct the content of the message fot the type <code>DigitalAssetMetadataTransactionType.META_DATA_TRANSMIT</code>
     *
     * @param chatMetadata
     * @return String message content
     */
    public static String encodeMSjContentChatMetadataTransmit(ChatMetadata chatMetadata, PlatformComponentType senderType, PlatformComponentType receiverType){

        String contemnt = "";

        /*
         * Create the json object
         */
        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE, ChatMessageTransactionType.META_DATA_TRANSMIT.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.CHAT_METADATA, chatMetadata.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.SENDER_TYPE, senderType.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RECEIVER_TYPE, receiverType.toString());

        return gson.toJson(jsonObjectContent);
    }

    /**
     * Construct the content of the message fot the type <code>DigitalAssetMetadataTransactionType.TRANSACTION_STATUS_UPDATE</code>
     *
     * @param genesisTransaction
     * @param newDistributionStatus
     * @return String message content
     */
    /*
    public static String encodeMSjContentTransactionNewStatusNotification(String genesisTransaction, DistributionStatus newDistributionStatus, PlatformComponentType senderType, PlatformComponentType receiverType) {


        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE, DigitalAssetMetadataTransactionType.TRANSACTION_STATUS_UPDATE.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.GENESIS_TRANSACTION, genesisTransaction);
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.NEW_DISTRIBUTION_STATUS, gson.toJson(newDistributionStatus));
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.SENDER_TYPE, senderType.toString());
        jsonObjectContent.addProperty(ChatTransmissionJsonAttNames.RECEIVER_TYPE, receiverType.toString());

        return gson.toJson(jsonObjectContent);
    }*/
}
