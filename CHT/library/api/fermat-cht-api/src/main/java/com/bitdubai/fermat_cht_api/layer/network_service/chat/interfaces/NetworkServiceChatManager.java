package com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
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
public interface NetworkServiceChatManager extends FermatManager {

    /**
     * @return
     */
    String getNetWorkServicePublicKey();

    //TODO JOSE UTILIZA ESTE METODO PARA MARCAR DELIVERED EL CHAT Y MENSAJE EN EL PRIMER ENVIO
    void sendChatMessageNewStatusNotification(final String localActorPubKey, final PlatformComponentType senderType, final String remoteActorPubKey, final PlatformComponentType receiverType, DistributionStatus newDistributionStatus, MessageStatus messageStatus, UUID chatId, UUID messageId) throws CantSendChatMessageNewStatusNotificationException;

    void sendWritingStatus(final String localActorPubKey, final PlatformComponentType senderType, final String remoteActorPubKey, final PlatformComponentType receiverType, UUID chatId) throws CantSendChatMessageNewStatusNotificationException;

//    void sendOnlineStatus(final String localActorPubKey, final PlatformComponentType senderType, final String remoteActorPubKey, final PlatformComponentType receiverType, UUID chatId) throws CantSendChatMessageNewStatusNotificationException;

    /**
     * @return
     * @throws CantRequestListException
     */
    List<String> getRegisteredPubliKey() throws CantRequestListException;

    /**
     * Sends for first time the chat data and message data.
     * @param localActorPubKey
     * @param remoteActorPubKey
     * @param chatMetadata
     * @throws CantSendChatMessageMetadataException
     * @throws IllegalArgumentException
     */
    //TODO ESTE PARA INICIAR UN CHAT
    void sendChatMetadata(String localActorPubKey, String remoteActorPubKey, ChatMetadata chatMetadata, MessageMetadata messageMetadata) throws CantSendChatMessageMetadataException, IllegalArgumentException;

    //TODO ESTE PARA ENVIAR LOS MENSAJES
    void sendMessageMetadata(String localActorPubKey, String remoteActorPubKey, MessageMetadata messageMetadata) throws CantSendChatMessageMetadataException, IllegalArgumentException;
    /**
     * @param localActorPubKey
     * @param senderType
     * @param remoteActorPubKey
     * @param receiverType
     * @param messageStatus
     * @param chatId
     * @param messageID
     * @throws CantSendChatMessageNewStatusNotificationException
     */
    //TODO JOSE DEBES USAR ESTE METODO PARA ENVIAR MENSAJES DE READ O DELIVERED LOS MENSAJES
    void sendMessageStatusUpdate(String localActorPubKey, PlatformComponentType senderType, String remoteActorPubKey, PlatformComponentType receiverType,DistributionStatus distributionStatus, MessageStatus messageStatus, UUID chatId, UUID messageID) throws CantSendChatMessageNewStatusNotificationException;

//    /**
//     *
//     * @param chatMetadata
//     * @throws CantSendChatMessageNewStatusNotificationException
//     */
//    void sendMessageChatBroadcast(ChatMetadata chatMetadata) throws CantSendChatMessageMetadataException;
}
