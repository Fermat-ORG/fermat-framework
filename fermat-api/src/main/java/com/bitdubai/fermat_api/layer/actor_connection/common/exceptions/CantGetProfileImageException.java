package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetProfileImageException</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/01/2016.
 */
public class CantGetProfileImageException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET PROFILE IMAGE EXCEPTION";

    public CantGetProfileImageException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetProfileImageException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
