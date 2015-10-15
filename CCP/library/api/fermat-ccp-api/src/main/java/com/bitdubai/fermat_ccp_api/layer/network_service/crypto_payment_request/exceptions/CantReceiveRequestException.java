package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantReceiveRequestException</code>
 * is thrown when there is an error receiving a crypto address request..
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 13/10/2015.
 */
public class CantReceiveRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T RECEIVE CRYPTO PAYMENT REQUEST EXCEPTION";

    public CantReceiveRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantReceiveRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
