package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/17/2015.
 */
public class CantGetUnholdTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Failed To get the Cash Unhold Transaction.";

    public CantGetUnholdTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}