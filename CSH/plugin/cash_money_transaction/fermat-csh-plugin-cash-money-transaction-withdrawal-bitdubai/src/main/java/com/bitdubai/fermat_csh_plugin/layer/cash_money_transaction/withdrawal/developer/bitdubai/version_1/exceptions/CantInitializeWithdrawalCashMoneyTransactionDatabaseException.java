package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.exceptions.CantInitializeWithdrawalCashMoneyTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 11/27/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeWithdrawalCashMoneyTransactionDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE WITHDRAWAL CASH MONEY TRANSACTION DATABASE EXCEPTION";

    public CantInitializeWithdrawalCashMoneyTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeWithdrawalCashMoneyTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeWithdrawalCashMoneyTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeWithdrawalCashMoneyTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}