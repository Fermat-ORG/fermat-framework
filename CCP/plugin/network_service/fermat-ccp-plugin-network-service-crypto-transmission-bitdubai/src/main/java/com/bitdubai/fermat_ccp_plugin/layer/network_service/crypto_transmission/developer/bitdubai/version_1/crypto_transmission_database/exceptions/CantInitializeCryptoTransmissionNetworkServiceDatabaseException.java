package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_ccp_plugin.layer.network_service.cryptotransmission.developer.bitdubai.version_1.exceptions.CantInitializeCryptoTransmissionNetworkServiceDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Matias Furszyfer - (matiasfurszyfer@gmail.com) on 05/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCryptoTransmissionNetworkServiceDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CRYPTOTRANSMISSION NETWORK_SERVICE DATABASE EXCEPTION";

    public CantInitializeCryptoTransmissionNetworkServiceDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCryptoTransmissionNetworkServiceDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCryptoTransmissionNetworkServiceDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCryptoTransmissionNetworkServiceDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}