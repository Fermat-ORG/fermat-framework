package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.CommunicationNetworkServiceDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 27/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationNetworkServiceDatabaseConstants {

    public static final String DATA_BASE_NAME  = "template_network_service_data_base";

    /**
     * incoming messages database table definition.
     */
    public static final String INCOMING_MESSAGES_TABLE_NAME                     = "incoming_messages" ;

    public static final String INCOMING_MESSAGES_ID_COLUMN_NAME                 = "id"                ;
    public static final String INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME          = "sender_id"         ;
    public static final String INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME        = "receiver_id"       ;
    public static final String INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME       = "text_content"      ;
    public static final String INCOMING_MESSAGES_TYPE_COLUMN_NAME               = "type"              ;
    public static final String INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String INCOMING_MESSAGES_STATUS_COLUMN_NAME             = "status"            ;

    public static final String INCOMING_MESSAGES_FIRST_KEY_COLUMN               = "id"                ;

    /**
     * outgoing messages database table definition.
     */
    public static final String OUTGOING_MESSAGES_TABLE_NAME                     = "outgoing_messages" ;

    public static final String OUTGOING_MESSAGES_ID_COLUMN_NAME                 = "id"                ;
    public static final String OUTGOING_MESSAGES_SENDER_ID_COLUMN_NAME          = "sender_id"         ;
    public static final String OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME        = "receiver_id"       ;
    public static final String OUTGOING_MESSAGES_TEXT_CONTENT_COLUMN_NAME       = "text_content"      ;
    public static final String OUTGOING_MESSAGES_TYPE_COLUMN_NAME               = "type"              ;
    public static final String OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String OUTGOING_MESSAGES_STATUS_COLUMN_NAME             = "status"            ;

    public static final String OUTGOING_MESSAGES_FIRST_KEY_COLUMN               = "id"                ;

    /**
     * Negotiation Transmission Network Service database table definition.
     */
    static final String NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME = "negotiation_transmission_network_service";

    static final String NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_ID_COLUMN_NAME = "transmission_id";
    static final String NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    static final String NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_ID_COLUMN_NAME = "negotiation_id";
    static final String NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_STATUS_COLUMN_NAME = "negotiation_status";
    static final String NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_TRANSACTION_TYPE_COLUMN_NAME = "negotiation_transaction_type";
    static final String NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_PUBLIC_KEY_ACTOR_SEND_COLUMN_NAME = "public_key_actor_send";
    static final String NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_ACTOR_SEND_TYPE_COLUMN_NAME = "actor_send_type";
    static final String NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_PUBLIC_KEY_ACTOR_RECEIVE_COLUMN_NAME = "public_key_actor_receive";
    static final String NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_ACTOR_RECEIVE_TYPE_COLUMN_NAME = "actor_receive_type";
    static final String NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_TYPE_COLUMN_NAME = "transmissionType";
    static final String NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_STATE_COLUMN_NAME = "transmissionstate";
    static final String NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TIMESTAMP_COLUMN_NAME = "timestamp";

    static final String NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_FIRST_KEY_COLUMN = "transmission_id";

}