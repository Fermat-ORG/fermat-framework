package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database;


/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraActorNetworkServiceDataBaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by natalia on 18/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class IntraActorNetworkServiceDataBaseConstants {

    public static final String DATA_BASE_NAME  = "cache_table";


    /**
     *  incming notification database table definition
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
    public static final String INCOMING_NOTIFICATION_SENDER_PHRASE_COLUMN_NAME = "sender_phrase";
    public static final String INCOMING_NOTIFICATION_SENDER_COUNTRY_COLUMN_NAME = "country";
    public static final String INCOMING_NOTIFICATION_SENDER_CITY_COLUMN_NAME = "city";

    public static final String INCOMING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME = "response_to_notification_id";

    public static final String INCOMING_NOTIFICATION_FIRST_KEY_COLUMN = "id";

    /**
     *  outgoing notification database table definition
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
    public static final String OUTGOING_NOTIFICATION_SENDER_PHRASE_COLUMN_NAME = "sender_phrase";
    public static final String OUTGOING_NOTIFICATION_COUNTRY_COLUMN_NAME = "country";
    public static final String OUTGOING_NOTIFICATION_CITY_COLUMN_NAME = "city";
    public static final String OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME = "response_to_notification_id";
    public static final String OUTGOING_NOTIFICATION_FIRST_KEY_COLUMN = "id";

    /**
     * Intra Actor Network Service Cache database table definition.
     */

    public static final String INTRA_ACTOR_ONLINE_CACHE_TABLE_NAME                      = "intra_actor_online_cache"      ;

    public static final String INTRA_ACTOR_ONLINE_CACHE_ID_COLUMN_NAME                  = "id"                            ;
    public static final String INTRA_ACTOR_ONLINE_CACHE_PUBLIC_KEY_COLUMN_NAME          = "actor_public_key"             ;
    public static final String INTRA_ACTOR_ONLINE_CACHE_ALIAS_COLUMN_NAME               = "intra_user_alias"      ;
    public static final String INTRA_ACTOR_ONLINE_CACHE_PHRASE_COLUMN_NAME              = "intra_user_phrase"      ;
    public static final String INTRA_ACTOR_ONLINE_CACHE_COUNTRY_COLUMN_NAME             = "intra_user_country";
    public static final String INTRA_ACTOR_ONLINE_CACHE_CITY_COLUMN_NAME                = "intra_user_city";
    public static final String INTRA_ACTOR_ONLINE_CACHE_TIMESTAMP_COLUMN_NAME               = "cache_date"                ;

    public static final String INTRA_ACTOR_ONLINE_CACHE_FIRST_KEY_COLUMN                = "id"                            ;

}


