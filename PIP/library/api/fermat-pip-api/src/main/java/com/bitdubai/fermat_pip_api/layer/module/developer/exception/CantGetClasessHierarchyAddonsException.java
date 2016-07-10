package com.bitdubai.fermat_pip_api.layer.module.developer.exception;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 2015.07.04..
 */
public class CantGetClasessHierarchyAddonsException extends FermatException {

    public static final String DEFAULT_MESSAGE = "THE LOGTOOL HAS TRIGGERED AN EXCEPTION";

    public CantGetClasessHierarchyAddonsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
