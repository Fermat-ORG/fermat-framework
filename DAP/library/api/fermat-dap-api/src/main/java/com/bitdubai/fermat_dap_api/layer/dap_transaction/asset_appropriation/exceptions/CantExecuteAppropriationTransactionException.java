package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by frank on 18/10/15.
 */
public class CantExecuteAppropriationTransactionException extends DAPException {
    static final String DEFAULT_MESSAGE = "There was an error in Appropiation Assets Transaction.";

    public CantExecuteAppropriationTransactionException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
