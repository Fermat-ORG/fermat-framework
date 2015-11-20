package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.give_cash_on_hand.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 04.10.15.
 */
public class GiveCashOnHandCashMoneyTransactionInconsistentTableStateException extends FermatException {
    public GiveCashOnHandCashMoneyTransactionInconsistentTableStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}