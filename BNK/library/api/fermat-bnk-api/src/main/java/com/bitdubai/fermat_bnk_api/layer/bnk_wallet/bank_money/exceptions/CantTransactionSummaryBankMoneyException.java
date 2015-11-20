package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */

public class CantTransactionSummaryBankMoneyException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Get Bank Transaction Summary Wallet Bank Money.";
    public CantTransactionSummaryBankMoneyException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
