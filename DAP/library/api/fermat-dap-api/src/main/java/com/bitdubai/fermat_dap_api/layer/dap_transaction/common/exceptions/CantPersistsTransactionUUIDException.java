package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 19/10/15.
 */
public class CantPersistsTransactionUUIDException extends DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error persisting a Digital Asset genesis transaction in Asset Issuing database.";

    public CantPersistsTransactionUUIDException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
