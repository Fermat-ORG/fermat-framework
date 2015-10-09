package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_bch_plugin.layer.cryptonetwork.bitcoin.developer.bitdubai.version_1.exceptions.CantInitializeBitcoinCryptoNetworkDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 09/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeBitcoinCryptoNetworkDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE BITCOIN CRYPTONETWORK DATABASE EXCEPTION";

    public CantInitializeBitcoinCryptoNetworkDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeBitcoinCryptoNetworkDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeBitcoinCryptoNetworkDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeBitcoinCryptoNetworkDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
