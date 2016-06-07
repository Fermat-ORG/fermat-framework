package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions;

/**
 * Created by natalia on 17/11/15.
 */
public class PaymentRequestNotFoundException extends FermatWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T ACCEPT REQUEST PAYMENT, Not Found EXCEPTION";

    public PaymentRequestNotFoundException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public PaymentRequestNotFoundException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public PaymentRequestNotFoundException(final String message) {
        this(message, null);
    }

    public PaymentRequestNotFoundException() {
        this(DEFAULT_MESSAGE);
    }
}
