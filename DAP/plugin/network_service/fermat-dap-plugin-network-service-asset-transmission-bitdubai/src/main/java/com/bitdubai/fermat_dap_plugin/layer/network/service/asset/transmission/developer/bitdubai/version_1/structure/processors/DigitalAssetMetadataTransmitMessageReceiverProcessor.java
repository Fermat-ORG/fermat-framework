/*
 * @#DigitalAssetMetadataTransmitMessageReceiverProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.AssetTransmissionJsonAttNames;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.AssetTransmissionMsjContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.google.gson.JsonObject;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.processors.DigitalAssetMetadataTransmitMessageReceiverProcessor</code> is
 * that implement the logic when a Digital Asset Metadata Transmit Message is Receiver
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DigitalAssetMetadataTransmitMessageReceiverProcessor extends FermatMessageProcessor {

    /**
     * (non-javadoc)
     *
     * @see FermatMessageProcessor#processingMessage(FermatMessage, JsonObject)
     */
    @Override
    public void processingMessage(FermatMessage fermatMessage, JsonObject jsonMsjContent) {

        /*
         * Get the XML representation of the Digital Asset Metadata
         */
        String digitalAssetMetadataXml = jsonMsjContent.get(AssetTransmissionJsonAttNames.DIGITAL_ASSET_METADATA).getAsString();

        /*
         * Convert the xml to object
         */
        DigitalAssetMetadata digitalAssetMetadata = (DigitalAssetMetadata) XMLParser.parseXML(digitalAssetMetadataXml, DigitalAssetMetadata.class);

        /*
         * Put into a event a
         */


    }

    /**
     * (non-javadoc)
     *
     * @see FermatMessageProcessor#getAssetTransmissionMsjContentType()
     */
    @Override
    public AssetTransmissionMsjContentType getAssetTransmissionMsjContentType() {
        return AssetTransmissionMsjContentType.META_DATA_TRANSMIT;
    }
}
