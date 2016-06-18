/*
 * @#TemplateNetworkServiceDatabaseConstants.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.version_1.database.communications;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseConstants</code> is
 * throw when error occurred updating new record in a table of the data base
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetUserNetworkServiceDatabaseConstants {

    public static final String DATA_BASE_NAME = "ActorNetworkServiceAssetUser";

    /**
     * incoming messages database table definition.
     */
//    public static final String INCOMING_MESSAGES_TABLE_NAME = "incoming_messages";
//
//    public static final String INCOMING_MESSAGES_ID_COLUMN_NAME = "id";
//    public static final String INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME = "sender_id";
//    public static final String INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME = "receiver_id";
//    public static final String INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME = "text_content";
//    public static final String INCOMING_MESSAGES_TYPE_COLUMN_NAME = "type";
//    public static final String INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
//    public static final String INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
//    public static final String INCOMING_MESSAGES_STATUS_COLUMN_NAME = "status";
//
//    public static final String INCOMING_MESSAGES_FIRST_KEY_COLUMN = INCOMING_MESSAGES_ID_COLUMN_NAME;
//
//    /**
//     * outgoing messages database table definition.
//     */
//    public static final String OUTGOING_MESSAGES_TABLE_NAME = "outgoing_messages";
//
//    public static final String OUTGOING_MESSAGES_ID_COLUMN_NAME = "id";
//    public static final String OUTGOING_MESSAGES_SENDER_ID_COLUMN_NAME = "sender_id";
//    public static final String OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME = "receiver_id";
//    public static final String OUTGOING_MESSAGES_TEXT_CONTENT_COLUMN_NAME = "text_content";
//    public static final String OUTGOING_MESSAGES_TYPE_COLUMN_NAME = "type";
//    public static final String OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
//    public static final String OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
//    public static final String OUTGOING_MESSAGES_STATUS_COLUMN_NAME = "status";
//
//    public static final String OUTGOING_MESSAGES_FIRST_KEY_COLUMN = OUTGOING_MESSAGES_ID_COLUMN_NAME;

    /**
     * incoming notifications database table definition.
     */
    public static final String INCOMING_NOTIFICATION_TABLE_NAME = "incoming_notification";

    public static final String INCOMING_NOTIFICATION_ID_COLUMN_NAME = "id";
    public static final String INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME = "sender_public_key";
    public static final String INCOMING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME = "receiver_public_key";
    public static final String INCOMING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME = "sender_type";
    public static final String INCOMING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME = "receiver_type";
    public static final String INCOMING_NOTIFICATION_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String INCOMING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME = "sender_alias";
    public static final String INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME = "notification_descriptor";
    public static final String INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME = "notification_protocol_state";
    public static final String INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME = "notification_read_mark";
    public static final String INCOMING_NOTIFICATION_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME = "notification_blockchain_network_type";
    public static final String INCOMING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME = "response_to_notification_id";

    public static final String INCOMING_NOTIFICATION_FIRST_KEY_COLUMN = INCOMING_NOTIFICATION_ID_COLUMN_NAME;

    /**
     * outgoing notification database table definition
     */
    public static final String OUTGOING_NOTIFICATION_TABLE_NAME = "outgoing_notification";

    public static final String OUTGOING_NOTIFICATION_ID_COLUMN_NAME = "id";
    public static final String OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME = "sender_public_key";
    public static final String OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME = "receiver_public_key";
    public static final String OUTGOING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME = "sender_type";
    public static final String OUTGOING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME = "receiver_type";
    public static final String OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String OUTGOING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME = "sender_alias";
    public static final String OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME = "notification_descriptor";
    public static final String OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME = "notification_protocol_state";
    public static final String OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME = "notification_read_mark";
    public static final String OUTGOING_NOTIFICATION_SENT_COUNT_COLUMN_NAME = "notification_sent_count";
    public static final String OUTGOING_NOTIFICATION_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME = "notification_blockchain_network_type";
    public static final String OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME = "response_to_notification_id";

    public static final String OUTGOING_NOTIFICATION_FIRST_KEY_COLUMN = OUTGOING_NOTIFICATION_ID_COLUMN_NAME;
}
