package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.messages;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.google.gson.Gson;

/**
 * Created by Yordin Alayn on 02.12.15.
 */
public class NegotiationTransmissionMessage {

    private NegotiationTransmissionType messageType;

    public NegotiationTransmissionMessage() {
    }

    public NegotiationTransmissionMessage(final NegotiationTransmissionType messageType) {
        this.messageType = messageType;
    }

    public NegotiationTransmissionType getMessageType() {
        return messageType;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
