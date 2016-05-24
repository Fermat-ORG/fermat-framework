package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSendCryptoPaymentRequestException</code>
 * is thrown when there is an error trying to send a crypto payment request.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/11/2015.
 */
public class CantSendCryptoPaymentRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T SEND CRYPTO PAYMENT REQUEST EXCEPTION";

    public CantSendCryptoPaymentRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSendCryptoPaymentRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
