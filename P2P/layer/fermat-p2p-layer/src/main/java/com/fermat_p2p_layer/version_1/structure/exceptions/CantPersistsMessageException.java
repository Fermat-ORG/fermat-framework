package com.fermat_p2p_layer.version_1.structure.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/08/16.
 */
public class CantPersistsMessageException extends FermatException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CANNOT PERSISTS A MESSAGE IN DATABASE";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantPersistsMessageException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantPersistsMessageException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     */
    public CantPersistsMessageException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CantPersistsMessageException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CantPersistsMessageException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CantPersistsMessageException() {
        this(DEFAULT_MESSAGE);
    }


}
