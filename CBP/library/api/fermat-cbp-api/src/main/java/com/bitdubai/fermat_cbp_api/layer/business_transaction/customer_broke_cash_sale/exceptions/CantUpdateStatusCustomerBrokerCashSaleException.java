package com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_cash_sale.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public class CantUpdateStatusCustomerBrokerCashSaleException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Falled To Update the Status the Business Transaction Customer Broker Cash Sale.";
    public CantUpdateStatusCustomerBrokerCashSaleException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
