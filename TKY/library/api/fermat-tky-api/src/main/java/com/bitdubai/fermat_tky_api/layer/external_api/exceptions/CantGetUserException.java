package com.bitdubai.fermat_tky_api.layer.external_api.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public class CantGetUserException extends TKYException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CANNOT GET THE USER FROM API TOKENLY";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantGetUserException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantGetUserException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     */
    public CantGetUserException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CantGetUserException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CantGetUserException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CantGetUserException() {
        this(DEFAULT_MESSAGE);
    }

}

