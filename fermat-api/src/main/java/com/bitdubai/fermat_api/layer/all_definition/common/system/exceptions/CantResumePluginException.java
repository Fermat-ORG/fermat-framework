package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantResumePluginException</code>
 * is thrown when there is an error trying to resume a plugin.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class CantResumePluginException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T RESUME PLUGIN EXCEPTION";

    public CantResumePluginException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantResumePluginException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
