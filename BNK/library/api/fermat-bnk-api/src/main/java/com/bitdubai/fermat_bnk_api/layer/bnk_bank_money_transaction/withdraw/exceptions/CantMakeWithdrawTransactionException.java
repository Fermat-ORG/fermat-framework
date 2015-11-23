package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by memo on 18/11/15.
 */
public class CantMakeWithdrawTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To make Withdraw Bank Transaction.";
    public CantMakeWithdrawTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
