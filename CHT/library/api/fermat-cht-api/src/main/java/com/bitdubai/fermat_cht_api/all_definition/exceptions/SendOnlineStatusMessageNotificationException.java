package com.bitdubai.fermat_cht_api.all_definition.exceptions;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 03/05/16.
 */
public class SendOnlineStatusMessageNotificationException extends CHTException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CANNOT PERSISTS AN CHAT IN DATABASE";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public SendOnlineStatusMessageNotificationException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param cause
     * @param context
     * @param possibleReason
     */
    public SendOnlineStatusMessageNotificationException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     */
    public SendOnlineStatusMessageNotificationException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public SendOnlineStatusMessageNotificationException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public SendOnlineStatusMessageNotificationException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public SendOnlineStatusMessageNotificationException() {
        this(DEFAULT_MESSAGE);
    }
}
