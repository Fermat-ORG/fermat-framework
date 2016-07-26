package com.bitdubai.fermat_api.layer.all_definition.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 4/9/16.
 */
public class ObjectNotSetException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Object not set.";

    public ObjectNotSetException(String message) {
        super(DEFAULT_MESSAGE, null, message, "null value");
    }

    public ObjectNotSetException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public ObjectNotSetException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
