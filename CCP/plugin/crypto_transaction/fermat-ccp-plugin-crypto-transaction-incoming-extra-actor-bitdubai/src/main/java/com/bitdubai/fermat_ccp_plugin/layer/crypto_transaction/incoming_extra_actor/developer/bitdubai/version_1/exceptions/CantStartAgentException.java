package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by ciencias on 3/30/15.
 */
public class CantStartAgentException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T START AGENT";

    public CantStartAgentException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartAgentException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantStartAgentException(final String message) {
        this(message, null);
    }

    public CantStartAgentException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantStartAgentException() {
        this(DEFAULT_MESSAGE);
    }

}
