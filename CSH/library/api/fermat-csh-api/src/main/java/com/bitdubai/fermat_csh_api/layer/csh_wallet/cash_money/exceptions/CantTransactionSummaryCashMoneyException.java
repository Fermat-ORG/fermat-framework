package com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */

public class CantTransactionSummaryCashMoneyException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Get Cash Transaction Summary Wallet Bank Money.";
    public CantTransactionSummaryCashMoneyException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
