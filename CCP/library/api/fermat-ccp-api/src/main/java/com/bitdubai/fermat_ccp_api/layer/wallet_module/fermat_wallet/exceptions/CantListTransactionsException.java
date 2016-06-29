package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions;

/**
 * The interface <code>CantListTransactionsException</code>
 * is thrown when i cant get transactions of the wallet.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/06/15.
 * @version 1.0
 */
public class CantListTransactionsException extends FermatWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T LIST TRANSACTIONS EXCEPTION";

    public CantListTransactionsException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListTransactionsException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantListTransactionsException(final String message) {
        this(message, null);
    }

    public CantListTransactionsException() {
        this(DEFAULT_MESSAGE);
    }
}
