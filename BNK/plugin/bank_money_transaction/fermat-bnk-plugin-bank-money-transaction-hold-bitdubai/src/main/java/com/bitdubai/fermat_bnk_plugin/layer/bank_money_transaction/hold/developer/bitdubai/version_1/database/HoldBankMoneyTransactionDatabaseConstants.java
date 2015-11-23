package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.database.HoldBankMoneyTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Guillermo Gutierrez - (guillermo20@gmail.com) on 18/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class HoldBankMoneyTransactionDatabaseConstants {

    /**
     * Hold database table definition.
     */
    static final String HOLD_TABLE_NAME = "hold";

    static final String HOLD_ID_COLUMN_NAME = "id";
    static final String HOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME = "plugin_public_key";
    static final String HOLD_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    static final String HOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME = "actor_public_key";
    static final String HOLD_ACCOUNT_NUMBER_COLUMN_NAME = "account_number";
    static final String HOLD_AMOUNT_COLUMN_NAME = "amount";
    static final String HOLD_TIMESTAMP_COLUMN_NAME = "timestamp";
    static final String HOLD_MEMO_COLUMN_NAME = "memo";
    static final String HOLD_STATUS_COLUMN_NAME = "status";

    static final String HOLD_FIRST_KEY_COLUMN = "id";

}