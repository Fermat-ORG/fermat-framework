package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database;

/**
 * The Class <code>com.bitdubai.fermat_dap_api.layer.transaction.asset_issuing.developer.bitdubai.version_1.database.AssetIssuingTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Manuel Perez - (darkpriestrelative@gmail.com) on 08/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetIssuingTransactionDatabaseConstants {

    public static final String DIGITAL_ASSET_TRANSACTION_DATABASE = "digital_asset_database";
    /**
     * Digital Asset Transaction database table definition.
     */
    public static final String DIGITAL_ASSET_TRANSACTION_TABLE_NAME = "digital_asset_transaction";

    public static final String DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME = "digital_asset_public_key";
    public static final String DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_LOCAL_STORAGE_PATH_COLUMN_NAME = "digital_asset_local_storage_path";
    public static final String DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_TO_GENERATE_COLUMN_NAME ="digital_asset_assets_to_generate";
    public static final String DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_GENERATED_COLUMN_NAME ="digital_asset_assets_generated";
    public static final String DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME="blockchain_network_type";
    public static final String DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_WALLET_PUBLIC_KEY_COLUMN_NAME="wallet_public_key";
    public static final String DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ISSUING_STATUS_COLUMN_NAME="issuing_status";

    public static final String DIGITAL_ASSET_TRANSACTION_FIRST_KEY_COLUMN = "digital_asset_public_key";

    /**
     * Events recorded database table definition.
     */
    public static final String DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TABLE_NAME = "issuing_events_recorded";

    public static final String DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_ID_COLUMN = "event_id";
    public static final String DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_EVENT_COLUMN = "event";
    public static final String DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_SOURCE_COLUMN = "source";
    public static final String DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_STATUS_COLUMN = "status";
    public static final String DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TIMESTAMP_COLUMN = "timestamp";

    public static final String DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "event_id";

    /**
     * Asset Issuing database table definition.
     */
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME = "asset_issuing_table";

    //Es posible que elimine este id en un futuro, por ahora lo voy a usar como index de la tabla.
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID_COLUMN_NAME ="transaction_id";
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME = "digital_asset_genesis_transaction";
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_BLOCK_COLUMN_NAME = "digital_asset_genesis_block";
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_ADDRESS_COLUMN_NAME = "digital_asset_genesis_address";
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME = "digital_asset_transaction_state";
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_CRYPTO_STATUS_COLUMN_NAME = "digital_asset_crypto_status";
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PROTOCOL_STATUS_COLUMN_NAME = "digital_asset_protocol_status";
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_DIGITAL_ASSET_HASH_COLUMN_NAME = "digital_asset_hash";
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PUBLIC_KEY_COLUMN_NAME ="public_key";
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_OUTGOING_ID_COLUMN_NAME ="outgoing_id";

    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_FIRST_KEY_COLUMN = "transaction_id";


}