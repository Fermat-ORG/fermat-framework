package com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/04/16.
 */
public class CantRequestExternalPlatformInformationException extends ARTException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CAN'T REQUEST EXTERNAL PLATFORM INFORMATION";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantRequestExternalPlatformInformationException(
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
    public CantRequestExternalPlatformInformationException(
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
    public CantRequestExternalPlatformInformationException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CantRequestExternalPlatformInformationException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CantRequestExternalPlatformInformationException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CantRequestExternalPlatformInformationException() {
        this(DEFAULT_MESSAGE);
    }

}