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

    public static final String ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME = "linked_identity_publicKey";
    public static final String ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME = "publicKey";
    public static final String ASSET_ISSUER_NAME_COLUMN_NAME = "name";
    public static final String ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME = "connection_state";
    public static final String ASSET_ISSUER_REGISTRATION_DATE_COLUMN_NAME = "registration_date";
    public static final String ASSET_ISSUER_LAST_CONNECTION_DATE_COLUMN_NAME = "last_connection_date";
    public static final String ASSET_ISSUER_PUBLIC_KEY_EXTENDED_COLUMN_NAME = "publicKey_extended";
//    public static final String ASSET_ISSUER_ISSUER_CRYPTO_ADDRESS_COLUMN_NAME = "crypto_address";
//    public static final String ASSET_ISSUER_ISSUER_CRYPTO_CURRENCY_COLUMN_NAME = "crypto_currency";
    public static final String ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME = "location_latitude";
    public static final String ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME = "location_longitude";
    public static final String ASSET_ISSUER_DESCRIPTION_COLUMN_NAME = "description";

    public static final String ASSET_ISSUER_FIRST_KEY_COLUMN = ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME;

    /**
     * Asset Issuer Actor REGISTERED in Actor Network Service Issuer database table definition.
     */
    public static final String ASSET_ISSUER_REGISTERED_TABLE_NAME = "register_asset_issuer_actor";

    public static final String ASSET_ISSUER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME = "register_linked_identity_publicKey";
    public static final String ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME = "register_publicKey";
    public static final String ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME = "register_name";
    public static final String ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME = "register_connection_state";
    public static final String ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME = "register_registration_date";
    public static final String ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME = "register_last_connection_date";
    public static final String ASSET_ISSUER_REGISTERED_PUBLIC_KEY_EXTENDED_COLUMN_NAME = "register_publicKey_extended";
//    public static final String ASSET_ISSUER_REGISTERED_ISSUER_CRYPTO_ADDRESS_COLUMN_NAME = "register_crypto_address";
//    public static final String ASSET_ISSUER_REGISTERED_ISSUER_CRYPTO_CURRENCY_COLUMN_NAME = "register_crypto_currency";
    public static final String ASSET_ISSUER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME = "register_location_latitude";
    public static final String ASSET_ISSUER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME = "register_location_longitude";
    public static final String ASSET_ISSUER_REGISTERED_DESCRIPTION_COLUMN_NAME = "register_description";

    public static final String ASSET_ISSUER_REGISTERED_FIRST_KEY_COLUMN = ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME;

    /**
     * Asset Issuer relation Redeem Point Associate table definition.
     */
//    public static final String ASSET_ISSUER_RELATION_REDEEM_POINT_TABLE_NAME = "asset_issuer_relation_redeem_point_actor";
//
//    public static final String ASSET_ISSUER_RELATION_REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME = "asset_issuer_redeem_point_publicKey_actor";
//    public static final String ASSET_ISSUER_RELATION_REDEEM_POINT_ISSUER_NAME_COLUMN_NAME = "asset_issuer_name_actor";
//    public static final String ASSET_ISSUER_RELATION_REDEEM_POINT_NAME_COLUMN_NAME = "redeem_point_name_actor";
//    public static final String ASSET_ISSUER_RELATION_REDEEM_POINT_STATUS_COLUMN_NAME = "redeem_point_status_actor";
//    public static final String ASSET_ISSUER_RELATION_REDEEM_POINT_ASSETS_COUNT_COLUMN_NAME = "redeem_point_assets_count_actor";
//    public static final String ASSET_ISSUER_RELATION_REDEEM_POINT_REGISTRATION_DATE_ISSUER_COLUMN_NAME = "redeem_point_registration_date_issuer_actor";
//    public static final String ASSET_ISSUER_RELATION_REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME = "redeem_point_registration_date_actor";
//    public static final String ASSET_ISSUER_RELATION_REDEEM_POINT_MODIFIED_DATE_COLUMN_NAME = "redeem_point_modified_date_actor_actor";
//
//    public static final String ASSET_ISSUER_RELATION_REDEEM_POINT_FIRST_KEY_COLUMN = "asset_issuer_redeem_point_publicKey_actor";
//
//    /**
//     * Asset Issuer relation Asset User Associate table definition.
//     */
//    public static final String ASSET_ISSUER_RELATION_USER_TABLE_NAME = "asset_issuer_relation_user_actor";
//
//    public static final String ASSET_ISSUER_RELATION_USER_PUBLIC_KEY_COLUMN_NAME = "asset_issuer_user_publicKey_actor";
//    public static final String ASSET_ISSUER_RELATION_USER_ISSUER_NAME_COLUMN_NAME = "asset_issuer_name_actor";
//    public static final String ASSET_ISSUER_RELATION_USER_ASSET_ID_COLUMN_NAME = "asset_user_asset_id_actor";
//    public static final String ASSET_ISSUER_RELATION_USER_ASSET_NAME_COLUMN_NAME = "asset_user_asset_name_actor";
//    public static final String ASSET_ISSUER_RELATION_USER_ASSET_HASH_COLUMN_NAME = "asset_user_asset_hash_actor";
//    public static final String ASSET_ISSUER_RELATION_USER_ASSET_STATUS_COLUMN_NAME = "asset_user_asset_status_actor";
//    public static final String ASSET_ISSUER_RELATION_USER_ASSET_RESOURCES_COLUMN_NAME = "asset_user_asset_resource_actor";
//    public static final String ASSET_ISSUER_RELATION_USER_ASSET_AMOUNT_COLUMN_NAME = "asset_user_asset_amount_actor";
//    public static final String ASSET_ISSUER_RELATION_USER_ASSET_CURRENCY_COLUMN_NAME = "asset_user_asset_currency_actor";
//    public static final String ASSET_ISSUER_RELATION_USER_ASSET_EXPIRATION_DATE_COLUMN_NAME = "asset_expiration_date_actor";
//    public static final String ASSET_ISSUER_RELATION_USER_STATUS_COLUMN_NAME = "user_status_actor";
//    public static final String ASSET_ISSUER_RELATION_USER_REGISTRATION_DATE_COLUMN_NAME = "user_registration_date_actor";
//    public static final String ASSET_ISSUER_RELATION_USER_MODIFIED_DATE_COLUMN_NAME = "user_modified_date_actor";
//    public static final String ASSET_ISSUER_RELATION_USER_TIMESTAMP_COLUMN_NAME = "asset_user_timestamp_actor";
//
//    public static final String ASSET_ISSUER_RELATION_USER_FIRST_KEY_COLUMN = "asset_issuer_user_publicKey_actor";
}
