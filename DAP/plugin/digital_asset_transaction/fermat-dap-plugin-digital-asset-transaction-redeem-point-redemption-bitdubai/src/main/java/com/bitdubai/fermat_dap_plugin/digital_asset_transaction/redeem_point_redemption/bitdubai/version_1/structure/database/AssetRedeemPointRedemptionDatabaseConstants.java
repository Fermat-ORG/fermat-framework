package com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/10/15.
 */
public class AssetRedeemPointRedemptionDatabaseConstants {

    /**
     * RPR stands for RedeemPointRedemption
     * DA stands for Digital Asset.
     */

    public static final String ASSET_RPR_DATABASE = "asset_rpr_database";

    /**
     * Events recorded database table definition.
     */
    public static final String ASSET_RPR_EVENTS_RECORDED_TABLE_NAME = "rpr_events_recorded";

    public static final String ASSET_RPR_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String ASSET_RPR_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String ASSET_RPR_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String ASSET_RPR_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String ASSET_RPR_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";


    public static final String ASSET_RPR_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = ASSET_RPR_EVENTS_RECORDED_ID_COLUMN_NAME;

    /**
     * Asset RedemptionAgent database table definition.
     */
    public static final String ASSET_RPR_METADATA_TABLE_NAME = "rpr_metadata";

    public static final String ASSET_RPR_METADATA_TRANSACTION_ID_COLUMN_NAME = "rpr_transaction_id";
    public static final String ASSET_RPR_METADATA_TRANSACTION_STATUS_COLUMN_NAME = "rpr_transaction_status";
    public static final String ASSET_RPR_METADATA_TRANSACTION_CRYPTO_STATUS_COLUMN_NAME = "rpr_transaction_cryptostatus";
    public static final String ASSET_RPR_METADATA_SENDER_KEY_COLUMN_NAME = "rpr_sender_publickey";
    public static final String ASSET_RPR_METADATA_RECEIVER_KEY_COLUMN_NAME = "rpr_receiver_publickey";
    public static final String ASSET_RPR_METADATA_TIMESTAMP_COLUMN_NAME = "rpr_timestamp";

    public static final String ASSET_RPR_METADATA_TABLE_FIRST_KEY_COLUMN = ASSET_RPR_METADATA_TRANSACTION_ID_COLUMN_NAME;

}
