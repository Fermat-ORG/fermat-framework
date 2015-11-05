package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantCreateSystemException</code>
 * is thrown when there is an error trying to instantiate the system class.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/11/2015.
 */
public class CantCreateSystemException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CREATE SYSTEM EXCEPTION";

    public CantCreateSystemException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateSystemException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
