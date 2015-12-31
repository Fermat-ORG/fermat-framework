package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Yordin Alayn 10.12.15
 * Based on AbstractOpenContractEventHandler Created by Manuel Perez
 */
public abstract class AbstractCustomerBrokerNewEventHandler implements FermatEventHandler {

    public CustomerBrokerNewServiceEventHandler customerBrokerNewServiceEventHandler;

    public void setCustomerBrokerNewService(CustomerBrokerNewServiceEventHandler customerBrokerNewService) throws CantSetObjectException {
        if(customerBrokerNewService==null){
            throw new CantSetObjectException("CustomerBrokerNewServiceEventHandler is null");
        }
        this.customerBrokerNewServiceEventHandler = customerBrokerNewService;
    }
}
