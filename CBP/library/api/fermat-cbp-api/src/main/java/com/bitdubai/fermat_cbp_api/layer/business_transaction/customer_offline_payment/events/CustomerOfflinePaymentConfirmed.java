package com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_offline_payment.events;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.events.GenericCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/12/15.
 */
public class CustomerOfflinePaymentConfirmed extends GenericCBPFermatEvent {

    PlatformComponentType destinationPlatformComponentType;

    public CustomerOfflinePaymentConfirmed(EventType eventType) {
        super(eventType);
    }

    public PlatformComponentType getDestinationPlatformComponentType() {
        return destinationPlatformComponentType;
    }

    public void setDestinationPlatformComponentType(PlatformComponentType destinationPlatformComponentType) {
        this.destinationPlatformComponentType = destinationPlatformComponentType;
    }
}
