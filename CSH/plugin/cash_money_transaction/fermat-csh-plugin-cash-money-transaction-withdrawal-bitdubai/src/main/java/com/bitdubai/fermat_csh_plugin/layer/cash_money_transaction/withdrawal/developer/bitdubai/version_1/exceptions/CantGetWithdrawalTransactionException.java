package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/27/2015.
 */
public class CantGetWithdrawalTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Failed To get the Cash Withdrawal Transaction.";

    public CantGetWithdrawalTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}