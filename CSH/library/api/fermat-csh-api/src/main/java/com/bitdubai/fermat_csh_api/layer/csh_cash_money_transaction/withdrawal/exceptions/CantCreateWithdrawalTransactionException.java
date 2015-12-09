package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/27/2015.
 */
public class CantCreateWithdrawalTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Failed To create a Cash Withdrawal Transaction.";

    public CantCreateWithdrawalTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}