package com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantGetPluginIdException</code>
 * is thrown when there is an error trying to get the plugin id.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 26/10/2015.
 */
public class CantGetPluginIdException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET PLUGIN ID EXCEPTION";

    public CantGetPluginIdException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetPluginIdException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetPluginIdException(String context, String possibleReason) {
        this(null, context, possibleReason);
    }


}
