package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.IncompatibleOsContextException</code>
 * is thrown when the os context to assign is not the expected.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/11/2015.
 */
public class IncompatibleOsContextException extends FermatException {

    private static final String DEFAULT_MESSAGE = "INCOMPATIBLE OS CONTEXT EXCEPTION";

    public IncompatibleOsContextException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public IncompatibleOsContextException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public IncompatibleOsContextException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
