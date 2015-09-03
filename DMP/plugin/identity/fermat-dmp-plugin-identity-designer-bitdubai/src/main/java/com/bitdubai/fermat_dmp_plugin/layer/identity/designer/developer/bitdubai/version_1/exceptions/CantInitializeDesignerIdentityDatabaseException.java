package com.bitdubai.fermat_dmp_plugin.layer.identity.designer.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantInitializeDesignerIdentityDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeDesignerIdentityDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE DESIGNER IDENTITY DATABASE EXCEPTION";

    public CantInitializeDesignerIdentityDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeDesignerIdentityDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeDesignerIdentityDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeDesignerIdentityDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}