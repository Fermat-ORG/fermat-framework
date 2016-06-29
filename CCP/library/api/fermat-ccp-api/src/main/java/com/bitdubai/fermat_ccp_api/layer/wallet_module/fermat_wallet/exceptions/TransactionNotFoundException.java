package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions-TransactionNotFoundException</code>
 * is thrown when i cant find an specific transaction.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/09/2015.
 * @version 1.0
 */
public class TransactionNotFoundException extends FermatWalletException {

    public static final String DEFAULT_MESSAGE = "TRANSACTION NOT FOUND EXCEPTION";

    public TransactionNotFoundException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public TransactionNotFoundException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public TransactionNotFoundException(final String message) {
        this(message, null);
    }

    public TransactionNotFoundException() {
        this(DEFAULT_MESSAGE);
    }
}
