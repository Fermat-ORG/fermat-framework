package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 04.10.15.
 */
public class CantUpdateStatusBankMoneyStockReplenishmentBusinessTransactionException extends FermatException {
    public CantUpdateStatusBankMoneyStockReplenishmentBusinessTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}