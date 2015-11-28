package com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.events;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.events.AbstractCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/11/15.
 */
public class IncomingConfirmBusinessTransactionContract extends AbstractCBPFermatEvent {

    PlatformComponentType destinationPlatformComponentType;

    public IncomingConfirmBusinessTransactionContract(EventType eventType) {
        super(eventType);
    }

    public PlatformComponentType getDestinationPlatformComponentType(){
        return destinationPlatformComponentType;
    }

    public void setDestinationPlatformComponentType(PlatformComponentType destinationPlatformComponentType){
        this.destinationPlatformComponentType = destinationPlatformComponentType;
    }

}
