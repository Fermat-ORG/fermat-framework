package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>VersionNotFoundException</code>
 * is thrown when we can't find a version for a specific plugin.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class VersionNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "PLUGIN VERSION NOT FOUND EXCEPTION";

    public VersionNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public VersionNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public VersionNotFoundException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public VersionNotFoundException(String context, String possibleReason, Exception e) {
        this(DEFAULT_MESSAGE, e, context, possibleReason);
    }

}
