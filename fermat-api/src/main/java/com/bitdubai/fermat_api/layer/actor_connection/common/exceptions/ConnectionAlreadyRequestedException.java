package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException</code>
 * is thrown when the actor connection that we're trying to request, it's already requested.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public class ConnectionAlreadyRequestedException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CONNECTION ALREADY REQUESTED EXCEPTION";

    public ConnectionAlreadyRequestedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ConnectionAlreadyRequestedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
