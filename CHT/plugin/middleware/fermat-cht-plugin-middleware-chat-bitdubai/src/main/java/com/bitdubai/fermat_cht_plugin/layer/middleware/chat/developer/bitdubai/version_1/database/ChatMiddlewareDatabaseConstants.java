package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cht_plugin.layer.middleware.chat.version_1.database.ChatMiddlewareDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Miguel Payarez - (miguel_payarez@hotmail.com) on 05/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ChatMiddlewareDatabaseConstants {

    public static final String DATABASE_NAME = "chat_middleware";

    /**
     * Chats database table definition.
     */
    public static final String CHATS_TABLE_NAME = "chats";

    public static final String CHATS_ID_CHAT_COLUMN_NAME = "id_chat";
    public static final String CHATS_LOCAL_ACTOR_PUB_KEY_COLUMN_NAME = "local_actor_pub_key";
    public static final String CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME = "remote_actor_pub_key";
    public static final String CHATS_STATUS_COLUMN_NAME = "status";
    public static final String CHATS_CREATION_DATE_COLUMN_NAME = "creation_date";
    public static final String CHATS_LAST_MESSAGE_DATE_COLUMN_NAME = "last_message_date";

    /**
     * Message database table definition.
     */
    public static final String MESSAGE_TABLE_NAME = "message";

    public static final String MESSAGE_ID_MESSAGE_COLUMN_NAME = "id_message";
    public static final String MESSAGE_ID_CHAT_COLUMN_NAME = "id_chat";
    public static final String MESSAGE_TEXT_MESSAGE_COLUMN_NAME = "text_message";
    public static final String MESSAGE_STATUS_COLUMN_NAME = "status";
    public static final String MESSAGE_TYPE_COLUMN_NAME = "type";
    public static final String MESSAGE_DATE_COLUMN_NAME = "date";

}