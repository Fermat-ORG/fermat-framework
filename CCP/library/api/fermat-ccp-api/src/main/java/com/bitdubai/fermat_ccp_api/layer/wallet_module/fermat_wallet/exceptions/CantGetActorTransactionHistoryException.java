package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetActorTransactionSummaryException</code>
 * is thrown when an error occurs trying to get the transaction history of an actor.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantGetActorTransactionHistoryException extends FermatWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET ACTOR TRANSACTION HISTORY EXCEPTION";

    public CantGetActorTransactionHistoryException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetActorTransactionHistoryException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetActorTransactionHistoryException(final String message) {
        this(message, null);
    }

    public CantGetActorTransactionHistoryException() {
        this(DEFAULT_MESSAGE);
    }
}
