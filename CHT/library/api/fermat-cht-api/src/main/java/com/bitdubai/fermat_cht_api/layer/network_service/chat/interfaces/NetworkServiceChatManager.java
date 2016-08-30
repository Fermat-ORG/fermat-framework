package com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageNewStatusNotificationException;

import java.util.UUID;

/**
 * Created by Gabriel Araujo on 05/01/16.
 */
public interface NetworkServiceChatManager extends FermatManager {

    void sendWritingStatus(final String localActorPubKey, final String remoteActorPubKey, UUID chatId) throws CantSendChatMessageNewStatusNotificationException;

    void sendMessageMetadata(String localActorPubKey, String remoteActorPubKey, MessageMetadata messageMetadata) throws CantSendChatMessageMetadataException, IllegalArgumentException;
    /**
     * @param localActorPubKey
     * @param remoteActorPubKey
     * @param messageStatus
     * @param messageID
     * @throws CantSendChatMessageNewStatusNotificationException
     */
    void sendMessageStatusUpdate(String localActorPubKey, String remoteActorPubKey, MessageStatus messageStatus, UUID messageID) throws CantSendChatMessageNewStatusNotificationException;

}
