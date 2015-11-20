package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.RuntimeManagerNotFoundException</code>
 * is thrown when the requested runtime manager doesn't exist or cannot be found.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/11/2015.
 */
public class RuntimeManagerNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "RUNTIME MANAGER NOT FOUND EXCEPTION";

    public RuntimeManagerNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public RuntimeManagerNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
