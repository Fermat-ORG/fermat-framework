package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantChangeActorConnectionStateException</code>
 * is thrown when there is an error trying to change the actor connection state.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/12/2015.
 */
public class CantChangeActorConnectionStateException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CHANGE ACTOR CONNECTION STATE EXCEPTION";

    public CantChangeActorConnectionStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantChangeActorConnectionStateException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
