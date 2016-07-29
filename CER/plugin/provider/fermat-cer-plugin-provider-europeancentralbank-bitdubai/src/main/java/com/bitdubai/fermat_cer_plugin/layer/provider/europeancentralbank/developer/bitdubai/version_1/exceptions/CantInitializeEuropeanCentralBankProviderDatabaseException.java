package com.bitdubai.fermat_cer_plugin.layer.provider.europeancentralbank.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_cer_plugin.layer.provider.europeancentralbank.developer.bitdubai.version_1.exceptions.CantInitializeDolartodayProviderDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 08/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeEuropeanCentralBankProviderDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE EUROPEAN_CENTRAL_BANK PROVIDER DATABASE EXCEPTION";

    public CantInitializeEuropeanCentralBankProviderDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeEuropeanCentralBankProviderDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeEuropeanCentralBankProviderDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeEuropeanCentralBankProviderDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}