package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException</code>
 * is thrown when there is an error trying to request an actor connection.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/11/2015.
 */
public class CantRequestActorConnectionException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T REQUEST ACTOR CONNECTION EXCEPTION";

    public CantRequestActorConnectionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRequestActorConnectionException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
