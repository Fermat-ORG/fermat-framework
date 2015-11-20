package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by memo on 18/11/15.
 */
public class CantMakeDepositTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To make Deposit Bank Transaction.";
    public CantMakeDepositTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
