package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.database.CommunicationNetworkServiceDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 02/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class CommunicationNetworkServiceDatabaseConstants {

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

}
