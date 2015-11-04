package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantStartPluginVersionException</code>
 * is thrown when there is an error trying to start a plugin version.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class CantStartPluginVersionException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T START PLUGIN VERSION EXCEPTION";

    public CantStartPluginVersionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartPluginVersionException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
