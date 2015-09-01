package com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
//TODO: Refactor this exception to fermat_dap_api
public class CantExecuteDatabaseOperationException extends FermatException {
    static final String DEFAULT_MESSAGE = "There was an error executing a database operation.";

    public CantExecuteDatabaseOperationException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
