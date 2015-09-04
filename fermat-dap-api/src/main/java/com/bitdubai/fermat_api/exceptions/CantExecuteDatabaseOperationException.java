package com.bitdubai.fermat_api.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Frankli Marcano (franklinmarcano1970@gmail.com) on 01/09/15.
 */
public class CantExecuteDatabaseOperationException extends DAPException {
    static final String DEFAULT_MESSAGE = "There was an error executing a database operation.";

    public CantExecuteDatabaseOperationException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
