package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database.CashMoneyWalletDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 13/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CashMoneyWalletDatabaseConstants {

    /**
     *
     */
    public static final String DATABASE_NAME= "CashMoneyConstructor";

    /**
     * Cash Money database table definition.
     */
    static final String CASH_MONEY_TABLE_NAME = "cash_money";

    static final String CASH_MONEY_CASH_TRANSACTION_ID_COLUMN_NAME = "cash_transaction_id";
    static final String CASH_MONEY_WALLET_KEY_BROKER_COLUMN_NAME = "wallet_key_broker";
    static final String CASH_MONEY_PUBLIC_KEY_CUSTOMER_COLUMN_NAME = "public_key_customer";
    static final String CASH_MONEY_PUBLIC_KEY_BROKER_COLUMN_NAME = "public_key_broker";
    static final String CASH_MONEY_BALANCE_TYPE_COLUMN_NAME = "balance_type";
    static final String CASH_MONEY_TRANSACTION_TYPE_COLUMN_NAME = "transaction_type";
    static final String CASH_MONEY_AMOUNT_COLUMN_NAME = "amount";
    static final String CASH_MONEY_CASH_CURRENCY_TYPE_COLUMN_NAME = "cash_currency_type";
    static final String CASH_MONEY_CASH_REFERENCE_COLUMN_NAME = "cash_reference";
    static final String CASH_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME = "running_book_balance";
    static final String CASH_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME = "running_available_balance";
    static final String CASH_MONEY_TIMESTAMP_COLUMN_NAME = "timestamp";
    static final String CASH_MONEY_MEMO_COLUMN_NAME = "memo";
    static final String CASH_MONEY_STATUS_COLUMN_NAME = "status";

    static final String CASH_MONEY_FIRST_KEY_COLUMN = "cash_transaction_id";

    /**
<<<<<<< Updated upstream
     * Cash Money Total Balances database table definition.
     */
    static final String CASH_MONEY_TOTAL_BALANCES_TABLE_NAME = "cash_money_total_balances";

    static final String CASH_MONEY_TOTAL_BALANCES_WALLET_KEY_BROKER_COLUMN_NAME = "wallet_key_broker";
    static final String CASH_MONEY_TOTAL_BALANCES_PUBLIC_KEY_BROKER_COLUMN_NAME = "public_key_broker";
    static final String CASH_MONEY_TOTAL_BALANCES_CASH_CURRENCY_TYPE_COLUMN_NAME = "cash_currency_type";
    static final String CASH_MONEY_TOTAL_BALANCES_NAME_COLUMN_NAME = "name";
    static final String CASH_MONEY_TOTAL_BALANCES_DESCRIPTION_COLUMN_NAME = "description";
    static final String CASH_MONEY_TOTAL_BALANCES_AVAILABLE_BALANCE_COLUMN_NAME = "available_balance";
    static final String CASH_MONEY_TOTAL_BALANCES_BOOK_BALANCE_COLUMN_NAME = "book_balance";

    static final String CASH_MONEY_TOTAL_BALANCES_FIRST_KEY_COLUMN = "wallet_key_broker";

    /**
     *CashMoneyWalletBalance Table
     *
     */
    static final String CASH_MONEY_BALANCE_RECORD_TABLE_NAME = "cash_money_balance_record";

    static final String CASH_MONEY_BALANCE_RECORD_CASH_TRANSACTION_ID_COLUMN_NAME = "cash_transaction_id";
    static final String CASH_MONEY_BALANCE_RECORD_PUBLIC_KEY_ACTOR_FROM =  "Public_Key_Actor_From";
    static final String CASH_MONEY_BALANCE_RECORD_PUBLIC_KEY_ACTOR_TO =  "Public_Key_actor_to";
    static final String CASH_MONEY_BALANCE_RECORD_STATUS =  "Status";
    static final String CASH_MONEY_BALANCE_RECORD_BALANCE_TYPE = "balance_type";
    static final String CASH_MONEY_BALANCE_RECORD_TRANSACTION_TYPE = "transaction_type";
    static final String CASH_MONEY_BALANCE_RECORD_AMAUNT = "amount";
    static final String CASH_MONEY_BALANCE_RECORD_CASH_CURRENCY_TYPE= "cash_currency_type";
    static final String CASH_MONEY_BALANCE_RECORD_CASH_REFERENCE= "cash_reference";
    static final String CASH_MONEY_BALANCE_RECORD_TIME_STAMP= "time_stamp";
    static final String CASH_MONEY_BALANCE_RECORD_MEMO= "memo";

    static final String CASH_MONEY_BALANCE_RECORD__FIRST_KEY_COLUMN = "cash_money_balance_record_key";

}