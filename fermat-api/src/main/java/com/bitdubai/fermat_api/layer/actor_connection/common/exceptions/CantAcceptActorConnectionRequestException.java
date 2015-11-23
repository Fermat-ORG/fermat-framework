package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException</code>
 * is thrown when there is an error trying to accept an actor connection request.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/11/2015.
 */
public class CantAcceptActorConnectionRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T ACCEPT ACTOR CONNECTION REQUEST EXCEPTION";

    public CantAcceptActorConnectionRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantAcceptActorConnectionRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
