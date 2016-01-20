package com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Angel on 07/12/15.
 */

public class CantGetListPaymentCurrencySaleException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET LIST PAYMENT CURRENCY SALE EXCEPTION";

    public CantGetListPaymentCurrencySaleException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}