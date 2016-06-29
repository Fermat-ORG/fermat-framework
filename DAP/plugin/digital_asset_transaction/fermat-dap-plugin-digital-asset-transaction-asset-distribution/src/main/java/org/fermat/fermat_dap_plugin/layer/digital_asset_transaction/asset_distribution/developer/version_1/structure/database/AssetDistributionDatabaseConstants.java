package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/09/15.
 */
public class AssetDistributionDatabaseConstants {

    public static final String ASSET_DISTRIBUTION_DATABASE = "asset_distribution_database";
    /**
     * Asset Distribution database table definition.
     */
    public static final String ASSET_DISTRIBUTION_TABLE_NAME = "asset_distribution";

    public static final String ASSET_DISTRIBUTION_GENESIS_TRANSACTION_COLUMN_NAME = "genesis_transaction";
    public static final String ASSET_DISTRIBUTION_DIGITAL_ASSET_HASH_COLUMN_NAME = "digital_asset_hash";
    public static final String ASSET_DISTRIBUTION_ACTOR_ASSET_USER_PUBLIC_KEY_COLUMN_NAME = "actor_asset_user_public_key";
    public static final String ASSET_DISTRIBUTION_DIGITAL_ASSET_STORAGE_LOCAL_PATH_COLUMN_NAME = "digital_asset_storage_local_path";
    public static final String ASSET_DISTRIBUTION_DISTRIBUTION_STATUS_COLUMN_NAME = "distribution_status";
    public static final String ASSET_DISTRIBUTION_CRYPTO_STATUS_COLUMN_NAME = "crypto_status";
    public static final String ASSET_DISTRIBUTION_PROTOCOL_STATUS_COLUMN_NAME = "protocol_status";
    public static final String ASSET_DISTRIBUTION_ACTOR_ASSET_USER_BITCOIN_ADDRESS_COLUMN_NAME = "actor_user_bitcoin_address";
    public static final String ASSET_DISTRIBUTION_DISTRIBUTION_ID_COLUMN_NAME = "distribution_id";

    public static final String ASSET_DISTRIBUTION_FIRST_KEY_COLUMN = ASSET_DISTRIBUTION_GENESIS_TRANSACTION_COLUMN_NAME;

    /**
     * Events recorded database table definition.
     */
    public static final String ASSET_DISTRIBUTION_EVENTS_RECORDED_TABLE_NAME = "distribution_events_recorded";

    public static final String ASSET_DISTRIBUTION_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String ASSET_DISTRIBUTION_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String ASSET_DISTRIBUTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String ASSET_DISTRIBUTION_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String ASSET_DISTRIBUTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String ASSET_DISTRIBUTION_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "event_id";

    /**
     * Delivering Asset database table definition
     */
    public static final String ASSET_DISTRIBUTION_DELIVERING_TABLE_NAME = "delivering";

    public static final String ASSET_DISTRIBUTION_DELIVERING_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String ASSET_DISTRIBUTION_DELIVERING_GENESIS_TRANSACTION_COLUMN_NAME = "genesis_transaction";
    public static final String ASSET_DISTRIBUTION_DELIVERING_NETWORK_TYPE_COLUMN_NAME = "networkType";
    public static final String ASSET_DISTRIBUTION_DELIVERING_ASSET_PUBLICKEY_COLUMN_NAME = "asset_publickey";
    public static final String ASSET_DISTRIBUTION_DELIVERING_USER_PUBLICKEY_COLUMN_NAME = "user_publickey";
    public static final String ASSET_DISTRIBUTION_DELIVERING_START_TIME_COLUMN_NAME = "start_time";
    public static final String ASSET_DISTRIBUTION_DELIVERING_TIMEOUT_COLUMN_NAME = "timeout";
    public static final String ASSET_DISTRIBUTION_DELIVERING_STATE_COLUMN_NAME = "delivering_state";
    public static final String ASSET_DISTRIBUTION_DELIVERING_SENT_GENESISTX_COLUMN_NAME = "sent_genesis_tx";
    public static final String ASSET_DISTRIBUTION_DELIVERING_ATTEMPT_NUMBER_COLUMN_NAME = "attempt_number";

    public static final String ASSET_DISTRIBUTION_DELIVERING_TABLE_FIRST_KEY_COLUMN = ASSET_DISTRIBUTION_DELIVERING_TRANSACTION_ID_COLUMN_NAME;

}
