package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces.NegotiationTransmission;

import java.util.UUID;

/**
 * Created by yordin on 29/11/15.
 */
public class NegotiationTransmissionImpl implements NegotiationTransmission{

    private final UUID transactionId;
    private final UUID negotiationId;
    private final NegotiationTransactionType negotiationTansactionType;
    private final String publicKeyActorSend;
    private final String actorSendType;
    private final String publicKeyActorReceive;
    private final PlatformComponentType actorReceiveType;
    private final NegotiationTransmissionState transmissionState;
    private final long timestamp;

    public NegotiationTransmissionImpl(
            final UUID transactionId,
            final UUID negotiationId,
            final NegotiationTransactionType negotiationTansactionType,
            final String publicKeyActorSend,
            final String actorSendType,
            final String publicKeyActorReceive,
            final PlatformComponentType actorReceiveType,
            final NegotiationTransmissionState transmissionState,
            final long timestamp
    ){
        this.transactionId = transactionId;
        this.negotiationId = negotiationId;
        this.negotiationTansactionType = negotiationTansactionType;
        this.publicKeyActorSend = publicKeyActorSend;
        this.actorSendType = actorSendType;
        this.publicKeyActorReceive = publicKeyActorReceive;
        this.actorReceiveType = actorReceiveType;
        this.transmissionState = transmissionState;
        this.timestamp = timestamp;
    }

    public UUID getTransactionId(){ return transactionId; }

    public UUID getNegotiationId(){ return negotiationId; }

    public NegotiationTransactionType getNegotiationTansactionType(){ return negotiationTansactionType; }

    public String getPublicKeyActorSend(){ return publicKeyActorSend; }

    public String getActorSendType(){ return actorSendType; }

    public String getPublicKeyActorReceive(){ return publicKeyActorReceive;}

    public PlatformComponentType getActorReceiveType(){ return actorReceiveType; }

    public NegotiationTransmissionState getTransmissionState(){ return transmissionState; }

    public long getTimestamp(){ return timestamp; }
}
