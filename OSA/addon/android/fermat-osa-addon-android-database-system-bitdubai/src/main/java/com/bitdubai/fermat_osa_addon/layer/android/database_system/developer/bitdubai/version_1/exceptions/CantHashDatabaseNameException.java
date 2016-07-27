package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.exceptions.CantHashDatabaseNameException</code>
 * is thrown when there is an error trying to hash the database name.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 27/10/2015.
 */
public class CantHashDatabaseNameException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T HASH DATABASE NAME EXCEPTION";

    public CantHashDatabaseNameException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHashDatabaseNameException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
