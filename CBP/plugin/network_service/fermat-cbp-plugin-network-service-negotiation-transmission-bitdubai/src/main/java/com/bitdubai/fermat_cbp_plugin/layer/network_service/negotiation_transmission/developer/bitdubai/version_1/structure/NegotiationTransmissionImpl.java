package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces.NegotiationTransmission;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.11.15.
 */
public class NegotiationTransmissionImpl implements NegotiationTransmission{

    private final UUID transmissionId;
    private final UUID transactionId;
    private final UUID negotiationId;
    private final NegotiationStatus negotiationStatus;
    private final NegotiationTransactionType negotiationTansactionType;
    private final String publicKeyActorSend;
    private final PlatformComponentType actorSendType;
    private final String publicKeyActorReceive;
    private final PlatformComponentType actorReceiveType;
    private final NegotiationTransmissionState transmissionState;
    private final long timestamp;

    public NegotiationTransmissionImpl(
            final UUID transmissionId,
            final UUID transactionId,
            final UUID negotiationId,
            final NegotiationStatus negotiationStatus,
            final NegotiationTransactionType negotiationTansactionType,
            final String publicKeyActorSend,
            final PlatformComponentType actorSendType,
            final String publicKeyActorReceive,
            final PlatformComponentType actorReceiveType,
            final NegotiationTransmissionState transmissionState,
            final long timestamp
    ){
        this.transmissionId = transmissionId;
        this.transactionId = transactionId;
        this.negotiationId = negotiationId;
        this.negotiationStatus = negotiationStatus;
        this.negotiationTansactionType = negotiationTansactionType;
        this.publicKeyActorSend = publicKeyActorSend;
        this.actorSendType = actorSendType;
        this.publicKeyActorReceive = publicKeyActorReceive;
        this.actorReceiveType = actorReceiveType;
        this.transmissionState = transmissionState;
        this.timestamp = timestamp;
    }

    public UUID getTransmissionId(){ return transmissionId; }

    public UUID getTransactionId(){ return transactionId; }

    public UUID getNegotiationId(){ return negotiationId; }

    public NegotiationStatus getNegotiationStatus(){ return negotiationStatus; }

    public NegotiationTransactionType getNegotiationTansactionType(){ return negotiationTansactionType; }

    public String getPublicKeyActorSend(){ return publicKeyActorSend; }

    public PlatformComponentType getActorSendType(){ return actorSendType; }

    public String getPublicKeyActorReceive(){ return publicKeyActorReceive;}

    public PlatformComponentType getActorReceiveType(){ return actorReceiveType; }

    public NegotiationTransmissionState getTransmissionState(){ return transmissionState; }

    public long getTimestamp(){ return timestamp; }
}
