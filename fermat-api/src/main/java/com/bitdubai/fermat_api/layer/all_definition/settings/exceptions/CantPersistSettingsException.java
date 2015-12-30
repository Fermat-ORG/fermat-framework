package com.bitdubai.fermat_api.layer.all_definition.settings.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException</code>
 * is thrown when there is an error persisting module settings.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 */
public class CantPersistSettingsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T PERSIST MODULE SETTINGS EXCEPTION";

    public CantPersistSettingsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantPersistSettingsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
