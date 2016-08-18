package com.bitdubai.fermat_cht_api.all_definition.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.MessageMetadata;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/01/16.
 */
public class AbstractCHTFermatEvent implements FermatEvent {

    private EventType eventType;

    private EventSource eventSource;

    private ChatMetadata chatMetadata;

    private MessageMetadata messageMetadata;

    /**
     * Represents the chatId
     */
    private UUID chatId;

    private UUID messageId;

    private String senderPk;

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

    public void setSenderPk(String senderPk) {
        this.senderPk = senderPk;
    }

    public String getSenderPk(){
    return senderPk;
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

    public MessageMetadata getMessageMetadata() {
        return messageMetadata;
    }

    public void setMessageMetadata(MessageMetadata messageMetadata) {
        this.messageMetadata = messageMetadata;
    }

    @Override
    public String toString() {
        return "AbstractCHTFermatEvent{" +
                "eventType=" + eventType +
                ", eventSource=" + eventSource +
                ", chatMetadata=" + chatMetadata +
                ", messageMetadata=" + messageMetadata +
                ", chatId=" + chatId +
                ", messageId=" + messageId +
                '}';
    }
}
