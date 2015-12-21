package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 12/17/15.
 */
public class CantGetTransactionCryptoStatusException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error getting the Crypto Status for the specified transaction.";

    public CantGetTransactionCryptoStatusException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
