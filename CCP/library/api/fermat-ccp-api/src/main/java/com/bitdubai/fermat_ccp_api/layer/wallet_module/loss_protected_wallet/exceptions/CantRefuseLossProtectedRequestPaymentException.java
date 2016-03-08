package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * Created by natalia on 17/11/15.
 */
public class CantRefuseLossProtectedRequestPaymentException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T REFUSE REQUEST PAYMENT EXCEPTION";

    public CantRefuseLossProtectedRequestPaymentException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRefuseLossProtectedRequestPaymentException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantRefuseLossProtectedRequestPaymentException(final String message) {
        this(message, null);
    }

    public CantRefuseLossProtectedRequestPaymentException() {
        this(DEFAULT_MESSAGE);
    }
}
