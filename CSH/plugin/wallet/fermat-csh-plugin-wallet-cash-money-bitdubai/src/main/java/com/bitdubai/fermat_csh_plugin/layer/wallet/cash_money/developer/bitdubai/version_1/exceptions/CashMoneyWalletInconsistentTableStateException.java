package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/24/2015.
 */
public class CashMoneyWalletInconsistentTableStateException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Inconsistent table state";

    public CashMoneyWalletInconsistentTableStateException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CashMoneyWalletInconsistentTableStateException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CashMoneyWalletInconsistentTableStateException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CashMoneyWalletInconsistentTableStateException(final String message) {
        this(message, null, null, null);
    }

    public CashMoneyWalletInconsistentTableStateException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CashMoneyWalletInconsistentTableStateException() {
        this(DEFAULT_MESSAGE);
    }

}
