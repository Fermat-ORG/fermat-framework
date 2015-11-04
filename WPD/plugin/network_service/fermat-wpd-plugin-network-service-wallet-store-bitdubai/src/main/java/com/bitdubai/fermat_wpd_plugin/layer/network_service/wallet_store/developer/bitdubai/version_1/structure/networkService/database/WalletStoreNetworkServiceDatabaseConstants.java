package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService.database;

/**
 * Created by rodrigo on 8/11/15.
 */
public class WalletStoreNetworkServiceDatabaseConstants {
    /**
     * Represent the name of the data base
     */
    public static final String DATA_BASE_NAME  = "WalletStore_networkService";

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
