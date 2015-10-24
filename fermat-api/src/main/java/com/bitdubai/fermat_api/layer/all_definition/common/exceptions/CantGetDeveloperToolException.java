package com.bitdubai.fermat_api.layer.all_definition.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantGetDeveloperToolException</code>
 * is thrown when there is an error trying to get a developer tool.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class CantGetDeveloperToolException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET DEVELOPER TOOL EXCEPTION";

    public CantGetDeveloperToolException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetDeveloperToolException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
