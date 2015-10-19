package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantReceiveAcceptanceException</code>
 * is thrown when there is an error trying to receive a message of acceptance.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/10/2015.
 */
public class CantReceiveAcceptanceException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T RECEIVE ACCEPTANCE EXCEPTION";

    public CantReceiveAcceptanceException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantReceiveAcceptanceException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
