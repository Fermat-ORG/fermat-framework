package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressesNetworkServiceDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Yordin Alayn - (laion.cj91@gmail.com) on 16/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CBP-NEGOTIATION TRANSACTION-CUSTOMER BROKER UPDATE. CAN'T INITIALIZE DATABASE EXCEPTION";

    public CantInitializeDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeDatabaseException(final Exception cause) {
        this(null, cause);
    }

    public CantInitializeDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}