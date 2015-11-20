package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantCancelActorConnectionRequestException</code>
 * is thrown when there is an error trying to cancel an actor connection request.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/11/2015.
 */
public class CantCancelActorConnectionRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CANCEL ACTOR CONNECTION REQUEST EXCEPTION";

    public CantCancelActorConnectionRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCancelActorConnectionRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
