package com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantRegisterAddonException</code>
 * is thrown when there is an error trying to register an addon.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/10/2015.
 */
public class CantRegisterAddonException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN¿T REGISTER ADDON EXCEPTION";

    public CantRegisterAddonException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRegisterAddonException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantRegisterAddonException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
