package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.receive_cash_on_hand.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */

public class CantCreateReceiveCashOnHandException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Create Cash Transaction Receive Cash On Hand.";
    public CantCreateReceiveCashOnHandException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
