package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_cash_purchase.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 09.10.15.
 */
public class CantListCustomerBrokerCashPurchaseBusinessTransactionException extends FermatException {
    public CantListCustomerBrokerCashPurchaseBusinessTransactionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}