package com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 12/16/15.
 */
public class CantDeriveNewKeysException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error trying to derive new keys.";

    public CantDeriveNewKeysException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
