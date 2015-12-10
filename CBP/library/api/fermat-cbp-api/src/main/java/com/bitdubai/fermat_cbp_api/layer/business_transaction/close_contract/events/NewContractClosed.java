package com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.events;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.events.AbstractCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/12/15.
 */
public class NewContractClosed extends AbstractCBPFermatEvent {

    PlatformComponentType destinationPlatformComponentType;

    public NewContractClosed(EventType eventType) {
        super(eventType);
    }

    public PlatformComponentType getDestinationPlatformComponentType(){
        return destinationPlatformComponentType;
    }

    public void setDestinationPlatformComponentType(PlatformComponentType destinationPlatformComponentType){
        this.destinationPlatformComponentType = destinationPlatformComponentType;
    }
}
