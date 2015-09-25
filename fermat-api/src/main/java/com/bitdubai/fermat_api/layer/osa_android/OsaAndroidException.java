package com.bitdubai.fermat_api.layer.osa_android;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by jorgegonzalez on 2015.06.24..
 */
public class OsaAndroidException extends FermatException {

    public static final String DEFAULT_MESSAGE = "THE OS ANDROID SUBSYSTEM HAS THROWN AN EXCEPTION";

    public OsaAndroidException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public OsaAndroidException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public OsaAndroidException(final String message) {
        this(message, null);
    }

    public OsaAndroidException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public OsaAndroidException() {
        this(DEFAULT_MESSAGE);
    }
}
