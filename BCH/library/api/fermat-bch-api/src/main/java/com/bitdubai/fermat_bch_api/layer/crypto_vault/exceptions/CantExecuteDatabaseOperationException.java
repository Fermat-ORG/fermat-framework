package com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 3/8/16.
 */
public class CantExecuteDatabaseOperationException extends FermatException {
    static public final String DEFAULT_MESSAGE = "There was an error trying to execute a database operation.";

    public CantExecuteDatabaseOperationException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
