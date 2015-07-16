package com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 01/07/15.
 */
public class AppRuntimeExceptions extends FermatException {
    public static final String DEFAULT_MESSAGE = "THE APP RUNTIME HAS TRIGGERED AN EXCEPTION";

    public AppRuntimeExceptions(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

}