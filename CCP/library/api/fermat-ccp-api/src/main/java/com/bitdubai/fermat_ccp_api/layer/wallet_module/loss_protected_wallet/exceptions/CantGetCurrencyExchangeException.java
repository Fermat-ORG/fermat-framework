package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * Created by natalia on 17/03/16.
 */
public class CantGetCurrencyExchangeException  extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET CRYPTO EXCHANGE CURRECY EXCEPTION";

    public CantGetCurrencyExchangeException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetCurrencyExchangeException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetCurrencyExchangeException(final String message) {
        this(message, null);
    }

    public CantGetCurrencyExchangeException() {
        this(DEFAULT_MESSAGE);
    }
}

