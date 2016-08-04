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
    public static final String CHATS_ID_OBJECT_COLUMN_NAME = "id_object";
    public static final String CHATS_LOCAL_ACTOR_TYPE_COLUMN_NAME = "local_actor_type";
    public static final String CHATS_LOCAL_ACTOR_PUB_KEY_COLUMN_NAME = "local_actor_pub_key";
    public static final String CHATS_REMOTE_ACTOR_TYPE_COLUMN_NAME = "remote_actor_type";
    public static final String CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME = "remote_actor_pub_key";
    public static final String CHATS_CHAT_NAME_COLUMN_NAME = "chat_name";
    public static final String CHATS_STATUS_COLUMN_NAME = "status";
    public static final String CHATS_CREATION_DATE_COLUMN_NAME = "creation_date";
    public static final String CHATS_LAST_MESSAGE_DATE_COLUMN_NAME = "last_message_date";
    public static final String CHATS_CONTACT_ASSOCIATED_LIST = "contact_associated_list";
    public static final String CHATS_TYPE_CHAT = "type_chat";
    public static final String CHATS_SCHEDULED_DELIVERY = "scheduled_delivery";
    public static final String CHATS_IS_WRITING = "is_Writing";
    public static final String CHATS_IS_ONLINE = "is_Online";

    public static final String CHATS_FIRST_KEY_COLUMN = "id_chat";

    /**
     * Message database table definition.
     */
    public static final String MESSAGE_TABLE_NAME = "message";

    public static final String MESSAGE_ID_MESSAGE_COLUMN_NAME = "id_message";
    public static final String MESSAGE_ID_CHAT_COLUMN_NAME = "id_chat";
    public static final String MESSAGE_TEXT_MESSAGE_COLUMN_NAME = "text_message";
    public static final String MESSAGE_STATUS_COLUMN_NAME = "status";
    public static final String MESSAGE_TYPE_COLUMN_NAME = "type";
    public static final String MESSAGE_MESSAGE_DATE_COLUMN_NAME = "message_date";
    public static final String MESSAGE_CONTACT_ID = "contact_id";
    public static final String MESSAGE_COUNT = "message_count";

    public static final String MESSAGE_FIRST_KEY_COLUMN = "id_message";

    /**
     * actions writing database table definition.
     */
    public static final String ACTIONS_WRITING_TABLE_NAME = "actions";

    public static final String ACTIONS_WRITING_ID_CHAT_COLUMN_NAME = "id_chat";
    public static final String ACTIONS_WRITING_STATE = "writing_state";
    public static final String ACTIONS_WRITING_VALUE = "writing_state";

    public static final String ACTIONS_WRITING_FIRST_KEY_COLUMN = "id_chat";

    /**
     * actions writing database table definition.
     */
    public static final String ACTIONS_ONLINE_TABLE_NAME = "actions_online";

    public static final String ACTIONS_ONLINE_ID_COLUMN_NAME = "id_action";
    public static final String ACTIONS_ONLINE_PUBLIC_KEY_COLUMN_NAME = "publik_key";
    public static final String ACTIONS_ONLINE_STATE = "online_state";
    public static final String ACTIONS_ONLINE_VALUE = "online_value";
    public static final String ACTIONS_ONLINE_LAST_ON = "last_on";
    public static final String ACTIONS_LAST_CONNECTION = "lats_connection";

    public static final String ACTIONS_ONLINE_FIRST_KEY_COLUMN = "id_action";

//    De acá a abajo tengo que revisar

    /**
     * Contacts database table definition.
     */
    public static final String CONTACTS_TABLE_NAME = "contacts";

    public static final String CONTACTS_ID_CONTACT_COLUMN_NAME = "id_contact";
    public static final String CONTACTS_REMOTE_NAME_COLUMN_NAME = "remote_name";
    public static final String CONTACTS_ALIAS_COLUMN_NAME = "alias";
    public static final String CONTACTS_REMOTE_ACTOR_TYPE_COLUMN_NAME = "remote_actor_type";
    public static final String CONTACTS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME = "remote_actor_pub_key";
    public static final String CONTACTS_CREATION_DATE_COLUMN_NAME = "creation_date";
    public static final String CONTACTS_CONTACT_STATUS_COLUMN_NAME = "contact_status";

    public static final String CONTACTS_FIRST_KEY_COLUMN = "id_contact";

    /**
     * Contacts Connection database table definition.
     */
    public static final String CONTACTS_CONNECTION_TABLE_NAME = "contacts_connections";

    public static final String CONTACTS_CONNECTION_ID_CONTACT_COLUMN_NAME = "id_contact";
    public static final String CONTACTS_CONNECTION_REMOTE_NAME_COLUMN_NAME = "remote_name";
    public static final String CONTACTS_CONNECTION_ALIAS_COLUMN_NAME = "alias";
    public static final String CONTACTS_CONNECTION_REMOTE_ACTOR_TYPE_COLUMN_NAME = "remote_actor_type";
    public static final String CONTACTS_CONNECTION_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME = "remote_actor_pub_key";
    public static final String CONTACTS_CONNECTION_CREATION_DATE_COLUMN_NAME = "creation_date";
    public static final String CONTACTS_CONNECTION_CONTACT_STATUS_COLUMN_NAME = "contact_status";

    public static final String CONTACTS_CONNECTION_FIRST_KEY_COLUMN = "id_contact";

    /**
     * Events recorded database table definition.
     */
    public static final String EVENTS_RECORDED_TABLE_NAME = "events_recorded";

    public static final String EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String EVENTS_RECORDED_CHAT_ID_COLUMN_NAME = "chat_id";

    public static final String EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "event_id";

    /**
     * Crypto Identity database table definition.
     */
    public static final String IDENTITY_TABLE_NAME = "identity";

    public static final String IDENTITY_PUBLIC_KEY_COLUMN_NAME = "identity_public_key";
    public static final String IDENTITY_ALIAS_COLUMN_NAME = "alias";
    public static final String IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME = "device_user_public_key";
    public static final String IDENTITY_ACTOR_TYPE_COLUMN_NAME = "actor_type";
    public static final String IDENTITY_PLATFORM_COMPONENT_TYPE_COLUMN_NAME = "platform_component_type";

    public static final String IDENTITY_FIRST_KEY_COLUMN = "identity_public_key";

    /**
     * Chat Actor GROUP MEMBER REGISTERED database table definition.
     */
    public static final String GROUP_MEMBER_TABLE_NAME = "group_member";

    public static final String GROUP_MEMBER_ID_COLUMN_NAME = "group_id_member";
    public static final String GROUP_MEMBER_GROUP_ID_COLUMN_NAME = "group_id";
    public static final String GROUP_MEMBER_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME = "publicKey";
    public static final String GROUP_MEMBER_ALIAS_COLUMN_NAME = "alias";

    public static final String GROUP_MEMBER_FIRST_KEY_COLUMN = GROUP_MEMBER_ID_COLUMN_NAME;


}