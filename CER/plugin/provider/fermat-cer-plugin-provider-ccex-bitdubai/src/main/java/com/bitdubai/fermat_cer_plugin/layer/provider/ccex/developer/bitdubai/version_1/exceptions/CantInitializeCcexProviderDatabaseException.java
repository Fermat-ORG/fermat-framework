package com.bitdubai.fermat_cer_plugin.layer.provider.ccex.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_cer_plugin.layer.provider.ccex.developer.bitdubai.version_1.exceptions.CantInitializeCcexProviderDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 27/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCcexProviderDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CCEX PROVIDER DATABASE EXCEPTION";

    public CantInitializeCcexProviderDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCcexProviderDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCcexProviderDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCcexProviderDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}