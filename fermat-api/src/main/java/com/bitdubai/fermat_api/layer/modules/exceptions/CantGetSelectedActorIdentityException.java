package com.bitdubai.fermat_api.layer.modules.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException</code>
 * is thrown when there is an error trying to get the selected actor identity.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/12/2015.
 */
public class CantGetSelectedActorIdentityException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET SELECTED ACTOR IDENTITY EXCEPTION";

    public CantGetSelectedActorIdentityException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetSelectedActorIdentityException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
