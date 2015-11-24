package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database.CashMoneyWalletDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CashMoneyWalletDatabaseConstants {

    /**
     * Wallets database table definition.
     */
    static final String WALLETS_TABLE_NAME = "wallets";

    static final String WALLETS_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    static final String WALLETS_AVAILABLE_BALANCE_COLUMN_NAME = "available_balance";
    static final String WALLETS_BOOK_BALANCE_COLUMN_NAME = "book_balance";
    static final String WALLETS_CURRENCY_COLUMN_NAME = "currency";
    static final String WALLETS_TIMESTAMP_WALLET_CREATION_COLUMN_NAME = "timestamp_wallet_creation";

    static final String WALLETS_FIRST_KEY_COLUMN = "wallet_public_key";

    /**
     * Transactions database table definition.
     */
    static final String TRANSACTIONS_TABLE_NAME = "transactions";

    static final String TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    static final String TRANSACTIONS_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    static final String TRANSACTIONS_ACTOR_PUBLIC_KEY_COLUMN_NAME = "actor_public_key";
    static final String TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME = "transaction_type";
    static final String TRANSACTIONS_AMOUNT_COLUMN_NAME = "amount";
    static final String TRANSACTIONS_MEMO_COLUMN_NAME = "memo";
    static final String TRANSACTIONS_TIMESTAMP_COLUMN_NAME = "timestamp";

    static final String TRANSACTIONS_FIRST_KEY_COLUMN = "transaction_id";

}