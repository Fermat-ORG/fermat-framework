package com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.database;

/**
 * Created by Nerio on 16/09/15.
 */
public class RedeemPointActorDatabaseConstants {
    /**
     * Redeem Point database table definition.
     */
    public static final String REDEEM_POINT_DATABASE_NAME = "RedeemPointUserActor";

    public static final String REDEEM_POINT_TABLE_NAME = "redeem_point_actor";

    static final String REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME = "redeem_point_publicKey";
    static final String REDEEM_POINT_ISSUER_NAME_COLUMN_NAME = "redeem_point_name";
    static final String REDEEM_POINT_STATUS_COLUMN_NAME = "redeem_point_status";
    static final String REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME = "redeem_point_registration_date";
    static final String REDEEM_POINT_MODIFIED_DATE_COLUMN_NAME = "redeem_point_modified_date";

    static final String REDEEM_POINT_FIRST_KEY_COLUMN = "redeem_point_publicKey";

    /**
     * Redeem Point Relation Issuer database table definition.
     */
    public static final String REDEEM_POINT_RELATION_ISSUER_TABLE_NAME = "redeem_point_relation_issuer_actor";

    static final String REDEEM_POINT_RELATION_ISSUER_PUBLIC_KEY_COLUMN_NAME = "redeem_point_asset_issuer_publicKey";
    static final String REDEEM_POINT_RELATION_REDEEM_POINT_KEY_COLUMN_NAME = "redeem_point_publicKey";
    static final String REDEEM_POINT_RELATION_ISSUER_NAME_COLUMN_NAME = "redeem_point_asset_issuer_name";
    static final String REDEEM_POINT_RELATION_REDEEM_POINT_NAME_COLUMN_NAME = "redeem_point_name";
    static final String REDEEM_POINT_RELATION_REDEEM_POINT_STATUS_COLUMN_NAME = "redeem_point_status";
    static final String REDEEM_POINT_RELATION_REGISTRATION_DATE_ISSUER_COLUMN_NAME = "redeem_point_registration_date_issuer";
    static final String REDEEM_POINT_RELATION_REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME = "redeem_point_registration_date";
    static final String REDEEM_POINT_RELATION_REDEEM_POINT_MODIFIED_DATE_COLUMN_NAME = "redeem_point_modified_date";

    static final String REDEEM_POINT_RELATION_ASSET_ISSUER_FIRST_KEY_COLUMN = "redeem_point_asset_issuer_publicKey";

    /**
     * Redeem Point Relation Aseet User database table definition.
     */
    public static final String ASSET_USER_DATABASE_NAME = "AssetUserActor";

    public static final String ASSET_USER_TABLE_NAME = "asset_user_actor";

    static final String REDEEM_POINT_RELATION_ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME = "asset_user_publicKey";
    static final String REDEEM_POINT_RELATION_ASSET_USER_RELATION_ISSUER_NAME_COLUMN_NAME = "asset_issuer_name";
    static final String REDEEM_POINT_RELATION_ASSET_USER_RELATION_ISSUER_PUBLIC_KEY_COLUMN_NAME = "asset_issuer_user_publicKey";
    static final String REDEEM_POINT_RELATION_ASSET_USER_USER_NAME_COLUMN_NAME = "asset_user_name";
    static final String REDEEM_POINT_RELATION_ASSET_USER_USER_ID_COLUMN_NAME = "asset_user_id";
    static final String REDEEM_POINT_RELATION_ASSET_USER_USER_HASH_COLUMN_NAME = "asset_user_hash";
    static final String REDEEM_POINT_RELATION_ASSET_USER_AMOUNT_COLUMN_NAME = "asset_user_amount";
    static final String REDEEM_POINT_RELATION_ASSET_USER_CURRENCY_COLUMN_NAME = "asset_user_currency";
    static final String REDEEM_POINT_RELATION_ASSET_USER_USER_EXPIRATION_DATE_COLUMN_NAME = "asset_expiration_date";
    static final String REDEEM_POINT_RELATION_ASSET_USER_ASSET_STATUS_COLUMN_NAME = "asset_user_status";
    static final String REDEEM_POINT_RELATION_ASSET_USER_USER_REGISTRATION_DATE_COLUMN_NAME = "asset_user_registration_date";
    static final String REDEEM_POINT_RELATION_ASSET_USER_USER_MODIFIED_DATE_COLUMN_NAME = "asset_user_modified_date";
    static final String REDEEM_POINT_RELATION_ASSET_USER_REDEEMPTION_TIMESTAMP_COLUMN_NAME = "asset_user_timestamp";
    static final String REDEEM_POINT_RELATION_ASSET_USER_REDEEMPTION_DATE_COLUMN_NAME = "asset_user_redeemption_date";

    static final String REDEEM_POINT_RELATION_ASSET_USER_FIRST_KEY_COLUMN = "asset_user_publicKey";

}
