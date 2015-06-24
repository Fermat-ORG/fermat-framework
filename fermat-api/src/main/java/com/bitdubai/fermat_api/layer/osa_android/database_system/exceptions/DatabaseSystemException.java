package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

import com.bitdubai.fermat_api.layer.osa_android.OsaAndroidException;

/**
 * Created by jorgegonzalez on 2015.06.24..
 */
public class DatabaseSystemException extends OsaAndroidException {

    private static final String DEFAULT_MESSAGE = "THE DATABASE SYSTEM HAS TRIGGERED AN EXCEPTION: ";

    public DatabaseSystemException(final String message, final Exception cause){
        super(DEFAULT_MESSAGE + message, cause);
    }

    public DatabaseSystemException(final String message){
        this(message, null);
    }

    public DatabaseSystemException(){
        this("");
    }
}
