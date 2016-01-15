package com.bitdubai.fermat_cht_api.all_definition.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/01/16.
 */
public class AbstractCHTFermatEvent implements FermatEvent {

    private EventType eventType;

    private EventSource eventSource;

    /**
     * Represents the chatId
     */
    private UUID chatId;

    public AbstractCHTFermatEvent(EventType eventType){
        this.eventType=eventType;
    }

    @Override
    public FermatEventEnum getEventType() {
        return this.eventType;
    }

    @Override
    public void setSource(EventSource eventSource) {
        this.eventSource=eventSource;
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

    @Override
    public String toString() {
        return "AbstractCHTFermatEvent{" +
                "eventType=" + eventType +
                ", eventSource=" + eventSource +
                ", chatId=" + chatId +
                '}';
    }
}
