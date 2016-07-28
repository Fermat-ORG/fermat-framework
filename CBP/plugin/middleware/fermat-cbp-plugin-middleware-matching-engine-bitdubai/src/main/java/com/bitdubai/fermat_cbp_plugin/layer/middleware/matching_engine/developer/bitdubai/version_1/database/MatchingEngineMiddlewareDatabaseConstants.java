package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/16.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MatchingEngineMiddlewareDatabaseConstants {

    /**
     * Crypto Broker Actor Network Service database global definition.
     */
    public static final String MATCHING_ENGINE_MIDDLEWARE_DATABASE_NAME = "matching_engine_middleware";


    /**
     * Wallets database table definition.
     */
    public static final String WALLETS_TABLE_NAME = "wallets";

    public static final String WALLETS_PUBLIC_KEY_COLUMN_NAME = "public_key";

    public static final String WALLETS_FIRST_KEY_COLUMN = "public_key";

    /**
     * Earning Pair database table definition.
     */
    public static final String EARNING_PAIR_TABLE_NAME = "earning_pair";

    public static final String EARNING_PAIR_ID_COLUMN_NAME = "id";
    public static final String EARNING_PAIR_EARNING_CURRENCY_COLUMN_NAME = "earning_currency";
    public static final String EARNING_PAIR_EARNING_CURRENCY_TYPE_COLUMN_NAME = "earning_currency_type";
    public static final String EARNING_PAIR_LINKED_CURRENCY_COLUMN_NAME = "linked_currency";
    public static final String EARNING_PAIR_LINKED_CURRENCY_TYPE_COLUMN_NAME = "linked_currency_type";
    public static final String EARNING_PAIR_EARNINGS_WALLET_PUBLIC_KEY_COLUMN_NAME = "earnings_wallet_public_key";
    public static final String EARNING_PAIR_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    public static final String EARNING_PAIR_STATE_COLUMN_NAME = "state";

    public static final String EARNING_PAIR_FIRST_KEY_COLUMN = "id";

    /**
     * Earning Transaction database table definition.
     */
    public static final String EARNING_TRANSACTION_TABLE_NAME = "earning_transaction";

    public static final String EARNING_TRANSACTION_ID_COLUMN_NAME = "id";
    public static final String EARNING_TRANSACTION_EARNING_CURRENCY_COLUMN_NAME = "earning_currency";
    public static final String EARNING_TRANSACTION_EARNING_CURRENCY_TYPE_COLUMN_NAME = "earning_currency_type";
    public static final String EARNING_TRANSACTION_AMOUNT_COLUMN_NAME = "amount";
    public static final String EARNING_TRANSACTION_STATE_COLUMN_NAME = "state";
    public static final String EARNING_TRANSACTION_TIME_COLUMN_NAME = "time";
    public static final String EARNING_TRANSACTION_EARNING_PAIR_ID_COLUMN_NAME = "earning_pair_id";

    public static final String EARNING_TRANSACTION_FIRST_KEY_COLUMN = "id";

    /**
     * Input Transaction database table definition.
     */
    public static final String INPUT_TRANSACTION_TABLE_NAME = "input_transaction";

    public static final String INPUT_TRANSACTION_ID_COLUMN_NAME = "id";
    public static final String INPUT_TRANSACTION_ORIGIN_TRANSACTION_ID_COLUMN_NAME = "origin_transaction_id";
    public static final String INPUT_TRANSACTION_CURRENCY_GIVING_COLUMN_NAME = "currency_giving";
    public static final String INPUT_TRANSACTION_CURRENCY_GIVING_TYPE_COLUMN_NAME = "currency_giving_type";
    public static final String INPUT_TRANSACTION_AMOUNT_GIVING_COLUMN_NAME = "amount_giving";
    public static final String INPUT_TRANSACTION_CURRENCY_RECEIVING_COLUMN_NAME = "currency_receiving";
    public static final String INPUT_TRANSACTION_CURRENCY_RECEIVING_TYPE_COLUMN_NAME = "currency_receiving_type";
    public static final String INPUT_TRANSACTION_AMOUNT_RECEIVING_COLUMN_NAME = "amount_receiving";
    public static final String INPUT_TRANSACTION_TYPE_COLUMN_NAME = "type";
    public static final String INPUT_TRANSACTION_STATE_COLUMN_NAME = "state";
    public static final String INPUT_TRANSACTION_EARNING_TRANSACTION_ID_COLUMN_NAME = "earning_transaction_id";
    public static final String INPUT_TRANSACTION_EARNING_PAIR_ID_COLUMN_NAME = "earning_pair_id";

    public static final String INPUT_TRANSACTION_FIRST_KEY_COLUMN = "id";

}
