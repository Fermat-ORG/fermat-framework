package com.bitdubai.fermat_api.layer.all_definition.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartPluginDeveloperException</code>
 * is thrown when there is an error trying to start a plugin developer.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class CantStartPluginDeveloperException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T START PLUGIN DEVELOPER EXCEPTION";

    public CantStartPluginDeveloperException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartPluginDeveloperException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
