package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.MessageMetadata;

import java.util.UUID;

/**
 * Created by Gabriel Araujo on 18/08/16.
 */
public class MessageMetadataRecord implements MessageMetadata {

    private PlatformComponentType localActorType = PlatformComponentType.ACTOR_CHAT;

    private String localActorPublicKey;

    private PlatformComponentType remoteActorType = PlatformComponentType.ACTOR_CHAT;

    private String remoteActorPublicKey;

    private UUID messageId;

    private String message;

    private MessageStatus messageStatus;

    private String date;

    public MessageMetadataRecord(PlatformComponentType localActorType, String localActorPublicKey, PlatformComponentType remoteActorType, String remoteActorPublicKey, UUID messageId, String message, MessageStatus messageStatus, String date) {
        this.localActorType = localActorType;
        this.localActorPublicKey = localActorPublicKey;
        this.remoteActorType = remoteActorType;
        this.remoteActorPublicKey = remoteActorPublicKey;
        this.messageId = messageId;
        this.message = message;
        this.messageStatus = messageStatus;
        this.date = date;
    }

    @Override
    public PlatformComponentType getLocalActorType() {
        return localActorType;
    }

    public void setLocalActorType(PlatformComponentType localActorType) {
        this.localActorType = localActorType;
    }

    @Override
    public String getLocalActorPublicKey() {
        return localActorPublicKey;
    }

    public void setLocalActorPublicKey(String localActorPublicKey) {
        this.localActorPublicKey = localActorPublicKey;
    }

    @Override
    public PlatformComponentType getRemoteActorType() {
        return remoteActorType;
    }

    public void setRemoteActorType(PlatformComponentType remoteActorType) {
        this.remoteActorType = remoteActorType;
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
