package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantInitializeUnholdCashMoneyTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 26/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeUnholdCashMoneyTransactionDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE UNHOLD CASH MONEY TRANSACTION DATABASE EXCEPTION";

    public CantInitializeUnholdCashMoneyTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeUnholdCashMoneyTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeUnholdCashMoneyTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeUnholdCashMoneyTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}