package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.messages;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 20.12.15.
 */
public class ConfirmMessage extends NegotiationTransmissionMessage {

    private final UUID transmissionId;
    private final PlatformComponentType actorReceiveType;

    public ConfirmMessage(
            final UUID transmissionId,
            final PlatformComponentType actorReceiveType
    ) {
        super(NegotiationTransmissionType.TRANSMISSION_CONFIRM);
        this.transmissionId = transmissionId;
        this.actorReceiveType = actorReceiveType;
    }

    public UUID getTransmissionId() {
        return transmissionId;
    }

    public PlatformComponentType getActorReceiveType() {
        return actorReceiveType;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("ConfirmMessage{")
                .append("  transmissionId   =").append(transmissionId)
                .append(", action           =").append(actorReceiveType)
                .append("}").toString();
    }
}
