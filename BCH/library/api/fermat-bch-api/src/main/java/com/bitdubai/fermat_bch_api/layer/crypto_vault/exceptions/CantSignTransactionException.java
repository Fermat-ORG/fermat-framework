package com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 2/10/16.
 */
public class CantSignTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error signing a transaction";

    public CantSignTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
