package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models;

import android.graphics.Bitmap;

import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Chat Model
 * <p/>
 * Created by Leon Acosta (laion.cj91@gmail.com) on 06/09/2016.
 *
 * @author  lnacosta
 * @version 1.0
 */
public class Chat {

    private String        contactName;
    private String        message;
    private UUID          messageId;
    private Timestamp     dateMessage;
    private UUID          chatId;
    private String        contactId;
    private MessageStatus status;
    private TypeMessage   typeMessage;
    private Long          noReadMsgs;
    private Bitmap        imgId;

    public Chat(final String        contactName,
                final String        message,
                final UUID          messageId,
                final Timestamp     dateMessage,
                final UUID          chatId,
                final String        contactId,
                final MessageStatus status,
                final TypeMessage   typeMessage,
                final Long          noReadMsgs,
                final Bitmap        imgId) {

        this.contactName = contactName;
        this.message     = message;
        this.messageId   = messageId;
        this.dateMessage = dateMessage;
        this.chatId      = chatId;
        this.contactId   = contactId;
        this.status      = status;
        this.typeMessage = typeMessage;
        this.noReadMsgs  = noReadMsgs;
        this.imgId       = imgId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getDateMessage() {
        return dateMessage;
    }

    public UUID getChatId() {
        return chatId;
    }

    public String getContactId() {
        return contactId;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public Long getNoReadMsgs() {
        return noReadMsgs;
    }

    public Bitmap getImgId() {
        return imgId;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDateMessage(Timestamp dateMessage) {
        this.dateMessage = dateMessage;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public void setTypeMessage(TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
    }

    public void setNoReadMsgs(Long noReadMsgs) {
        this.noReadMsgs = noReadMsgs;
    }

    public void setImgId(Bitmap imgId) {
        this.imgId = imgId;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "contactName='" + contactName + '\'' +
                ", message='" + message + '\'' +
                ", messageId='" + messageId + '\'' +
                ", dateMessage='" + dateMessage + '\'' +
                ", chatId=" + chatId +
                ", contactId='" + contactId + '\'' +
                ", status='" + status + '\'' +
                ", typeMessage='" + typeMessage + '\'' +
                ", noReadMsgs=" + noReadMsgs +
                ", imgId=" + (imgId != null) +
                '}';
    }
}
