package com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events;

import com.bitdubai.fermat_cbp_api.all_definition.events.GenericCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;

/**
 * Created by Yordin Alayn 25.11.15.
 */
public final class IncomingNegotiationTransmissionUpdateEvent extends GenericCBPFermatEvent {

    public IncomingNegotiationTransmissionUpdateEvent(EventType eventType) {
        super(eventType);
    }

}
