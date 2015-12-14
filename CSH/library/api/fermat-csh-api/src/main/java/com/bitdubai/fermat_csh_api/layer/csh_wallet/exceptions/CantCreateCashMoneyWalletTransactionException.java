package com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/23/2015.
 */
public class CantCreateCashMoneyWalletTransactionException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Cant create this wallet's transaction";

    public CantCreateCashMoneyWalletTransactionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateCashMoneyWalletTransactionException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateCashMoneyWalletTransactionException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantCreateCashMoneyWalletTransactionException(final String message) {
        this(message, null, null, null);
    }

    public CantCreateCashMoneyWalletTransactionException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateCashMoneyWalletTransactionException() {
        this(DEFAULT_MESSAGE);
    }

}
