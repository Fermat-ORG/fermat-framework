package com.bitdubai.fermat_api.layer.all_definition.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.exceptions.IncompatibleReferenceException</code>
 * is thrown when we're trying to assign an incompatible type of reference..
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 28/10/2015.
 */
public class IncompatibleReferenceException extends FermatException {

    private static final String DEFAULT_MESSAGE = "INCOMPATIBLE REFERENCE EXCEPTION";

    public IncompatibleReferenceException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public IncompatibleReferenceException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public IncompatibleReferenceException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
