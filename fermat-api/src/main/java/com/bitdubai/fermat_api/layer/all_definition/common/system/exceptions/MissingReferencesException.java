package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>MissingReferencesException</code>
 * is thrown when there is missing references for a plugin or addon.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 26/10/2015.
 */
public class MissingReferencesException extends FermatException {

    private static final String DEFAULT_MESSAGE = "MISSING REFERENCES EXCEPTION";

    public MissingReferencesException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public MissingReferencesException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public MissingReferencesException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }


}
