package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantGetPendingAddressExchangeRequestException</code>
 * is thrown when there is an error trying to get an address exchange request.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 */
public class CantGetPendingAddressExchangeRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET ADDRESS EXCHANGE REQUEST EXCEPTION";

    public CantGetPendingAddressExchangeRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetPendingAddressExchangeRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
