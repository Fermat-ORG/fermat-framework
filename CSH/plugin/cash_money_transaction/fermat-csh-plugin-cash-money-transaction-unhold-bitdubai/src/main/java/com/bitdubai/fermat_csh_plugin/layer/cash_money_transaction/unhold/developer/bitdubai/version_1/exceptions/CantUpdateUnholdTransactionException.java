package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.exceptions.CantInitializeHoldCashMoneyTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 18/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantUpdateUnholdTransactionException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE UNHOLD CASH MONEY TRANSACTION STATUS EXCEPTION";

    public CantUpdateUnholdTransactionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateUnholdTransactionException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantUpdateUnholdTransactionException(final String message) {
        this(message, null);
    }

    public CantUpdateUnholdTransactionException() {
        this(DEFAULT_MESSAGE);
    }
}