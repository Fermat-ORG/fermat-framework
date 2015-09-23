package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addressees.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantListPendingAddressExchangeRequestsException</code>
 * is thrown when there is an error trying to list pending address exchange requests.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 */
public class CantListPendingAddressExchangeRequestsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T LIST PENDING ADDRESS EXCHANGE REQUESTS EXCEPTION";

    public CantListPendingAddressExchangeRequestsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListPendingAddressExchangeRequestsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
