package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 12/22/15.
 */
public class CantFixTransactionInconsistenciesException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error checking and fixing Crypto transactions inconsistencies.";

    public CantFixTransactionInconsistenciesException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
