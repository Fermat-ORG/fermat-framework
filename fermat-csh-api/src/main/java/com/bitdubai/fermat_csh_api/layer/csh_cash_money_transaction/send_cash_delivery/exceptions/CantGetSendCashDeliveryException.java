package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.send_cash_delivery.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public class CantGetSendCashDeliveryException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Get Cash Money Transaction Send Cash Delivery.";
    public CantGetSendCashDeliveryException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
