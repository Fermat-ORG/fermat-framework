package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 04/01/16.
 */
public class CantDeletePaymentRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T DELETE PAYMENT REQUEST EXCEPTION";

    public CantDeletePaymentRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDeletePaymentRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantDeletePaymentRequestException(String context, String possibleReason) {
        this(null, context, possibleReason);
    }

}
