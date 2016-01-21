package com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageNewStatusNotificationException;
import java.util.UUID;

/**
 * Created by Gabriel Araujo on 05/01/16.
 */
public interface ChatManager extends FermatManager, TransactionProtocolManager<ChatMetadata> {

   // String getNetWorkServicePublicKey();


    //TODO IN CONSTRUCTION
    void sendChatMetadata(String localActorPubKey, String remoteActorPubKey, ChatMetadata chatMetadata) throws CantSendChatMessageMetadataException;

    void sendChatMessageNewStatusNotification(String localActorPubKey, PlatformComponentType senderType, String remoteActorPubKey, PlatformComponentType receiverType, DistributionStatus newDistributionStatus, UUID chatId) throws CantSendChatMessageNewStatusNotificationException;
}
