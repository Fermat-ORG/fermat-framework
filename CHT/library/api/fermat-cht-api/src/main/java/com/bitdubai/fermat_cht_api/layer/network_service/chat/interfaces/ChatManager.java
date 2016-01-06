package com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageNewStatusNotificationException;

/**
 * Created by Gabriel Araujo on 05/01/16.
 */
public interface ChatManager extends FermatManager, TransactionProtocolManager<ChatMessageMetada> {

    //TODO IN CONSTRUCTION
    void sendChatMessageMetadata(String idSender, String idReciever) throws CantSendChatMessageMetadataException;

    void sendChatMessageNewStatusNotification() throws CantSendChatMessageNewStatusNotificationException;
}
