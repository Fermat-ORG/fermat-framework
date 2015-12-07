package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.messages;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 02.12.15.
 */
public class NegotiationTransmissionResponseMessage {
    private UUID transmissionId;
    private NegotiationTransmissionState negotiationTransmissionState;
    private NegotiationTransactionType negotiationTransactionType;


    public NegotiationTransmissionResponseMessage(
            UUID transmissionId,
            NegotiationTransmissionState negotiationTransmissionState,
            NegotiationTransactionType negotiationTransactionType
    ) {
        this.transmissionId = transmissionId;
        this.negotiationTransmissionState = negotiationTransmissionState;
        this.negotiationTransactionType = negotiationTransactionType;
    }

    public UUID getTransmissionId() {
        return transmissionId;
    }

    public void setTransactionId(UUID transmissionId) {
        this.transmissionId = transmissionId;
    }

    public NegotiationTransmissionState getNegotiationTransmissionState() {
        return negotiationTransmissionState;
    }

    public void setNegotiationTransmissionState(NegotiationTransmissionState negotiationTransmissionState) {
        this.negotiationTransmissionState = negotiationTransmissionState;
    }

    public NegotiationTransactionType getNegotiationTransactionType() {
        return negotiationTransactionType;
    }

    @Override
    public String toString() {
        return  "NegotiationTransmissionResponseMessage{" +
                "transmissionId=" + transmissionId +
                ", negotiationTransmissionState=" + negotiationTransmissionState +
                "}";
    }
}
