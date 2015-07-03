package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by eze on 09/06/15.
 */
public class CantConfirmTransactionException extends FermatException {
    public CantConfirmTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
