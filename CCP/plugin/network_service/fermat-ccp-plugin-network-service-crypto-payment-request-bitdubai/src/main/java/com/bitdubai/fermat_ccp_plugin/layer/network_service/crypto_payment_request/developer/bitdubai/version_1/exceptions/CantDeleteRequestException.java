package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantDeleteRequestException</code>
 * is thrown when there is an error trying to delete a crypto payment request.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/10/2015.
 */
public class CantDeleteRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T DELETE REQUEST EXCEPTION";

    public CantDeleteRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDeleteRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantDeleteRequestException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
