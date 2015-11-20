package com.bitdubai.fermat_cbp_api.layer.business_transaction.cash_money_stock_replenishment.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public class CantUpdateStatusCashMoneyStockReplenishmentException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Falled To Update the Status the Business Transaction Cash Money Stock Replenishment.";
    public CantUpdateStatusCashMoneyStockReplenishmentException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
