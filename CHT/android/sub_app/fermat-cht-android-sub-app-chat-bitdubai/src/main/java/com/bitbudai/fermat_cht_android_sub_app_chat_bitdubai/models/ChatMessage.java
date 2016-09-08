package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models;

import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * ChatMessage Model
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 05/01/15.
 * @version 1.0
 */
public class ChatMessage {

    private UUID id;
    private boolean isMe;
    private String message;
    private Timestamp dateTime;
    private MessageStatus status;
    private String type;

    public ChatMessage() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean getIsme() {
        return isMe;
    }

    public void setMe(boolean isMe) {
        this.isMe = isMe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getDate() {

        return dateTime;
    }

    public void setDate(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", isMe=" + isMe +
                ", message='" + message + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", status=" + status +
                ", type='" + type + '\'' +
                '}';
    }
}
