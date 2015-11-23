package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 17/11/15.
 */
public class MissingCashMoneyDestockDataException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error trying to insert data in table. The referenced Bank Money Transaction transactionId does not exists.";

    public MissingCashMoneyDestockDataException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}