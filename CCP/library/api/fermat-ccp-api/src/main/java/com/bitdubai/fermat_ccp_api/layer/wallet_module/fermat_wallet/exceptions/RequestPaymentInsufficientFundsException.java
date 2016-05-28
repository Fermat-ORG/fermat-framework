package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions;

/**
 * Created by natalia on 17/11/15.
 */
public class RequestPaymentInsufficientFundsException extends FermatWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T APPROVE REQUEST PAYMENT Insufficient FUNDS EXCEPTION";

    public RequestPaymentInsufficientFundsException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public RequestPaymentInsufficientFundsException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public RequestPaymentInsufficientFundsException(final String message) {
        this(message, null);
    }

    public RequestPaymentInsufficientFundsException() {
        this(DEFAULT_MESSAGE);
    }
}
