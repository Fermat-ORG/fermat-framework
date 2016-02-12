package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantInitializeActorConnectionDatabaseException</code>
 * is thrown when there is an error trying to initialize actor connection database.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/12/2015.
 */
public class CantInitializeActorConnectionDatabaseException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE ACTOR CONNECTION DATABASE EXCEPTION";

    public CantInitializeActorConnectionDatabaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeActorConnectionDatabaseException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
