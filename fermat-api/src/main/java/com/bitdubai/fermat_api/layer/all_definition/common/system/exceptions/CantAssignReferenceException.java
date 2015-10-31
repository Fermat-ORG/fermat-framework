package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantAssignReferenceException</code>
 * is thrown when there is an error trying to assign a reference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 27/10/2015.
 */
public class CantAssignReferenceException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T ASSIGN REFERENCE EXCEPTION";

    public CantAssignReferenceException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantAssignReferenceException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantAssignReferenceException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
