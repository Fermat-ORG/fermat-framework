package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/17/2015.
 */
public class CantCreateHoldTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Failed To create a Cash Hold Transaction.";

    public CantCreateHoldTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}