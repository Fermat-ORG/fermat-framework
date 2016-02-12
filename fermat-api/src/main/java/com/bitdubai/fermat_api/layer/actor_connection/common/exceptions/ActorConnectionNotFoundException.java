package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException</code>
 * is thrown when an actor connection cannot be found.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/11/2015.
 */
public class ActorConnectionNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "ACTOR CONNECTION NOT FOUND EXCEPTION";

    public ActorConnectionNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ActorConnectionNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public ActorConnectionNotFoundException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
