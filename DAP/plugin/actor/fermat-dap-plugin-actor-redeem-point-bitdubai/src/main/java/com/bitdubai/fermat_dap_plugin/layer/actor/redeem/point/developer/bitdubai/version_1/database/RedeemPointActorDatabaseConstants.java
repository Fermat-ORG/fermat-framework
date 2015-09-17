package com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.database;

/**
 * Created by Nerio on 16/09/15.
 */
//TODO DEFINICION BD PARA MEJORAR
public class RedeemPointActorDatabaseConstants {
    /**
     * Redeem Point database table definition.
     */
    public static final String REDEEM_POINT_DATABASE_NAME = "RedeemPointUserActor";

    public static final String REDEEM_POINT_TABLE_NAME = "redeem_point_actor";

    static final String REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME = "redeem_point_publicKey_actor";
    static final String REDEEM_POINT_ISSUER_NAME_COLUMN_NAME = "redeem_point_name_actor";
    static final String REDEEM_POINT_STATUS_COLUMN_NAME = "redeem_point_status_actor";
    static final String REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME = "redeem_point_registration_date_actor";
    static final String REDEEM_POINT_MODIFIED_DATE_COLUMN_NAME = "redeem_point_modified_date_actor";

    static final String REDEEM_POINT_FIRST_KEY_COLUMN = "redeem_point_publicKey_actor";

    /**
     * Redeem Point Relation Issuer database table definition.
     */
    public static final String REDEEM_POINT_RELATION_ISSUER_TABLE_NAME = "redeem_point_relation_issuer_actor";

    static final String REDEEM_POINT_RELATION_ISSUER_REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME = "redeem_point_publicKey_actor";
    static final String REDEEM_POINT_RELATION_ISSUER_PUBLIC_KEY_COLUMN_NAME = "redeem_point_asset_issuer_publicKey_actor";
    static final String REDEEM_POINT_RELATION_ISSUER_NAME_COLUMN_NAME = "redeem_point_asset_issuer_name_actor";
    static final String REDEEM_POINT_RELATION_ISSUER_STATUS_COLUMN_NAME = "redeem_point_issuer_status_actor";
    static final String REDEEM_POINT_RELATION_ISSUER_ASSETS_COUNT_COLUMN_NAME = "redeem_point_issuer_assets_count_actor";
    static final String REDEEM_POINT_RELATION_ISSUER_REGISTRATION_DATE_COLUMN_NAME = "redeem_point_registration_date_issuer_actor";
    static final String REDEEM_POINT_RELATION_ISSUER_REDEEM_POINT_NAME_COLUMN_NAME = "redeem_point_name_actor";
    static final String REDEEM_POINT_RELATION_ISSUER_REDEEM_POINT_STATUS_COLUMN_NAME = "redeem_point_status_actor";
    static final String REDEEM_POINT_RELATION_ISSUER_REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME = "redeem_point_registration_date_actor";
    static final String REDEEM_POINT_RELATION_ISSUER_REDEEM_POINT_MODIFIED_DATE_COLUMN_NAME = "redeem_point_modified_date_actor";

    static final String REDEEM_POINT_RELATION_ISSUER_ASSET_FIRST_KEY_COLUMN = "redeem_point_asset_issuer_publicKey_actor";

    /**
     * Redeem Point Relation Aseet User database table definition.
     */
    public static final String REDEEM_POINT_RELATION_ASSET_USER_TABLE_NAME = "redeem_point_relation_asset_user_actor";

    static final String REDEEM_POINT_RELATION_ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME = "asset_user_publicKey";
    static final String REDEEM_POINT_RELATION_ASSET_USER_RELATION_ISSUER_NAME_COLUMN_NAME = "asset_issuer_name_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_ISSUER_IDENTITY_COLUMN_NAME = "asset_issuer_identity_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_USER_NAME_COLUMN_NAME = "asset_user_name_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_USER_SEX_COLUMN_NAME = "asset_user_sex_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_USER_UBICACION_COLUMN_NAME = "asset_user_ubicacion_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_USER_AGE_COLUMN_NAME = "asset_user_age_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_ASSET_DESCRIPTION_COLUMN_NAME = "asset_user_description_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_ASSET_NAME_COLUMN_NAME = "asset_user_asset_name_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_ASSET_ID_COLUMN_NAME = "asset_user_asset_id_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_ASSET_HASH_COLUMN_NAME = "asset_user_asset_hash_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_ASSET_STATUS_COLUMN_NAME = "asset_user_asset_status_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_ASSET_RESOURCES_COLUMN_NAME = "asset_user_asset_resources_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_ASSET_AMOUNT_COLUMN_NAME = "asset_user_asset_amount_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_ASSET_CURRENCY_COLUMN_NAME = "asset_user_asset_currency_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_ASSET_EXPIRATION_DATE_COLUMN_NAME = "asset_expiration_date_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_REDEEMPTION_TIMESTAMP_COLUMN_NAME = "asset_user_redeemption_timestamp_actor";
    static final String REDEEM_POINT_RELATION_ASSET_USER_REDEEMPTION_DATE_COLUMN_NAME = "asset_user_redeemption_date_actor";

    static final String REDEEM_POINT_RELATION_ASSET_USER_FIRST_KEY_COLUMN = "asset_user_publicKey_actor";
}
