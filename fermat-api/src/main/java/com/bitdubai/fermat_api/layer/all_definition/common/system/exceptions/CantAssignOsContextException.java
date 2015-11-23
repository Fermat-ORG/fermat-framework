package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantAssignOsContextException</code>
 * is thrown when there is an error trying to assign the os context.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/11/2015.
 */
public class CantAssignOsContextException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T ASSIGN OS CONTEXT EXCEPTION";

    public CantAssignOsContextException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantAssignOsContextException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantAssignOsContextException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
