package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * The interface <code>CantListTransactionsException</code>
 * is thrown when i cant get transactions of the wallet.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/06/15.
 * @version 1.0
 */
public class CantListLossProtectedTransactionsException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T LIST TRANSACTIONS EXCEPTION";

    public CantListLossProtectedTransactionsException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListLossProtectedTransactionsException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantListLossProtectedTransactionsException(final String message) {
        this(message, null);
    }

    public CantListLossProtectedTransactionsException() {
        this(DEFAULT_MESSAGE);
    }
}
