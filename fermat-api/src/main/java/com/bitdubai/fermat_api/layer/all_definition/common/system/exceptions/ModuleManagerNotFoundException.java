package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>ModuleManagerNotFoundException</code>
 * is thrown when the requested module manager doesn't exist.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/10/2015.
 */
public class ModuleManagerNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "MODULE MANAGER NOT FOUND EXCEPTION";

    public ModuleManagerNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ModuleManagerNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
