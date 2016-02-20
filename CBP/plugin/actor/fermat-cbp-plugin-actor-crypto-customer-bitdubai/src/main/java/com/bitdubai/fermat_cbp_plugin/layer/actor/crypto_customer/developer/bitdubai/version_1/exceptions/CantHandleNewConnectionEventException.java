package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Angel 15/02/2015
 */
public class CantHandleNewConnectionEventException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T HANDLE NEW CONNECTION EVENT EXCEPTION";

    public CantHandleNewConnectionEventException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHandleNewConnectionEventException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, null, context, possibleReason);
    }
}