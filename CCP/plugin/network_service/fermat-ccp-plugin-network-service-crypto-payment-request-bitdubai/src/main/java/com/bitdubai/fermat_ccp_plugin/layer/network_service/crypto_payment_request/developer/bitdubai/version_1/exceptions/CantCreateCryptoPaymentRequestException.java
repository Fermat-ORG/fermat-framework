package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantCreateCryptoPaymentRequestException</code>
 * is thrown when there is an error trying to create a crypto payment request.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/10/2015.
 */
public class CantCreateCryptoPaymentRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CREATE CRYPTO PAYMENT REQUEST EXCEPTION";

    public CantCreateCryptoPaymentRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateCryptoPaymentRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
