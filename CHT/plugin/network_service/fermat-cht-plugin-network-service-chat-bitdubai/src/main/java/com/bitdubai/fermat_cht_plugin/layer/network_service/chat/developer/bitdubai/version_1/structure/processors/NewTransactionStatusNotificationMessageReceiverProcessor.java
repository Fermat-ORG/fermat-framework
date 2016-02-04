/*
 * @#NewTransactionStatusNotificationMessageReceiverProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingNewChatStatusUpdate;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.NetworkServiceChatNetworkServicePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatMetadataTransactionRecord;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatTransmissionJsonAttNames;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;

import com.google.gson.JsonObject;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.processors.NewTransactionStatusNotificationMessageReceiverProcessor</code> is
 * that implement the logic when a Transaction New Status Notification Message is Receiver<p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NewTransactionStatusNotificationMessageReceiverProcessor extends FermatMessageProcessor {

    /**
     * Constructor with parameters
     * @param chatNetworkServicePluginRoot
     */
    public NewTransactionStatusNotificationMessageReceiverProcessor(NetworkServiceChatNetworkServicePluginRoot chatNetworkServicePluginRoot) {
        super(chatNetworkServicePluginRoot);
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
             * Get the XML representation of the Chat MetaData
             */


            DistributionStatus distributionStatus   = (jsonMsjContent.has(ChatTransmissionJsonAttNames.DISTRIBUTION_STATUS)) ? gson.fromJson(jsonMsjContent.get(ChatTransmissionJsonAttNames.DISTRIBUTION_STATUS).getAsString(), DistributionStatus.class) : null;
            MessageStatus messageStatus             = (jsonMsjContent.has(ChatTransmissionJsonAttNames.MESSAGE_STATUS)) ? gson.fromJson(jsonMsjContent.get(ChatTransmissionJsonAttNames.MESSAGE_STATUS).getAsString(), MessageStatus.class) : null;
            UUID chatID                             = gson.fromJson(jsonMsjContent.get(ChatTransmissionJsonAttNames.ID_CHAT).getAsString(), UUID.class);
            UUID messageID                          = gson.fromJson(jsonMsjContent.get(ChatTransmissionJsonAttNames.MESSAGE_ID).getAsString(), UUID.class);

            /*
             * Get the ChatMetadataTransactionRecord
             */

            String transactionHash = CryptoHasher.performSha256(chatID.toString() + messageID.toString());
            ChatMetadataTransactionRecord chatMetadataTransactionRecord =  getNetworkServiceChatNetworkServicePluginRoot().getChatMetaDataDao().findByTransactionHash(transactionHash);

            if(chatMetadataTransactionRecord != null){

                if(distributionStatus != null)
                    chatMetadataTransactionRecord.setDistributionStatus(distributionStatus);
                if(messageStatus != null)
                    chatMetadataTransactionRecord.setMessageStatus(messageStatus);
                chatMetadataTransactionRecord.setProcessed(ChatMetadataTransactionRecord.NO_PROCESSED);
                getNetworkServiceChatNetworkServicePluginRoot().getChatMetaDataDao().update(chatMetadataTransactionRecord);


                /*
                * Mark the message as read
                */

                ((FermatMessageCommunication)fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
                ((CommunicationNetworkServiceConnectionManager) getNetworkServiceChatNetworkServicePluginRoot().getNetworkServiceConnectionManager()).getIncomingMessageDao().update(fermatMessage);

                /*
                * Notify to the interested
                */

                IncomingNewChatStatusUpdate event = (IncomingNewChatStatusUpdate) getNetworkServiceChatNetworkServicePluginRoot().getEventManager().getNewEvent(EventType.INCOMING_STATUS);
                event.setChatId(chatMetadataTransactionRecord.getChatId());
                event.setSource(NetworkServiceChatNetworkServicePluginRoot.EVENT_SOURCE);
                getNetworkServiceChatNetworkServicePluginRoot().getEventManager().raiseEvent(event);
             //   System.out.println("NetworkServiceChatNetworkServicePluginRoot - Incoming Status fired!");

            }

        } catch (Exception e) {
            getNetworkServiceChatNetworkServicePluginRoot().getErrorManager().reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }


    }

    @Override
    public ChatMessageTransactionType getChatMessageTransactionType() {
        return ChatMessageTransactionType.TRANSACTION_STATUS_UPDATE;
    }

}
