package com.bitdubai.fermat_api.layer.modules.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/01/2016.
 */
public class ActorIdentityNotSelectedException extends FermatException {

    private static final String DEFAULT_MESSAGE = "ACTOR IDENTITY NOT SELECTED EXCEPTION";

    public ActorIdentityNotSelectedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ActorIdentityNotSelectedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public ActorIdentityNotSelectedException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
