package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.exceptions.CantInitializeWalletFactoryMiddlewareDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 17/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeWalletFactoryMiddlewareDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE WALLET FACTORY MIDDLEWARE DATABASE EXCEPTION";

    public CantInitializeWalletFactoryMiddlewareDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeWalletFactoryMiddlewareDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeWalletFactoryMiddlewareDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeWalletFactoryMiddlewareDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
