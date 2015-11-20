package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ErrorManagerNotFoundException</code>
 * is thrown when the error manager cannot be found.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/11/2015.
 */
public class ErrorManagerNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "ERROR MANAGER NOT FOUND EXCEPTION";

    public ErrorManagerNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ErrorManagerNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
