package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Yordin Alayn 18.12.15
 */
public abstract class AbstractCustomerBrokerUpdateEventHandler implements FermatEventHandler {

    public CustomerBrokerUpdateServiceEventHandler customerBrokerUpdateServiceEventHandler;

    public void setCustomerBrokerUpdateService(CustomerBrokerUpdateServiceEventHandler customerBrokerUpdateServiceEventHandler) throws CantSetObjectException {
        if (customerBrokerUpdateServiceEventHandler == null) {
            throw new CantSetObjectException("CustomerBrokerUpdateServiceEventHandler is null");
        }
        this.customerBrokerUpdateServiceEventHandler = customerBrokerUpdateServiceEventHandler;
    }

}
