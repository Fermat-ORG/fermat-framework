package com.bitdubai.fermat_api.layer.modules.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.modules.exceptions.CantPersistModuleSettingsException</code>
 * is thrown when there is an error persisting module settings.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 */
public class CantPersistModuleSettingsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T PERSIST MODULE SETTINGS EXCEPTION";

    public CantPersistModuleSettingsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantPersistModuleSettingsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
