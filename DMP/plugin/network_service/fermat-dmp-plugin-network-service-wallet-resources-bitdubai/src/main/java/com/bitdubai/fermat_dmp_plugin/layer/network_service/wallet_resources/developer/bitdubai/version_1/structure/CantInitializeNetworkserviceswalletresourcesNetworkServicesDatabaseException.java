package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_dmp_plugin.layer.network_services.network_services_wallet_resources.developer.bitdubai.version_1.exceptions.CantInitializeNetworkserviceswalletresourcesNetworkServicesDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Matias Furszyfer - (matiasfurszyfer@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeNetworkserviceswalletresourcesNetworkServicesDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE NETWORK SERVICES WALLET RESOURCES NETWORK SERVICES DATABASE EXCEPTION";

    public CantInitializeNetworkserviceswalletresourcesNetworkServicesDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeNetworkserviceswalletresourcesNetworkServicesDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeNetworkserviceswalletresourcesNetworkServicesDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeNetworkserviceswalletresourcesNetworkServicesDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}