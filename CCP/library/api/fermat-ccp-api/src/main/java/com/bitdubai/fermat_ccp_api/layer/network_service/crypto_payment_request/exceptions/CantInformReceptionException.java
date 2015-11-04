package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantInformReceptionException</code>
 * is thrown when there is an error trying to inform the reception for a crypto payment request.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/10/2015.
 */
public class CantInformReceptionException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T INFORM RECEPTION EXCEPTION";

    public CantInformReceptionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInformReceptionException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
