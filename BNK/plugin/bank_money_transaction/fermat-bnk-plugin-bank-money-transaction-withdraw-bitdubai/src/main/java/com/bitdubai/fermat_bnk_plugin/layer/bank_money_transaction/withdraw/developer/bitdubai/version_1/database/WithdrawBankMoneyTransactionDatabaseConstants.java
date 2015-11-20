package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.database.WithdrawBankMoneyTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Guillermo Gutierrez - (guillermo20@gmail.com) on 18/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WithdrawBankMoneyTransactionDatabaseConstants {

    /**
     * Withdraw database table definition.
     */
    static final String WITHDRAW_TABLE_NAME = "withdraw";

    static final String WITHDRAW_ID_COLUMN_NAME = "id";
    static final String WITHDRAW_PLUGIN_PUBLIC_KEY_COLUMN_NAME = "plugin_public_key";
    static final String WITHDRAW_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    static final String WITHDRAW_ACTOR_PUBLIC_KEY_COLUMN_NAME = "actor_public_key";
    static final String WITHDRAW_ACCOUNT_NUMBER_COLUMN_NAME = "account_number";
    static final String WITHDRAW_AMOUNT_COLUMN_NAME = "amount";
    static final String WITHDRAW_TIMESTAMP_COLUMN_NAME = "timestamp";
    static final String WITHDRAW_MEMO_COLUMN_NAME = "memo";
    static final String WITHDRAW_STATUS_COLUMN_NAME = "status";

    static final String WITHDRAW_FIRST_KEY_COLUMN = "id";

}