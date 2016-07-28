package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/12/15.
 */
public abstract class AbstractCustomerOnlinePaymentEventHandler implements FermatEventHandler {

    public CustomerOnlinePaymentRecorderService customerOnlinePaymentRecorderService;

    public void setCustomerOnlinePaymentRecorderService(
            CustomerOnlinePaymentRecorderService customerOnlinePaymentRecorderService) throws
            CantSetObjectException {
        if (customerOnlinePaymentRecorderService == null) {
            throw new CantSetObjectException("customerOnlinePaymentRecorderService is null");
        }
        this.customerOnlinePaymentRecorderService = customerOnlinePaymentRecorderService;
    }

}
