package com.bitdubai.fermat_api.layer.all_definition.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartPluginException</code>
 * is thrown when there is an error trying to start a plugin.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class CantStartPluginException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T START PLUGIN EXCEPTION";

    public CantStartPluginException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartPluginException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
