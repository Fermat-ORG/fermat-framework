package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.crypto_money_stock_replenishment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.crypto_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoMoneyStockReplenishmentBusinessTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 29/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCryptoMoneyStockReplenishmentBusinessTransactionDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CRYPTO MONEY STOCK REPLENISHMENT BUSINESS TRANSACTION DATABASE EXCEPTION";

    public CantInitializeCryptoMoneyStockReplenishmentBusinessTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCryptoMoneyStockReplenishmentBusinessTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCryptoMoneyStockReplenishmentBusinessTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCryptoMoneyStockReplenishmentBusinessTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}