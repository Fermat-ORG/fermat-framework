package com.bitdubai.fermat_api.layer.all_definition.settings.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException</code>
 * is thrown when the module settings was not found.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 */
public class SettingsNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "MODULE SETTINGS NOT FOUND EXCEPTION";

    public SettingsNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public SettingsNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
