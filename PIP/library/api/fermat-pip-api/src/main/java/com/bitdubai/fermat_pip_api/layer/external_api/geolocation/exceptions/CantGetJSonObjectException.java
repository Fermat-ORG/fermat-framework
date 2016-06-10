package com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public class CantGetJSonObjectException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CANNOT GET JSON OBJECT";

    /**
     * Constructor with parameters
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantGetJSonObjectException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantGetJSonObjectException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     * @param message
     * @param cause
     */
    public CantGetJSonObjectException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameters
     * @param message
     */
    public CantGetJSonObjectException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameters
     * @param exception
     */
    public CantGetJSonObjectException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor with parameters
     */
    public CantGetJSonObjectException() {
        this(DEFAULT_MESSAGE);
    }

}
