package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Manuel Perez - (darkpriestrelative@gmail.com) on 08/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants {

    public static final String DATABASE_NAME = "broker_ack_online_payment_database";
    /**
     * Ack Online Payment database table definition.
     */
    public static final String ACK_ONLINE_PAYMENT_TABLE_NAME = "ack_online_payment";


    public static final String ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME = "contract_hash";
    public static final String ACK_ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME = "customer_public_key";
    public static final String ACK_ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    public static final String ACK_ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String ACK_ONLINE_PAYMENT_TRANSACTION_HASH_COLUMN_NAME = "transaction_hash";
    public static final String ACK_ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME = "crypto_status";
    public static final String ACK_ONLINE_PAYMENT_CRYPTO_CURRENCY_COLUMN_NAME = "crypto_currency";
    public static final String ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME = "contract_transaction_status";
    public static final String ACK_ONLINE_PAYMENT_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String ACK_ONLINE_PAYMENT_CRYPTO_ADDRESS_COLUMN_NAME = "crypto_address";
    public static final String ACK_ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    public static final String ACK_ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME = "crypto_amount";
    public static final String ACK_ONLINE_PAYMENT_COMPLETION_DATE_COLUMN_NAME = "completion_date";


    public static final String ACK_ONLINE_PAYMENT_FIRST_KEY_COLUMN = "transaction_id";

    /**
     * Events recorded database table definition.
     */
    public static final String ACK_ONLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME = "ack_online_payment_events_recorded";

    public static final String ACK_ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String ACK_ONLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String ACK_ONLINE_PAYMENT_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String ACK_ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String ACK_ONLINE_PAYMENT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String ACK_ONLINE_PAYMENT_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "event_id";

    /**
     * Incoming money database table definition
     */
    public static final String ACK_ONLINE_PAYMENT_INCOMING_MONEY_TABLE_NAME = "incoming_money_events_recorded";

    public static final String ACK_ONLINE_PAYMENT_INCOMING_MONEY_EVENT_ID_COLUMN_NAME = "event_id";
    public static final String ACK_ONLINE_PAYMENT_INCOMING_MONEY_RECEIVER_PUBLIC_KEY_COLUMN_NAME = "receiver_public_key";
    public static final String ACK_ONLINE_PAYMENT_INCOMING_MONEY_CRYPTO_AMOUNT_COLUMN_NAME = "crypto_amount";
    public static final String ACK_ONLINE_PAYMENT_INCOMING_MONEY_CRYPTO_CURRENCY_COLUMN_NAME = "crypto_currency";
    public static final String ACK_ONLINE_PAYMENT_INCOMING_MONEY_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    public static final String ACK_ONLINE_PAYMENT_INCOMING_MONEY_SENDER_PUBLIC_KEY_COLUMN_NAME = "sender_public_key";
    public static final String ACK_ONLINE_PAYMENT_INCOMING_MONEY_STATUS_COLUMN_NAME = "status";
    public static final String ACK_ONLINE_PAYMENT_INCOMING_MONEY_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String ACK_ONLINE_PAYMENT_INCOMING_MONEY_TRANSACTION_HASH_COLUMN_NAME = "transactionHash";

    public static final String ACK_ONLINE_PAYMENT_INCOMING_MONEY_TABLE_FIRST_KEY_COLUMN = "event_id";

}
