package com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by jorgegonzalez on 2015.06.30..
 */
public class FileSystemException extends FermatException {
    public static final String DEFAULT_MESSAGE = "THE FILE SYSTEM HAS TRIGGERED AN EXCEPTION";

    public FileSystemException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

}
