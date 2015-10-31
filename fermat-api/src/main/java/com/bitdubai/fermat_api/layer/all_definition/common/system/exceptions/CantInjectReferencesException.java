package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantInjectReferencesException</code>
 * is thrown when there is an error trying to inject references.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 26/10/2015.
 */
public class CantInjectReferencesException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T INJECT REFERENCES EXCEPTION";

    public CantInjectReferencesException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInjectReferencesException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
