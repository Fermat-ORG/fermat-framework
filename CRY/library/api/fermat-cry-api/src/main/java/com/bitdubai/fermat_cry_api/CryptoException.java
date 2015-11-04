package com.bitdubai.fermat_cry_api;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by jorgegonzalez on 2015.06.24..
 */
public class CryptoException extends FermatException {

    public static final String DEFAULT_MESSAGE = "THE CRYPTOGRAPHY LAYER HAS TRIGGERED AN EXCEPTION";

    public CryptoException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CryptoException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CryptoException(final String message) {
        this(message, null);
    }

    public CryptoException() {
        this(DEFAULT_MESSAGE);
    }
}