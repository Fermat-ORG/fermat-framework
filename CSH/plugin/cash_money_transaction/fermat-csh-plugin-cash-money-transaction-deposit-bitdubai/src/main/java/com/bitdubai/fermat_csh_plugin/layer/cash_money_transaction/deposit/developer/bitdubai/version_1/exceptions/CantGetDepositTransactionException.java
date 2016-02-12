package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.deposit.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/27/2015.
 */
public class CantGetDepositTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Failed To get the Cash Deposit Transaction.";

    public CantGetDepositTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}