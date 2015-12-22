package com.bitdubai.fermat_api.layer.all_definition.settings.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantBuildSettingsObjectException</code>
 * is thrown when there is an error trying to build settings object.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/12/2015.
 */
public class CantBuildSettingsObjectException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T BUILD SETTINGS OBJECT EXCEPTION";

    public CantBuildSettingsObjectException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantBuildSettingsObjectException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
