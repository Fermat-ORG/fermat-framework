package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 18/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoBrokerWalletDatabaseConstants {

    /**
     * Crypto Broker database table definition.
     */
    static final String CRYPTO_BROKER_TABLE_NAME = "crypto_broker";

    static final String CRYPTO_BROKER_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    static final String CRYPTO_BROKER_PUBLIC_KEY_WALLET_COLUMN_NAME = "public_key_wallet";
    static final String CRYPTO_BROKER_PUBLIC_KEY_BROKER_COLUMN_NAME = "public_key_broker";
    static final String CRYPTO_BROKER_PUBLIC_KEY_CUSTOMER_COLUMN_NAME = "public_key_customer";
    static final String CRYPTO_BROKER_BALANCE_TYPE_COLUMN_NAME = "balance_type";
    static final String CRYPTO_BROKER_TRANSACTION_TYPE_COLUMN_NAME = "transaction_type";
    static final String CRYPTO_BROKER_AMOUNT_COLUMN_NAME = "amount";
    static final String CRYPTO_BROKER_CURRENCY_TYPE_COLUMN_NAME = "currency_type";
    static final String CRYPTO_BROKER_RUNNING_BOOK_BALANCE_COLUMN_NAME = "running_book_balance";
    static final String CRYPTO_BROKER_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME = "running_available_balance";
    static final String CRYPTO_BROKER_TIMESTAMP_COLUMN_NAME = "timestamp";
    static final String CRYPTO_BROKER_MEMO_COLUMN_NAME = "memo";

    static final String CRYPTO_BROKER_FIRST_KEY_COLUMN = "transaction_id";

    /**
     * Crypto Broker Total Balances database table definition.
     */
    static final String CRYPTO_BROKER_TOTAL_BALANCES_TABLE_NAME = "crypto_broker_total_balances";

    static final String CRYPTO_BROKER_TOTAL_BALANCES_PUBLIC_KEY_WALLET_COLUMN_NAM = "public_key_wallet";
    static final String CRYPTO_BROKER_TOTAL_BALANCES_CURRENCY_TYPE_COLUMN_NAME = "currency_type";
    static final String CRYPTO_BROKER_TOTAL_BALANCES_DESCRIPTION_COLUMN_NAME = "description";
    static final String CRYPTO_BROKER_TOTAL_BALANCES_AVAILABLE_BALANCE_COLUMN_NAME = "available_balance";
    static final String CRYPTO_BROKER_TOTAL_BALANCES_BOOK_BALANCE_COLUMN_NAME = "book_balance";

    static final String CRYPTO_BROKER_TOTAL_BALANCES_FIRST_KEY_COLUMN = "public_key_wallet";

}