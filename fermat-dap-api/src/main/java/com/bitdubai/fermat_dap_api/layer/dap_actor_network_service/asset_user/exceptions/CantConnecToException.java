package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by root on 06/10/15.
 */
public class CantConnecToException extends DAPException {


    public static final String DEFAULT_MESSAGE = "CAN'T CONNECT TO";

    public CantConnecToException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantConnecToException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantConnecToException(final String message) {
        this(message, null);
    }

    public CantConnecToException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantConnecToException() {
        this(DEFAULT_MESSAGE);
    }

}
