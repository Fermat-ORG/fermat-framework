package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models;

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
    private UUID userId;
    private String dateTime;
    private String status;
    private String type;

    public ChatMessage(/*String idm, boolean isMem, String messagem, String userIdm, String dateTimem*/) {
       /* id = idm;
        isMe = isMem;
        message = messagem;
        userId = userIdm;
        dateTime = dateTimem;*/
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getDate() {
        return dateTime;
    }

    public void setDate(String dateTime) {
        this.dateTime = dateTime;
    }
}
