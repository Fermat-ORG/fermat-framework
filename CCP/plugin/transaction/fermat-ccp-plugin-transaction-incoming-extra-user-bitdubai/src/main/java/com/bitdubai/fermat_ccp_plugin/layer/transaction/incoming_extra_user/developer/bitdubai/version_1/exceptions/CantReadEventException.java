package com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by arturo on 05/05/15.
 */
public class CantReadEventException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T READ EVENT";

    public CantReadEventException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantReadEventException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantReadEventException(final String message) {
        this(message, null);
    }

    public CantReadEventException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantReadEventException() {
        this(DEFAULT_MESSAGE);
    }
}
