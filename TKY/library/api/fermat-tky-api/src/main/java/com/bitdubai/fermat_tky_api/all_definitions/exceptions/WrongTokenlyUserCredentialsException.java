package com.bitdubai.fermat_tky_api.all_definitions.exceptions;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/04/16.
 */
public class WrongTokenlyUserCredentialsException extends TKYException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "THE USER SUBMIT INVALID CREDENTIALS";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public WrongTokenlyUserCredentialsException(
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
    public WrongTokenlyUserCredentialsException(
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
    public WrongTokenlyUserCredentialsException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public WrongTokenlyUserCredentialsException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public WrongTokenlyUserCredentialsException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public WrongTokenlyUserCredentialsException() {
        this(DEFAULT_MESSAGE);
    }

}

