package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException</code>
 * is thrown when i couldn't find a crypto payment request.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public class RequestNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "REQUEST NOT FOUND EXCEPTION";

    public RequestNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public RequestNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public RequestNotFoundException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
