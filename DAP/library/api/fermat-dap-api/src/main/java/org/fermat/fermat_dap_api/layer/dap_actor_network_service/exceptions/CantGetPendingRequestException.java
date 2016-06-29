package org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantGetPendingAddressExchangeRequestException</code>
 * is thrown when there is an error trying to get an address exchange request.
 * <p/>
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
