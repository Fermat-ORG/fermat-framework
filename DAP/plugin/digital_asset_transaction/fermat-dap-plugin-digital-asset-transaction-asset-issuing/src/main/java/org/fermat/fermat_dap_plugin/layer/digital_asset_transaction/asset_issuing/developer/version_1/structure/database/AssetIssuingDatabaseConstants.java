package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database;

/**
 * The Class <code>com.bitdubai.fermat_dap_api.layer.transaction.asset_issuing.developer.bitdubai.version_1.database.AssetIssuingTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Manuel Perez - (darkpriestrelative@gmail.com) on 08/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetIssuingDatabaseConstants {

    public static final String ASSET_ISSUING_DATABASE = "asset_issuing";
    /**
     * Digital Asset Transaction database table definition.
     */
    public static final String ASSET_ISSUING_TABLE_NAME = "asset_issuing";

    public static final String ASSET_ISSUING_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME = "digital_asset_public_key";
    public static final String ASSET_ISSUING_ASSETS_TO_GENERATE_COLUMN_NAME = "digital_asset_assets_to_generate";
    public static final String ASSET_ISSUING_ASSETS_COMPLETED_COLUMN_NAME = "digital_asset_assets_generated";
    public static final String ASSET_ISSUING_ASSETS_PROCESSED_COLUMN_NAME = "digital_asset_assets_processed";
    public static final String ASSET_ISSUING_NETWORK_TYPE_COLUMN_NAME = "blockchain_network_type";
    public static final String ASSET_ISSUING_BTC_WALLET_PUBLIC_KEY_COLUMN_NAME = "btc_wallet_public_key";
    public static final String ASSET_ISSUING_ISSUER_WALLET_PUBLIC_KEY_COLUMN_NAME = "issuer_wallet_public_key";
    public static final String ASSET_ISSUING_ISSUING_STATUS_COLUMN_NAME = "issuing_status";
    public static final String ASSET_ISSUING_CREATION_DATE_COLUMN_NAME = "creation_date";
    public static final String ASSET_ISSUING_PROCESSING_COLUMN_NAME = "processing";

    public static final String ASSET_ISSUING_FIRST_KEY_COLUMN = ASSET_ISSUING_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME;

    /**
     * Events recorded database table definition.
     */
    public static final String ASSET_ISSUING_EVENTS_RECORDED_TABLE_NAME = "issuing_events_recorded";

    public static final String ASSET_ISSUING_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String ASSET_ISSUING_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String ASSET_ISSUING_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String ASSET_ISSUING_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String ASSET_ISSUING_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String ASSET_ISSUING_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN_NAME = ASSET_ISSUING_EVENTS_RECORDED_ID_COLUMN_NAME;

    /**
     * Asset Issuing database table definition.
     */
    public static final String ASSET_ISSUING_METADATA_TABLE = "asset_metadata";

    //Es posible que elimine este id en un futuro, por ahora lo voy a usar como index de la tabla.

    public static final String ASSET_ISSUING_METADATA_ID_COLUMN_NAME = "transaction_id";
    public static final String ASSET_ISSUING_METADATA_OUTGOING_ID_COLUMN_NAME = "outgoing_id";
    public static final String ASSET_ISSUING_METADATA_GENESIS_TRANSACTION_COLUMN_NAME = "genesis_transaction";
    public static final String ASSET_ISSUING_METADATA_ISSUING_STATUS_COLUMN_NAME = "issuing_status";
    public static final String ASSET_ISSUING_METADATA_CREATION_TIME_COLUMN_NAME = "creation_time";

}