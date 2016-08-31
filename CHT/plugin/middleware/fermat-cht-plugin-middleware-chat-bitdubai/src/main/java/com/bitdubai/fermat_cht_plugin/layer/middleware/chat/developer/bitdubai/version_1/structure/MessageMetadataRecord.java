package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.MessageMetadata;

import java.util.UUID;

/**
 * Created by Gabriel Araujo on 18/08/16.
 */
public class MessageMetadataRecord implements MessageMetadata {

    private String localActorPublicKey;

    private String remoteActorPublicKey;

    private UUID messageId;

    private String message;

    private MessageStatus messageStatus;

    private String date;

    public MessageMetadataRecord(String localActorPublicKey, String remoteActorPublicKey, UUID messageId, String message, MessageStatus messageStatus, String date) {
        this.localActorPublicKey = localActorPublicKey;
        this.remoteActorPublicKey = remoteActorPublicKey;
        this.messageId = messageId;
        this.message = message;
        this.messageStatus = messageStatus;
        this.date = date;
    }

    @Override
    public String getLocalActorPublicKey() {
        return localActorPublicKey;
    }

    public void setLocalActorPublicKey(String localActorPublicKey) {
        this.localActorPublicKey = localActorPublicKey;
    }

    @Override
    public String getRemoteActorPublicKey() {
        return remoteActorPublicKey;
    }

    public void setRemoteActorPublicKey(String remoteActorPublicKey) {
        this.remoteActorPublicKey = remoteActorPublicKey;
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
    public String getDate() {
        return date;
    }

    @Override
    public String toJson() {
        return null;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
