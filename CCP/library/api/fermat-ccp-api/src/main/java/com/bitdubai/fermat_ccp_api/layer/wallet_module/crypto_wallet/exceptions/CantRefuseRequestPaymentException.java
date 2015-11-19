package com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions;

/**
 * Created by natalia on 17/11/15.
 */
public class CantRefuseRequestPaymentException extends CryptoWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T REFUSE REQUEST PAYMENT EXCEPTION";

    public CantRefuseRequestPaymentException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRefuseRequestPaymentException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantRefuseRequestPaymentException(final String message) {
        this(message, null);
    }

    public CantRefuseRequestPaymentException() {
        this(DEFAULT_MESSAGE);
    }
}
