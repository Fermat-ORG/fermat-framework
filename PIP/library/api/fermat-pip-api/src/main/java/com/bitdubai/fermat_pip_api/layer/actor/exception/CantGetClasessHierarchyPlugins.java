package com.bitdubai.fermat_pip_api.layer.actor.exception;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 03/07/15.
 */
public class CantGetClasessHierarchyPlugins extends FermatException {
    public static final String DEFAULT_MESSAGE = "THE LOGTOOL HAS TRIGGERED AN EXCEPTION";

    public CantGetClasessHierarchyPlugins(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

}