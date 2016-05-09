package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * Created by natalia on 30/03/16.
 */
public class CantSetBasicWalletExchangeProviderException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T SET EXCHANGE PROVIDER EXCEPTION";

    public CantSetBasicWalletExchangeProviderException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSetBasicWalletExchangeProviderException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSetBasicWalletExchangeProviderException(final String message) {
        this(message, null);
    }

    public CantSetBasicWalletExchangeProviderException() {
        this(DEFAULT_MESSAGE);
    }
}
