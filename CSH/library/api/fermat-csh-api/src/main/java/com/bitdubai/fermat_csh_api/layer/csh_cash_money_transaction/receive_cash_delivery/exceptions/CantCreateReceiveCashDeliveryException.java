package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.receive_cash_delivery.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */

public class CantCreateReceiveCashDeliveryException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Create Cash Transaction Receive Cash Delivery.";
    public CantCreateReceiveCashDeliveryException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
