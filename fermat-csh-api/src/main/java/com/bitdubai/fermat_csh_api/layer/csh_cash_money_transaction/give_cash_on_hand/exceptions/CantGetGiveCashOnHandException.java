package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.give_cash_on_hand.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public class CantGetGiveCashOnHandException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Get Cash Money Transaction Cash Transaction Give Cash On Hand..";
    public CantGetGiveCashOnHandException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
