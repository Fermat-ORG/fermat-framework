package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * The Class <code>CantUpdateWalletContactException</code>
 * is thrown when an error occurs trying to update any contact from a wallet
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantUpdateLossProtectedWalletContactException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE REQUESTED CONTACT EXCEPTION";

    public CantUpdateLossProtectedWalletContactException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateLossProtectedWalletContactException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantUpdateLossProtectedWalletContactException(final String message) {
        this(message, null);
    }

    public CantUpdateLossProtectedWalletContactException() {
        this(DEFAULT_MESSAGE);
    }
}
