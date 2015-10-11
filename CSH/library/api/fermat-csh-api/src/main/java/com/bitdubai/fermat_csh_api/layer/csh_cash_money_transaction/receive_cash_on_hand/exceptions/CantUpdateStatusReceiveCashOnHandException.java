package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.receive_cash_on_hand.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public class CantUpdateStatusReceiveCashOnHandException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Update the Status the Cash Money Transaction Cant Receive Cash On Hand.";
    public CantUpdateStatusReceiveCashOnHandException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
