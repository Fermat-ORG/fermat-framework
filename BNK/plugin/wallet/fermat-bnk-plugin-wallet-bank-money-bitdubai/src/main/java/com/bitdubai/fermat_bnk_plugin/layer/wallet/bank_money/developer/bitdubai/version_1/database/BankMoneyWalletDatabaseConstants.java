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
     * Bank Money database table definition.
     */
    static final String BANK_MONEY_TABLE_NAME = "bank_money";

    static final String BANK_MONEY_BANK_TRANSACTION_ID_COLUMN_NAME = "bank_transaction_id";
    static final String BANK_MONEY_PUBLIC_KEY_CUSTOMER_COLUMN_NAME = "public_key_customer";
    static final String BANK_MONEY_PUBLIC_KEY_BROKER_COLUMN_NAME = "public_key_broker";
    static final String BANK_MONEY_BALANCE_TYPE_COLUMN_NAME = "balance_type";
    static final String BANK_MONEY_TRANSACTION_TYPE_COLUMN_NAME = "transaction_type";
    static final String BANK_MONEY_AMOUNT_COLUMN_NAME = "amount";
    static final String BANK_MONEY_BANK_CURRENCY_TYPE_COLUMN_NAME = "bank_currency_type";
    static final String BANK_MONEY_BANK_OPERATION_TYPE_COLUMN_NAME = "bank_operation_type";
    static final String BANK_MONEY_BANK_DOCUMENT_REFERENCE_COLUMN_NAME = "bank_document_reference";
    static final String BANK_MONEY_BANK_NAME_COLUMN_NAME = "bank_name";
    static final String BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME = "bank_account_number";
    static final String BANK_MONEY_BANK_ACCOUNT_TYPE_COLUMN_NAME = "bank_account_type";
    static final String BANK_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME = "running_book_balance";
    static final String BANK_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME = "running_available_balance";
    static final String BANK_MONEY_TIMESTAMP_COLUMN_NAME = "timestamp";
    static final String BANK_MONEY_MEMO_COLUMN_NAME = "memo";
    static final String BANK_MONEY_STATUS_COLUMN_NAME = "status";

    static final String BANK_MONEY_FIRST_KEY_COLUMN = "bank_transaction_id";

    /**
     * Bank Money Total Balances database table definition.
     */
    static final String BANK_MONEY_TOTAL_BALANCES_TABLE_NAME = "bank_money_total_balances";

    static final String BANK_MONEY_TOTAL_BALANCES_WALLET_KEY_BROKER_COLUMN_NAME = "wallet_key_broker";
    static final String BANK_MONEY_TOTAL_BALANCES_PUBLIC_KEY_BROKER_COLUMN_NAME = "public_key_broker";
    static final String BANK_MONEY_TOTAL_BALANCES_BANK_CURRENCY_TYPE_COLUMN_NAME = "bank_currency_type";
    static final String BANK_MONEY_TOTAL_BALANCES_NAME_COLUMN_NAME = "name";
    static final String BANK_MONEY_TOTAL_BALANCES_DESCRIPTION_COLUMN_NAME = "description";
    static final String BANK_MONEY_TOTAL_BALANCES_AVAILABLE_BALANCE_COLUMN_NAME = "available_balance";
    static final String BANK_MONEY_TOTAL_BALANCES_BOOK_BALANCE_COLUMN_NAME = "book_balance";

    static final String BANK_MONEY_TOTAL_BALANCES_FIRST_KEY_COLUMN = "wallet_key_broker";

}