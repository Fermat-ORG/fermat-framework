package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * Created by natalia on 08/10/15.
 */
public class CantListLossProtectedPaymentRequestDateOrderException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T LIST PAYMENT REQUEST DATE ORDER EXCEPTION";

    public CantListLossProtectedPaymentRequestDateOrderException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListLossProtectedPaymentRequestDateOrderException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantListLossProtectedPaymentRequestDateOrderException(final String message) {
        this(message, null);
    }

    public CantListLossProtectedPaymentRequestDateOrderException() {
        this(DEFAULT_MESSAGE);
    }
}
