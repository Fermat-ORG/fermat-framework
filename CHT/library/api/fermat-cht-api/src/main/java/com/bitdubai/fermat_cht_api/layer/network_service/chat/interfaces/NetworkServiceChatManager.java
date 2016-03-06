package com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageNewStatusNotificationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo on 05/01/16.
 */
public interface NetworkServiceChatManager extends FermatManager, TransactionProtocolManager<ChatMetadata> {

    /**
     *
     * @return
     */
    String getNetWorkServicePublicKey();

    /**
     * Notifies TX that the message had been recieved.
     * @param localActorPubKey
     * @param senderType
     * @param remoteActorPubKey
     * @param receiverType
     * @param newDistributionStatus
     * @param chatId
     * @param messageID
     * @throws CantSendChatMessageNewStatusNotificationException
     */
    void sendChatMessageNewStatusNotification(String localActorPubKey, PlatformComponentType senderType, String remoteActorPubKey, PlatformComponentType receiverType, DistributionStatus newDistributionStatus, UUID chatId, UUID messageID) throws CantSendChatMessageNewStatusNotificationException;

    /**
     *
     * @return
     * @throws CantRequestListException
     */
    List<String> getRegisteredPubliKey() throws CantRequestListException;

    /**
     *
     * @param localActorPubKey
     * @param remoteActorPubKey
     * @param chatMetadata
     * @throws CantSendChatMessageMetadataException
     * @throws IllegalArgumentException
     */
    void sendChatMetadata(String localActorPubKey, String remoteActorPubKey, ChatMetadata chatMetadata) throws CantSendChatMessageMetadataException, IllegalArgumentException;

    /**
     * 
     * @param localActorPubKey
     * @param senderType
     * @param remoteActorPubKey
     * @param receiverType
     * @param messageStatus
     * @param chatId
     * @param messageID
     * @throws CantSendChatMessageNewStatusNotificationException
     */
    void sendMessageStatusUpdate(String localActorPubKey, PlatformComponentType senderType, String remoteActorPubKey, PlatformComponentType receiverType, MessageStatus messageStatus, UUID chatId, UUID messageID) throws CantSendChatMessageNewStatusNotificationException;
}
