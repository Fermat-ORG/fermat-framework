package com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantListCryptoPaymentRequestsException</code>
 * is thrown when there is an error trying to list the crypto payment requests.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/09/2015.
 */
public class CantListCryptoPaymentRequestsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T LIST CRYPTO PAYMENT REQUEST EXCEPTION";

    public CantListCryptoPaymentRequestsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListCryptoPaymentRequestsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantListCryptoPaymentRequestsException(Exception cause) {
        this(DEFAULT_MESSAGE, cause, null, null);
    }

}
