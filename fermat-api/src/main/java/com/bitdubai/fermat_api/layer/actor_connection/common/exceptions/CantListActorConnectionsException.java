package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException</code>
 * is thrown when there is an error trying to list actor connections.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/11/2015.
 */
public class CantListActorConnectionsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T LIST ACTOR CONNECTIONS EXCEPTION";

    public CantListActorConnectionsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListActorConnectionsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
