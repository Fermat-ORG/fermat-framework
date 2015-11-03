package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CryptoAddressesMiddlewarePluginNotStartedException</code>
 * is thrown when the plugin is not started..
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 */
public class CryptoAddressesMiddlewarePluginNotStartedException extends FermatException {

    public static final String DEFAULT_MESSAGE = "PLUGIN CRYPTO ADDRESSES MIDDLEWARE BCH IS NOT STARTED.";

    public CryptoAddressesMiddlewarePluginNotStartedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CryptoAddressesMiddlewarePluginNotStartedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CryptoAddressesMiddlewarePluginNotStartedException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}