package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.receive_cash_delivery.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public class CantUpdateStatusReceiveCashDeliveryException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Update the Status the Cash Money Transaction Receive Cash Delivery.";
    public CantUpdateStatusReceiveCashDeliveryException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
