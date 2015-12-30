package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 12/30/15.
 */
public class UnexpectedResultReturnedFromDatabaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "The query returned an unexpected result.";

    public UnexpectedResultReturnedFromDatabaseException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
