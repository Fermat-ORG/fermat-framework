package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database;

/**
 * The Class <code>CryptoBrokerWalletDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
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
    static final String CRYPTO_BROKER_WALLET_SPREAD_TABLE_NAME = "spread";

    static final String CRYPTO_BROKER_WALLET_SPREAD_ID_COLUMN_NAME = "spread_id";
    static final String CRYPTO_BROKER_WALLET_SPREAD_VALUE_COLUMN_NAME = "spread";
    static final String CRYPTO_BROKER_WALLET_SPREAD_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";

    /**
     * Crypto Wallet Associated Setting table definition.
     */
    static final String CRYPTO_BROKER_WALLET_ASSOCIATED_TABLE_NAME = "associated_wallet";

    static final String CRYPTO_BROKER_WALLET_ASSOCIATED_ID_COLUMN_NAME = "wallet_associated_id";
    static final String CRYPTO_BROKER_WALLET_ASSOCIATED_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    static final String CRYPTO_BROKER_WALLET_ASSOCIATED_PLATFORM_COLUMN_NAME = "platform";
    static final String CRYPTO_BROKER_WALLET_ASSOCIATED_MERCHANDISE_COLUMN_NAME = "merchandise";
    static final String CRYPTO_BROKER_WALLET_ASSOCIATED_CURRENCY_TYPE_COLUMN_NAME = "currency_type";
    static final String CRYPTO_BROKER_WALLET_ASSOCIATED_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    static final String CRYPTO_BROKER_WALLET_ASSOCIATED_BANK_ACCOUNT_COLUMN_NAME = "bank_account";

    /**
     * Crypto Wallet Providers Setting table definition.
     */
    static final String CRYPTO_BROKER_WALLET_PROVIDER_TABLE_NAME = "provider";

    static final String CRYPTO_BROKER_WALLET_PROVIDER_ID_COLUMN_NAME = "provider_id";
    static final String CRYPTO_BROKER_WALLET_PROVIDER_PLUGIN_COLUMN_NAME = "plugin";
    static final String CRYPTO_BROKER_WALLET_PROVIDER_DESCRIPTION_COLUMN_NAME = "description";
    static final String CRYPTO_BROKER_WALLET_PROVIDER_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";

    /**
     * Crypto Broker Stock Balance database table definition.
     */
    static final String CRYPTO_BROKER_STOCK_BALANCE_TABLE_NAME = "stock_balance";

    static final String CRYPTO_BROKER_STOCK_BALANCE_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    static final String CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME = "merchandise";
    static final String CRYPTO_BROKER_STOCK_BALANCE_CURRENCY_TYPE_COLUMN_NAME = "currency_type";
    static final String CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME = "available_balance";
    static final String CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME = "book_balance";

    static final String CRYPTO_BROKER_STOCK_BALANCE_FIRST_KEY_COLUMN = "merchandise";

    /**
     * Crypto Broker Stock Transactions database table definition.
     */
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME = "stock_transaction";

    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME = "balance_type";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME = "transaction_type";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME = "amount";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME = "merchandise";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_CURRENCY_TYPE_COLUMN_NAME = "currency_type";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_BOOK_BALANCE_COLUMN_NAME = "running_book_balance";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME = "running_available_balance";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME = "timestamp";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME = "memo";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_COLUMN_NAME = "origin_transaction";
    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_PRICE_REFERENCE_COLUMN_NAME = "price_reference";

    static final String CRYPTO_BROKER_STOCK_TRANSACTIONS_FIRST_KEY_COLUMN = "transaction_id";

}