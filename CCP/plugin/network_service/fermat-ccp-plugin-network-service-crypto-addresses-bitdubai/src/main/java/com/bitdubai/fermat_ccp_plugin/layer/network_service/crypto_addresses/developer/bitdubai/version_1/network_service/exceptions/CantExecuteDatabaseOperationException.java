package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.network_service.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/23/15.
 */
public class CantExecuteDatabaseOperationException extends FermatException {
    static final String DEFAULT_MESSAGE = "There was an error executing a database operation.";

    public CantExecuteDatabaseOperationException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
