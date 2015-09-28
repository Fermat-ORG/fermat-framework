package com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */

public class CantGetCashMoneyException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Get Cash Transaction Wallet Cash Money.";
    public CantGetCashMoneyException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
