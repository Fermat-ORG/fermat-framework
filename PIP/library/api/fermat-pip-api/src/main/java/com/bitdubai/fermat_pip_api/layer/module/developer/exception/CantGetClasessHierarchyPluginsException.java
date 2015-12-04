package com.bitdubai.fermat_pip_api.layer.module.developer.exception;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 03/07/15.
 */
public class CantGetClasessHierarchyPluginsException extends FermatException {
    public static final String DEFAULT_MESSAGE = "THE LOGTOOL HAS TRIGGERED AN EXCEPTION";

    public CantGetClasessHierarchyPluginsException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

}