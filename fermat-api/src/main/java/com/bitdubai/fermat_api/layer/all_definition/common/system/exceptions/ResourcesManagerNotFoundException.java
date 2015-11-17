package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>ResourcesManagerNotFoundException</code>
 * is thrown when the requested resources manager doesn't exist.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/11/2015.
 */
public class ResourcesManagerNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "RESOURCES MANAGER NOT FOUND EXCEPTION";

    public ResourcesManagerNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ResourcesManagerNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
