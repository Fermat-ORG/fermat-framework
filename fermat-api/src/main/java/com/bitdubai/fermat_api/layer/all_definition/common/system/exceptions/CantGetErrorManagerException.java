package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetErrorManagerException</code>
 * is thrown when there is an error trying to get the error manager.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/11/2015.
 */
public class CantGetErrorManagerException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET ERROR MANAGER EXCEPTION";

    public CantGetErrorManagerException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetErrorManagerException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
