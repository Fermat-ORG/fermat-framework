package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database.BankMoneyWalletDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 13/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BankMoneyWalletDatabaseConstants {

    /**
     * Database name
     */
    public static final String DATABASE_NAME = "BankMoneyWallet";

    /**
     * Bank Money database table definition.
     */
    public static final String BANK_MONEY_TABLE_NAME = "bank_money";

    public static final String BANK_MONEY_BANK_TRANSACTION_ID_COLUMN_NAME = "bank_transaction_id";
    public static final String BANK_MONEY_PUBLIC_KEY_CUSTOMER_COLUMN_NAME = "public_key_customer";
    public static final String BANK_MONEY_PUBLIC_KEY_BROKER_COLUMN_NAME = "public_key_broker";
    public static final String BANK_MONEY_BALANCE_TYPE_COLUMN_NAME = "balance_type";
    public static final String BANK_MONEY_TRANSACTION_TYPE_COLUMN_NAME = "transaction_type";
    public static final String BANK_MONEY_AMOUNT_COLUMN_NAME = "amount";
    public static final String BANK_MONEY_BANK_CURRENCY_TYPE_COLUMN_NAME = "bank_currency_type";
    public static final String BANK_MONEY_BANK_OPERATION_TYPE_COLUMN_NAME = "bank_operation_type";
    public static final String BANK_MONEY_BANK_DOCUMENT_REFERENCE_COLUMN_NAME = "bank_document_reference";
    public static final String BANK_MONEY_BANK_NAME_COLUMN_NAME = "bank_name";
    public static final String BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME = "bank_account_number";
    public static final String BANK_MONEY_BANK_ACCOUNT_TYPE_COLUMN_NAME = "bank_account_type";
    public static final String BANK_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME = "running_book_balance";
    public static final String BANK_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME = "running_available_balance";
    public static final String BANK_MONEY_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String BANK_MONEY_MEMO_COLUMN_NAME = "memo";
    public static final String BANK_MONEY_STATUS_COLUMN_NAME = "status";

    public static final String BANK_MONEY_FIRST_KEY_COLUMN = "bank_transaction_id";

    /**
     * Bank Money Total Balances database table definition.
     */
    public static final String BANK_MONEY_TOTAL_BALANCES_TABLE_NAME = "bank_money_total_balances";

    public static final String BANK_MONEY_TOTAL_BALANCES_WALLET_KEY_BROKER_COLUMN_NAME = "wallet_key_broker";
    public static final String BANK_MONEY_TOTAL_BALANCES_PUBLIC_KEY_BROKER_COLUMN_NAME = "public_key_broker";
    public static final String BANK_MONEY_TOTAL_BALANCES_BANK_CURRENCY_TYPE_COLUMN_NAME = "bank_currency_type";
    public static final String BANK_MONEY_TOTAL_BALANCES_NAME_COLUMN_NAME = "name";
    public static final String BANK_MONEY_TOTAL_BALANCES_DESCRIPTION_COLUMN_NAME = "description";
    public static final String BANK_MONEY_TOTAL_BALANCES_AVAILABLE_BALANCE_COLUMN_NAME = "available_balance";
    public static final String BANK_MONEY_TOTAL_BALANCES_BOOK_BALANCE_COLUMN_NAME = "book_balance";

    public static final String BANK_MONEY_TOTAL_BALANCES_FIRST_KEY_COLUMN = "wallet_key_broker";

}