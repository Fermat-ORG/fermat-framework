/*
 * @#NewTransactionStatusNotificationMessageReceiverProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingNewChatStatusUpdate;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.ChatPluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.NetworkServiceChatNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatMetadataTransactionRecord;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatTransmissionJsonAttNames;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.UUID;

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


        try {


            /*
             * Get the XML representation of the Chat MetaData
             */


            DistributionStatus distributionStatus = gson.fromJson(jsonMsjContent.get(ChatTransmissionJsonAttNames.DISTRIBUTION_STATUS).getAsString(), DistributionStatus.class);
            UUID idChat                             = gson.fromJson(jsonMsjContent.get(ChatTransmissionJsonAttNames.ID_CHAT).getAsString(), UUID.class);
            PlatformComponentType senderType      = gson.fromJson(jsonMsjContent.get(ChatTransmissionJsonAttNames.SENDER_TYPE).getAsString(), PlatformComponentType.class);
            PlatformComponentType receiverType    = gson.fromJson(jsonMsjContent.get(ChatTransmissionJsonAttNames.RECEIVER_TYPE).getAsString(), PlatformComponentType.class);


            /*
             * Get the digitalAssetMetadataTransaction
             */

            List<ChatMetadataTransactionRecord> list =  getChatPluginRoot().getChatMetaDataDao().findAll(NetworkServiceChatNetworkServiceDatabaseConstants.CHAT_IDCHAT_COLUMN_NAME, idChat.toString());

            ChatMetadataTransactionRecord chatMetadataTransactionRecord = null;
            if(list.size()>0){
                for (int i = 0; i < list.size(); i++) {
                    chatMetadataTransactionRecord = list.get(i);
                    //chatMetadataTransactionRecord.setDistributionStatus(DistributionStatus.DELIVERED);
                   // chatMetadataTransactionRecord.setChatMessageStatus(ChatMessageStatus.CREATED_CHAT);

                getChatPluginRoot().getChatMetaDataDao().update(chatMetadataTransactionRecord);


                /*
                * Mark the message as read
                */

                ((FermatMessageCommunication)fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
                ((CommunicationNetworkServiceConnectionManager) getChatPluginRoot().getNetworkServiceConnectionManager()).getIncomingMessageDao().update(fermatMessage);

                /*
                * Notify to the interested
                */
                    IncomingNewChatStatusUpdate event = (IncomingNewChatStatusUpdate) getChatPluginRoot().getEventManager().getNewEvent(EventType.INCOMING_STATUS);
                    event.setChatId(chatMetadataTransactionRecord.getChatId());
                event.setSource(ChatPluginRoot.EVENT_SOURCE);
                getChatPluginRoot().getEventManager().raiseEvent(event);
                    System.out.println("ChatPluginRoot - Incoming Status fired!");


                }
            }


//            if (list != null && !list.isEmpty()){
//                chatMetadataTransactionRecord = list.get(0);
//
//                chatMetadataTransactionRecord.setIdChat(idChat);
//                chatMetadataTransactionRecord.setObjectId(idObject);
//                chatMetadataTransactionRecord.setReceiverId(fermatMessage.getReceiver());
//                chatMetadataTransactionRecord.setReceiverType(receiverType);
//                chatMetadataTransactionRecord.setDistributionStatus(distributionStatus);
//                chatMetadataTransactionRecord.setProcessed(DigitalAssetMetadataTransactionImpl.NO_PROCESSED);
//                chatMetadataTransactionRecord.setType(DigitalAssetMetadataTransactionType.TRANSACTION_STATUS_UPDATE);
//
//
//                /*
//                * Save into data base like a new transaction
//                */
//
//                getChatPluginRoot().getDigitalAssetMetaDataTransactionDao().create(chatMetadataTransactionRecord);
//
//
//                /*
//                * Mark the message as read
//                */
//
//                ((FermatMessageCommunication)fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
//                ((CommunicationNetworkServiceConnectionManager) getChatPluginRoot().getNetworkServiceConnectionManager()).getIncomingMessageDao().update(fermatMessage);
//
//
//                /*
//                * Notify to the interested
//                */
//
//                FermatEvent event =  getChatPluginRoot().getEventManager().getNewEvent(EventType.RECEIVED_NEW_TRANSACTION_STATUS_NOTIFICATION);
//                event.setSource(AssetTransmissionNetworkServicePluginRoot.EVENT_SOURCE);
//                getChatPluginRoot().getEventManager().raiseEvent(event);
//            }
        } catch (Exception e) {
            getChatPluginRoot().getErrorManager().reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }


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
