package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException</code>
 * is thrown when the actor type that we're trying to add is not supported for this type of actor identity.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public class UnsupportedActorTypeException extends FermatException {

    private static final String DEFAULT_MESSAGE = "UNSUPPORTED ACTION TYPE EXCEPTION";

    public UnsupportedActorTypeException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public UnsupportedActorTypeException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
