package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/12/15.
 */
public abstract class AbstractCustomerAckOfflineMerchandiseEventHandler implements FermatEventHandler {

    public CustomerAckOfflineMerchandiseRecorderService customerAckOfflineMerchandiseRecorderService;

    public void setCustomerAckOfflineMerchandiseRecorderService(
            CustomerAckOfflineMerchandiseRecorderService customerAckOfflineMerchandiseRecorderService) throws
            CantSetObjectException {
        if (customerAckOfflineMerchandiseRecorderService == null) {
            throw new CantSetObjectException("customerAckOfflineMerchandiseRecorderService is null");
        }
        this.customerAckOfflineMerchandiseRecorderService = customerAckOfflineMerchandiseRecorderService;
    }

}
