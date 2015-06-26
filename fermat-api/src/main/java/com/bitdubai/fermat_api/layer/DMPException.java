package com.bitdubai.fermat_api.layer;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by jorgegonzalez on 2015.06.25..
 */
public class DMPException extends FermatException {

    public static final String DEFAULT_MESSAGE = "THE DMP LAYER HAS DETECTED AN EXCEPTION";

    public DMPException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public DMPException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public DMPException(final String message) {
        this(message, null);
    }

    public DMPException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public DMPException() {
        this(DEFAULT_MESSAGE);
    }
}
