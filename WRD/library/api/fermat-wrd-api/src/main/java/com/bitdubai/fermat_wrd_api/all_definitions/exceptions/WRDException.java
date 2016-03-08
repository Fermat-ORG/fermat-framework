package com.bitdubai.fermat_wrd_api.all_definitions.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/03/16.
 */
public class WRDException extends FermatException {

    public static final String DEFAULT_MESSAGE = "THE WORLD LAYER HAS DETECTED AN EXCEPTION";

    public WRDException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public WRDException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public WRDException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public WRDException(final String message) {
        this(message, null);
    }

    public WRDException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public WRDException() {
        this(DEFAULT_MESSAGE);
    }
}
