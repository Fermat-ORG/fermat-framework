package com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/23/2015.
 */
public class CantGetCashMoneyWalletBalanceException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Cant get this cash wallet's balance";

    public CantGetCashMoneyWalletBalanceException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetCashMoneyWalletBalanceException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetCashMoneyWalletBalanceException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantGetCashMoneyWalletBalanceException(final String message) {
        this(message, null, null, null);
    }

    public CantGetCashMoneyWalletBalanceException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetCashMoneyWalletBalanceException() {
        this(DEFAULT_MESSAGE);
    }

}
