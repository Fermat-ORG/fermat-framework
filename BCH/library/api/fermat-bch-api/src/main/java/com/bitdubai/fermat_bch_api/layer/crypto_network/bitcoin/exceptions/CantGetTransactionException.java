package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 1/11/16.
 */
public class CantGetTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error getting the Transaction.";

    public CantGetTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
