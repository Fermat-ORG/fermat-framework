package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.database;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 12/10/15.
 */
public class AssetReceptionDatabaseConstants {
    public static final String ASSET_RECEPTION_DATABASE = "assetReceptionDatabase";
    /**
     * Asset reception database table definition.
     */
    public static final String ASSET_RECEPTION_TABLE_NAME = "asset_reception";

    public static final String ASSET_RECEPTION_GENESIS_TRANSACTION_COLUMN_NAME = "genesis_transaction";
    public static final String ASSET_RECEPTION_DIGITAL_ASSET_HASH_COLUMN_NAME = "digital_asset_hash";
    public static final String ASSET_RECEPTION_SENDER_ID_COLUMN_NAME = "sender_id";
    public static final String ASSET_RECEPTION_SENDER_TYPE_COLUMN_NAME = "sender_type";
    public static final String ASSET_RECEPTION_DIGITAL_ASSET_STORAGE_LOCAL_PATH_COLUMN_NAME = "digital_asset_storage_local_path";
    public static final String ASSET_RECEPTION_RECEPTION_STATUS_COLUMN_NAME = "reception_status";
    public static final String ASSET_RECEPTION_CRYPTO_STATUS_COLUMN_NAME = "crypto_status";
    public static final String ASSET_RECEPTION_PROTOCOL_STATUS_COLUMN_NAME = "protocol_status";
    public static final String ASSET_RECEPTION_ACTOR_ASSET_ISSUER_BITCOIN_ADDRESS_COLUMN_NAME = "actor_issuer_bitcoin_address";
    public static final String ASSET_RECEPTION_RECEPTION_ID_COLUMN_NAME = "reception_id";

    public static final String ASSET_RECEPTION_FIRST_KEY_COLUMN = "genesis_transaction";

    /**
     * Events recorded database table definition.
     */
    public static final String ASSET_RECEPTION_EVENTS_RECORDED_TABLE_NAME = "reception_events_recorded";

    public static final String ASSET_RECEPTION_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String ASSET_RECEPTION_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String ASSET_RECEPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String ASSET_RECEPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String ASSET_RECEPTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String ASSET_RECEPTION_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "event_id";

    /**
     * receiving Asset database table definition
     */
    public static final String ASSET_RECEPTION_RECEIVING_TABLE_NAME = "receiving";

    public static final String ASSET_RECEPTION_RECEIVING_MESSAGE_ID_COLUMN_NAME = "message_id";
    public static final String ASSET_RECEPTION_RECEIVING_GENESIS_TRANSACTION_COLUMN_NAME = "receiving_genesis_transaction";
    public static final String ASSET_RECEPTION_RECEIVING_MESSAGE_TYPE_COLUMN_NAME = "message_type";
    public static final String ASSET_RECEPTION_RECEIVING_TIMESTAMP_COLUMN_NAME = "receiving_timestamp";
    public static final String ASSET_RECEPTION_RECEIVING_EVENT_ID_COLUMN_NAME = "receiving_event_id";

    public static final String ASSET_RECEPTION_RECEIVING_TABLE_FIRST_KEY_COLUMN = "message_id";
}
