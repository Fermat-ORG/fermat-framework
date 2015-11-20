package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetRuntimeManagerException</code>
 * is thrown when there is an error trying to get the runtime manager.
 * <p/>
 * Created by lnacosta (laion.cj91@gmail.com) on 16/11/2015.
 */
public class CantGetRuntimeManagerException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET RUNTIME MANAGER EXCEPTION";

    public CantGetRuntimeManagerException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetRuntimeManagerException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetRuntimeManagerException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
