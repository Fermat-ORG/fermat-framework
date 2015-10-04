package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantChangeCryptoPaymentRequestStateException</code>
 * is thrown when there is an error trying to change the crypto payment request state.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public class CantChangeCryptoPaymentRequestStateException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CHANGE CRYPTO PAYMENT REQUEST STATE EXCEPTION";

    public CantChangeCryptoPaymentRequestStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantChangeCryptoPaymentRequestStateException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantChangeCryptoPaymentRequestStateException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
