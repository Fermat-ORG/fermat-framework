package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.ack_payment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 09.10.15.
 */
public class CantListCustomerBrokerCashSaleBusinessTransactionException extends FermatException {
    public CantListCustomerBrokerCashSaleBusinessTransactionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}