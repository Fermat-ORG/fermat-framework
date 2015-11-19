package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by memo on 17/11/15.
 */
public class CantGetUnholdTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Failed to get unhold transactions. ";
    public CantGetUnholdTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
