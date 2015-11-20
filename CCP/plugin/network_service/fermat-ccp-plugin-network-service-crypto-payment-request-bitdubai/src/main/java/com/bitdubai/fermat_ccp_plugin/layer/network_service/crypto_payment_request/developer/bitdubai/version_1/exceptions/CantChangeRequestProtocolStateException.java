package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantChangeRequestProtocolStateException</code>
 * is thrown when there is an error trying to change the request protocol state.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/10/2015.
 */
public class CantChangeRequestProtocolStateException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CHANGE CRYPTO PAYMENT REQUEST PROTOCOL STATE EXCEPTION";

    public CantChangeRequestProtocolStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantChangeRequestProtocolStateException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantChangeRequestProtocolStateException(String context, String possibleReason) {
        this(null, context, possibleReason);
    }

}
