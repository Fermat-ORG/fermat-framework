package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database;

/**
 * The Class <code>CryptoBrokerWalletDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 30/10/15.
 * Modified by Franklin 01.12.2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoBrokerWalletDatabaseConstants {
    /**
     * Crypto Wallet Spread Setting table definition.
     */
    public static final String CRYPTO_BROKER_WALLET_SPREAD_TABLE_NAME = "spread";

    public static final String CRYPTO_BROKER_WALLET_SPREAD_ID_COLUMN_NAME = "spread_id";
    public static final String CRYPTO_BROKER_WALLET_SPREAD_VALUE_COLUMN_NAME = "spread";
    public static final String CRYPTO_BROKER_WALLET_SPREAD_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    public static final String CRYPTO_BROKER_WALLET_SPREAD_RESTOCK_AUTOMATIC = "restock_automatic";

    /**
     * Crypto Wallet Associated Setting table definition.
     */
    public static final String CRYPTO_BROKER_WALLET_ASSOCIATED_TABLE_NAME = "associated_wallet";

    public static final String CRYPTO_BROKER_WALLET_ASSOCIATED_ID_COLUMN_NAME = "wallet_associated_id";
    public static final String CRYPTO_BROKER_WALLET_ASSOCIATED_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    public static final String CRYPTO_BROKER_WALLET_ASSOCIATED_PLATFORM_COLUMN_NAME = "platform";
    public static final String CRYPTO_BROKER_WALLET_ASSOCIATED_MERCHANDISE_COLUMN_NAME = "merchandise";
    public static final String CRYPTO_BROKER_WALLET_ASSOCIATED_MONEY_TYPE_COLUMN_NAME = "money_type";
    public static final String CRYPTO_BROKER_WALLET_ASSOCIATED_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    public static final String CRYPTO_BROKER_WALLET_ASSOCIATED_BANK_ACCOUNT_COLUMN_NAME = "bank_account";

    /**
     * Crypto Wallet Providers Setting table definition.
     */
    public static final String CRYPTO_BROKER_WALLET_PROVIDER_TABLE_NAME = "provider";

    public static final String CRYPTO_BROKER_WALLET_PROVIDER_ID_COLUMN_NAME = "provider_id";
    public static final String CRYPTO_BROKER_WALLET_PROVIDER_PLUGIN_COLUMN_NAME = "plugin";
    public static final String CRYPTO_BROKER_WALLET_PROVIDER_DESCRIPTION_COLUMN_NAME = "description";
    public static final String CRYPTO_BROKER_WALLET_PROVIDER_CURRENCY_FROM_COLUMN_NAME = "currencyFrom";
    public static final String CRYPTO_BROKER_WALLET_PROVIDER_CURRENCY_TO_COLUMN_NAME = "currencyTo";
    public static final String CRYPTO_BROKER_WALLET_PROVIDER_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";

    /**
     * Crypto Broker Stock Balance database table definition.
     */
    public static final String CRYPTO_BROKER_STOCK_BALANCE_TABLE_NAME = "stock_balance";

    public static final String CRYPTO_BROKER_STOCK_BALANCE_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    public static final String CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME = "merchandise";
    public static final String CRYPTO_BROKER_STOCK_BALANCE_MONEY_TYPE_COLUMN_NAME = "money_type";
    public static final String CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME = "available_balance";
    public static final String CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME = "book_balance";

    public static final String CRYPTO_BROKER_STOCK_BALANCE_FIRST_KEY_COLUMN = "merchandise";

    /**
     * Crypto Broker Stock Transactions database table definition.
     */
    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME = "stock_transaction";

    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME = "balance_type";
    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME = "transaction_type";
    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME = "amount";
    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME = "merchandise";
    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_MONEY_TYPE_COLUMN_NAME = "money_type";
    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_BOOK_BALANCE_COLUMN_NAME = "running_book_balance";
    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME = "running_available_balance";
    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME = "memo";
    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_COLUMN_NAME = "origin_transaction";
    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_PRICE_REFERENCE_COLUMN_NAME = "price_reference";
    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_ID_COLUMN_NAME = "origin_transaction_id";
    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_SEEN_COLUMN_NAME = "seen";

    public static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_FIRST_KEY_COLUMN = "transaction_id";

}