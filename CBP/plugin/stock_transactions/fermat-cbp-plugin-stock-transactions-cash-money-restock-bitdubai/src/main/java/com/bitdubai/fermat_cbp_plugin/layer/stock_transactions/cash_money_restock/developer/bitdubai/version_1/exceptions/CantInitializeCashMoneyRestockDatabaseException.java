package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 16/11/15.
 */
public class CantInitializeCashMoneyRestockDatabaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE BANK MONEY RESTOCK  DATABASE EXCEPTION";

    public CantInitializeCashMoneyRestockDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCashMoneyRestockDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCashMoneyRestockDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCashMoneyRestockDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
