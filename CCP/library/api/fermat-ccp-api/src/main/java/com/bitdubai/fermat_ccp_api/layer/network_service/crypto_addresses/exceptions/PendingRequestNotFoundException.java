package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException</code>
 * is thrown when there's no pending request with the given UUID.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 */
public class PendingRequestNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "PENDING REQUEST NOT FOUND EXCEPTION";

    public PendingRequestNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public PendingRequestNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
