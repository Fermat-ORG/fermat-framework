package com.bitdubai.fermat_cbp_api.all_definition.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;

/**
 * Created by angel on 24/9/15.
 */
public class CustomerBrokerPurchaseRequestUpdated extends CBPEvent {

    public CustomerBrokerPurchaseRequestUpdated(FermatEventEnum eventType) {
        super(eventType);
    }

}
