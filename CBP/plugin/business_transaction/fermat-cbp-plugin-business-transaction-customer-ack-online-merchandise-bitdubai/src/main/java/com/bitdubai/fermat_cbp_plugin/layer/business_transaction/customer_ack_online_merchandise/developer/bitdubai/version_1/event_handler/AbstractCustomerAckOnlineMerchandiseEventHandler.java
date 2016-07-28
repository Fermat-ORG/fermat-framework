package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/12/15.
 */
public abstract class AbstractCustomerAckOnlineMerchandiseEventHandler implements FermatEventHandler {

    public CustomerAckOnlineMerchandiseRecorderService customerAckOnlineMerchandiseRecorderService;

    public void setCustomerAckOnlineMerchandiseRecorderService(
            CustomerAckOnlineMerchandiseRecorderService customerAckOnlineMerchandiseRecorderService) throws
            CantSetObjectException {
        if (customerAckOnlineMerchandiseRecorderService == null) {
            throw new CantSetObjectException("customerAckOnlineMerchandiseRecorderService is null");
        }
        this.customerAckOnlineMerchandiseRecorderService = customerAckOnlineMerchandiseRecorderService;
    }

}
