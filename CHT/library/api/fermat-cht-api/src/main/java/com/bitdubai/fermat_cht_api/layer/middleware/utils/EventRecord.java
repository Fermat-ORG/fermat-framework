package com.bitdubai.fermat_cht_api.layer.middleware.utils;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/01/16.
 */
public class EventRecord {

    String eventId;

    EventStatus eventStatus;

    EventType eventType;

    EventSource eventSource;

    long timestamp;

    UUID chatId;

    public EventRecord(EventType eventType) {

        this.eventType = eventType;

    }

    public EventSource getEventSource() {
        return eventSource;
    }

    public void setEventSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("EventRecord{")
                .append("eventId='").append(eventId)
                .append('\'')
                .append(", eventStatus=").append(eventStatus)
                .append(", eventType=").append(eventType)
                .append(", eventSource=").append(eventSource)
                .append(", timestamp=").append(timestamp)
                .append(", chatId=").append(chatId).append('}').toString();
    }
}
