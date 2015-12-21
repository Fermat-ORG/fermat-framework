package com.bitdubai.fermat_api.layer.modules.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.modules.exceptions.ModuleSettingsNotFoundException</code>
 * is thrown when the module settings was not found.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 */
public class ModuleSettingsNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "MODULE SETTINGS NOT FOUND EXCEPTION";

    public ModuleSettingsNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ModuleSettingsNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
