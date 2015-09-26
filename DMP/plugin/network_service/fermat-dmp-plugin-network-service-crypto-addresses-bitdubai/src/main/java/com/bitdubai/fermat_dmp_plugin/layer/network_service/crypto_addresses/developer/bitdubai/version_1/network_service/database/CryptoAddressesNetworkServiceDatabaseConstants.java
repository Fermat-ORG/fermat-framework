package com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.network_service.database;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoAddressesNetworkServiceDatabaseConstants {
    /**
     * Represent the name of the data base
     */
    public static final String DATA_BASE_NAME  = "crypto_addresses_network_service";

    /**
     * Incoming messages database table definition.
     */
    public static final String INCOMING_MESSAGES_TABLE_NAME = "incoming_messages";
    public static final String INCOMING_MESSAGES_TABLE_ID_COLUMN_NAME = "id";
    public static final String INCOMING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME = "sender_id";
    public static final String INCOMING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME = "receiver_id";
    public static final String INCOMING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME = "text_content";
    public static final String INCOMING_MESSAGES_TABLE_TYPE_COLUMN_NAME = "type";
    public static final String INCOMING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String INCOMING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String INCOMING_MESSAGES_TABLE_STATUS_COLUMN_NAME = "status";

    /**
     * Outgoing messages database table definition.
     */
    public static final String OUTGOING_MESSAGES_TABLE_NAME = "outgoing_messages";
    public static final String OUTGOING_MESSAGES_TABLE_ID_COLUMN_NAME = "id";
    public static final String OUTGOING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME = "sender_id";
    public static final String OUTGOING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME = "receiver_id";
    public static final String OUTGOING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME = "text_content";
    public static final String OUTGOING_MESSAGES_TABLE_TYPE_COLUMN_NAME = "type";
    public static final String OUTGOING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String OUTGOING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String OUTGOING_MESSAGES_TABLE_STATUS_COLUMN_NAME = "status";
}
