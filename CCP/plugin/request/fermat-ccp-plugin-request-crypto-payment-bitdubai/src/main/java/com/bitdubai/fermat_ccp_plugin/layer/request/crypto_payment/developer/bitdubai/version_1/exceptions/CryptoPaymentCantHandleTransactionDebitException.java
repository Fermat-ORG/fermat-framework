package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 26/11/15.
 */
public class CryptoPaymentCantHandleTransactionDebitException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE INCOMING INTRA USER TRANSACTION DEBIT EVENT EXCEPTION";

    public CryptoPaymentCantHandleTransactionDebitException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CryptoPaymentCantHandleTransactionDebitException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
