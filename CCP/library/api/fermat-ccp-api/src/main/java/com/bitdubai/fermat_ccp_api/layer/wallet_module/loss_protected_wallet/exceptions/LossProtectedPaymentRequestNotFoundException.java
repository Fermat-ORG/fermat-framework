package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * Created by natalia on 17/11/15.
 */
public class LossProtectedPaymentRequestNotFoundException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T ACCEPT REQUEST PAYMENT, Not Found EXCEPTION";

    public LossProtectedPaymentRequestNotFoundException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public LossProtectedPaymentRequestNotFoundException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public LossProtectedPaymentRequestNotFoundException(final String message) {
        this(message, null);
    }

    public LossProtectedPaymentRequestNotFoundException() {
        this(DEFAULT_MESSAGE);
    }
}
