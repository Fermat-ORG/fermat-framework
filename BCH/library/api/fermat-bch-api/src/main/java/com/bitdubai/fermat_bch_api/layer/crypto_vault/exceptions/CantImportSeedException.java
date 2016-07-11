package com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/4/16.
 */
public class CantImportSeedException extends FermatException {

    static public final String DEFAULT_MESSAGE = "There was an error importing the passed Seed.";
    public CantImportSeedException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantImportSeedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
