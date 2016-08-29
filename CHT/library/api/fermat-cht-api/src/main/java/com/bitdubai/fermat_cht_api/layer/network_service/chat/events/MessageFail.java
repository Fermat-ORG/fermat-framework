package com.bitdubai.fermat_cht_api.layer.network_service.chat.events;

import com.bitdubai.fermat_cht_api.all_definition.events.AbstractCHTFermatEvent;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;

/**
 * Created by Gabriel Araujo on 24/08/16.
 */
public class MessageFail extends AbstractCHTFermatEvent {

    public MessageFail(EventType eventType) {
        super(eventType);
    }

    private ChatMessageTransactionType chatMessageTransactionType;

    public ChatMessageTransactionType getChatMessageTransactionType() {
        return chatMessageTransactionType;
    }

    public void setChatMessageTransactionType(ChatMessageTransactionType chatMessageTransactionType) {
        this.chatMessageTransactionType = chatMessageTransactionType;
    }
}
