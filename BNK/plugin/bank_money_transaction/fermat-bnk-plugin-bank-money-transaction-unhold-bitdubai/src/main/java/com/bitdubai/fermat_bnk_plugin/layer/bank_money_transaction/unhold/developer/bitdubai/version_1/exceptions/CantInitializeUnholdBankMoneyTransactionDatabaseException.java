package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantInitializeUnholdBankMoneyTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Guillermo Gutierrez - (guillermo20@gmail.com) on 18/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeUnholdBankMoneyTransactionDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE UNHOLD BANK MONEY TRANSACTION DATABASE EXCEPTION";

    public CantInitializeUnholdBankMoneyTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeUnholdBankMoneyTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeUnholdBankMoneyTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeUnholdBankMoneyTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}