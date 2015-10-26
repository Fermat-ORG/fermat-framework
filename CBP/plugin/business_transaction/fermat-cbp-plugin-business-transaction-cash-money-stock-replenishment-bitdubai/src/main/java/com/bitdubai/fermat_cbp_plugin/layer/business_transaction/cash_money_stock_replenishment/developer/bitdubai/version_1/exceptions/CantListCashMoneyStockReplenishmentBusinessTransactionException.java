package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.cash_money_stock_replenishment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 09.10.15.
 */
public class CantListCashMoneyStockReplenishmentBusinessTransactionException extends FermatException {
    public CantListCashMoneyStockReplenishmentBusinessTransactionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}