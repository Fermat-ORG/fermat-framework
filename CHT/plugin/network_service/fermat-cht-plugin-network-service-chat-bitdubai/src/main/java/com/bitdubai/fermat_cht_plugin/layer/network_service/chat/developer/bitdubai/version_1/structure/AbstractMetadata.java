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

    private PlatformComponentType localActorType;

    private String localActorPublicKey;

    private PlatformComponentType remoteActorType;

    private String remoteActorPublicKey;

    private transient String date;

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

}
