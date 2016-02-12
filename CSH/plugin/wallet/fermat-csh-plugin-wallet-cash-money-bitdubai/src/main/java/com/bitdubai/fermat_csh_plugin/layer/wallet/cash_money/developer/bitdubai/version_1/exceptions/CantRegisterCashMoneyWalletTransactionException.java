package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/24/2015.
 */
public class CantRegisterCashMoneyWalletTransactionException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Cant register cash money wallet transaction";

    public CantRegisterCashMoneyWalletTransactionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRegisterCashMoneyWalletTransactionException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantRegisterCashMoneyWalletTransactionException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantRegisterCashMoneyWalletTransactionException(final String message) {
        this(message, null, null, null);
    }

    public CantRegisterCashMoneyWalletTransactionException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantRegisterCashMoneyWalletTransactionException() {
        this(DEFAULT_MESSAGE);
    }

}

