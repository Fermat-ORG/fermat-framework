package com.bitdubai.fermat_csh_api.layer.csh_wallet_module.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 12/10/2015.
 */
public class CantGetCashMoneyWalletBalancesException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Cant get this cash wallet's balances";

    public CantGetCashMoneyWalletBalancesException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetCashMoneyWalletBalancesException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetCashMoneyWalletBalancesException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantGetCashMoneyWalletBalancesException(final String message) {
        this(message, null, null, null);
    }

    public CantGetCashMoneyWalletBalancesException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetCashMoneyWalletBalancesException() {
        this(DEFAULT_MESSAGE);
    }

}
