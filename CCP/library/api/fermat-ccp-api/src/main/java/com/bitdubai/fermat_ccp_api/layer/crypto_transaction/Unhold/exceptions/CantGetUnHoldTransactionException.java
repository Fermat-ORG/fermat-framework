package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.Unhold.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Franklin Marcano on 21/11/2015.
 */
public class CantGetUnHoldTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Failed To get the Cash Hold Transaction.";

    public CantGetUnHoldTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}