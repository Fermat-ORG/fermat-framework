package com.bitdubai.fermat_api.layer.all_definition.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 4/9/16.
 */
public class CantSetObjectException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Can't set object.";

    public CantSetObjectException(String message) {
        super(DEFAULT_MESSAGE, null, message, "cast exception");
    }

    public CantSetObjectException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantSetObjectException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
