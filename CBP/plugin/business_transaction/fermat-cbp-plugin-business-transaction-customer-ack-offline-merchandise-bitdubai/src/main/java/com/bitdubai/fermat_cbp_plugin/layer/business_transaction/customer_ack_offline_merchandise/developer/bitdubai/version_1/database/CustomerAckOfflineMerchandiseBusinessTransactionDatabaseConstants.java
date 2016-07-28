package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Manuel Perez - (darkpriestrelative@gmail.com) on 26/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CustomerAckOfflineMerchandiseBusinessTransactionDatabaseConstants {

    public static final String DATABASE_NAME = "customer_ack_offline_merchandise_database";
    /**
     * Ack Online Merchandise database table definition.
     */
    public static final String ACK_OFFLINE_MERCHANDISE_TABLE_NAME = "ack_offline_merchandise";


    public static final String ACK_OFFLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME = "contract_hash";
    public static final String ACK_OFFLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME = "customer_public_key";
    public static final String ACK_OFFLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    public static final String ACK_OFFLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String ACK_OFFLINE_MERCHANDISE_TRANSACTION_HASH_COLUMN_NAME = "transaction_hash";
    public static final String ACK_OFFLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME = "contract_transaction_status";
    public static final String ACK_OFFLINE_MERCHANDISE_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String ACK_OFFLINE_MERCHANDISE_MERCHANDISE_AMOUNT_COLUMN_NAME = "merchandise_amount";
    public static final String ACK_OFFLINE_MERCHANDISE_MERCHANDISE_TYPE_COLUMN_NAME = "merchandise_type";
    public static final String ACK_OFFLINE_MERCHANDISE_CURRENCY_TYPE_COLUMN_NAME = "currency_type";
    public static final String ACK_OFFLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME = "completion_date";

    public static final String ACK_OFFLINE_MERCHANDISE_FIRST_KEY_COLUMN = "transaction_id";

    /**
     * Events recorded database table definition.
     */
    public static final String ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_TABLE_NAME = "ack_online_payment_events_recorded";

    public static final String ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String ACK_OFFLINE_MERCHANDISE_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "event_id";

}
