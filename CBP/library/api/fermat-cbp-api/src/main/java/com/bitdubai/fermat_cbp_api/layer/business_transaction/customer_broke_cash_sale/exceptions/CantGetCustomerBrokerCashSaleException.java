package com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_cash_sale.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public class CantGetCustomerBrokerCashSaleException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Get Business Transaction Customer Broker Cash Sale.";
    public CantGetCustomerBrokerCashSaleException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
