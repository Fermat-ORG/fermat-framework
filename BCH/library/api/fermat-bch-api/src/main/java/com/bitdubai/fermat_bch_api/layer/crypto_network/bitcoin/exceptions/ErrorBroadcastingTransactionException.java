package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 12/31/15.
 */
public class ErrorBroadcastingTransactionException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error broadcasting a transaction. Retrying is possible.";

    public ErrorBroadcastingTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
