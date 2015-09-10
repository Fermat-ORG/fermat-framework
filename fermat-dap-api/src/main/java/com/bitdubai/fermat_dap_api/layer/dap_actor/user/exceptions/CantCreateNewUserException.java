package com.bitdubai.fermat_dap_api.layer.dap_actor.user.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Nerio on 07/09/15.
 */
public class CantCreateNewUserException extends DAPException {

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE NEW USER";

    public CantCreateNewUserException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateNewUserException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateNewUserException(final String message) {
        this(message, null);
    }

    public CantCreateNewUserException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateNewUserException() {
        this(DEFAULT_MESSAGE);
    }
}
