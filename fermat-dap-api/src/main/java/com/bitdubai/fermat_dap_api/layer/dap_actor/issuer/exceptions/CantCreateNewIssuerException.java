package com.bitdubai.fermat_dap_api.layer.dap_actor.issuer.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Nerio on 07/09/15.
 */
public class CantCreateNewIssuerException extends DAPException {

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE NEW ISSUER";

    public CantCreateNewIssuerException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateNewIssuerException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateNewIssuerException(final String message) {
        this(message, null);
    }

    public CantCreateNewIssuerException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateNewIssuerException() {
        this(DEFAULT_MESSAGE);
    }
}
