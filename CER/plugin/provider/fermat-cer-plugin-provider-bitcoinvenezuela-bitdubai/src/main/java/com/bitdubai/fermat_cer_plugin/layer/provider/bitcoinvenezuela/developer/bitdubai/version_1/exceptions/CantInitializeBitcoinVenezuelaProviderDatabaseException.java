package com.bitdubai.fermat_cer_plugin.layer.provider.bitcoinvenezuela.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_cer_plugin.layer.provider.bitcoinvenezuela.developer.bitdubai.version_1.exceptions.CantInitializeDolartodayProviderDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 08/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeBitcoinVenezuelaProviderDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE BITCOINVENEZUELA PROVIDER DATABASE EXCEPTION";

    public CantInitializeBitcoinVenezuelaProviderDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeBitcoinVenezuelaProviderDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeBitcoinVenezuelaProviderDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeBitcoinVenezuelaProviderDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}