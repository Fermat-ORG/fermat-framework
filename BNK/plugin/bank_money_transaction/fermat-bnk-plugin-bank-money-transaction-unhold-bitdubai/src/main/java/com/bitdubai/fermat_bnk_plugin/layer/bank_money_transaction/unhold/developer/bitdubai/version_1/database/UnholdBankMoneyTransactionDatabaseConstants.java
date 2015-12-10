package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.database.UnholdBankMoneyTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Guillermo Gutierrez - (guillermo20@gmail.com) on 18/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class UnholdBankMoneyTransactionDatabaseConstants {

    /**
     * Unhold database table definition.
     */
    static final String UNHOLD_TABLE_NAME = "unhold";

    static final String UNHOLD_ID_COLUMN_NAME = "id";
    static final String UNHOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME = "plugin_public_key";
    static final String UNHOLD_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    static final String UNHOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME = "actor_public_key";
    static final String UNHOLD_ACCOUNT_NUMBER_COLUMN_NAME = "account_number";
    static final String UNHOLD_AMOUNT_COLUMN_NAME = "amount";
    static final String UNHOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME = "timestamp_acknowledge";
    static final String UNHOLD_MEMO_COLUMN_NAME = "memo";
    static final String UNHOLD_STATUS_COLUMN_NAME = "status";
    static final String UNHOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME="timestamp_confirm_rejected";
    static final String UNHOLD_CURRENCY_COLUMN_NAME = "currency";

    static final String UNHOLD_FIRST_KEY_COLUMN = "id";

}