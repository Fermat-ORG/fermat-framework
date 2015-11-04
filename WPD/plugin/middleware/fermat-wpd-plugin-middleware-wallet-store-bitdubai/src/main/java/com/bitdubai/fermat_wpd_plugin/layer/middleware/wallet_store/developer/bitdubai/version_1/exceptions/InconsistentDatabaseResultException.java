package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/27/15.
 */
public class InconsistentDatabaseResultException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Inconsistent database result detected.";
    public InconsistentDatabaseResultException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
