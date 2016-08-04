package com.bitdubai.fermat_cht_api.layer.middleware.utils;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private UUID contactId;
    private long count;

    public MessageImpl() {
    }

    public MessageImpl(
            UUID chatId,
            ChatMetadata chatMetadata,
            MessageStatus messageStatus,
            TypeMessage typeMessage,
            UUID contactId
    ) {
        messageId = chatMetadata.getMessageId();
//        messageId=UUID.randomUUID();
        this.chatId = chatId;
        message = chatMetadata.getMessage();
        status = messageStatus;
        type = typeMessage;
//        messageDate=new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(chatMetadata.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        messageDate = new Timestamp(
                timestamp.getTime());
        this.contactId = contactId;
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

    @Override
    public UUID getContactId() {
        return this.contactId;
    }

    @Override
    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }

    @Override
    public long getCount() {
        return count;
    }

    @Override
    public void setCount(long count) {
        this.count = count;
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
