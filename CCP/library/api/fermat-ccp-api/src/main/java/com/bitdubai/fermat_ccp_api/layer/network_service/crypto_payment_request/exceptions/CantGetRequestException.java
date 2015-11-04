package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantGetRequestException</code>
 * is thrown when there is an error trying to get a crypto payment request.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public class CantGetRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET CRYPTO PAYMENT REQUEST EXCEPTION";

    public CantGetRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetRequestException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
