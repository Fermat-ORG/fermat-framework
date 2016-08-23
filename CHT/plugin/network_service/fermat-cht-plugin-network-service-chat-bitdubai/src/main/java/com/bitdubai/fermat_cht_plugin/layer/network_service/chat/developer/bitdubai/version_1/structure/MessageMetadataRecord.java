package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.MessageMetadata;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Gabriel Araujo on 16/08/16.
 */
public class MessageMetadataRecord extends AbstractMetadata implements MessageMetadata, Serializable {

    private UUID messageId;

    private String message;

    private MessageStatus messageStatus;

    public MessageMetadataRecord() {
    }

    public MessageMetadataRecord(UUID messageId, String message, MessageStatus messageStatus) {
        this.messageId = messageId;
        this.message = message;
        this.messageStatus = messageStatus;
    }

    @Override
    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    @Override
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static MessageMetadataRecord fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, MessageMetadataRecord.class);
    }
}
