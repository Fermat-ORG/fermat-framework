package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantExecutePendingActionsException</code>
 * is thrown when there is an error trying to handle a crypto address new event.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 */
public class CantExecutePendingActionsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE CRYPTO ADDRESS NEW EVENT EXCEPTION";

    public CantExecutePendingActionsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantExecutePendingActionsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantExecutePendingActionsException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
