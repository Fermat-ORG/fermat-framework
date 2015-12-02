package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces.NegotiationTransmission;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.11.15.
 */
public class NegotiationTransmissionImpl implements NegotiationTransmission {

    private final UUID transmissionId;
    private final UUID transactionId;
    private final UUID negotiationId;
    private final NegotiationTransactionType negotiationTansactionType;
    private final String publicKeyActorSend;
    private final PlatformComponentType actorSendType;
    private final String publicKeyActorReceive;
    private final PlatformComponentType actorReceiveType;
    private final NegotiationTransmissionType transmissionType;
    private NegotiationTransmissionState transmissionState;
    private final long timestamp;
    private boolean pendingFlag;

    public NegotiationTransmissionImpl(
            final UUID transmissionId,
            final UUID transactionId,
            final UUID negotiationId,
            final NegotiationTransactionType negotiationTansactionType,
            final String publicKeyActorSend,
            final PlatformComponentType actorSendType,
            final String publicKeyActorReceive,
            final PlatformComponentType actorReceiveType,
            final NegotiationTransmissionType transmissionType,
            final NegotiationTransmissionState transmissionState,
            final long timestamp
    ){
        this.transmissionId = transmissionId;
        this.transactionId = transactionId;
        this.negotiationId = negotiationId;
        this.negotiationTansactionType = negotiationTansactionType;
        this.publicKeyActorSend = publicKeyActorSend;
        this.actorSendType = actorSendType;
        this.publicKeyActorReceive = publicKeyActorReceive;
        this.actorReceiveType = actorReceiveType;
        this.transmissionType = transmissionType;
        this.transmissionState = transmissionState;
        this.timestamp = timestamp;
        this.pendingFlag = false;
    }

    @Override
    public UUID getTransmissionId(){ return transmissionId; }

    @Override
    public UUID getTransactionId(){ return transactionId; }

    @Override
    public UUID getNegotiationId(){ return negotiationId; }

    @Override
    public NegotiationTransactionType getNegotiationTansactionType(){ return negotiationTansactionType; }

    @Override
    public String getPublicKeyActorSend(){ return publicKeyActorSend; }

    @Override
    public PlatformComponentType getActorSendType(){ return actorSendType; }

    @Override
    public String getPublicKeyActorReceive(){ return publicKeyActorReceive;}

    @Override
    public PlatformComponentType getActorReceiveType(){ return actorReceiveType; }

    @Override
    public NegotiationTransmissionType getTransmissionType(){ return transmissionType; }

    @Override
    public NegotiationTransmissionState getTransmissionState(){ return transmissionState; }

    @Override
    public long getTimestamp(){ return timestamp; }

    @Override
    public boolean isPendingToRead() { return this.pendingFlag; }

    @Override
    public void confirmRead() { this.pendingFlag=true; }

    @Override
    public void setTransmissionState(NegotiationTransmissionState state){  this.transmissionState = state; }

}
