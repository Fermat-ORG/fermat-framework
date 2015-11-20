package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantCreateRequestException</code>
 * is thrown when there is an error trying to create a request.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/10/2015.
 */
public class CantCreateRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CREATE REQUEST EXCEPTION";

    public CantCreateRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
