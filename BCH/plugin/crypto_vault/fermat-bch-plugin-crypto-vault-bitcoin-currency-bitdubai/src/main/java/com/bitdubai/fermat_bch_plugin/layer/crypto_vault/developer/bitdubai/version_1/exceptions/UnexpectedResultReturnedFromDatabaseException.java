package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 2015.07.03..
 */
public class UnexpectedResultReturnedFromDatabaseException extends FermatException {
    public UnexpectedResultReturnedFromDatabaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
