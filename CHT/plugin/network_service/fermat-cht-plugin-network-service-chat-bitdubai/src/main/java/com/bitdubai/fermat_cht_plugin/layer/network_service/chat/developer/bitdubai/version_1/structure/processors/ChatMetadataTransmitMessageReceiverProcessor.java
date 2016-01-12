/*
 * @#DigitalAssetMetadataTransmitMessageReceiverProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.ChatPluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatMetadaTransactionRecord;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatTransmissionJsonAttNames;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.google.gson.JsonObject;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.processors.ChatMetadataTransmitMessageReceiverProcessor</code> is
 * that implement the logic when a Digital Asset Metadata Transmit Message is Receiver
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ChatMetadataTransmitMessageReceiverProcessor extends FermatMessageProcessor {

    /**
     * Constructor with parameters
     * @param chatPluginRoot
     */
    public ChatMetadataTransmitMessageReceiverProcessor(ChatPluginRoot chatPluginRoot) {
        super(chatPluginRoot);
    }

    /**
     * (non-javadoc)
     *
     * @see FermatMessageProcessor#processingMessage(FermatMessage, JsonObject)
     */
    @Override
    public void processingMessage(FermatMessage fermatMessage, JsonObject jsonMsjContent) {

        try {

            /*
             * Get the XML representation of the Digital Asset Metadata
             */
            String digitalAssetMetadataXml     = jsonMsjContent.get(ChatTransmissionJsonAttNames.CHAT_METADATA).getAsString();
            PlatformComponentType senderType   = gson.fromJson(jsonMsjContent.get(ChatTransmissionJsonAttNames.SENDER_TYPE).getAsString(), PlatformComponentType.class);
            PlatformComponentType receiverType = gson.fromJson(jsonMsjContent.get(ChatTransmissionJsonAttNames.RECEIVER_TYPE).getAsString(), PlatformComponentType.class);

            /*
             * Convert the xml to object
             */
            ChatMetadaTransactionRecord chatMetadaTransactionRecord = (ChatMetadaTransactionRecord) XMLParser.parseXML(digitalAssetMetadataXml, new ChatMetadaTransactionRecord());


            /*
             * Construct a new digitalAssetMetadataTransaction

            ChatMetadaTransactionRecord chatMetadaTransactionRecord1 = new DigitalAssetMetadataTransactionImpl();
            chatMetadaTransactionRecord1.setGenesisTransaction(chatMetadaTransactionRecord.getGenesisTransaction());
            chatMetadaTransactionRecord1.setSenderId(fermatMessage.getSender());
            chatMetadaTransactionRecord1.setSenderType(senderType);
            chatMetadaTransactionRecord1.setReceiverId(fermatMessage.getReceiver());
            chatMetadaTransactionRecord1.setReceiverType(receiverType);
            chatMetadaTransactionRecord1.setDigitalAssetMetadata(chatMetadaTransactionRecord);
            chatMetadaTransactionRecord1.setDistributionStatus(DistributionStatus.ASSET_DISTRIBUTED);
            chatMetadaTransactionRecord1.setType(DigitalAssetMetadataTransactionType.META_DATA_TRANSMIT);
            chatMetadaTransactionRecord1.setProcessed(DigitalAssetMetadataTransactionImpl.NO_PROCESSED);
            */
            chatMetadaTransactionRecord.setDistributionStatus(DistributionStatus.DELIVERED);
            /*
             * Save into data base for audit control
             */
            getChatPluginRoot().getChatMetaDataDao().create(chatMetadaTransactionRecord);

            /*
             * Mark the message as read
             */
            ((FermatMessageCommunication)fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
            ((CommunicationNetworkServiceConnectionManager) getChatPluginRoot().getNetworkServiceConnectionManager()).getIncomingMessageDao().update(fermatMessage);

            /*
             * Notify to the interested
             */
            FermatEvent event =  getChatPluginRoot().getEventManager().getNewEvent(EventType.INCOMING_CHAT);
            event.setSource(ChatPluginRoot.EVENT_SOURCE);
            getChatPluginRoot().getEventManager().raiseEvent(event);

        } catch (Exception e) {
            getChatPluginRoot().getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

    }

    @Override
    public ChatMessageTransactionType getChatMessageTransactionType() {
        return ChatMessageTransactionType.TRANSACTION_STATUS_UPDATE;
    }


}
