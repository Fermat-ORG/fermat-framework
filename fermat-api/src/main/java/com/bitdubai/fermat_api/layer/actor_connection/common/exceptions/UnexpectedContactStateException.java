package com.bitdubai.fermat_api.layer.actor_connection.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedContactStateException</code>
 * is thrown when the contact state is not the expected.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public class UnexpectedContactStateException extends FermatException {

    private static final String DEFAULT_MESSAGE = "UNEXPECTED CONTACT STATE EXCEPTION";

    public UnexpectedContactStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public UnexpectedContactStateException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
