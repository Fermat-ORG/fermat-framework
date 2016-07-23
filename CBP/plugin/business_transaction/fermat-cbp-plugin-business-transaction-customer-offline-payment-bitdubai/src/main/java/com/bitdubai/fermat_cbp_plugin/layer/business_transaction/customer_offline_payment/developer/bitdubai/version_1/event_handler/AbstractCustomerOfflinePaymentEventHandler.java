package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 14/12/15.
 */
public abstract class AbstractCustomerOfflinePaymentEventHandler implements FermatEventHandler {

    public CustomerOfflinePaymentRecorderService customerOfflinePaymentRecorderService;

    public void setCustomerOfflinePaymentRecorderService(
            CustomerOfflinePaymentRecorderService customerOfflinePaymentRecorderService) throws
            CantSetObjectException {
        if (customerOfflinePaymentRecorderService == null) {
            throw new CantSetObjectException("customerOfflinePaymentRecorderService is null");
        }
        this.customerOfflinePaymentRecorderService = customerOfflinePaymentRecorderService;
    }

}
