package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressesNetworkServiceDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCryptoAddressesNetworkServiceDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE CRYPTO ADDRESSES NETWORK SERVICE DATABASE EXCEPTION";

    public CantInitializeCryptoAddressesNetworkServiceDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCryptoAddressesNetworkServiceDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCryptoAddressesNetworkServiceDatabaseException(final Exception cause) {
        this(null, cause);
    }

    public CantInitializeCryptoAddressesNetworkServiceDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCryptoAddressesNetworkServiceDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}