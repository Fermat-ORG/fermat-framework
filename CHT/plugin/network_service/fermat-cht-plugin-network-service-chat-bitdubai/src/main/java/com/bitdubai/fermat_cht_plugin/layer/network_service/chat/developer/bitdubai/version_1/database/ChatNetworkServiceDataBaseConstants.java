package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database;


/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.ChatNetworkServiceDataBaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by natalia on 18/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class ChatNetworkServiceDataBaseConstants {

    public static final String DATA_BASE_NAME  = "cache_table";


    /**
     * incomingchatmetadata database table definition.
     */
    public static final String INCOMING_CHAT_TABLE_NOTIFICATION_TABLE_NAME = "incomingchatmetadata";

    public static final String INCOMING_NOTIFICATION_CHAT_TRANSACTION_ID_COLUMN_NAME = "incoming_transaction_id";
    public static final String INCOMING_NOTIFICATION_CHAT_TRANSACTION_HASH_COLUMN_NAME = "incoming_transaction_hash";
    public static final String INCOMING_NOTIFICATION_CHAT_IDCHAT_COLUMN_NAME = "incoming_idchat";
    public static final String INCOMING_NOTIFICATION_CHAT_IDOBJECTO_COLUMN_NAME = "incoming_idobjecto";
    public static final String INCOMING_NOTIFICATION_CHAT_LOCALACTORTYPE_COLUMN_NAME = "incoming_localactortype";
    public static final String INCOMING_NOTIFICATION_CHAT_LOCALACTORPUBKEY_COLUMN_NAME = "incoming_localactorpubkey";
    public static final String INCOMING_NOTIFICATION_CHAT_REMOTEACTORTYPE_COLUMN_NAME = "incoming_remoteactortype";
    public static final String INCOMING_NOTIFICATION_CHAT_REMOTEACTORPUBKEY_COLUMN_NAME = "incoming_remoteactorpubkey";
    public static final String INCOMING_NOTIFICATION_CHAT_CHATNAME_COLUMN_NAME = "incoming_chatname";
    public static final String INCOMING_NOTIFICATION_CHAT_CHATSTATUS_COLUMN_NAME = "incoming_chatstatus";
    public static final String INCOMING_NOTIFICATION_CHAT_MESSAGE_STATUS_COLUMN_NAME = "incoming_chatmessagestatus";
    public static final String INCOMING_NOTIFICATION_CHAT_DATE_COLUMN_NAME = "incoming_date";
    public static final String INCOMING_NOTIFICATION_CHAT_IDMENSAJE_COLUMN_NAME = "incoming_idmensaje";
    public static final String INCOMING_NOTIFICATION_CHAT_MESSAGE_COLUMN_NAME = "incoming_message";
    public static final String INCOMING_NOTIFICATION_CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME = "incoming_distributionstatus";
    public static final String INCOMING_NOTIFICATION_CHAT_PROCCES_STATUS_COLUMN_NAME = "incoming_process";
    public static final String INCOMING_NOTIFICATION_CHAT_PROTOCOL_STATE_COLUMN_NAME = "incoming_protocol_state";
    public static final String INCOMING_NOTIFICATION_CHAT_SENTDATE_COLUMN_NAME = "incoming_sent_date";
    public static final String INCOMING_NOTIFICATION_CHAT_READ_MARK_COLUMN_NAME = "incoming_mark_read";
    public static final String INCOMING_NOTIFICATION_CHAT_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME = "incoming_response_to_notification_id";


    public static final String INCOMING_CHAT_FIRST_KEY_COLUMN = INCOMING_NOTIFICATION_CHAT_TRANSACTION_ID_COLUMN_NAME;

    /**
     * incomingchatmetadata database table definition.
     */
    public static final String OUTGOING_CHAT_TABLE_NOTIFICATION_TABLE_NAME = "outgoingchatmetadata";

    public static final String OUTGOING_NOTIFICATION_CHAT_TRANSACTION_ID_COLUMN_NAME = "outgoing_transaction_id";
    public static final String OUTGOING_NOTIFICATION_CHAT_TRANSACTION_HASH_COLUMN_NAME = "outgoing_transaction_hash";
    public static final String OUTGOING_NOTIFICATION_CHAT_IDCHAT_COLUMN_NAME = "outgoing_idchat";
    public static final String OUTGOING_NOTIFICATION_CHAT_IDOBJECTO_COLUMN_NAME = "outgoing_idobjecto";
    public static final String OUTGOING_NOTIFICATION_CHAT_LOCALACTORTYPE_COLUMN_NAME = "outgoing_localactortype";
    public static final String OUTGOING_NOTIFICATION_CHAT_LOCALACTORPUBKEY_COLUMN_NAME = "outgoing_localactorpubkey";
    public static final String OUTGOING_NOTIFICATION_CHAT_REMOTEACTORTYPE_COLUMN_NAME = "outgoing_remoteactortype";
    public static final String OUTGOING_NOTIFICATION_CHAT_REMOTEACTORPUBKEY_COLUMN_NAME = "outgoing_remoteactorpubkey";
    public static final String OUTGOING_NOTIFICATION_CHAT_CHATNAME_COLUMN_NAME = "outgoing_chatname";
    public static final String OUTGOING_NOTIFICATION_CHAT_CHATSTATUS_COLUMN_NAME = "outgoing_chatstatus";
    public static final String OUTGOING_NOTIFICATION_CHAT_MESSAGE_STATUS_COLUMN_NAME = "outgoing_chatmessagestatus";
    public static final String OUTGOING_NOTIFICATION_CHAT_DATE_COLUMN_NAME = "outgoing_date";
    public static final String OUTGOING_NOTIFICATION_CHAT_IDMENSAJE_COLUMN_NAME = "outgoing_idmensaje";
    public static final String OUTGOING_NOTIFICATION_CHAT_MESSAGE_COLUMN_NAME = "outgoing_message";
    public static final String OUTGOING_NOTIFICATION_CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME = "outgoing_distributionstatus";
    public static final String OUTGOING_NOTIFICATION_CHAT_PROCCES_STATUS_COLUMN_NAME = "outgoing_process";
    public static final String OUTGOING_NOTIFICATION_CHAT_PROTOCOL_STATE_COLUMN_NAME = "outgoing_protocol_state";
    public static final String OUTGOING_NOTIFICATION_CHAT_SENTDATE_COLUMN_NAME = "outgoing_sent_date";
    public static final String OUTGOING_NOTIFICATION_CHAT_READ_MARK_COLUMN_NAME = "outgoing_mark_read";
    public static final String OUTGOING_NOTIFICATION_CHAT_SENT_COUNT_COLUMN_NAME ="outgoing_sent_count";
    public static final String OUTGOING_NOTIFICATION_CHAT_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME = "outgoing_response_to_notification_id";


    public static final String OUTGOING_CHAT_FIRST_KEY_COLUMN = OUTGOING_NOTIFICATION_CHAT_TRANSACTION_ID_COLUMN_NAME;

}


