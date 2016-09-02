package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.MetadataToSend;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Gabriel Araujo on 16/08/16.
 */
public abstract class AbstractMetadata implements MetadataToSend , Serializable {

    private UUID packageId;

    private ChatMessageTransactionType chatMessageTransactionType;

    private String localActorPublicKey;

    private String remoteActorPublicKey;

    private String date;

    public AbstractMetadata() {
    }

    public UUID getPackageId() {
        return packageId;
    }

    public void setPackageId(UUID transactionId) {
        this.packageId = transactionId;
    }

    @Override
    public final String getLocalActorPublicKey() {
        return localActorPublicKey;
    }

    public void setLocalActorPublicKey(String localActorPublicKey) {
        this.localActorPublicKey = localActorPublicKey;
    }

    @Override
    public final String getRemoteActorPublicKey() {
        return remoteActorPublicKey;
    }

    public void setRemoteActorPublicKey(String remoteActorPublicKey) {
        this.remoteActorPublicKey = remoteActorPublicKey;
    }

    @Override
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ChatMessageTransactionType getType() {
        return chatMessageTransactionType;
    }

    public void setChatMessageTransactionType(ChatMessageTransactionType chatMessageTransactionType) {
        this.chatMessageTransactionType = chatMessageTransactionType;
    }
}
