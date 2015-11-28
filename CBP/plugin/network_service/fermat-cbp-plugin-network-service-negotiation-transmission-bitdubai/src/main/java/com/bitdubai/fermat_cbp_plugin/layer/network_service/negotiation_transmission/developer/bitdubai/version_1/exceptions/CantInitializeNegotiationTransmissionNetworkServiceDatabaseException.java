package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions;

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
public class CantInitializeNegotiationTransmissionNetworkServiceDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE NEGOTIATION TRANSACTION NETWORK SERVICE DATABASE EXCEPTION";

    public CantInitializeNegotiationTransmissionNetworkServiceDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeNegotiationTransmissionNetworkServiceDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeNegotiationTransmissionNetworkServiceDatabaseException(final Exception cause) {
        this(null, cause);
    }

    public CantInitializeNegotiationTransmissionNetworkServiceDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeNegotiationTransmissionNetworkServiceDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}