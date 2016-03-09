package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * The interface <code>CantRequestCryptoAddressException</code>
 * is thrown when i cant request an address of the wallet.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 * @version 1.0
 */
public class CantRequestLossProtectedAddressException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T REQUEST CRYPTO ADDRESS EXCEPTION";

    public CantRequestLossProtectedAddressException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRequestLossProtectedAddressException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantRequestLossProtectedAddressException(final String message) {
        this(message, null);
    }

    public CantRequestLossProtectedAddressException() {
        this(DEFAULT_MESSAGE);
    }
}
