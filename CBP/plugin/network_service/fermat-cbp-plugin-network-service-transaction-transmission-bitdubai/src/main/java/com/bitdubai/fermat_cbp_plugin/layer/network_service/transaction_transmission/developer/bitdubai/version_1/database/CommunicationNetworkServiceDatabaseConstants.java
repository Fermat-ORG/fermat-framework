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

    /**
     * Transaction Transmission Has database table definition.
     */
    public static final String TRANSACTION_TRANSMISSION_HASH_TABLE_NAME = "transaction_transmission_hash";

    public static final String TRANSACTION_TRANSMISSION_HASH_TRANSMISSION_ID_COLUMN_NAME = "transmission_id";
    public static final String TRANSACTION_TRANSMISSION_HASH_CONTRACT_HASH_COLUMN_NAME = "contract_hash";
    public static final String TRANSACTION_TRANSMISSION_HASH_CONTRACT_STATUS_COLUMN_NAME="contract_status";
    public static final String TRANSACTION_TRANSMISSION_HASH_SENDER_PUBLIC_KEY_COLUMN_NAME = "sender_public_key";
    public static final String TRANSACTION_TRANSMISSION_HASH_SENDER_TYPE_COLUMN_NAME = "sender_type";
    public static final String TRANSACTION_TRANSMISSION_HASH_RECEIVER_PUBLIC_KEY_COLUMN_NAME = "receiver_public_key";
    public static final String TRANSACTION_TRANSMISSION_HASH_RECEIVER_TYPE_COLUMN_NAME = "receiver_type";
    public static final String TRANSACTION_TRANSMISSION_HASH_CONTRACT_ID_COLUMN_NAME = "contract_id";
    public static final String TRANSACTION_TRANSMISSION_HASH_NEGOTIATION_ID_COLUMN_NAME = "negotiation_id";
    public static final String TRANSACTION_TRANSMISSION_HASH_TRANSACTION_TYPE_COLUMN_NAME = "transaction_type";
    public static final String TRANSACTION_TRANSMISSION_HASH_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String TRANSACTION_TRANSMISSION_HASH_STATE_COLUMN_NAME = "state";
    public static final String TRANSACTION_TRANSMISSION_HASH_PENDING_FLAG_COLUMN_NAME = "pending_flag";

    public static final String TRANSACTION_TRANSMISSION_HASH_FIRST_KEY_COLUMN = "transmission_id";

    /**
     * COMPONENT VERSIONS DETAILS database table definition.
     */
    public static final String COMPONENT_VERSIONS_DETAILS_TABLE_NAME = "component_versions_details";

    public static final String COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME = "id";
    public static final String COMPONENT_VERSIONS_DETAILS_ACTOR_PUBLIC_KEY_COLUMN_NAME = "actor_public_key";
    public static final String COMPONENT_VERSIONS_DETAILS_IPK_COLUMN_NAME = "ipk";
    public static final String COMPONENT_VERSIONS_DETAILS_LAST_CONNECTION_COLUMN_NAME = "last_connection";

    public static final String COMPONENT_VERSIONS_DETAILS_FIRST_KEY_COLUMN = "id";

}
