package com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageNewStatusNotificationException;
import java.sql.Timestamp;

/**
 * Created by Gabriel Araujo on 05/01/16.
 */
public interface ChatManager extends FermatManager, TransactionProtocolManager<ChatMetada> {

    //TODO IN CONSTRUCTION
    void sendChatMetadata(Integer idChat,Integer idObjecto,String localActorType,String localActorPubKey,String remoteActorType,String remoteActorPubKey, String chatName,ChatMessageStatus chatStatus, Timestamp date, Integer idMessage, String message, DistributionStatus distributionStatus) throws CantSendChatMessageMetadataException;

    void sendChatMessageNewStatusNotification() throws CantSendChatMessageNewStatusNotificationException;
}
