package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cht_plugin.layer.network_service.network_service_chat.developer.bitdubai.version_1.database.NetworkServiceChatNetworkServiceDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Gabriel Araujo - (gabe_512@hotmail.com) on 06/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkServiceChatNetworkServiceDatabaseConstants {

    public static final String DATA_BASE_NAME  = "chat_network_service_data_base";

    /**
     * Outgoing Chat database table definition.
     */
    public static final String OUTGOING_CHAT_TABLE_NAME = "outgoing_chat";

    public static final String OUTGOING_CHAT_IDCHAT_COLUMN_NAME = "idchat";
    public static final String OUTGOING_CHAT_IDOBJECTO_COLUMN_NAME = "idobjecto";
    public static final String OUTGOING_CHAT_LOCALACTORTYPE_COLUMN_NAME = "localactortype";
    public static final String OUTGOING_CHAT_LOCALACTORPUBKEY_COLUMN_NAME = "localactorpubkey";
    public static final String OUTGOING_CHAT_REMOTEACTORTYPE_COLUMN_NAME = "remoteactortype";
    public static final String OUTGOING_CHAT_REMOTEACTORPUBKEY_COLUMN_NAME = "remoteactorpubkey";
    public static final String OUTGOING_CHAT_CHATNAME_COLUMN_NAME = "chatname";
    public static final String OUTGOING_CHAT_CHATSTATUS_COLUMN_NAME = "chatstatus";
    public static final String OUTGOING_CHAT_DATE_COLUMN_NAME = "date";
    public static final String OUTGOING_CHAT_IDMENSAJE_COLUMN_NAME = "idmensaje";
    public static final String OUTGOING_CHAT_MESSAGE_COLUMN_NAME = "message";
    public static final String OUTGOING_CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME = "distributionstatus";

    public static final String OUTGOING_CHAT_FIRST_KEY_COLUMN = "idchat";

    /**
     * Incoming Chat database table definition.
     */
    public static final String INCOMING_CHAT_TABLE_NAME = "incoming_chat";

    public static final String INCOMING_CHAT_IDCHAT_COLUMN_NAME = "idchat";
    public static final String INCOMING_CHAT_IDOBJECTO_COLUMN_NAME = "idobjecto";
    public static final String INCOMING_CHAT_LOCALACTORTYPE_COLUMN_NAME = "localactortype";
    public static final String INCOMING_CHAT_LOCALACTORPUBKEY_COLUMN_NAME = "localactorpubkey";
    public static final String INCOMING_CHAT_REMOTEACTORTYPE_COLUMN_NAME = "remoteactortype";
    public static final String INCOMING_CHAT_REMOTEACTORPUBKEY_COLUMN_NAME = "remoteactorpubkey";
    public static final String INCOMING_CHAT_CHATNAME_COLUMN_NAME = "chatname";
    public static final String INCOMING_CHAT_CHATSTATUS_COLUMN_NAME = "chatstatus";
    public static final String INCOMING_CHAT_DATE_COLUMN_NAME = "date";
    public static final String INCOMING_CHAT_IDMENSAJE_COLUMN_NAME = "idmensaje";
    public static final String INCOMING_CHAT_MESSAGE_COLUMN_NAME = "message";
    public static final String INCOMING_CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME = "distributionstatus";

    public static final String INCOMING_CHAT_FIRST_KEY_COLUMN = "idchat";

    /**
     * Incoming Chat Message database table definition.
     */
    public static final String INCOMING_CHAT_MESSAGE_TABLE_NAME = "incoming_chat_message";

    public static final String INCOMING_CHAT_MESSAGE_IDMENSAJE_COLUMN_NAME = "idmensaje";
    public static final String INCOMING_CHAT_MESSAGE_IDCHAT_COLUMN_NAME = "idchat";
    public static final String INCOMING_CHAT_MESSAGE_MESSAGE_COLUMN_NAME = "message";
    public static final String INCOMING_CHAT_MESSAGE_DATE_COLUMN_NAME = "date";
    public static final String INCOMING_CHAT_MESSAGE_DISTRIBUTIONSTATUS_COLUMN_NAME = "distributionstatus";

    public static final String INCOMING_CHAT_MESSAGE_FIRST_KEY_COLUMN = "idchat";

    /**
     * Outgoing Chat Message database table definition.
     */
    public static final String OUTGOING_CHAT_MESSAGE_TABLE_NAME = "outgoing_chat_message";

    public static final String OUTGOING_CHAT_MESSAGE_IDMENSAJE_COLUMN_NAME = "idmensaje";
    public static final String OUTGOING_CHAT_MESSAGE_IDCHAT_COLUMN_NAME = "idchat";
    public static final String OUTGOING_CHAT_MESSAGE_MESSAGE_COLUMN_NAME = "message";
    public static final String OUTGOING_CHAT_MESSAGE_DATE_COLUMN_NAME = "date";
    public static final String OUTGOING_CHAT_MESSAGE_DISTRIBUTIONSTATUS_COLUMN_NAME = "distributionstatus";

    public static final String OUTGOING_CHAT_MESSAGE_FIRST_KEY_COLUMN = "idchat";

    /**
     * Incoming Chat Message Notification Status database table definition.
     */
    public static final String INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_TABLE_NAME = "incoming_chat_message_notification_status";

    public static final String INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_IDMENSAJE_COLUMN_NAME = "idmensaje";
    public static final String INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_IDCHAT_COLUMN_NAME = "idchat";
    public static final String INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_DATE_COLUMN_NAME = "date";
    public static final String INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_DISTRIBUTIONSTATUS_COLUMN_NAME = "distributionstatus";

    public static final String INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_FIRST_KEY_COLUMN = "idchat";

    /**
     * Outgoing Chat Message Notification Status database table definition.
     */
    public static final String OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_TABLE_NAME = "outgoing_chat_message_notification_status";

    public static final String OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_IDMENSAJE_COLUMN_NAME = "idmensaje";
    public static final String OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_IDCHAT_COLUMN_NAME = "idchat";
    public static final String OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_DATE_COLUMN_NAME = "date";
    public static final String OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_DISTRIBUTIONSTATUS_COLUMN_NAME = "distributionstatus";

    public static final String OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_FIRST_KEY_COLUMN = "idchat";

    /**
     * incoming messages database table definition.
     */
    public static final String INCOMING_MESSAGES_TABLE_NAME = "incoming_messages";

    public static final String INCOMING_MESSAGES_ID_COLUMN_NAME = "incoming_id";
    public static final String INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME = "sender_id";
    public static final String INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME = "receiver_id";
    public static final String INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME = "text_content";
    public static final String INCOMING_MESSAGES_TYPE_COLUMN_NAME = "type";
    public static final String INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String INCOMING_MESSAGES_STATUS_COLUMN_NAME = "status";

    public static final String INCOMING_MESSAGES_FIRST_KEY_COLUMN = INCOMING_MESSAGES_ID_COLUMN_NAME;

    /**
     * outgoing messages database table definition.
     */
    public static final String OUTGOING_MESSAGES_TABLE_NAME = "outgoing_messages";

    public static final String OUTGOING_MESSAGES_ID_COLUMN_NAME = "outgoing_id";
    public static final String OUTGOING_MESSAGES_SENDER_ID_COLUMN_NAME = "sender_id";
    public static final String OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME = "receiver_id";
    public static final String OUTGOING_MESSAGES_TEXT_CONTENT_COLUMN_NAME = "text_content";
    public static final String OUTGOING_MESSAGES_TYPE_COLUMN_NAME = "type";
    public static final String OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String OUTGOING_MESSAGES_STATUS_COLUMN_NAME = "status";

    public static final String OUTGOING_MESSAGES_FIRST_KEY_COLUMN = OUTGOING_MESSAGES_ID_COLUMN_NAME;


}