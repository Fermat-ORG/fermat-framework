package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantSendAddressExchangeRequestException</code>
 * is thrown when there is an error trying to create an address exchange request.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 */
public class CantSendAddressExchangeRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CREATE ADDRESS EXCHANGE REQUEST EXCEPTION";

    public CantSendAddressExchangeRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSendAddressExchangeRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}