package com.bitdubai.fermat_cer_plugin.layer.provider.bter.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_cer_plugin.layer.provider.bter.developer.bitdubai.version_1.exceptions.CantInitializeBterProviderDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 19/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeBterProviderDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE BTER PROVIDER DATABASE EXCEPTION";

    public CantInitializeBterProviderDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeBterProviderDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeBterProviderDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeBterProviderDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}