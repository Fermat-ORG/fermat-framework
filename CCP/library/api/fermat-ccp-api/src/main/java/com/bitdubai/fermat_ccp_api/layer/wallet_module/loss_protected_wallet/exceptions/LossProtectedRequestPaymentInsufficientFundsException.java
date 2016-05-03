package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;


/**
 * Created by natalia on 17/11/15.
 */
public class LossProtectedRequestPaymentInsufficientFundsException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T APPROVE REQUEST PAYMENT Insufficient FUNDS EXCEPTION";

    public LossProtectedRequestPaymentInsufficientFundsException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public LossProtectedRequestPaymentInsufficientFundsException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public LossProtectedRequestPaymentInsufficientFundsException(final String message) {
        this(message, null);
    }

    public LossProtectedRequestPaymentInsufficientFundsException() {
        this(DEFAULT_MESSAGE);
    }
}
