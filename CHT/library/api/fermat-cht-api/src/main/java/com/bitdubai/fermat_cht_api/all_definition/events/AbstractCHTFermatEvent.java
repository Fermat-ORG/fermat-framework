package com.bitdubai.fermat_cht_api.all_definition.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/01/16.
 */
public class AbstractCHTFermatEvent implements FermatEvent {

    private EventType eventType;

    private EventSource eventSource;

    private ChatMetadata chatMetadata;

    /**
     * Represents the chatId
     */
    private UUID chatId;

    private UUID messageId;

    public AbstractCHTFermatEvent(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public FermatEventEnum getEventType() {
        return this.eventType;
    }

    @Override
    public void setSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    @Override
    public EventSource getSource() {
        return this.eventSource;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public ChatMetadata getChatMetadata() {
        return chatMetadata;
    }

    public void setChatMetadata(ChatMetadata chatMetadata) {
        this.chatMetadata = chatMetadata;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("AbstractCHTFermatEvent{")
                .append("eventType=").append(eventType)
                .append(", eventSource=").append(eventSource)
                .append(", chatId=").append(chatId)
                .append('}').toString();
    }
}
