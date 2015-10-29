package com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.database;

/**
 * Created by franklin on 08/09/15.
 */
public class AssertFactoryMiddlewareDatabaseConstant {
    /**
     * Database name definition
     */
    public static final String DATABASE_NAME = "asset_factory";

    /**
     * Asset Factory database table definition.
     */

    static final String ASSET_FACTORY_TABLE_NAME = "asset";

    public static final String ASSET_FACTORY_ASSET_PUBLIC_KEY_COLUMN = "asset_public_key";
    static final String ASSET_FACTORY_NAME_COLUMN = "name";
    static final String ASSET_FACTORY_DESCRIPTION_COLUMN = "description";
    public static final String ASSET_FACTORY_ISSUER_IDENTITY_PUBLIC_KEY_COLUMN = "issuer_identity_public_key";
    public static final String ASSET_FACTORY_ISSUER_IDENTITY_ALIAS_COLUMN = "alias";
    public static final String ASSET_FACTORY_ISSUER_IDENTITY_SIGNATURE_COLUMN = "signature";
    public static final String ASSET_FACTORY_STATE_COLUMN = "state";
    static final String ASSET_FACTORY_AMOUNT_COLUMN = "amount";
    static final String ASSET_FACTORY_QUANTITY_COLUMN = "quantity";
    static final String ASSET_FACTORY_FEE_COLUMN = "fee";
    static final String ASSET_FACTORY_CREATION_TIME_COLUMN = "creation_time";
    static final String ASSET_FACTORY_LAST_UPDATE_TIME_COLUMN = "last_update_time";
    static final String ASSET_FACTORY_ASSET_BEHAVIOR_COLUMN = "asset_behavior";
    static final String ASSET_FACTORY_ASSET_WALLET_PUBLIC_KEY = "wallet_public_key";
    static final String ASSET_FACTORY_EXPIRATION_DATE = "expiration_date";
    static final String ASSET_FACTORY_IS_REDEEMABLE = "is_redeemable";
    static final String ASSET_FACTORY_FIRST_KEY_COLUMN = "asset_public_key";

    /**
     * Asset Factory Resource database table definition.
     */

    static final String ASSET_FACTORY_RESOURCE_TABLE_NAME = "asset_factory_resource";

    static final String ASSET_FACTORY_RESOURCE_ID_COLUMN = "id";
    static final String ASSET_FACTORY_RESOURCE_ASSET_PUBLIC_KEY_COLUMN = "asset_public_key";
    static final String ASSET_FACTORY_RESOURCE_NAME_COLUMN = "name";
    static final String ASSET_FACTORY_RESOURCE_FILE_NAME_COLUMN = "file_name";
    static final String ASSET_FACTORY_RESOURCE_RESOURCE_TYPE_COLUMN = "resource_type";
    static final String ASSET_FACTORY_RESOURCE_RESOURCE_DENSITY_COLUMN = "resource_density";
    static final String ASSET_FACTORY_RESOURCE_PATH_COLUMN = "path";

    static final String ASSET_FACTORY_RESOURCE_FIRST_KEY_COLUMN = "id";

    /**
     * Asset Factory Contract database table definition.
     */

    static final String ASSET_FACTORY_CONTRACT_TABLE_NAME = "asset_factory_contract";

    //static final String ASSET_FACTORY_CONTRACT_ID_COLUMN = "id";
    static final String ASSET_FACTORY_CONTRACT_ASSET_PUBLIC_KEY_COLUMN = "asset_public_key";
    static final String ASSET_FACTORY_CONTRACT_NAME_COLUMN = "name";
    static final String ASSET_FACTORY_CONTRACT_VALUE_COLUMN = "value";

    static final String ASSET_FACTORY_CONTRACT_FIRST_KEY_COLUMN = "name";

    /**
     * Asset Factory Identity Issuer database table definition.
     */
    static final String ASSET_FACTORY_IDENTITY_ISSUER_TABLE_NAME = "asset_factory_identiy_issuer";

    static final String ASSET_FACTORY_IDENTITY_ISSUER_PUBLIC_KEY_COLUMN = "public_key";
    static final String ASSET_FACTORY_IDENTITY_ISSUER_ASSET_PUBLIC_KEY_COLUMN = "asset_public_key";
    static final String ASSET_FACTORY_IDENTITY_ISSUER_NAME_COLUMN = "name";
    static final String ASSET_FACTORY_IDENTITY_ISSUER_SIGNATURE_COLUMN = "signature";

    static final String ASSET_FACTORY_IDENTITY_ISSUER_FIRST_KEY_COLUMN = "public_key";

}
