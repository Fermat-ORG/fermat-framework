package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantExposeActorIdentitiesException</code>
 * is thrown when there is an error trying to expose crypto broker identities.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/11/2015.
 */
public class CantExposeActorIdentitiesException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T EXPOSE IDENTITIES EXCEPTION";

    public CantExposeActorIdentitiesException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantExposeActorIdentitiesException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
