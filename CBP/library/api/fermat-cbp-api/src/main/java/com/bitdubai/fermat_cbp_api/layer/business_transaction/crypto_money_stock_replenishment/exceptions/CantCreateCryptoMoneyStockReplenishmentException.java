package com.bitdubai.fermat_cbp_api.layer.business_transaction.crypto_money_stock_replenishment.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */

public class CantCreateCryptoMoneyStockReplenishmentException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Create Business Transaction Crypto Money Stock Replenishment.";
    public CantCreateCryptoMoneyStockReplenishmentException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
