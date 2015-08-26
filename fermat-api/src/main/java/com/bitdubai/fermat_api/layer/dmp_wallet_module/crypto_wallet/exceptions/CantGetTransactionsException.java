package com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions;

/**
 * The interface <code>CantGetTransactionsException</code>
 * is thrown when i cant get transactions of the wallet.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/06/15.
 * @version 1.0
 */
public class CantGetTransactionsException extends CryptoWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET TRANSACTIONS EXCEPTION";

    public CantGetTransactionsException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetTransactionsException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetTransactionsException(final String message) {
        this(message, null);
    }

    public CantGetTransactionsException() {
        this(DEFAULT_MESSAGE);
    }
}
