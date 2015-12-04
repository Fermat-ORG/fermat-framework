package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException</code>
 * is thrown when an actor connection already exists and we're trying to create a new one.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/12/2015.
 */
public class ActorConnectionAlreadyExistsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "ACTOR CONNECTION ALREADY EXISTS EXCEPTION";

    public ActorConnectionAlreadyExistsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ActorConnectionAlreadyExistsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public ActorConnectionAlreadyExistsException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
