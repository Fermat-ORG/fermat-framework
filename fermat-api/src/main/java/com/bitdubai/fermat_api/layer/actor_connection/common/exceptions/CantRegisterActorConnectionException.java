package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRegisterActorConnectionException</code>
 * is thrown when there is an error trying to register an actor connection.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/12/2015.
 */
public class CantRegisterActorConnectionException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T REGISTER ACTOR CONNECTION EXCEPTION";

    public CantRegisterActorConnectionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRegisterActorConnectionException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
