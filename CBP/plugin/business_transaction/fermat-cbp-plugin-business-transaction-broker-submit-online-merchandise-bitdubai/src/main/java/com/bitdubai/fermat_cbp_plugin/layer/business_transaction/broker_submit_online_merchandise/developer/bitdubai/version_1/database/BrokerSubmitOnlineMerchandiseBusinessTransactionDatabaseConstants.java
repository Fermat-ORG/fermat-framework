package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Manuel Perez - (darkpriestrelative@gmail.com) on 19/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants {

    public static final String DATABASE_NAME = "broker_submit_online_merchandise_database";
    /**
     * Submit Online Merchandise database table definition.
     */
    public static final String SUBMIT_ONLINE_MERCHANDISE_TABLE_NAME = "submit_online_merchandise";


    public static final String SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME = "contract_hash";
    public static final String SUBMIT_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME = "customer_public_key";
    public static final String SUBMIT_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    public static final String SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_HASH_COLUMN_NAME = "transaction_hash";
    public static final String SUBMIT_ONLINE_MERCHANDISE_CRYPTO_STATUS_COLUMN_NAME = "crypto_status";
    public static final String SUBMIT_ONLINE_MERCHANDISE_CRYPTO_CURRENCY_COLUMN_NAME = "crypto_currency";
    public static final String SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME = "contract_transaction_status";
    public static final String SUBMIT_ONLINE_MERCHANDISE_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String SUBMIT_ONLINE_MERCHANDISE_CRYPTO_ADDRESS_COLUMN_NAME = "crypto_address";
    public static final String SUBMIT_ONLINE_MERCHANDISE_INTRA_ACTOR_PUBLIC_KEY_COLUMN_NAME = "intra_actor_public_key";
    public static final String SUBMIT_ONLINE_MERCHANDISE_CRYPTO_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    public static final String SUBMIT_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME = "crypto_amount";
    public static final String SUBMIT_ONLINE_MERCHANDISE_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME = "cbp_wallet_public_key";
    public static final String SUBMIT_ONLINE_MERCHANDISE_REFERENCE_PRICE_COLUMN_NAME = "reference_price";
    public static final String SUBMIT_ONLINE_MERCHANDISE_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME = "blockchain_type";
    public static final String SUBMIT_ONLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME = "completion_date";
    public static final String SUBMIT_ONLINE_MERCHANDISE_ORIGIN_FEE_COLUMN_NAME = "origin_fee";
    public static final String SUBMIT_ONLINE_MERCHANDISE_FEE_COLUMN_NAME = "fee";

    public static final String SUBMIT_ONLINE_MERCHANDISE_FIRST_KEY_COLUMN = "contract_hash";

    /**
     * Events recorded database table definition.
     */
    public static final String SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_TABLE_NAME = "submit_online_merchandise_events_recorded";

    public static final String SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "event_id";

}
