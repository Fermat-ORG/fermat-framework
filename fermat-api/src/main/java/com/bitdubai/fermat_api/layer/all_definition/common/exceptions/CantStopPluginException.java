package com.bitdubai.fermat_api.layer.all_definition.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStopPluginException</code>
 * is thrown when there is an error trying to stop a plugin.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class CantStopPluginException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T STOP PLUGIN EXCEPTION";

    public CantStopPluginException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStopPluginException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
