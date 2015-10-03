package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException</code>
 * is thrown when there is an error trying to initialize Crypto Payment Request Network Service Database.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public class CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE CRYPTO PAYMENT REQUEST NETWORK SERVICE DATABASE EXCEPTION";

    public CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException(Exception cause) {
        this(DEFAULT_MESSAGE, cause, null, null);
    }

}
