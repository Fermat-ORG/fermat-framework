package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.structure.database;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/11/15.
 */
public class UserRedemptionDatabaseConstants {

    public static final String USER_REDEMPTION_DATABASE = "user_redemption_database";
    /**
     * Asset Distribution database table definition.
     */
    public static final String USER_REDEMPTION_TABLE_NAME = "asset_redeemption";

    public static final String USER_REDEMPTION_GENESIS_TRANSACTION_COLUMN_NAME = "genesis_transaction";
    public static final String USER_REDEMPTION_DIGITAL_ASSET_HASH_COLUMN_NAME = "digital_asset_hash";
    public static final String USER_REDEMPTION_ACTOR_REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME = "actor_redeem_point_public_key";
    public static final String USER_REDEMPTION_DIGITAL_ASSET_STORAGE_LOCAL_PATH_COLUMN_NAME = "digital_asset_storage_local_path";
    public static final String USER_REDEMPTION_REDEMPTION_STATUS_COLUMN_NAME = "redemption_status";
    public static final String USER_REDEMPTION_CRYPTO_STATUS_COLUMN_NAME = "crypto_status";
    public static final String USER_REDEMPTION_PROTOCOL_STATUS_COLUMN_NAME = "protocol_status";
    public static final String USER_REDEMPTION_ACTOR_REDEEM_POINT_BITCOIN_ADDRESS_COLUMN_NAME = "redeem_point_bitcoin_address";
    public static final String USER_REDEMPTION_REDEMPTION_ID_COLUMN_NAME = "redemption_id";

    public static final String USER_REDEMPTION_FIRST_KEY_COLUMN = "genesis_transaction";

    /**
     * Events recorded database table definition.
     */
    public static final String USER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME = "redeemption_events_recorded";

    public static final String USER_REDEMPTION_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String USER_REDEMPTION_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String USER_REDEMPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String USER_REDEMPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String USER_REDEMPTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String USER_REDEMPTION_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "event_id";

    /**
     * Delivering Asset database table definition
     */
    public static final String USER_REDEMPTION_DELIVERING_TABLE_NAME = "delivering";

    public static final String USER_REDEMPTION_DELIVERING_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String USER_REDEMPTION_DELIVERING_GENESIS_TRANSACTION_COLUMN_NAME = "delivering_genesis_transaction";
    public static final String USER_REDEMPTION_DELIVERING_NETWORK_TYPE_COLUMN_NAME = "networkType";
    public static final String USER_REDEMPTION_DELIVERING_ASSET_PUBLICKEY_COLUMN_NAME = "asset_publickey";
    public static final String USER_REDEMPTION_DELIVERING_REPO_PUBLICKEY_COLUMN_NAME = "repo_publickey";
    public static final String USER_REDEMPTION_DELIVERING_START_TIME_COLUMN_NAME = "start_time";
    public static final String USER_REDEMPTION_DELIVERING_TIMEOUT_COLUMN_NAME = "timeut";
    public static final String USER_REDEMPTION_DELIVERING_STATE_COLUMN_NAME = "state";
    public static final String USER_REDEMPTION_DELIVERING_SENT_GENESISTX_COLUMN_NAME = "sent_genesistx";
    public static final String USER_REDEMPTION_DELIVERING_ATTEMPT_NUMBER_COLUMN_NAME = "attempt_number";

    public static final String USER_REDEMPTION_DELIVERING_TABLE_FIRST_KEY_COLUMN = USER_REDEMPTION_DELIVERING_TRANSACTION_ID_COLUMN_NAME;

}
