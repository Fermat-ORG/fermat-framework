package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by ciencias on 3/30/15.
 */
public class CantSaveEventException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T SAVE EVENT";

    public CantSaveEventException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSaveEventException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSaveEventException(final String message) {
        this(message, null);
    }

    public CantSaveEventException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantSaveEventException() {
        this(DEFAULT_MESSAGE);
    }
}
