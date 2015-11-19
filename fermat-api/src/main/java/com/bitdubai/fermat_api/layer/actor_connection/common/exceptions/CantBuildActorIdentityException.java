package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantBuildActorIdentityException</code>
 * is thrown when there is an error trying to build an actor identity instance.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/11/2015.
 */
public class CantBuildActorIdentityException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T BUILD ACTOR IDENTITY EXCEPTION";

    public CantBuildActorIdentityException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantBuildActorIdentityException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
