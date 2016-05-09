package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * Created by natalia on 17/03/16.
 */
public class CantGetCurrencyExchangeProviderException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET CRYPTO EXCHANGE CURRECY PROVIDER EXCEPTION";

    public CantGetCurrencyExchangeProviderException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetCurrencyExchangeProviderException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetCurrencyExchangeProviderException(final String message) {
        this(message, null);
    }

    public CantGetCurrencyExchangeProviderException() {
        this(DEFAULT_MESSAGE);
    }
}

