package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Yordin Alayn on 22.12.15.
 */
public abstract class AbstractCustomerBrokerCloseEventHandler implements FermatEventHandler {

    public CustomerBrokerCloseServiceEventHandler customerBrokerCloseServiceEventHandler;

    public void setCustomerBrokerNewService(CustomerBrokerCloseServiceEventHandler customerBrokerNewService) throws CantSetObjectException {
        if (customerBrokerNewService == null) {
            throw new CantSetObjectException("CustomerBrokerCloseServiceEventHandler is null");
        }
        this.customerBrokerCloseServiceEventHandler = customerBrokerNewService;
    }
}
