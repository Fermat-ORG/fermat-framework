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
public interface ChatManager extends FermatManager, TransactionProtocolManager<ChatMetada> {

    //TODO IN CONSTRUCTION
    //TODO FROM Manuel: Please, refactor this interface, use UUID instead integer for Id, check the new version of chat middleware interface.
    void sendChatMetadata(String localActorPubKey, String remoteActorPubKey, ChatMetada chatMetada) throws CantSendChatMessageMetadataException;

    void sendChatMessageNewStatusNotification(UUID localActorPubKey, PlatformComponentType senderType, UUID remoteActorPubKey, PlatformComponentType receiverType, DistributionStatus newDistributionStatus) throws CantSendChatMessageNewStatusNotificationException;
}
