package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions-CantSaveTransactionDescriptionException</code>
 * is thrown when i cant save the description for an specific transaction.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/09/2015.
 * @version 1.0
 */
public class CantSaveLossProtectedTransactionDescriptionException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CANT SAVE TRANSACTION DESCRIPTION EXCEPTION";

    public CantSaveLossProtectedTransactionDescriptionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSaveLossProtectedTransactionDescriptionException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSaveLossProtectedTransactionDescriptionException(final String message) {
        this(message, null);
    }

    public CantSaveLossProtectedTransactionDescriptionException() {
        this(DEFAULT_MESSAGE);
    }
}
