package com.bitdubai.fermat_api.layer.osa_android;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by jorgegonzalez on 2015.06.24..
 */
public class OsaAndroidException extends FermatException {

    private static final String DEFAULT_MESSAGE = "THE OS ANDROID SUBSYSTEM HAS THROWN AN EXCEPTION: ";

    public OsaAndroidException(final String message, final Exception cause){
        super(DEFAULT_MESSAGE + message, cause);
    }

    public OsaAndroidException(final String message){
        this(message, null);
    }

    public OsaAndroidException(){
        this("");
    }
}
