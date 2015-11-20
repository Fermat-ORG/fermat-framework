package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException</code>
 * is thrown when there is an error trying to disconnect from the actor.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/11/2015.
 */
public class CantDisconnectFromActorException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T DISCONNECT FROM ACTOR EXCEPTION";

    public CantDisconnectFromActorException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDisconnectFromActorException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
