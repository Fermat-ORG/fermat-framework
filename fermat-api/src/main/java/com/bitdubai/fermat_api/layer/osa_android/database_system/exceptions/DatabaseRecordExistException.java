package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

import com.bitdubai.fermat_api.layer.osa_android.OsaAndroidException;

/**
 * Created by jorgegonzalez on 2015.06.24..
 */
public class DatabaseRecordExistException extends OsaAndroidException {

    public static final String DEFAULT_MESSAGE = "Record exist exception";

    public DatabaseRecordExistException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public DatabaseRecordExistException(final String message) {
        super(message, null, null, null);
    }
}
