/*
 * @#EncodeMsjContent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.enums.DigitalAssetMetadataTransactionType;
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
     * @param digitalAssetMetadataToSend
     * @return String message content
     */
    public static String encodeMSjContentDigitalAssetMetadataTransmit(DigitalAssetMetadata digitalAssetMetadataToSend, PlatformComponentType senderType, PlatformComponentType receiverType){

        String contemnt = "";

        /*
         * Create the json object
         */
        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(AssetTransmissionJsonAttNames.MSJ_CONTENT_TYPE, DigitalAssetMetadataTransactionType.META_DATA_TRANSMIT.toString());
        jsonObjectContent.addProperty(AssetTransmissionJsonAttNames.DIGITAL_ASSET_METADATA, digitalAssetMetadataToSend.toString());
        jsonObjectContent.addProperty(AssetTransmissionJsonAttNames.SENDER_TYPE, senderType.toString());
        jsonObjectContent.addProperty(AssetTransmissionJsonAttNames.RECEIVER_TYPE, receiverType.toString());

        return gson.toJson(jsonObjectContent);
    }

    /**
     * Construct the content of the message fot the type <code>DigitalAssetMetadataTransactionType.TRANSACTION_STATUS_UPDATE</code>
     *
     * @param transactionId
     * @param newDistributionStatus
     * @return String message content
     */
    public static String encodeMSjContentTransactionNewStatusNotification(String transactionId, DistributionStatus newDistributionStatus, PlatformComponentType senderType, PlatformComponentType receiverType){

        /*
         * Create the json object
         */
        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(AssetTransmissionJsonAttNames.MSJ_CONTENT_TYPE, DigitalAssetMetadataTransactionType.TRANSACTION_STATUS_UPDATE.toString());
        jsonObjectContent.addProperty(AssetTransmissionJsonAttNames.GENESIS_TRANSACTION, transactionId);
        jsonObjectContent.addProperty(AssetTransmissionJsonAttNames.NEW_DISTRIBUTION_STATUS, gson.toJson(newDistributionStatus));
        jsonObjectContent.addProperty(AssetTransmissionJsonAttNames.SENDER_TYPE, senderType.toString());
        jsonObjectContent.addProperty(AssetTransmissionJsonAttNames.RECEIVER_TYPE, receiverType.toString());

        return gson.toJson(jsonObjectContent);
    }
}
