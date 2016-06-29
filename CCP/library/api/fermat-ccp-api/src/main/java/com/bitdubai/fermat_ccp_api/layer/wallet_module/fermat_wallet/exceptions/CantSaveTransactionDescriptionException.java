package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions-CantSaveTransactionDescriptionException</code>
 * is thrown when i cant save the description for an specific transaction.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/09/2015.
 * @version 1.0
 */
public class CantSaveTransactionDescriptionException extends FermatWalletException {

    public static final String DEFAULT_MESSAGE = "CANT SAVE TRANSACTION DESCRIPTION EXCEPTION";

    public CantSaveTransactionDescriptionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSaveTransactionDescriptionException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSaveTransactionDescriptionException(final String message) {
        this(message, null);
    }

    public CantSaveTransactionDescriptionException() {
        this(DEFAULT_MESSAGE);
    }
}
