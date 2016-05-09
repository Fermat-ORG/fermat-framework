package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * Created by natalia on 30/03/16.
 */
public class CantGetBasicWalletExchangeProviderException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET EXCHANGE PROVIDER";

    public CantGetBasicWalletExchangeProviderException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetBasicWalletExchangeProviderException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetBasicWalletExchangeProviderException(final String message) {
        this(message, null);
    }

    public CantGetBasicWalletExchangeProviderException() {
        this(DEFAULT_MESSAGE);
    }
}
