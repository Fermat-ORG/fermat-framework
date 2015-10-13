/*
 * @#TransactionNewStatusNotificationMessageReceiverProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.AssetTransmissionMsjContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.google.gson.JsonObject;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.processor.TransactionNewStatusNotificationMessageReceiverProcessor</code> is
 * that implement the logic when a Transaction New Status Notification Message is Receiver<p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TransactionNewStatusNotificationMessageReceiverProcessor extends FermatMessageProcessor {

    /**
     * (non-javadoc)
     *
     * @see FermatMessageProcessor#processingMessage(FermatMessage, JsonObject)
     */
    @Override
    public void processingMessage(FermatMessage fermatMessage, JsonObject jsonMsjContent) {

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
