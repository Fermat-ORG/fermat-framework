package com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events;

import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 25/11/15.
 */
public final class IncomingNewContractStatusUpdate extends AbstractBusinessTransactionEvent {

    public IncomingNewContractStatusUpdate(EventType eventType) {

        super(eventType);

    }

}
