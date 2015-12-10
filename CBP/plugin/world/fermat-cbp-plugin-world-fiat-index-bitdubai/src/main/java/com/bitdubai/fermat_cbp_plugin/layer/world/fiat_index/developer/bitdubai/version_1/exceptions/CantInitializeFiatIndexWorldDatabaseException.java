package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.exceptions.CantInitializeFiatIndexWorldDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 21/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeFiatIndexWorldDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE FIAT INDEX WORLD DATABASE EXCEPTION";

    public CantInitializeFiatIndexWorldDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeFiatIndexWorldDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeFiatIndexWorldDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeFiatIndexWorldDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}