package com.fermat_p2p_layer.version_1.structure.database;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/08/16.
 */
public class P2PLayerDatabaseConstants {

    public static final String DATABASE_NAME = "p2p_layer_database";

    /**
     * Messages Failed database table definition.
     */
    public static final String P2P_LAYER_MESSAGES_TABLE_NAME = "p2p_layer_messages"  ;

    //Columns
    public static final String P2P_LAYER_PACKAGE_ID_COLUMN_NAME = "id";
    public static final String P2P_LAYER_CONTENT_COLUMN_NAME = "content";
    public static final String P2P_LAYER_NETWORK_SERVICE_TYPE_COLUMN_NAME = "network_service_type";
    public static final String P2P_LAYER_SENDER_PUBLIC_KEY_COLUMN_NAME = "sender_public_key";
    public static final String P2P_LAYER_RECEIVER_PUBLIC_KEY_COLUMN_NAME = "receiver_public_key";
    public static final String P2P_LAYER_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String P2P_LAYER_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String P2P_LAYER_IS_BETWEEN_ACTORS_COLUMN_NAME = "is_between_actors";
    public static final String P2P_LAYER_FERMAT_MESSAGE_STATUS_COLUMN_NAME = "fermat_messages_status";
    public static final String P2P_LAYER_SIGNATURE_COLUMN_NAME = "signature";
    public static final String P2P_LAYER_FAIL_COUNT_COLUMN_NAME = "fail_count";


    /**
     * Messages Failed database table definition.
     */
    public static final String P2P_LAYER_EVENT_TABLE_NAME = "p2p_layer_events"  ;

    //Columns
    public static final String P2P_LAYER_EVENT_ID_COLUMN_NAME = "package_id";
    public static final String P2P_LAYER_EVENT_NS_OWNER_COLUMN_NAME = "ns_owner";

}
