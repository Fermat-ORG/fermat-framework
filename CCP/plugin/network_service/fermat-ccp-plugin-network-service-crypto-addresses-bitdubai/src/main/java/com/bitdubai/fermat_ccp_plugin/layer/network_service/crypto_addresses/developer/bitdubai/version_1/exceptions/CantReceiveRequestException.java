package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantReceiveRequestException</code>
 * is thrown when there is an error trying to receive a, address exchange request.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/10/2015.
 */
public class CantReceiveRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CANT' RECEIVE REQUEST EXCEPTION";

    public CantReceiveRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantReceiveRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantReceiveRequestException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
