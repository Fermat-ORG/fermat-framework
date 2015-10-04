package com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.InsufficientFundsException</code>
 * is thrown when there's no enough crypto to send the transaction.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/10/2015.
 */
public class InsufficientFundsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "INSUFFICIENT FOUNDS EXCEPTION";

    public InsufficientFundsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public InsufficientFundsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public InsufficientFundsException(Exception cause) {
        this(DEFAULT_MESSAGE, cause, null, null);
    }

}
