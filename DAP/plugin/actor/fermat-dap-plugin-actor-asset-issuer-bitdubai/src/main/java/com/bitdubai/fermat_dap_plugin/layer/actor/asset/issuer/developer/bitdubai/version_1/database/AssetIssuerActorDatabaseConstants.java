package com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.database;

/**
 * Created by Nerio on 16/09/15.
 */
public class AssetIssuerActorDatabaseConstants {
    /**
     * Asset Issuer database table definition.
     */
    public static final String ASSET_ISSUER_DATABASE_NAME = "AssetIssuerActor";

    public static final String ASSET_ISSUER_TABLE_NAME = "asset_issuer_actor";

    static final String ASSET_ISSUER_ISSUER_PUBLIC_KEY_COLUMN_NAME = "asset_issuer_publicKey";
    static final String ASSET_ISSUER_ISSUER_PRIVATE_KEY_COLUMN_NAME = "asset_issuer_privateKey";
    static final String ASSET_ISSUER_ISSUER_NAME_COLUMN_NAME = "asset_issuer_name";
    static final String ASSET_ISSUER_ISSUER_STATE_COLUMN_NAME = "contact_state";
    static final String ASSET_ISSUER_ISSUER_REGISTRATION_DATE_COLUMN_NAME = "registration_date";
    static final String ASSET_ISSUER_ISSUER_MODIFIED_DATE_COLUMN_NAME = "modified_date";

    static final String ASSET_ISSUER_FIRST_KEY_COLUMN = "asset_issuer_publicKey";

    /**
     * Asset Issuer relation Redeem Point associate table definition.
     */
    public static final String ASSET_ISSUER_RELATION_REDEEM_POINT_TABLE_NAME = "asset_issuer_relation_redeem_point_actor";

    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME = "asset_issuer_redeem_point_publicKey";
    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_ISSUER_NAME_COLUMN_NAME = "asset_issuer_name";
    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_NAME_COLUMN_NAME = "redeem_point_name";
    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_STATUS_COLUMN_NAME = "redeem_point_status";
    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_REGISTRATION_DATE_ISSUER_COLUMN_NAME = "redeem_point_registration_date_issuer";
    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME = "redeem_point_registration_date";
    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_MODIFIED_DATE_COLUMN_NAME = "redeem_point_modified_date";

    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_FIRST_KEY_COLUMN = "asset_issuer_redeem_point_publicKey";

    /**
     * Asset Issuer relation user table definition.
     */
    public static final String ASSET_ISSUER_RELATION_USER_TABLE_NAME = "asset_issuer_relation_user_actor";

    static final String ASSET_ISSUER_RELATION_USER_PUBLIC_KEY_COLUMN_NAME = "asset_issuer_user_publicKey";
    static final String ASSET_ISSUER_RELATION_USER_ISSUER_NAME_COLUMN_NAME = "asset_issuer_name";
    static final String ASSET_ISSUER_RELATION_USER_ID_COLUMN_NAME = "asset_user_id";
    static final String ASSET_ISSUER_RELATION_USER_NAME_COLUMN_NAME = "asset_user_name";
    static final String ASSET_ISSUER_RELATION_USER_HASH_COLUMN_NAME = "asset_user_hash";
    static final String ASSET_ISSUER_RELATION_USER_STATUS_COLUMN_NAME = "user_status";
    static final String ASSET_ISSUER_RELATION_USER_REGISTRATION_DATE_COLUMN_NAME = "user_registration_date";
    static final String ASSET_ISSUER_RELATION_USER_MODIFIED_DATE_COLUMN_NAME = "user_modified_date";
    static final String ASSET_ISSUER_RELATION_USER_TIMESTAMP_COLUMN_NAME = "asset_user_timestamp";

    static final String ASSET_ISSUER_RELATION_USER_FIRST_KEY_COLUMN = "asset_issuer_user_publicKey";
}
