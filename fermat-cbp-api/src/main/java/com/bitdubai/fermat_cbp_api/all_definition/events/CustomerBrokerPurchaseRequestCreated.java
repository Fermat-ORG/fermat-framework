package com.bitdubai.fermat_cbp_api.all_definition.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;

/**
 * Created by jorgegonzalez on 23-09-2015.
 */

public class CustomerBrokerPurchaseRequestCreated extends CBPEvent {

    public CustomerBrokerPurchaseRequestCreated(FermatEventEnum eventType) {
        super(eventType);
    }
}
