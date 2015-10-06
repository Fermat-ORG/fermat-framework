package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantChangeCryptoPaymentRequestProtocolStateException</code>
 * is thrown when there is an error trying to change the protocol state of a crypto payment request.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/10/2015.
 */
public class CantChangeCryptoPaymentRequestProtocolStateException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CHANGE CRYPTO PAYMENT REQUEST PROTOCOL STATE EXCEPTION";

    public CantChangeCryptoPaymentRequestProtocolStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantChangeCryptoPaymentRequestProtocolStateException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantChangeCryptoPaymentRequestProtocolStateException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
