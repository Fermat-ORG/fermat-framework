package com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.exceptions;

import com.bitdubai.fermat_api.layer.CBPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 02/12/15.
 */
public class CantCloseContractException extends CBPException {
    private static final String DEFAULT_MESSAGE = "Cannot close contract";

    public CantCloseContractException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCloseContractException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantCloseContractException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }
}
