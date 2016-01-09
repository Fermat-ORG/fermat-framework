package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 04/01/16.
 */
public class CantChangeRequestSentCountException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CHANGE CRYPTO PAYMENT REQUEST SENT COUNT EXCEPTION";

    public CantChangeRequestSentCountException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantChangeRequestSentCountException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantChangeRequestSentCountException(String context, String possibleReason) {
        this(null, context, possibleReason);
    }

}
