/*
 * @#NewTransactionStatusNotificationMessageReceiverProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.ChatPluginRoot;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.google.gson.JsonObject;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.processor.NewTransactionStatusNotificationMessageReceiverProcessor</code> is
 * that implement the logic when a Transaction New Status Notification Message is Receiver<p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NewTransactionStatusNotificationMessageReceiverProcessor extends FermatMessageProcessor {

    /**
     * Constructor with parameters
     * @param chatPluginRoot
     */
    public NewTransactionStatusNotificationMessageReceiverProcessor(ChatPluginRoot chatPluginRoot) {
        super(chatPluginRoot);
    }

    /**
     * (non-javadoc)
     *
     * @see FermatMessageProcessor#processingMessage(FermatMessage, JsonObject)
     */

    @Override
    public void processingMessage(FermatMessage fermatMessage, JsonObject jsonMsjContent) {
/*

        try {

            */
/*
             * Get the XML representation of the Digital Asset Metadata
             *//*

            String genesisTransaction             = jsonMsjContent.get(AssetTransmissionJsonAttNames.GENESIS_TRANSACTION).getAsString();
            DistributionStatus distributionStatus = gson.fromJson(jsonMsjContent.get(AssetTransmissionJsonAttNames.NEW_DISTRIBUTION_STATUS).getAsString(), DistributionStatus.class);
            PlatformComponentType senderType      = gson.fromJson(jsonMsjContent.get(AssetTransmissionJsonAttNames.SENDER_TYPE).getAsString(), PlatformComponentType.class);
            PlatformComponentType receiverType    = gson.fromJson(jsonMsjContent.get(AssetTransmissionJsonAttNames.RECEIVER_TYPE).getAsString(), PlatformComponentType.class);

            */
/*
             * Get the digitalAssetMetadataTransaction
             *//*

            List<DigitalAssetMetadataTransactionImpl> list =  getChatPluginRoot().getDigitalAssetMetaDataTransactionDao().findAll(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_GENESIS_TRANSACTION_COLUMN_NAME, genesisTransaction);

            DigitalAssetMetadataTransactionImpl digitalAssetMetadataTransactionImpl = null;

            if (list != null && !list.isEmpty()){
                digitalAssetMetadataTransactionImpl = list.get(0);
                digitalAssetMetadataTransactionImpl.setSenderId(fermatMessage.getSender());
                digitalAssetMetadataTransactionImpl.setSenderType(senderType);
                digitalAssetMetadataTransactionImpl.setReceiverId(fermatMessage.getReceiver());
                digitalAssetMetadataTransactionImpl.setReceiverType(receiverType);
                digitalAssetMetadataTransactionImpl.setDistributionStatus(distributionStatus);
                digitalAssetMetadataTransactionImpl.setProcessed(DigitalAssetMetadataTransactionImpl.NO_PROCESSED);
                digitalAssetMetadataTransactionImpl.setType(DigitalAssetMetadataTransactionType.TRANSACTION_STATUS_UPDATE);

                */
/*
                * Save into data base like a new transaction
                *//*

                getChatPluginRoot().getDigitalAssetMetaDataTransactionDao().create(digitalAssetMetadataTransactionImpl);

                */
/*
                * Mark the message as read
                *//*

                ((FermatMessageCommunication)fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
                ((CommunicationNetworkServiceConnectionManager) getChatPluginRoot().getNetworkServiceConnectionManager()).getIncomingMessageDao().update(fermatMessage);

                */
/*
                * Notify to the interested
                *//*

                FermatEvent event =  getChatPluginRoot().getEventManager().getNewEvent(EventType.RECEIVED_NEW_TRANSACTION_STATUS_NOTIFICATION);
                event.setSource(AssetTransmissionNetworkServicePluginRoot.EVENT_SOURCE);
                getChatPluginRoot().getEventManager().raiseEvent(event);
            }
        } catch (Exception e) {
            getChatPluginRoot().getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
*/

    }

    @Override
    public ChatMessageTransactionType getChatMessageTransactionType() {
        return ChatMessageTransactionType.TRANSACTION_STATUS_UPDATE;
    }

    /**
     * (non-javadoc)
     *
     * @see FermatMessageProcessor#getDigitalAssetMetadataTransactionType()
     */

}
