package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/25/15.
 */
public class CantExecuteDatabaseOperationException extends FermatException {
    static final String DEFAULT_MESSAGE = "there was an error executing a database operation.";
    public CantExecuteDatabaseOperationException( Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
