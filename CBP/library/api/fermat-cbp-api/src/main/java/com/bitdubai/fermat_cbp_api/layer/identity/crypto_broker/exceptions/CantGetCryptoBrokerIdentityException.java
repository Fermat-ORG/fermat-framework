package com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantGetCryptoBrokerIdentityException</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/12/2015.
 */
public class CantGetCryptoBrokerIdentityException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET CRYPTO BROKER IDENTITY EXCEPTION";

    public CantGetCryptoBrokerIdentityException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetCryptoBrokerIdentityException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
