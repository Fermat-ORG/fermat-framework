package com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraUserIdentityDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeIntraUserIdentityDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE INTRA USER IDENTITY DATABASE EXCEPTION";

    public CantInitializeIntraUserIdentityDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeIntraUserIdentityDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeIntraUserIdentityDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeIntraUserIdentityDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}