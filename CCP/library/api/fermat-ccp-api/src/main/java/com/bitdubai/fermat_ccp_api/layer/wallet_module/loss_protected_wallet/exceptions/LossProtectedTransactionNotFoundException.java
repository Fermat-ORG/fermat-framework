package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedWalletException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions-TransactionNotFoundException</code>
 * is thrown when i cant find an specific transaction.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/09/2015.
 * @version 1.0
 */
public class LossProtectedTransactionNotFoundException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "TRANSACTION NOT FOUND EXCEPTION";

    public LossProtectedTransactionNotFoundException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public LossProtectedTransactionNotFoundException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public LossProtectedTransactionNotFoundException(final String message) {
        this(message, null);
    }

    public LossProtectedTransactionNotFoundException() {
        this(DEFAULT_MESSAGE);
    }
}
