package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.cash_money_stock_replenishment.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */

public class CantCreateCashMoneyStockReplenishmentException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Create Business Transaction Cash Money Stock Replenishment.";
    public CantCreateCashMoneyStockReplenishmentException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
