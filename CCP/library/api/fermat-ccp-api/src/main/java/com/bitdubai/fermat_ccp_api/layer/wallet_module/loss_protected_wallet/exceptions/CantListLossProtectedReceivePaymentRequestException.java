package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * Created by natalia on 08/10/15.
 */
public class CantListLossProtectedReceivePaymentRequestException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T LIST RECEIVE PAYMENT REQUEST";

    public CantListLossProtectedReceivePaymentRequestException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListLossProtectedReceivePaymentRequestException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantListLossProtectedReceivePaymentRequestException(final String message) {
        this(message, null);
    }

    public CantListLossProtectedReceivePaymentRequestException() {
        this(DEFAULT_MESSAGE);
    }
}
