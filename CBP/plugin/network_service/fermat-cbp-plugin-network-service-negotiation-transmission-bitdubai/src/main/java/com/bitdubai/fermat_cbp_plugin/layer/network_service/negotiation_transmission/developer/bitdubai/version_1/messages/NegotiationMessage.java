package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.messages;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 20/12/15.
 */
public final class NegotiationMessage extends NegotiationTransmissionMessage {

    private final UUID transmissionId;
    private final UUID transactionId;
    private final UUID negotiationId;
    private final NegotiationTransactionType negotiationTransactionType;
    private final String publicKeyActorSend;
    private final PlatformComponentType actorSendType;
    private final String publicKeyActorReceive;
    private final PlatformComponentType actorReceiveType;
    private final NegotiationTransmissionType transmissionType;
    private final NegotiationTransmissionState transmissionState;
    private final NegotiationType negotiationType;
    private final String negotiationXML;
    private final long timestamp;

    public NegotiationMessage(
            final UUID transmissionId,
            final UUID transactionId,
            final UUID negotiationId,
            final NegotiationTransactionType negotiationTransactionType,
            final String publicKeyActorSend,
            final PlatformComponentType actorSendType,
            final String publicKeyActorReceive,
            final PlatformComponentType actorReceiveType,
            final NegotiationTransmissionType transmissionType,
            final NegotiationTransmissionState transmissionState,
            final NegotiationType negotiationType,
            final String negotiationXML,
            final long timestamp
    ) {

        super(NegotiationTransmissionType.TRANSMISSION_NEGOTIATION);

        this.transmissionId = transmissionId;
        this.transactionId = transactionId;
        this.negotiationId = negotiationId;
        this.negotiationTransactionType = negotiationTransactionType;
        this.publicKeyActorSend = publicKeyActorSend;
        this.actorSendType = actorSendType;
        this.publicKeyActorReceive = publicKeyActorReceive;
        this.actorReceiveType = actorReceiveType;
        this.transmissionType = transmissionType;
        this.transmissionState = transmissionState;
        this.negotiationType = negotiationType;
        this.negotiationXML = negotiationXML;
        this.timestamp = timestamp;
    }


    public final UUID getTransmissionId() {
        return transmissionId;
    }

    public final UUID getTransactionId() {
        return transactionId;
    }

    public final UUID getNegotiationId() {
        return negotiationId;
    }

    public final NegotiationTransactionType getNegotiationTransactionType() {
        return negotiationTransactionType;
    }

    public final String getPublicKeyActorSend() {
        return publicKeyActorSend;
    }

    public final PlatformComponentType getActorSendType() {
        return actorSendType;
    }

    public final String getPublicKeyActorReceive() {
        return publicKeyActorReceive;
    }

    public final PlatformComponentType getActorReceiveType() {
        return actorReceiveType;
    }

    public final NegotiationTransmissionType getTransmissionType() {
        return transmissionType;
    }

    public final NegotiationTransmissionState getTransmissionState() {
        return transmissionState;
    }

    public final NegotiationType getNegotiationType() {
        return negotiationType;
    }

    public final String getNegotiationXML() {
        return negotiationXML;
    }

    public final long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("NegotiationMessage{")
                .append("  transmissionId             = ").append(transmissionId)
                .append(", transactionId              = ").append(transactionId)
                .append(", negotiationId              = ").append(negotiationId)
                .append(", negotiationTransactionType = ").append(negotiationTransactionType)
                .append(", publicKeyActorSend         = ").append(publicKeyActorSend)
                .append(", actorSendType              = ").append(actorSendType)
                .append(", publicKeyActorReceive      = ").append(publicKeyActorReceive)
                .append(", actorReceiveType           = ").append(actorReceiveType)
                .append(", transmissionType           = ").append(transmissionType)
                .append(", transmissionState          = ").append(transmissionState)
                .append(", negotiationXML             = ").append(negotiationXML)
                .append(", negotiationType            = ").append(negotiationType)
                .append(", timestamp                  = ").append(timestamp)
                .append("}").toString();
    }
}
