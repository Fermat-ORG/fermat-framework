package com.bitdubai.fermat_tky_api.all_definitions.exceptions;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/03/16.
 */
public class UnexpectedResultReturnedFromDatabaseException extends TKYException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "UNEXPECTED RESULTS IN DATABASE QUERY";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public UnexpectedResultReturnedFromDatabaseException(
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
    public UnexpectedResultReturnedFromDatabaseException(
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
    public UnexpectedResultReturnedFromDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public UnexpectedResultReturnedFromDatabaseException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public UnexpectedResultReturnedFromDatabaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public UnexpectedResultReturnedFromDatabaseException() {
        this(DEFAULT_MESSAGE);
    }

}


