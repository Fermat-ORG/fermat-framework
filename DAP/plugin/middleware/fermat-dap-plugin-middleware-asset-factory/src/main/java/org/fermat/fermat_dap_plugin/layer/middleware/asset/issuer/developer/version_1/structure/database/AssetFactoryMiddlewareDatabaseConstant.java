package org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database;

/**
 * Created by franklin on 08/09/15.
 */
public class AssetFactoryMiddlewareDatabaseConstant {
    /**
     * Database name definition
     */
    public static final String DATABASE_NAME = "asset_factory";

    /**
     * Asset Factory database table definition.
     */

    public static final String ASSET_FACTORY_TABLE_NAME = "asset";

    public static final String ASSET_FACTORY_ID_COLUMN = "factory_id";
    public static final String ASSET_FACTORY_ASSET_PUBLIC_KEY_COLUMN = "asset_public_key";
    public static final String ASSET_FACTORY_NAME_COLUMN = "name";
    public static final String ASSET_FACTORY_QUANTITY_COLUMN = "quantity";
    public static final String ASSET_FACTORY_TOTAL_QUANTITY_COLUMN = "total_quantity";
    public static final String ASSET_FACTORY_AMOUNT_COLUMN = "amount";
    public static final String ASSET_FACTORY_FEE_COLUMN = "fee";
    public static final String ASSET_FACTORY_STATE_COLUMN = "state";
    public static final String ASSET_FACTORY_DESCRIPTION_COLUMN = "description";
    public static final String ASSET_FACTORY_ISSUER_IDENTITY_PUBLIC_KEY_COLUMN = "issuer_identity_public_key";
    public static final String ASSET_FACTORY_ISSUER_IDENTITY_ALIAS_COLUMN = "alias";
    public static final String ASSET_FACTORY_ISSUER_IDENTITY_SIGNATURE_COLUMN = "signature";
    public static final String ASSET_FACTORY_CREATION_TIME_COLUMN = "creation_time";
    public static final String ASSET_FACTORY_LAST_UPDATE_TIME_COLUMN = "last_update_time";
    public static final String ASSET_FACTORY_ASSET_BEHAVIOR_COLUMN = "asset_behavior";
    public static final String ASSET_FACTORY_IS_REDEEMABLE = "is_redeemable";
    public static final String ASSET_FACTORY_IS_TRANSFERABLE = "is_transfer";
    public static final String ASSET_FACTORY_IS_EXCHANGEABLE = "is_exchangeable";
    public static final String ASSET_FACTORY_EXPIRATION_DATE = "expiration_date";
    public static final String ASSET_FACTORY_ASSET_WALLET_PUBLIC_KEY = "wallet_public_key";
    public static final String ASSET_FACTORY_NETWORK_TYPE = "networkType";

    public static final String ASSET_FACTORY_FIRST_KEY_COLUMN = ASSET_FACTORY_ID_COLUMN;

    /**
     * Asset Factory Resource database table definition.
     */

    public static final String ASSET_FACTORY_RESOURCE_TABLE_NAME = "asset_factory_resource";

    public static final String ASSET_FACTORY_RESOURCE_ID_COLUMN = "id";
    public static final String ASSET_FACTORY_RESOURCE_ASSET_PUBLIC_KEY_COLUMN = "asset_public_key";
    public static final String ASSET_FACTORY_RESOURCE_NAME_COLUMN = "name";
    public static final String ASSET_FACTORY_RESOURCE_FILE_NAME_COLUMN = "file_name";
    public static final String ASSET_FACTORY_RESOURCE_RESOURCE_TYPE_COLUMN = "resource_type";
    public static final String ASSET_FACTORY_RESOURCE_RESOURCE_DENSITY_COLUMN = "resource_density";
    public static final String ASSET_FACTORY_RESOURCE_PATH_COLUMN = "path";

    public static final String ASSET_FACTORY_RESOURCE_FIRST_KEY_COLUMN = ASSET_FACTORY_RESOURCE_ID_COLUMN;

    /**
     * Asset Factory Contract database table definition.
     */

    public static final String ASSET_FACTORY_CONTRACT_TABLE_NAME = "asset_factory_contract";

    public static final String ASSET_FACTORY_CONTRACT_ID_COLUMN = "id";
    public static final String ASSET_FACTORY_CONTRACT_ASSET_PUBLIC_KEY_COLUMN = "asset_public_key";
    public static final String ASSET_FACTORY_CONTRACT_NAME_COLUMN = "name_contract";
    public static final String ASSET_FACTORY_CONTRACT_VALUE_COLUMN = "value_contract";

    public static final String ASSET_FACTORY_CONTRACT_FIRST_KEY_COLUMN = ASSET_FACTORY_CONTRACT_ID_COLUMN;

    /**
     * Asset Factory Identity Issuer database table definition.
     */
    public static final String ASSET_FACTORY_IDENTITY_ISSUER_TABLE_NAME = "asset_factory_identiy_issuer";

    public static final String ASSET_FACTORY_IDENTITY_ID_COLUMN = "id";
    public static final String ASSET_FACTORY_IDENTITY_ISSUER_PUBLIC_KEY_COLUMN = "public_key";
    public static final String ASSET_FACTORY_IDENTITY_ISSUER_ASSET_PUBLIC_KEY_COLUMN = "asset_public_key";
    public static final String ASSET_FACTORY_IDENTITY_ISSUER_NAME_COLUMN = "name";
    public static final String ASSET_FACTORY_IDENTITY_ISSUER_SIGNATURE_COLUMN = "signature";

    public static final String ASSET_FACTORY_IDENTITY_ISSUER_FIRST_KEY_COLUMN = ASSET_FACTORY_IDENTITY_ID_COLUMN;

}
