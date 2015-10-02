package com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.InsufficientFoundsException</code>
 * is thrown when there's no enough crypto to send the transaction.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/10/2015.
 */
public class InsufficientFoundsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "INSUFFICIENT FOUNDS EXCEPTION";

    public InsufficientFoundsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public InsufficientFoundsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public InsufficientFoundsException(Exception cause) {
        this(DEFAULT_MESSAGE, cause, null, null);
    }

}
