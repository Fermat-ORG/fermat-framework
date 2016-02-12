package com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>PlatformNotFoundException</code>
 * is thrown when we can't find the requested platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/10/2015.
 */
public class PlatformNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "PLATFORM NOT FOUND EXCEPTION";

    public PlatformNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public PlatformNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public PlatformNotFoundException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
