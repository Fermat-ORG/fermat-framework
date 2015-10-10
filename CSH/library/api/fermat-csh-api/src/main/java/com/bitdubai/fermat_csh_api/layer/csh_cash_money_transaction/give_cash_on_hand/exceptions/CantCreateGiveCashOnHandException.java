package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.give_cash_on_hand.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */

public class CantCreateGiveCashOnHandException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Create Cash Money Transaction Cash Transaction Give Cash On Hand.";
    public CantCreateGiveCashOnHandException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
