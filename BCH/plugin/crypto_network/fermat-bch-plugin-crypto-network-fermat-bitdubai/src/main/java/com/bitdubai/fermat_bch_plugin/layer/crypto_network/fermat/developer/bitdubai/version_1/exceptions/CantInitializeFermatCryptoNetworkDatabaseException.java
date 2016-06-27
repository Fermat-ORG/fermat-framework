package com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 6/22/16.
 */
public class CantInitializeFermatCryptoNetworkDatabaseException  extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE FERMAT CRYPTONETWORK DATABASE EXCEPTION";

    public CantInitializeFermatCryptoNetworkDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeFermatCryptoNetworkDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeFermatCryptoNetworkDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeFermatCryptoNetworkDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
