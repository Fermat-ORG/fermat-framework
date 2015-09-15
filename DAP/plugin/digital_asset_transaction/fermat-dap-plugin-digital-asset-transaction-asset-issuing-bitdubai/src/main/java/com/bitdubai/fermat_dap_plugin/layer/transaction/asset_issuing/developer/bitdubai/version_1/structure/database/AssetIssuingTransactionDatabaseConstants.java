package com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.structure.database;

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
    public static final String DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_TO_GENERATE="digital_asset_assets_to_generate";
    public static final String DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_GENERATED="digital_asset_assets_generated";

    public static final String DIGITAL_ASSET_TRANSACTION_FIRST_KEY_COLUMN = "digital_asset_public_key";

    /**
     * Transition protocol status database table definition.
     */
    public static final String DIGITAL_ASSET_TRANSACTION_TRANSITION_PROTOCOL_STATUS_TABLE_NAME = "transition_protocol_status";

    public static final String DIGITAL_ASSET_TRANSACTION_TRANSITION_PROTOCOL_STATUS_TABLE_PROTOCOL_STATUS = "protocol_status";
    public static final String DIGITAL_ASSET_TRANSACTION_TRANSITION_PROTOCOL_STATUS_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String DIGITAL_ASSET_TRANSACTION_TRANSITION_PROTOCOL_STATUS_OCCURRENCES_COLUMN_NAME = "occurrences";

    public static final String DIGITAL_ASSET_TRANSACTION_TRANSITION_PROTOCOL_STATUS_FIRST_KEY_COLUMN = "protocol_status";

    /**
     * Asset Issuing database table definition.
     */
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME = "asset_issuing";

    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID="transaction_id";
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME = "digital_asset_genesis_transaction";
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_ADDRESS_COLUMN_NAME = "digital_asset_genesis_address";
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME = "digital_asset_transaction_state";
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PROTOCOL_STATUS = "digital_asset_protocol_status";
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_HASH = "digital_asset_hash";
    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PUBLIC_KEY="public_key";

    public static final String DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_FIRST_KEY_COLUMN = "transaction_id";


}