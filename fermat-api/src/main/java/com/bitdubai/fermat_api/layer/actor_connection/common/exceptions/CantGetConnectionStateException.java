package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetConnectionStateException</code>
 * is thrown when there is an error trying to get the connection state.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/12/2015.
 */
public class CantGetConnectionStateException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET CONNECTION STATE EXCEPTION";

    public CantGetConnectionStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetConnectionStateException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetConnectionStateException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
