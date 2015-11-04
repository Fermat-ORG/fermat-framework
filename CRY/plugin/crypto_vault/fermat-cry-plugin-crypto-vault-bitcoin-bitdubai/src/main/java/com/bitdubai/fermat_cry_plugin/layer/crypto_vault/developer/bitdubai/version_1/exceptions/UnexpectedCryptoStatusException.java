package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * This exception is thrown when I'm not expecting a crypto status and I can't react in front of it.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/09/2015.
 */
public class UnexpectedCryptoStatusException extends FermatException {

    public static final String DEFAULT_MESSAGE = "UNEXPECTED CRYPTO STATUS EXCEPTION";

    public UnexpectedCryptoStatusException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public UnexpectedCryptoStatusException(String context, String possibleReason) {
        super(DEFAULT_MESSAGE, null, context, possibleReason);
    }
}
