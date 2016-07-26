package com.bitdubai.fermat_api.layer.all_definition.events.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException</code>
 * is thrown when the event is not recognized.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 */
public class UnexpectedEventException extends FermatException {

    public static final String DEFAULT_MESSAGE = "UNEXPECTED EVENT: THE EVENT IS NOT RECOGNIZED.";

    public UnexpectedEventException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public UnexpectedEventException(String context) {
        this(DEFAULT_MESSAGE, null, context, null);
    }

}