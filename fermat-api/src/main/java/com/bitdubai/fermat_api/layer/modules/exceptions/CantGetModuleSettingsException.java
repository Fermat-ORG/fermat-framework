package com.bitdubai.fermat_api.layer.modules.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.modules.exceptions.CantGetModuleSettingsException</code>
 * is thrown when there is an error trying to get module settings.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 */
public class CantGetModuleSettingsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET MODULE SETTINGS EXCEPTION";

    public CantGetModuleSettingsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetModuleSettingsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
