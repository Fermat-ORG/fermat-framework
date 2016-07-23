package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Manuel Perez - (darkpriestrelative@gmail.com) on 12/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CustomerOfflinePaymentBusinessTransactionDatabaseConstants {

    public static final String DATABASE_NAME = "customer_offline_payment_database";
    /**
     * Online Payment database table definition.
     */
    public static final String OFFLINE_PAYMENT_TABLE_NAME = "offline_payment";


    public static final String OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME = "contract_hash";
    public static final String OFFLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME = "customer_public_key";
    public static final String OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    public static final String OFFLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String OFFLINE_PAYMENT_TRANSACTION_HASH_COLUMN_NAME = "transaction_hash";
    public static final String OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME = "contract_transaction_status";
    public static final String OFFLINE_PAYMENT_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String OFFLINE_PAYMENT_FIRST_KEY_COLUMN = "transaction_id";
    public static final String OFFLINE_PAYMENT_COMPLETION_DATE_COLUMN_NAME = "completion_date";

    /**
     * Events recorded database table definition.
     */
    public static final String OFFLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME = "events_recorded";

    public static final String OFFLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String OFFLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String OFFLINE_PAYMENT_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String OFFLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String OFFLINE_PAYMENT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String OFFLINE_PAYMENT_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "event_id";

}
