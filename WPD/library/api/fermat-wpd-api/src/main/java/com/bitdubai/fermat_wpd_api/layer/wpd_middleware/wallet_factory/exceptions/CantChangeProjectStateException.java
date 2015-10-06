package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 8/24/15.
 */
public class CantChangeProjectStateException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Error trying to change the project state.";

    public CantChangeProjectStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
