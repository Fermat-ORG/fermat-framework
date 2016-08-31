package com.fermat_p2p_layer.version_1.structure.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/08/16.
 */
public class CantCheckPackageIdException extends FermatException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CANNOT CHECK A MESSAGE IN DATABASE";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantCheckPackageIdException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantCheckPackageIdException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     */
    public CantCheckPackageIdException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CantCheckPackageIdException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CantCheckPackageIdException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CantCheckPackageIdException() {
        this(DEFAULT_MESSAGE);
    }


}
