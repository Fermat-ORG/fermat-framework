package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantGetPendingAddressExchangeRequestException</code>
 * is thrown when there is an error trying to get an address exchange request.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 */
public class CantGetPendingRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET REQUEST EXCEPTION";

    public CantGetPendingRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetPendingRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
