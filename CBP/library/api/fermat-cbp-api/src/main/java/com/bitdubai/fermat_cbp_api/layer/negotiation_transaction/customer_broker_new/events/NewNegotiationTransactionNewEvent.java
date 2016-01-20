package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.events;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.events.AbstractCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;

/**
 * Created by Yordin Alayn on 04.12.15.
 */
public class NewNegotiationTransactionNewEvent extends AbstractCBPFermatEvent {

    PlatformComponentType destinationPlatformComponentType;

    public NewNegotiationTransactionNewEvent(EventType eventType) {
        super(eventType);
    }

    public PlatformComponentType getDestinationPlatformComponentType(){
        return destinationPlatformComponentType;
    }

    public void setDestinationPlatformComponentType(PlatformComponentType destinationPlatformComponentType){
        this.destinationPlatformComponentType = destinationPlatformComponentType;
    }
}
