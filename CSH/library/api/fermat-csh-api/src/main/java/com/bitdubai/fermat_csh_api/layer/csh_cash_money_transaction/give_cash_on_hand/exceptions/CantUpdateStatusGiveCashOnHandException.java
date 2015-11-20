package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.give_cash_on_hand.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public class CantUpdateStatusGiveCashOnHandException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Falled To Update the Status the Cash Money Transaction Cant Create Give Cash On Hand.";
    public CantUpdateStatusGiveCashOnHandException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
