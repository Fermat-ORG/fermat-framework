package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */

public class CantCreateCashMoneyException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Create Cash Transaction Wallet Cash Money.";
    public CantCreateCashMoneyException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
