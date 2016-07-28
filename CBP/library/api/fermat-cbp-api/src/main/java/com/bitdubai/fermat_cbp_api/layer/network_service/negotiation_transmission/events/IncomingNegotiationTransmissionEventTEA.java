package com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_cbp_api.all_definition.events.AbstractCBPFermatEventTEA;

/**
 * Created by yordin on 16/01/16.
 */
public class IncomingNegotiationTransmissionEventTEA extends AbstractCBPFermatEventTEA {

    public IncomingNegotiationTransmissionEventTEA(FermatEventEnum eventType) {
        super(eventType);
    }
}
