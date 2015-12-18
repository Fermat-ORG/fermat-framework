package com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 12/16/15.
 */
public class CantGetExtendedPublicKeyException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error getting the extended public key.";

    public CantGetExtendedPublicKeyException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
