package com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/03/16.
 */
public class CantUpdateSongStatusException extends TKYException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CANNOT UPDATE SONG STATUS PATH";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantUpdateSongStatusException(
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
    public CantUpdateSongStatusException(
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
    public CantUpdateSongStatusException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CantUpdateSongStatusException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CantUpdateSongStatusException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CantUpdateSongStatusException() {
        this(DEFAULT_MESSAGE);
    }


}

