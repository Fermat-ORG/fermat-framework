package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.deposit.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.deposit.developer.bitdubai.version_1.exceptions.CantInitializeDepositCashMoneyTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 11/27/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeDepositCashMoneyTransactionDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE DEPOSIT CASH MONEY TRANSACTION DATABASE EXCEPTION";

    public CantInitializeDepositCashMoneyTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeDepositCashMoneyTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeDepositCashMoneyTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeDepositCashMoneyTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}