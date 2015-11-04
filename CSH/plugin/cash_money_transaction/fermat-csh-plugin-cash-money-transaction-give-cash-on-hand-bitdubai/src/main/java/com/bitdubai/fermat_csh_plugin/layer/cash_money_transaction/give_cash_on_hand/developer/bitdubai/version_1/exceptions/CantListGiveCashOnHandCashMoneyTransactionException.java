package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.give_cash_on_hand.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 09.10.15.
 */
public class CantListGiveCashOnHandCashMoneyTransactionException extends FermatException {
    public CantListGiveCashOnHandCashMoneyTransactionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}