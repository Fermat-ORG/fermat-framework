package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions;

/**
 * Created by natalia on 08/10/15.
 */
public class CantListSentPaymentRequestException extends FermatWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T LIST SENT PAYMENT REQUEST";

    public CantListSentPaymentRequestException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListSentPaymentRequestException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantListSentPaymentRequestException(final String message) {
        this(message, null);
    }

    public CantListSentPaymentRequestException() {
        this(DEFAULT_MESSAGE);
    }
}
