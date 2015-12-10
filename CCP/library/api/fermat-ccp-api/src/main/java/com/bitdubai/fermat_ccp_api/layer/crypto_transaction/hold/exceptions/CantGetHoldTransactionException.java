package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Franklin Marcano on 21/11/2015.
 */
public class CantGetHoldTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Failed To get the Cash Hold Transaction.";

    public CantGetHoldTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}