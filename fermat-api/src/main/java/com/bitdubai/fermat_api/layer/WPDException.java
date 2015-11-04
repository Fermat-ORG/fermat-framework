package com.bitdubai.fermat_api.layer;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Nerio on 29/09/15.
 */
public class WPDException extends FermatException {

    public static final String DEFAULT_MESSAGE = "THE DAP LAYER HAS DETECTED AN EXCEPTION";

    public WPDException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public WPDException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public WPDException(final String message) {
        this(message, null);
    }

    public WPDException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public WPDException() {
        this(DEFAULT_MESSAGE);
    }
}
