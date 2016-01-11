package com.bitdubai.fermat_cht_api.layer.middleware.utils;

import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;

import java.sql.Date;
import java.util.UUID;

/**
 * Created by franklin on 08/01/16.
 */
public class MessageImpl implements Message {
    //TODO: Documentar
    private UUID messageId;
    private UUID chatId;
    private String message;
    private MessageStatus status;
    private TypeMessage type;
    private Date messageDate;

    public MessageImpl(){};

    @Override
    public UUID getMessageId() {
        return this.messageId;
    }

    @Override
    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    @Override
    public UUID getChatId() {
        return this.chatId;
    }

    @Override
    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public MessageStatus getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    @Override
    public TypeMessage getType() {
        return this.type;
    }

    @Override
    public void setType(TypeMessage type) {
        this.type = type;
    }

    @Override
    public Date getMessageDate() {
        return this.messageDate;
    }

    @Override
    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }
}
