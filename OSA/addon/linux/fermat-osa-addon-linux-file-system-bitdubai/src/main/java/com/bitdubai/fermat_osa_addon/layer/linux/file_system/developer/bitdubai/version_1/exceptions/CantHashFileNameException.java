package com.bitdubai.fermat_osa_addon.layer.linux.file_system.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.exceptions.CantHashFileNameException</code>
 * is thrown when there is an error trying to hash the database name.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/12/2015.
 */
public class CantHashFileNameException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T HASH DATABASE NAME EXCEPTION";

    public CantHashFileNameException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHashFileNameException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
