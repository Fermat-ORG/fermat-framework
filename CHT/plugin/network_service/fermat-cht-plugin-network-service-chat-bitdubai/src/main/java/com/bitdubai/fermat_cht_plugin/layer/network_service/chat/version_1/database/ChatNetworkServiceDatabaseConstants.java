package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.version_1.database;


/**
 * The Class <code>com.bitdubai.fermat_cht_plugin.layer.network_service.chat.version_1.layer.network_service.chat.developer.bitdubai.version_1.database.ChatNetworkServiceDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Gabriel Araujo - (gabe_512@hotmail.com) on 30/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ChatNetworkServiceDatabaseConstants {

    public static final String DATA_BASE_NAME = "network_service_chat";

    /**
     * incoming messages database table definition.
     */
    public static final String INCOMING_MESSAGES_TABLE_NAME = "incoming_messages";

    public static final String INCOMING_MESSAGES_ID_COLUMN_NAME = "id";
    public static final String INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME = "sender_id";
    public static final String INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME = "receiver_id";
    public static final String INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME = "text_content";
    public static final String INCOMING_MESSAGES_TYPE_COLUMN_NAME = "type";
    public static final String INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String INCOMING_MESSAGES_STATUS_COLUMN_NAME = "status";

    public static final String INCOMING_MESSAGES_FIRST_KEY_COLUMN = "id";

    /**
     * outgoing messages database table definition.
     */
    public static final String OUTGOING_MESSAGES_TABLE_NAME = "outgoing_messages";

    public static final String OUTGOING_MESSAGES_ID_COLUMN_NAME = "id";
    public static final String OUTGOING_MESSAGES_SENDER_ID_COLUMN_NAME = "sender_id";
    public static final String OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME = "receiver_id";
    public static final String OUTGOING_MESSAGES_TEXT_CONTENT_COLUMN_NAME = "text_content";
    public static final String OUTGOING_MESSAGES_TYPE_COLUMN_NAME = "type";
    public static final String OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String OUTGOING_MESSAGES_STATUS_COLUMN_NAME = "status";

    public static final String OUTGOING_MESSAGES_FIRST_KEY_COLUMN = "id";

    /**
     * chat_metadata_transaction database table definition.
     */
    public static final String CHAT_METADATA_TRANSACTION_TABLE_NAME = "chat_metadata_transaction";

    public static final String CHAT_METADATA_TRANSACTION_CHAT_ID_COLUMN_NAME = "chat_id";
    public static final String CHAT_METADATA_TRANSACTION_SENDER_ID_COLUMN_NAME = "sender_id";
    public static final String CHAT_METADATA_TRANSACTION_SENDER_TYPE_COLUMN_NAME = "sender_type";
    public static final String CHAT_METADATA_TRANSACTION_RECEIVER_ID_COLUMN_NAME = "receiver_id";
    public static final String CHAT_METADATA_TRANSACTION_RECEIVER_TYPE_COLUMN_NAME = "receiver_type";
    public static final String CHAT_METADATA_TRANSACTION_META_DATA_XML_COLUMN_NAME = "meta_data_xml";
    public static final String CHAT_METADATA_TRANSACTION_TYPE_COLUMN_NAME = "type";
    public static final String CHAT_METADATA_TRANSACTION_DISTRIBUTION_STATUS_COLUMN_NAME = "distribution_status";
    public static final String CHAT_METADATA_TRANSACTION_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String CHAT_METADATA_TRANSACTION_PROCESSED_COLUMN_NAME = "processed";

    public static final String CHAT_METADATA_TRANSACTION_FIRST_KEY_COLUMN = "chat_id";

}