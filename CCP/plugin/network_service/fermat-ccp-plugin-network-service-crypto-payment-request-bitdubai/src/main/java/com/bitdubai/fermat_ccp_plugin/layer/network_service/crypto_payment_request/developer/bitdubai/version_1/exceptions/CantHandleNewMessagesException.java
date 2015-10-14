package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantHandleNewMessagesException</code>
 * is thrown when there is an error trying to handle a new message for the crypto payment request network service.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 13/10/2015.
 */
public class CantHandleNewMessagesException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE NEW MESSAGES EXCEPTION";

    public CantHandleNewMessagesException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHandleNewMessagesException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantHandleNewMessagesException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
