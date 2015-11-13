package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.database;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15.
 */
public class AssetAppropriationDatabaseConstants {

    //VARIABLE DECLARATION

    /*
     * AA stands for Asset Appropriation.
     */
    public static final String ASSET_APPROPRIATION_DATABASE = "asset_appropriation_database";

    /*
     * Events recorded database table definition.
     */
    public static final String ASSET_APPROPRIATION_EVENTS_RECORDED_TABLE_NAME = "aa_events_recorded";

    public static final String ASSET_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String ASSET_APPROPRIATION_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String ASSET_APPROPRIATION_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String ASSET_APPROPRIATION_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String ASSET_APPROPRIATION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String ASSET_APPROPRIATION_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = ASSET_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME;

    /*
     * Transaction Metadata database table definition.
     */
    public static final String ASSET_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME = "aa_transaction_metadata";

    public static final String ASSET_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME = "record_id";
    public static final String ASSET_APPROPRIATION_TRANSACTION_METADATA_STATUS_COLUMN_NAME = "status";
    public static final String ASSET_APPROPRIATION_TRANSACTION_METADATA_DA_PUBLIC_KEY_COLUMN_NAME = "da_public_key"; //The path to the file where the Digital Asset is stored.
    public static final String ASSET_APPROPRIATION_TRANSACTION_METADATA_USER_WALLET_KEY_TO_COLUMN_NAME = "user_wallet";
    public static final String ASSET_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_ADDRESS_TO_COLUMN_NAME = "addressto";
    public static final String ASSET_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_CURRENCY_TO_COLUMN_NAME = "currencyto";
    public static final String ASSET_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME = "start_time";
    public static final String ASSET_APPROPRIATION_TRANSACTION_METADATA_END_TIME_COLUMN_NAME = "end_time";
    public static final String ASSET_APPROPRIATION_TRANSACTION_METADATA_GENESIS_COLUMN_NAME = "genesis_transaction";

    public static final String ASSET_APPROPRIATION_TRANSACTION_METADATA_TABLE_FIRST_KEY_COLUMN = ASSET_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME;

    //CONSTRUCTORS

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
