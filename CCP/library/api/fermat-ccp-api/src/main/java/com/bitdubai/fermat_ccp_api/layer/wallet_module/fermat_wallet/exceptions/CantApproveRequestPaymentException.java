package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions;

/**
 * Created by natalia on 17/11/15.
 */
public class CantApproveRequestPaymentException extends FermatWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T APPROVE REQUEST PAYMENT EXCEPTION";

    public CantApproveRequestPaymentException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantApproveRequestPaymentException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantApproveRequestPaymentException(final String message) {
        this(message, null);
    }

    public CantApproveRequestPaymentException() {
        this(DEFAULT_MESSAGE);
    }
}
