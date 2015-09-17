package com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.database;

/**
 * Created by Nerio on 16/09/15.
 */
//TODO DEFINICION BD PARA MEJORAR
public class AssetIssuerActorDatabaseConstants {
    /**
     * Asset Issuer database table definition.
     */
    public static final String ASSET_ISSUER_DATABASE_NAME = "AssetIssuerActor";

    public static final String ASSET_ISSUER_TABLE_NAME = "asset_issuer_actor";

    static final String ASSET_ISSUER_ISSUER_PUBLIC_KEY_COLUMN_NAME = "asset_issuer_publicKey_actor";
    static final String ASSET_ISSUER_ISSUER_PRIVATE_KEY_COLUMN_NAME = "asset_issuer_privateKey_actor";
    static final String ASSET_ISSUER_ISSUER_NAME_COLUMN_NAME = "asset_issuer_name_actor";
    static final String ASSET_ISSUER_ISSUER_STATE_COLUMN_NAME = "contact_state_actor";
    static final String ASSET_ISSUER_ISSUER_REGISTRATION_DATE_COLUMN_NAME = "registration_date_actor";
    static final String ASSET_ISSUER_ISSUER_MODIFIED_DATE_COLUMN_NAME = "modified_date_actor";

    static final String ASSET_ISSUER_FIRST_KEY_COLUMN = "asset_issuer_publicKey_actor";

    /**
     * Asset Issuer relation Redeem Point Associate table definition.
     */
    public static final String ASSET_ISSUER_RELATION_REDEEM_POINT_TABLE_NAME = "asset_issuer_relation_redeem_point_actor";

    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME = "asset_issuer_redeem_point_publicKey_actor";
    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_ISSUER_NAME_COLUMN_NAME = "asset_issuer_name_actor";
    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_NAME_COLUMN_NAME = "redeem_point_name_actor";
    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_STATUS_COLUMN_NAME = "redeem_point_status_actor";
    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_ASSETS_COUNT_COLUMN_NAME = "redeem_point_assets_count_actor";
    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_REGISTRATION_DATE_ISSUER_COLUMN_NAME = "redeem_point_registration_date_issuer_actor";
    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME = "redeem_point_registration_date_actor";
    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_MODIFIED_DATE_COLUMN_NAME = "redeem_point_modified_date_actor_actor";

    static final String ASSET_ISSUER_RELATION_REDEEM_POINT_FIRST_KEY_COLUMN = "asset_issuer_redeem_point_publicKey_actor";

    /**
     * Asset Issuer relation Asset User table definition.
     */
    public static final String ASSET_ISSUER_RELATION_USER_TABLE_NAME = "asset_issuer_relation_user_actor";

    static final String ASSET_ISSUER_RELATION_USER_PUBLIC_KEY_COLUMN_NAME = "asset_issuer_user_publicKey_actor";
    static final String ASSET_ISSUER_RELATION_USER_ISSUER_NAME_COLUMN_NAME = "asset_issuer_name_actor";
    static final String ASSET_ISSUER_RELATION_USER_ASSET_ID_COLUMN_NAME = "asset_user_asset_id_actor";
    static final String ASSET_ISSUER_RELATION_USER_ASSET_NAME_COLUMN_NAME = "asset_user_asset_name_actor";
    static final String ASSET_ISSUER_RELATION_USER_ASSET_HASH_COLUMN_NAME = "asset_user_asset_hash_actor";
    static final String ASSET_ISSUER_RELATION_USER_ASSET_STATUS_COLUMN_NAME = "asset_user_asset_status_actor";
    static final String ASSET_ISSUER_RELATION_USER_ASSET_RESOURCES_COLUMN_NAME = "asset_user_asset_resource_actor";
    static final String ASSET_ISSUER_RELATION_USER_ASSET_AMOUNT_COLUMN_NAME = "asset_user_asset_amount_actor";
    static final String ASSET_ISSUER_RELATION_USER_ASSET_CURRENCY_COLUMN_NAME = "asset_user_asset_currency_actor";
    static final String ASSET_ISSUER_RELATION_USER_ASSET_EXPIRATION_DATE_COLUMN_NAME = "asset_expiration_date_actor";
    static final String ASSET_ISSUER_RELATION_USER_STATUS_COLUMN_NAME = "user_status_actor";
    static final String ASSET_ISSUER_RELATION_USER_REGISTRATION_DATE_COLUMN_NAME = "user_registration_date_actor";
    static final String ASSET_ISSUER_RELATION_USER_MODIFIED_DATE_COLUMN_NAME = "user_modified_date_actor";
    static final String ASSET_ISSUER_RELATION_USER_TIMESTAMP_COLUMN_NAME = "asset_user_timestamp_actor";

    static final String ASSET_ISSUER_RELATION_USER_FIRST_KEY_COLUMN = "asset_issuer_user_publicKey_actor";
}
