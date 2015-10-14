package com.bitdubai.fermat_api.layer.all_definition.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by eze on 2015.06.18..
 */

public class InvalidParameterException extends FermatException {

    public static final String DEFAULT_MESSAGE = "INVALID PARAMETER";

    public InvalidParameterException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public InvalidParameterException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public InvalidParameterException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public InvalidParameterException(final String message) {
        this(message, null, null, null);
    }

    public InvalidParameterException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public InvalidParameterException() {
        this(DEFAULT_MESSAGE);
    }

}
