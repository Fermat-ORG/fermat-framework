package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.developer.version_1.structure.database;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/11/15.
 */
public class IssuerRedemptionDatabaseConstants {
    public static final String ASSET_ISSUER_REDEMPTION_DATABASE = "assetIssuerRedemptionDatabase";

    public static final String ASSET_ISSUER_REDEMPTION_TABLE_NAME = "asset_issuer_redemption";

    public static final String ASSET_ISSUER_REDEMPTION_GENESIS_TRANSACTION_COLUMN_NAME = "genesis_transaction";
    public static final String ASSET_ISSUER_REDEMPTION_DIGITAL_ASSET_HASH_COLUMN_NAME = "digital_asset_hash";
    public static final String ASSET_ISSUER_REDEMPTION_NETWORK_COLUMN_NAME = "networkType";
    public static final String ASSET_ISSUER_REDEMPTION_ACTOR_ASSET_USER_ID_COLUMN_NAME = "user_id";
    public static final String ASSET_ISSUER_REDEMPTION_CRYPTO_STATUS_COLUMN_NAME = "crypto_status";
    public static final String ASSET_ISSUER_REDEMPTION_PROTOCOL_STATUS_COLUMN_NAME = "protocol_status";
    public static final String ASSET_ISSUER_REDEMPTION_ACTOR_ASSET_ISSUER_BITCOIN_ADDRESS_COLUMN_NAME = "actor_issuer_bitcoin_address";
    public static final String ASSET_ISSUER_REDEMPTION_REDEMPTION_ID_COLUMN_NAME = "redemption_id";

    public static final String ASSET_ISSUER_REDEMPTION_FIRST_KEY_COLUMN = "genesis_transaction";

    /**
     * Events recorded database table definition.
     */
    public static final String ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME = "redemption_events_recorded";

    public static final String ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String ASSET_ISSUER_REDEMPTION_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "event_id";
}
