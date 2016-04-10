package com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/04/16.
 */
public class FanActorConnectionNotStartedException extends ARTException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "FAN ACTOR CONNECTION NOT STARTED";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public FanActorConnectionNotStartedException(
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
    public FanActorConnectionNotStartedException(
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
    public FanActorConnectionNotStartedException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public FanActorConnectionNotStartedException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public FanActorConnectionNotStartedException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public FanActorConnectionNotStartedException() {
        this(DEFAULT_MESSAGE);
    }

}

