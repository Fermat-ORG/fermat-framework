package com.bitdubai.fermat_cer_plugin.layer.provider.btce.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_cer_plugin.layer.provider.btce.developer.bitdubai.version_1.exceptions.CantInitializeBtceProviderDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 27/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeBtceProviderDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE BTCE PROVIDER DATABASE EXCEPTION";

    public CantInitializeBtceProviderDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeBtceProviderDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeBtceProviderDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeBtceProviderDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}