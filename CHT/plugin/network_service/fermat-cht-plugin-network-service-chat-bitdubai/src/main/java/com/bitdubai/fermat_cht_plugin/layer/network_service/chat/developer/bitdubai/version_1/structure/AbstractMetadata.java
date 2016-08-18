package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatProtocolState;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.MetadataToSend;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Gabriel Araujo on 16/08/16.
 */
public abstract class AbstractMetadata implements MetadataToSend , Serializable {

    private transient UUID transactionId;

    private transient String responseToNotification;

    private PlatformComponentType localActorType = PlatformComponentType.ACTOR_CHAT;

    private String localActorPublicKey;

    private PlatformComponentType remoteActorType = PlatformComponentType.ACTOR_CHAT;

    private String remoteActorPublicKey;

    private transient String date;

    private transient ChatProtocolState chatProtocolState;

    private DistributionStatus distributionStatus;

    private transient boolean flagReadead;

    private transient int sentCount;

    /**
     * Represent the value of processed
     */
    private transient String processed;

    public AbstractMetadata() {
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public final PlatformComponentType getLocalActorType() {
        return localActorType;
    }

    public void setLocalActorType(PlatformComponentType localActorType) {
        this.localActorType = localActorType;
    }

    @Override
    public final String getLocalActorPublicKey() {
        return localActorPublicKey;
    }

    public void setLocalActorPublicKey(String localActorPublicKey) {
        this.localActorPublicKey = localActorPublicKey;
    }

    @Override
    public final PlatformComponentType getRemoteActorType() {
        return remoteActorType;
    }

    public void setRemoteActorType(PlatformComponentType remoteActorType) {
        this.remoteActorType = remoteActorType;
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

    public ChatProtocolState getChatProtocolState() {
        return chatProtocolState;
    }

    public void changeState(ChatProtocolState chatProtocolState) {
        this.chatProtocolState = chatProtocolState;
    }

    public DistributionStatus getDistributionStatus() {
        return distributionStatus;
    }

    public void setDistributionStatus(DistributionStatus distributionStatus) {
        this.distributionStatus = distributionStatus;
    }

    public boolean isFlagReadead() {
        return flagReadead;
    }

    public void setFlagReadead(boolean flagReadead) {
        this.flagReadead = flagReadead;
    }

    public int getSentCount() {
        return sentCount;
    }

    public void setSentCount(int sentCount) {
        this.sentCount = sentCount;
    }

    public String getProcessed() {
        return processed;
    }

    public void setProcessed(String processed) {
        this.processed = processed;
    }

    public String getResponseToNotification() {
        return responseToNotification;
    }

    public void setResponseToNotification(String responseToNotification) {
        this.responseToNotification = responseToNotification;
    }
}
