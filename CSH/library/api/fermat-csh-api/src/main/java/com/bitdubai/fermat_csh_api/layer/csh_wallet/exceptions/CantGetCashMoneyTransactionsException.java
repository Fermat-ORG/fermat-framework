package com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/23/2015.
 */
public class CantGetCashMoneyTransactionsException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Cant get this wallet's transactions";

    public CantGetCashMoneyTransactionsException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetCashMoneyTransactionsException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetCashMoneyTransactionsException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantGetCashMoneyTransactionsException(final String message) {
        this(message, null, null, null);
    }

    public CantGetCashMoneyTransactionsException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetCashMoneyTransactionsException() {
        this(DEFAULT_MESSAGE);
    }

}
