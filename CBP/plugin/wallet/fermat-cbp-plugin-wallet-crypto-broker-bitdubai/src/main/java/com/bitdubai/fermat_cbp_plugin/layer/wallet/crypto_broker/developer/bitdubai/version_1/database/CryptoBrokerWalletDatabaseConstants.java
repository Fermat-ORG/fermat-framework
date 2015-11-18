package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 30/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoBrokerWalletDatabaseConstants {

    /**
     * Crypto Broker Wallet database table definition.
     */
    static final String CRYPTO_BROKER_WALLET_TABLE_NAME = "crypto_broker_wallet";

    static final String CRYPTO_BROKER_WALLET_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    static final String CRYPTO_BROKER_WALLET_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    /**
     * Crypto Broker Stock Balance database table definition.
     */
    static final String CRYPTO_BROKER_STOCK_BALANCE_TABLE_NAME = "crypto_broker_stock_balance";

    static final String CRYPTO_BROKER_STOCK_BALANCE_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    static final String CRYPTO_BROKER_STOCK_BALANCE_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    static final String CRYPTO_BROKER_STOCK_BALANCE_CURRENCY_TYPE_COLUMN_NAME = "currency_type";
    static final String CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME = "merchandise";
    static final String CRYPTO_BROKER_STOCK_BALANCE_DESCRIPTION_COLUMN_NAME = "description";
    static final String CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME = "available_balance";
    static final String CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME = "book_balance";

    static final String CRYPTO_BROKER_STOCK_BALANCE_FIRST_KEY_COLUMN = "wallet_key_broker";

    /**
     * Crypto Broker Stock Transactions database table definition.
     */
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME = "crypto_broker_stock_transactions";

    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME = "balance_type";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME = "transaction_type";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME = "amount";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_CURRENCY_TYPE_COLUMN_NAME = "currency_type";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME = "merchandise";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_BOOK_BALANCE_COLUMN_NAME = "running_book_balance";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME = "running_available_balance";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME = "timestamp";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME = "memo";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_STATUS_COLUMN_NAME = "status";

    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_FIRST_KEY_COLUMN = "transaction_id";

}