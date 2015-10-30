package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>AddonNotFoundException</code>
 * is thrown when we can't find the requested addon.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/10/2015.
 */
public class AddonNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "ADDON NOT FOUND EXCEPTION";

    public AddonNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public AddonNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public AddonNotFoundException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
