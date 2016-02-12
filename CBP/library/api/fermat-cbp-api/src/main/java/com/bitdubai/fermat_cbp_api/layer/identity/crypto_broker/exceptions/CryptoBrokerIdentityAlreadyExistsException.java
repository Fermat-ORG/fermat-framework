package com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CryptoBrokerIdentityAlreadyExistsException</code>
 * is thrown when we're trying to create an identity but there is one with the given alias.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/12/2015.
 */
public class CryptoBrokerIdentityAlreadyExistsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "IDENTITY ALREADY EXISTS EXCEPTION";

    public CryptoBrokerIdentityAlreadyExistsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CryptoBrokerIdentityAlreadyExistsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
