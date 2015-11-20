package com.bitdubai.fermat_cbp_api.layer.business_transaction.crypto_money_stock_replenishment.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public class CantGetCryptoMoneyStockReplenishmentException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Get Business Transaction Crypto Money Stock Replenishment.";
    public CantGetCryptoMoneyStockReplenishmentException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
