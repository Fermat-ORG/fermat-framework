package com.bitdubai.fermat_cbp_plugin.layer.middleware.crypto_broker_wallet_identity.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.middleware.crypto_broker_wallet_identity.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerWalletIdentityMiddlewareDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 28/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCryptoBrokerWalletIdentityMiddlewareDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CRYPTO BROKER WALLET IDENTITY MIDDLEWARE DATABASE EXCEPTION";

    public CantInitializeCryptoBrokerWalletIdentityMiddlewareDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCryptoBrokerWalletIdentityMiddlewareDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCryptoBrokerWalletIdentityMiddlewareDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCryptoBrokerWalletIdentityMiddlewareDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}