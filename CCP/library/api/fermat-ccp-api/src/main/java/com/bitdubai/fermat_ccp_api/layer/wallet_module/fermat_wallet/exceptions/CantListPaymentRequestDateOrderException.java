package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions;

/**
 * Created by natalia on 08/10/15.
 */
public class CantListPaymentRequestDateOrderException extends FermatWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T LIST PAYMENT REQUEST DATE ORDER EXCEPTION";

    public CantListPaymentRequestDateOrderException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListPaymentRequestDateOrderException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantListPaymentRequestDateOrderException(final String message) {
        this(message, null);
    }

    public CantListPaymentRequestDateOrderException() {
        this(DEFAULT_MESSAGE);
    }
}
