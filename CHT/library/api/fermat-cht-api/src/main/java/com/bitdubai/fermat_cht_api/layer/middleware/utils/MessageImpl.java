package com.bitdubai.fermat_cht_api.layer.middleware.utils;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.MessageMetadata;

import java.sql.Timestamp;
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
    private Timestamp messageDate;

    public MessageImpl() {
    }

    public MessageImpl(UUID chatId,
                       MessageMetadata messageMetadata,
                       MessageStatus messageStatus,
                       TypeMessage typeMessage) {

        messageId = messageMetadata.getMessageId();
        this.chatId = chatId;
        message = messageMetadata.getMessage();
        status = messageStatus;
        type = typeMessage;
        messageDate = new Timestamp(System.currentTimeMillis());
    }

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
    public Timestamp getMessageDate() {
        return this.messageDate;
    }

    @Override
    public void setMessageDate(Timestamp messageDate) {
        this.messageDate = messageDate;
    }

    /**
     * This method returns a String in XML format containing all this object information
     *
     * @return
     */
    public String toString() {
        return XMLParser.parseObject(this);
    }
}
