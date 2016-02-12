package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/27/2015.
 */
public class CantCreateDepositTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Failed To create a Cash Deposit Transaction.";

    public CantCreateDepositTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}