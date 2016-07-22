package com.bitdubai.fermat_cbp_api.all_definition.exceptions;

/**
 * Created by José Vilchez on 28/01/16.
 */
public class CantSendNotificationReviewNegotiation extends CBPException {
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
    public CantSendNotificationReviewNegotiation(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantSendNotificationReviewNegotiation(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     */
    public CantSendNotificationReviewNegotiation(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CantSendNotificationReviewNegotiation(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CantSendNotificationReviewNegotiation(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CantSendNotificationReviewNegotiation() {
        this(DEFAULT_MESSAGE);
    }
}
