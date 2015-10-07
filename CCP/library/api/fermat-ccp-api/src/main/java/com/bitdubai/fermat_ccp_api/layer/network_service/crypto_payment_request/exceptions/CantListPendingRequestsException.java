package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantListPendingRequestsException</code>
 * is thrown when there is an error trying to list the pending requests.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public class CantListPendingRequestsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T LIST PENDING REQUESTS EXCEPTION";

    public CantListPendingRequestsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListPendingRequestsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
