package com.bitdubai.fermat_csh_api.all_definition.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
public class InvalidParameterException extends FermatException {
    private static String defaultMsg = "Wrong Parameter Found: ";

    public static final String DEFAULT_MESSAGE = "INVALID PARAMETER";

    public InvalidParameterException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public InvalidParameterException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public InvalidParameterException(final String message) {
        this(message, null);
    }

    public InvalidParameterException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public InvalidParameterException() {
        this(DEFAULT_MESSAGE);
    }
}
