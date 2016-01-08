package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 1/8/16.
 */
public class CantCancellBroadcastTransactionException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error cancelling a broascast transaction.";
    public CantCancellBroadcastTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
