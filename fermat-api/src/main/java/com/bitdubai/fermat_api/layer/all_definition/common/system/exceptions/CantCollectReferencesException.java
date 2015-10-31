package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantCollectReferencesException</code>
 * is thrown when there is an error collecting references.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 28/10/2015.
 */
public class CantCollectReferencesException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T COLLECT REFERENCES EXCEPTION";

    public CantCollectReferencesException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCollectReferencesException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
