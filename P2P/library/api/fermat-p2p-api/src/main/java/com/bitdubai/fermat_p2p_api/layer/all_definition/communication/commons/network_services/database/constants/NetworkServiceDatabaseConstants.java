package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 14/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public final class NetworkServiceDatabaseConstants {

    public static final String DATABASE_NAME                                   = "network_service_database";

    /**
     * Incoming Messages database table definition.
     */
    public static final String INCOMING_MESSAGES_TABLE_NAME                      = "incoming_messages"  ;

    public static final String INCOMING_MESSAGES_ID_COLUMN_NAME                  = "id"                 ;
    public static final String INCOMING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME   = "sender_public_key"  ;
    public static final String INCOMING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME = "receiver_public_key";
    public static final String INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME  = "shipping_timestamp" ;
    public static final String INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME  = "delivery_timestamp" ;
    public static final String INCOMING_MESSAGES_CONTENT_TYPE_COLUMN_NAME        = "content_type"       ;
    public static final String INCOMING_MESSAGES_STATUS_COLUMN_NAME              = "status"             ;
    public static final String INCOMING_MESSAGES_CONTENT_COLUMN_NAME             = "content"            ;

    /**
     * Outgoing Messages database table definition.
     */
    public static final String OUTGOING_MESSAGES_TABLE_NAME                             = "outgoing_messages"         ;

    public static final String OUTGOING_MESSAGES_ID_COLUMN_NAME                         = "id"                        ;
    public static final String OUTGOING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME          = "sender_public_key"         ;
    public static final String OUTGOING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME        = "receiver_public_key"       ;
    public static final String OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME         = "shipping_timestamp"        ;
    public static final String OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME         = "delivery_timestamp"        ;
    public static final String OUTGOING_MESSAGES_CONTENT_TYPE_COLUMN_NAME               = "content_type"              ;
    public static final String OUTGOING_MESSAGES_STATUS_COLUMN_NAME                     = "status"                    ;
    public static final String OUTGOING_MESSAGES_IS_BETWEEN_ACTORS_COLUMN_NAME          = "is_between_actors"         ;
    public static final String OUTGOING_MESSAGES_FAIL_COUNT_COLUMN_NAME                 = "fail_count"                ;
    public static final String OUTGOING_MESSAGES_CONTENT_COLUMN_NAME                    = "content"                   ;

    /**
     * Queries database table definition.
     */
    public static final String QUERIES_TABLE_NAME                         = "queries"               ;

    public static final String QUERIES_ID_COLUMN_NAME                     = "id"                    ;
    public static final String QUERIES_BROADCAST_CODE_COLUMN_NAME         = "broadcast_code"        ;
    public static final String QUERIES_DISCOVERY_QUERY_PARAMS_COLUMN_NAME = "discovery_query_params";
    public static final String QUERIES_EXECUTION_TIME_COLUMN_NAME         = "execution_time"        ;
    public static final String QUERIES_TYPE_COLUMN_NAME                   = "type"                  ;
    public static final String QUERIES_STATUS_COLUMN_NAME                 = "status"                ;

}
