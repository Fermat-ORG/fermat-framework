package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 3/16/16.
 */
public class CantGetTransactionsException extends FermatException {

    public static final String DEFAULT_MSSAGE = "There was an error getting a transaction.";

    public CantGetTransactionsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
