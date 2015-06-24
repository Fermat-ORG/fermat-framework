package com.bitdubai.fermat_cry_api;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by jorgegonzalez on 2015.06.24..
 */
public class FermatCryptoException extends FermatException {

    private static final String DEFAULT_MESSAGE = "THE CRYPTOGRAPHY LAYER HAS TRIGGERED AN EXCEPTION: ";

    public FermatCryptoException(final String message, final Exception cause, final String context, final String possibleReason){
        super(DEFAULT_MESSAGE + message, cause, context, possibleReason);
    }
    public FermatCryptoException(final String message, final Exception cause){
        this(DEFAULT_MESSAGE + message, cause, "", "");
    }

    public FermatCryptoException(final String message){
        this(message, null);
    }

    public FermatCryptoException(){
        this("");
    }
}
