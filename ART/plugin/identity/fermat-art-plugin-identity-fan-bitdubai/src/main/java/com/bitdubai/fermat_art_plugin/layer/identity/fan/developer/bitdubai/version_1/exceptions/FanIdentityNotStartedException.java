package com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/04/16.
 */
public class FanIdentityNotStartedException extends ARTException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "FAN IDENTITY PLUGIN IS NOT STARTED";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public FanIdentityNotStartedException(
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
    public FanIdentityNotStartedException(
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
    public FanIdentityNotStartedException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public FanIdentityNotStartedException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public FanIdentityNotStartedException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public FanIdentityNotStartedException() {
        this(DEFAULT_MESSAGE);
    }

}
