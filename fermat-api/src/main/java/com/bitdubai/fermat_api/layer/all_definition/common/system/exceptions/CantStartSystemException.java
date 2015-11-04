package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantStartSystemException</code>
 * is thrown when there is an error trying to start the system.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/10/2015.
 */
public class CantStartSystemException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T START SYSTEM EXCEPTION";

    public CantStartSystemException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartSystemException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
