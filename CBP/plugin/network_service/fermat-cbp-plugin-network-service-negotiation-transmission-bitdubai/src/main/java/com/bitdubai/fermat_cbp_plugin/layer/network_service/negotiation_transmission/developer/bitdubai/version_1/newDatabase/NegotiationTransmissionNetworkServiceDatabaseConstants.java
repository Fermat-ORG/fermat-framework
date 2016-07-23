package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.newDatabase;


/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 27/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class NegotiationTransmissionNetworkServiceDatabaseConstants {

    public static final String DATA_BASE_NAME = "negotiation_transmission_data_base";

    /**
     * incoming notification database table definition
     */

    public static final String INCOMING_NOTIFICATION_TABLE_NAME = "incoming_notification";

    public static final String INCOMING_NOTIFICATION_TRANSMISSION_ID_COLUMN_NAME = "transmission_id";
    public static final String INCOMING_NOTIFICATION_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String INCOMING_NOTIFICATION_NEGOTIATION_ID_COLUMN_NAME = "negotiation_id";
    public static final String INCOMING_NOTIFICATION_NEGOTIATION_STATUS_COLUMN_NAME = "negotiation_status";
    public static final String INCOMING_NOTIFICATION_NEGOTIATION_TRANSACTION_TYPE_COLUMN_NAME = "negotiation_transaction_type";
    public static final String INCOMING_NOTIFICATION_PUBLIC_KEY_ACTOR_SEND_COLUMN_NAME = "public_key_actor_send";
    public static final String INCOMING_NOTIFICATION_ACTOR_SEND_TYPE_COLUMN_NAME = "actor_send_type";
    public static final String INCOMING_NOTIFICATION_PUBLIC_KEY_ACTOR_RECEIVE_COLUMN_NAME = "public_key_actor_receive";
    public static final String INCOMING_NOTIFICATION_ACTOR_RECEIVE_TYPE_COLUMN_NAME = "actor_receive_type";
    public static final String INCOMING_NOTIFICATION_TRANSMISSION_TYPE_COLUMN_NAME = "transmission_type";
    public static final String INCOMING_NOTIFICATION_TRANSMISSION_STATE_COLUMN_NAME = "transmissionstate";
    public static final String INCOMING_NOTIFICATION_NEGOTIATION_TYPE_COLUMN_NAME = "negotiation_type";
    public static final String INCOMING_NOTIFICATION_NEGOTIATION_XML_COLUMN_NAME = "negotiation_xml";
    public static final String INCOMING_NOTIFICATION_NETWORK_SERVICE_PENDING_FLAG_COLUMN_NAME = "pending_flag";
    public static final String INCOMING_NOTIFICATION_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME = "notification_protocol_state";
    public static final String INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME = "notification_read_mark";
    public static final String INCOMING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME = "response_to_notification_id";

    public static final String INCOMING_NOTIFICATION_FIRST_KEY_COLUMN = "id";

    /**
     * outgoing notification database table definition
     */
    public static final String OUTGOING_NOTIFICATION_TABLE_NAME = "outgoing_notification";
    //
    public static final String OUTGOING_NOTIFICATION_TRANSMISSION_ID_COLUMN_NAME = "transmission_id";
    public static final String OUTGOING_NOTIFICATION_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String OUTGOING_NOTIFICATION_NEGOTIATION_ID_COLUMN_NAME = "negotiation_id";
    public static final String OUTGOING_NOTIFICATION_NEGOTIATION_STATUS_COLUMN_NAME = "negotiation_status";
    public static final String OUTGOING_NOTIFICATION_NEGOTIATION_TRANSACTION_TYPE_COLUMN_NAME = "negotiation_transaction_type";
    public static final String OUTGOING_NOTIFICATION_PUBLIC_KEY_ACTOR_SEND_COLUMN_NAME = "public_key_actor_send";
    public static final String OUTGOING_NOTIFICATION_ACTOR_SEND_TYPE_COLUMN_NAME = "actor_send_type";
    public static final String OUTGOING_NOTIFICATION_PUBLIC_KEY_ACTOR_RECEIVE_COLUMN_NAME = "public_key_actor_receive";
    public static final String OUTGOING_NOTIFICATION_ACTOR_RECEIVE_TYPE_COLUMN_NAME = "actor_receive_type";
    public static final String OUTGOING_NOTIFICATION_TRANSMISSION_TYPE_COLUMN_NAME = "transmission_type";
    public static final String OUTGOING_NOTIFICATION_TRANSMISSION_STATE_COLUMN_NAME = "transmissionstate";
    public static final String OUTGOING_NOTIFICATION_NEGOTIATION_TYPE_COLUMN_NAME = "negotiation_type";
    public static final String OUTGOING_NOTIFICATION_NEGOTIATION_XML_COLUMN_NAME = "negotiation_xml";
    public static final String OUTGOING_NOTIFICATION_NETWORK_SERVICE_PENDING_FLAG_COLUMN_NAME = "pending_flag";
    public static final String OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME = "notification_protocol_state";
    public static final String OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME = "notification_read_mark";
    public static final String OUTGOING_NOTIFICATION_SENT_COUNT_COLUMN_NAME = "notification_sent_count";
    public static final String OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME = "response_to_notification_id";

    public static final String OUTGOING_NOTIFICATION_FIRST_KEY_COLUMN = "id";

}


