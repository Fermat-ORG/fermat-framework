package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantGetModuleManagerException</code>
 * is thrown when there is an error trying to get a module manager.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/10/2015.
 */
public class CantGetModuleManagerException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CANT' GET MODULE MANAGER EXCEPTION";

    public CantGetModuleManagerException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetModuleManagerException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetModuleManagerException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
