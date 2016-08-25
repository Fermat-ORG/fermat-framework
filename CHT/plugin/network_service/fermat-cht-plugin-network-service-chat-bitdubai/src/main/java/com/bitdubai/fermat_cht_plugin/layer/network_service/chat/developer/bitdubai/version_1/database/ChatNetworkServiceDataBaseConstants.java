package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database;


/**
 * <p/>
 * <p/>
 * Created by Gabriel Araujo.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class ChatNetworkServiceDataBaseConstants {

    public static final String DATA_BASE_NAME = "chatmetadata_database";

    public static final String MESSAGE_METADATA_TRANSACTION_RECORD_TABLE = "message_metadata_transaction_record_table";

    public static final String MESSAGE_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME = "message_metadata_transaction_id";
    public static final String MESSAGE_METADATA_TRANSACTION_RECORD_TRANSACTION_TYPE_COLUMN_NAME = "message_metadata_transaction_type";
    public static final String MESSAGE_METADATA_TRANSACTION_RECORD_LOCALACTORTYPE_COLUMN_NAME = "message_metadata_localactortype";
    public static final String MESSAGE_METADATA_TRANSACTION_RECORD_LOCALACTORPUBKEY_COLUMN_NAME = "message_metadata_localactorpubkey";
    public static final String MESSAGE_METADATA_TRANSACTION_RECORD_REMOTEACTORTYPE_COLUMN_NAME = "message_metadata_remoteactortype";
    public static final String MESSAGE_METADATA_TRANSACTION_RECORD_REMOTEACTORPUBKEY_COLUMN_NAME = "message_metadata_remoteactorpubkey";
    public static final String MESSAGE_METADATA_TRANSACTION_RECORD_IDMESSAGE_COLUMN_NAME = "message_metadata_idmessage";
    public static final String MESSAGE_METADATA_TRANSACTION_RECORD_MESSAGE_COLUMN_NAME = "message_metadata_message";
    public static final String MESSAGE_METADATA_TRANSACTION_RECORD_MESSAGE_STATUS_COLUMN_NAME = "message_metadata_messagestatus";
    public static final String MESSAGE_METADATA_TRANSACTION_RECORD_DATE_COLUMN_NAME = "message_metadata_date";
    public static final String MESSAGE_METADATA_TRANSACTION_RECORD_SENTDATE_COLUMN_NAME = "message_metadata_sent_date";

    public static final String MESSAGE_METADATA_TRANSACTION_RECORD_FIRST_KEY_COLUMN = MESSAGE_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME;

    public static final String P2P_CLIENT_EVENT_RECORD_TABLE = "p2p_client_event_record_table";

    public static final String PACKAGE_ID_RECORD_COLUMN_NAME = "event_package_id";
    public static final String SENDER_PUBLIC_KEY_COLUMN_NAME ="sender_public_key";
    public static final String DESTINATION_PUBLICK_KEY_COLUMN_NAME = "destination_public_key";

    public static final String P2P_CLIENT_EVENT_RECORD_FIRST_KEY_COLUMN = PACKAGE_ID_RECORD_COLUMN_NAME;



}


