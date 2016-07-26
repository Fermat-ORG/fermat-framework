package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database;


/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Manuel Perez - (darkpriestrelative@gmail.com) on 08/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants /*extends AbstractBusinessTransactionDatabaseConstants*/ {

    public static final String DATABASE_NAME = "broker_ack_offline_payment_database";
    /**
     * Ack Online Payment database table definition.
     */
    public static final String ACK_OFFLINE_PAYMENT_TABLE_NAME = "ack_offline_payment";


    public static final String ACK_OFFLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME = "contract_hash";
    public static final String ACK_OFFLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME = "customer_public_key";
    public static final String ACK_OFFLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    public static final String ACK_OFFLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String ACK_OFFLINE_PAYMENT_TRANSACTION_HASH_COLUMN_NAME = "transaction_hash";
    public static final String ACK_OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME = "contract_transaction_status";
    public static final String ACK_OFFLINE_PAYMENT_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String ACK_OFFLINE_PAYMENT_PAYMENT_AMOUNT_COLUMN_NAME = "payment_amount";
    public static final String ACK_OFFLINE_PAYMENT_PAYMENT_TYPE_COLUMN_NAME = "payment_type";
    public static final String ACK_OFFLINE_PAYMENT_CURRENCY_TYPE_COLUMN_NAME = "currency_type";
    public static final String ACK_OFFLINE_PAYMENT_EXTERNAL_TRANSACTION_ID_COLUMN_NAME = "external_transaction_id";
    public static final String ACK_OFFLINE_PAYMENT_CUSTOMER_ALIAS_COLUMN_NAME = "customer_alias";
    public static final String ACK_OFFLINE_PAYMENT_ACTOR_PUBLIC_KEY_COLUMN_NAME = "actor_public_key";
    public static final String ACK_OFFLINE_PAYMENT_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME = "cbp_wallet_public_key";
    public static final String ACK_OFFLINE_PAYMENT_COMPLETION_DATE_COLUMN_NAME = "completion_date";

    public static final String ACK_OFFLINE_PAYMENT_FIRST_KEY_COLUMN = "transaction_id";

    /**
     * Events recorded database table definition.
     */
    public static final String ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME = "ack_offline_payment_events_recorded";

    public static final String ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String ACK_OFFLINE_PAYMENT_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "event_id";

}
