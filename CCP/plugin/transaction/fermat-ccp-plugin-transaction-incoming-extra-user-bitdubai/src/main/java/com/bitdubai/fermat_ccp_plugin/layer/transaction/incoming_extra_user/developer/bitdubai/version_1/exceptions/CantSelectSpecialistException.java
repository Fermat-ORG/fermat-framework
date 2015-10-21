package com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by eze on 12/06/15.
 */
public class CantSelectSpecialistException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T SELECT SPECIALIST";

    public CantSelectSpecialistException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSelectSpecialistException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSelectSpecialistException(final String message) {
        this(message, null);
    }

    public CantSelectSpecialistException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantSelectSpecialistException() {
        this(DEFAULT_MESSAGE);
    }
}
