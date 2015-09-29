package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.cash_money_stock_replenishment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.cash_money_stock_replenishment.developer.bitdubai.version_1.exceptions.CantInitializeCashMoneyStockReplenishmentBusinessTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 29/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCashMoneyStockReplenishmentBusinessTransactionDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CASH MONEY STOCK REPLENISHMENT BUSINESS TRANSACTION DATABASE EXCEPTION";

    public CantInitializeCashMoneyStockReplenishmentBusinessTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCashMoneyStockReplenishmentBusinessTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCashMoneyStockReplenishmentBusinessTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCashMoneyStockReplenishmentBusinessTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}