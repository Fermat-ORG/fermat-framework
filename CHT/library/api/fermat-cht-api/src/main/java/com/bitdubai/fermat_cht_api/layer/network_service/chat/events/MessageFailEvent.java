package com.bitdubai.fermat_cht_api.layer.network_service.chat.events;

import com.bitdubai.fermat_cht_api.all_definition.events.AbstractCHTFermatEvent;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;

import java.util.UUID;

/**
 * Created by Gabriel Araujo on 24/08/16.
 */
public class MessageFailEvent extends AbstractCHTFermatEvent {

    public MessageFailEvent(EventType eventType) {
        super(eventType);
    }

    private UUID messageId;

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }
}
