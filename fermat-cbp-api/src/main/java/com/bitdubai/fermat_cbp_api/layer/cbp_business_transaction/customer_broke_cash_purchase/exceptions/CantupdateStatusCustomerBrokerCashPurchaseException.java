package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_cash_purchase.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public class CantupdateStatusCustomerBrokerCashPurchaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Falled To Update the Status the Business Transaction Customer Broker Cash Purchase.";
    public CantupdateStatusCustomerBrokerCashPurchaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
