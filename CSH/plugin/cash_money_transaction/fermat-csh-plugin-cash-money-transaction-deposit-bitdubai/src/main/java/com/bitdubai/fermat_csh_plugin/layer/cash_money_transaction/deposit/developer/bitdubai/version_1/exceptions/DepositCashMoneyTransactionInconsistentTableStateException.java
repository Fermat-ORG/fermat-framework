package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.deposit.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/18/2015.
 */

public class DepositCashMoneyTransactionInconsistentTableStateException extends FermatException {
    public DepositCashMoneyTransactionInconsistentTableStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}