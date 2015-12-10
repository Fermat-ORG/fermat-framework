package com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/23/2015.
 */
public class CantGetCashMoneyWalletTransactionsException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Cant get this wallet's transactions";

    public CantGetCashMoneyWalletTransactionsException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetCashMoneyWalletTransactionsException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetCashMoneyWalletTransactionsException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantGetCashMoneyWalletTransactionsException(final String message) {
        this(message, null, null, null);
    }

    public CantGetCashMoneyWalletTransactionsException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetCashMoneyWalletTransactionsException() {
        this(DEFAULT_MESSAGE);
    }

}
