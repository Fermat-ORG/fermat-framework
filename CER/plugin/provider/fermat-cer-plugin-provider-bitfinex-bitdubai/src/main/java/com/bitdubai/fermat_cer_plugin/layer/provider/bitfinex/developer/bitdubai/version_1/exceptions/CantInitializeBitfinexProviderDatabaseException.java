package com.bitdubai.fermat_cer_plugin.layer.provider.bitfinex.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_cer_plugin.layer.provider.bitfinex.developer.bitdubai.version_1.exceptions.CantInitializeBitfinexProviderDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 18/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeBitfinexProviderDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE BITFINEX PROVIDER DATABASE EXCEPTION";

    public CantInitializeBitfinexProviderDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeBitfinexProviderDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeBitfinexProviderDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeBitfinexProviderDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}