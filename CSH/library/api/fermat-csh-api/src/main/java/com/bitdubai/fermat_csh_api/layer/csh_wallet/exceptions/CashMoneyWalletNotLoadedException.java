package com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/30/2015.
 */
public class CashMoneyWalletNotLoadedException extends FermatException {

    public static final String DEFAULT_MESSAGE = "No cash wallet has been loaded";

    public CashMoneyWalletNotLoadedException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CashMoneyWalletNotLoadedException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CashMoneyWalletNotLoadedException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CashMoneyWalletNotLoadedException(final String message) {
        this(message, null, null, null);
    }

    public CashMoneyWalletNotLoadedException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CashMoneyWalletNotLoadedException() {
        this(DEFAULT_MESSAGE);
    }

}
