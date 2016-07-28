package com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.events.GenericCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;

/**
 * Created by Yordin Alayn on 04.12.15.
 */
public class IncomingNegotiationTransmissionConfirmResponseEvent extends GenericCBPFermatEvent {
    PlatformComponentType destinationPlatformComponentType;

    public IncomingNegotiationTransmissionConfirmResponseEvent(EventType eventType) {
        super(eventType);
    }

    public PlatformComponentType getDestinationPlatformComponentType() {
        return destinationPlatformComponentType;
    }

    public void setDestinationPlatformComponentType(PlatformComponentType destinationPlatformComponentType) {
        this.destinationPlatformComponentType = destinationPlatformComponentType;
    }
}
