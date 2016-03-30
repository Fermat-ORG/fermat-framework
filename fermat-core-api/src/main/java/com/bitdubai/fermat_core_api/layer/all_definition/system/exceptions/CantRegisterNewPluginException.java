package com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterNewPluginException</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/03/2016.
 */
public class CantRegisterNewPluginException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T REGISTER NEW PLUGIN EXCEPTION EXCEPTION";

    public CantRegisterNewPluginException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRegisterNewPluginException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantRegisterNewPluginException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }


}
