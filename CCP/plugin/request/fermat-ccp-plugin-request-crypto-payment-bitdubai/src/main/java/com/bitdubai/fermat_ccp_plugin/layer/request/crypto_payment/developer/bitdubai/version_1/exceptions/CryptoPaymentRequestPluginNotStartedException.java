package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CryptoPaymentRequestPluginNotStartedException</code>
 * is thrown when we're trying to manage an event but the plugin is not started.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public class CryptoPaymentRequestPluginNotStartedException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CRYPTO PAYMENT REQUEST PLUGIN NOT STARTED EXCEPTION";

    public CryptoPaymentRequestPluginNotStartedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CryptoPaymentRequestPluginNotStartedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
