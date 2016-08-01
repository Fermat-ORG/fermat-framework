package com.bitdubai.fermat_cht_api.all_definition.exceptions;

/**
 * Created by Gabriel Araujo 27/01/2016
 */
public class CantSendNotificationNewIncomingMessageException extends CHTException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CANNOT SEND NOTIFICATION TO PIP";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantSendNotificationNewIncomingMessageException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantSendNotificationNewIncomingMessageException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     */
    public CantSendNotificationNewIncomingMessageException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CantSendNotificationNewIncomingMessageException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CantSendNotificationNewIncomingMessageException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CantSendNotificationNewIncomingMessageException() {
        this(DEFAULT_MESSAGE);
    }
}

