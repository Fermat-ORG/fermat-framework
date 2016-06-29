package org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.database.communications;

/**
 * Created by franklin on 17/10/15.
 */
public class AssetRedeemNetworkServiceDatabaseConstants {

    public static final String DATA_BASE_NAME = "ActorNetworkServiceAssetRedeemPoint";

    /**
     * incoming notifications database table definition.
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
    public static final String INCOMING_NOTIFICATION_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME = "notification_blockchain_network_type";
    public static final String INCOMING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME = "response_to_notification_id";

    public static final String INCOMING_NOTIFICATION_FIRST_KEY_COLUMN = INCOMING_NOTIFICATION_ID_COLUMN_NAME;

    /**
     * outgoing notification database table definition
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
    public static final String OUTGOING_NOTIFICATION_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME = "notification_blockchain_network_type";
    public static final String OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME = "response_to_notification_id";

    public static final String OUTGOING_NOTIFICATION_FIRST_KEY_COLUMN = OUTGOING_NOTIFICATION_ID_COLUMN_NAME;

}
