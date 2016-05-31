package com.bitdubai.fermat_cer_plugin.layer.provider.fermatexchange.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cer_plugin.layer.provider.fermatexchange.developer.bitdubai.version_1.exceptions.CantInitializeFermatExchangeProviderDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 23/05/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeFermatExchangeProviderDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE FERMAT_EXCHANGE PROVIDER DATABASE EXCEPTION";

    public CantInitializeFermatExchangeProviderDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeFermatExchangeProviderDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeFermatExchangeProviderDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeFermatExchangeProviderDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}