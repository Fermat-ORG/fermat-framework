package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.deposit.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.deposit.developer.bitdubai.version_1.database.DepositCashMoneyTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 11/27/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DepositCashMoneyTransactionDatabaseConstants {

    /**
     * Deposit database table definition.
     */
    static final String DEPOSIT_TABLE_NAME = "deposit";

    static final String DEPOSIT_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    static final String DEPOSIT_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    static final String DEPOSIT_ACTOR_PUBLIC_KEY_COLUMN_NAME = "actor_public_key";
    static final String DEPOSIT_PLUGIN_PUBLIC_KEY_COLUMN_NAME = "plugin_public_key";
    static final String DEPOSIT_AMOUNT_COLUMN_NAME = "amount";
    static final String DEPOSIT_CURRENCY_COLUMN_NAME = "currency";
    static final String DEPOSIT_MEMO_COLUMN_NAME = "memo";
    static final String DEPOSIT_TIMESTAMP_COLUMN_NAME = "timestamp";

    static final String DEPOSIT_FIRST_KEY_COLUMN = "transaction_id";

}