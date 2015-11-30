package com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.exceptions;

import com.bitdubai.fermat_api.layer.CBPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/11/15.
 */
public class CantOpenContractException extends CBPException {
    private static final String DEFAULT_MESSAGE = "Cannot open contract";

    public CantOpenContractException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantOpenContractException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantOpenContractException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }
}
