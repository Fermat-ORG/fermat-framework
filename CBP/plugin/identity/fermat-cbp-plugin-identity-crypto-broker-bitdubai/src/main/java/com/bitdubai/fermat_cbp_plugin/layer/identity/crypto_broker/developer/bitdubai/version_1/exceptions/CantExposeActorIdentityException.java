package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantExposeActorIdentityException</code>
 * is thrown when there is an error trying to expose crypto broker identity.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/11/2015.
 */
public class CantExposeActorIdentityException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T EXPOSE IDENTITY EXCEPTION";

    public CantExposeActorIdentityException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantExposeActorIdentityException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
