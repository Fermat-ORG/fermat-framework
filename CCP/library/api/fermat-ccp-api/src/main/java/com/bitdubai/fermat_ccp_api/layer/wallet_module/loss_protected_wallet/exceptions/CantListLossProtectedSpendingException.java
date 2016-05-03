package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * Created by natalia on 14/03/16.
 */
public class CantListLossProtectedSpendingException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T LIST SPENDING BTC EXCEPTION";

    public CantListLossProtectedSpendingException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListLossProtectedSpendingException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantListLossProtectedSpendingException(final String message) {
        this(message, null);
    }

    public CantListLossProtectedSpendingException() {
        this(DEFAULT_MESSAGE);
    }
}
