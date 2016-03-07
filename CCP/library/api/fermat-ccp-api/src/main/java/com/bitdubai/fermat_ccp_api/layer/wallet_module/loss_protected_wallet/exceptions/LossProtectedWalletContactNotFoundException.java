package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * The interface <code>CantGetBalanceException</code>
 * is thrown when i cant get balance of the wallet.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 * @version 1.0
 */
public class LossProtectedWalletContactNotFoundException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "WALLET CONTACT NOT FOUND EXCEPTION";

    public LossProtectedWalletContactNotFoundException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public LossProtectedWalletContactNotFoundException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public LossProtectedWalletContactNotFoundException(final String message) {
        this(message, null);
    }

    public LossProtectedWalletContactNotFoundException() {
        this(DEFAULT_MESSAGE);
    }
}
