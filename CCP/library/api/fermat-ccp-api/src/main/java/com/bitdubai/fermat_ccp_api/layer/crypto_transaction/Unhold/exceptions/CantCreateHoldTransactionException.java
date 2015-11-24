package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.Unhold.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Franklin Marcano on 21/11/2015.
 */
public class CantCreateHoldTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Failed To create a Cash Hold Transaction.";

    public CantCreateHoldTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}