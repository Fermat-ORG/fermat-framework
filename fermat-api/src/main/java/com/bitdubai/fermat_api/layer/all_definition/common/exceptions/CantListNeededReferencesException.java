package com.bitdubai.fermat_api.layer.all_definition.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantListNeededReferencesException</code>
 * is thrown when there is an error trying to list needed references.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 27/10/2015.
 */
public class CantListNeededReferencesException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T LIST NEEDED REFERENCES EXCEPTION";

    public CantListNeededReferencesException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListNeededReferencesException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
