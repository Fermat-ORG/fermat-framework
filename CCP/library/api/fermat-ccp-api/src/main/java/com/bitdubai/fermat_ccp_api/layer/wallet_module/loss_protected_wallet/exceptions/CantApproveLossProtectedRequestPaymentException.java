package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedWalletException;

/**
 * Created by natalia on 17/11/15.
 */
public class CantApproveLossProtectedRequestPaymentException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T APPROVE REQUEST PAYMENT EXCEPTION";

    public CantApproveLossProtectedRequestPaymentException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantApproveLossProtectedRequestPaymentException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantApproveLossProtectedRequestPaymentException(final String message) {
        this(message, null);
    }

    public CantApproveLossProtectedRequestPaymentException() {
        this(DEFAULT_MESSAGE);
    }
}
