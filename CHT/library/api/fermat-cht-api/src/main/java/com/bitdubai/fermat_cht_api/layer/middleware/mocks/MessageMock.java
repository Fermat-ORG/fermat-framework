package com.bitdubai.fermat_cht_api.layer.middleware.mocks;

import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;

import java.sql.Date;
import java.util.UUID;

/**
 * This class is only for testing
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/01/16.
 */
public class MessageMock implements Message {

    UUID chatId;

    public MessageMock(UUID chatId){
        this.chatId=chatId;
    }

    @Override
    public UUID getMessageId() {
        return UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    }

    @Override
    public void setMessageId(UUID messageId) {

    }

    @Override
    public UUID getChatId() {
        return this.chatId;
    }

    @Override
    public void setChatId(UUID chatId) {
        this.chatId=chatId;
    }

    @Override
    public String getMessage() {
        return "Luke, I am not your father!";
    }

    @Override
    public void setMessage(String message) {

    }

    @Override
    public MessageStatus getStatus() {
        return MessageStatus.CREATED;
    }

    @Override
    public void setStatus(MessageStatus status) {

    }

    @Override
    public TypeMessage getType() {
        return TypeMessage.OUTGOING;
    }

    @Override
    public void setType(TypeMessage type) {

    }

    @Override
    public Date getMessageDate() {
        return new Date(2001);
    }

    @Override
    public void setMessageDate(Date messageDate) {

    }
}
