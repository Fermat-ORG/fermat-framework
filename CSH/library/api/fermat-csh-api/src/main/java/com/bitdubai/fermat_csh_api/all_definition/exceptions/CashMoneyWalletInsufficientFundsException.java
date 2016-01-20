package com.bitdubai.fermat_csh_api.all_definition.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 22.12.15.
 */
public class CashMoneyWalletInsufficientFundsException extends FermatException {

    public static final String DEFAULT_MESSAGE = "INSUFFICIENT FUNDS";

    public CashMoneyWalletInsufficientFundsException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CashMoneyWalletInsufficientFundsException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CashMoneyWalletInsufficientFundsException(final String message) {
        this(message, null);
    }

    public CashMoneyWalletInsufficientFundsException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CashMoneyWalletInsufficientFundsException() {
        this(DEFAULT_MESSAGE);
    }
}
