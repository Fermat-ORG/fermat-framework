package org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.database;

/**
 * Created by Nerio on 16/09/15.
 */
//TODO DEFINICION BD PARA MEJORAR
public class AssetUserActorDatabaseConstants {
    /**
     * Asset User Actor database table definition.
     */
    public static final String ASSET_USER_DATABASE_NAME = "AssetUserActor";

    public static final String ASSET_USER_TABLE_NAME = "asset_user_actor";

    public static final String ASSET_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME = "linked_identity_publicKey";
    public static final String ASSET_USER_PUBLIC_KEY_COLUMN_NAME = "publicKey";
    public static final String ASSET_USER_NAME_COLUMN_NAME = "name";
    public static final String ASSET_USER_AGE_COLUMN_NAME = "age";
    public static final String ASSET_USER_GENDER_COLUMN_NAME = "gender";
    public static final String ASSET_USER_CONNECTION_STATE_COLUMN_NAME = "connection_state";
    public static final String ASSET_USER_LOCATION_LATITUDE_COLUMN_NAME = "location_latitude";
    public static final String ASSET_USER_LOCATION_LONGITUDE_COLUMN_NAME = "location_longitude";
    public static final String ASSET_USER_REGISTRATION_DATE_COLUMN_NAME = "registration_date";
    public static final String ASSET_USER_LAST_CONNECTION_DATE_COLUMN_NAME = "last_connection_date";
    public static final String ASSET_USER_ACTOR_TYPE_COLUMN_NAME = "actor_type";

    public static final String ASSET_USER_FIRST_KEY_COLUMN = ASSET_USER_PUBLIC_KEY_COLUMN_NAME;

    /**
     * Asset User Actor REGISTERED in Actor Network Service User database table definition.
     */
    public static final String ASSET_USER_REGISTERED_TABLE_NAME = "asset_user_register_actor";

    public static final String ASSET_USER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME = "register_linked_identity_publicKey";
    public static final String ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME = "register_publicKey";
    public static final String ASSET_USER_REGISTERED_NAME_COLUMN_NAME = "register_name";
    public static final String ASSET_USER_REGISTERED_AGE_COLUMN_NAME = "register_age";
    public static final String ASSET_USER_REGISTERED_GENDER_COLUMN_NAME = "register_gender";
    public static final String ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME = "register_connection_state";
    public static final String ASSET_USER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME = "register_location_latitude";
    public static final String ASSET_USER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME = "register_location_longitude";
    public static final String ASSET_USER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME = "register_registration_date";
    public static final String ASSET_USER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME = "register_last_connection_date";
    public static final String ASSET_USER_REGISTERED_ACTOR_TYPE_COLUMN_NAME = "register_actor_type";

    public static final String ASSET_USER_REGISTERED_FIRST_KEY_COLUMN = ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME;

    /**
     * Asset User Actor GROUP REGISTERED database table definition.
     */
    public static final String ASSET_USER_GROUP_TABLE_NAME = "asset_user_group";

    public static final String ASSET_USER_GROUP_ID_COLUMN_NAME = "group_id";
    public static final String ASSET_USER_GROUP_NAME_COLUMN_NAME = "group_name";

    public static final String ASSET_USER_GROUP_FIRST_KEY_COLUMN = ASSET_USER_GROUP_ID_COLUMN_NAME;

    /**
     * Asset User Actor GROUP MEMBER REGISTERED database table definition.
     */
    public static final String ASSET_USER_GROUP_MEMBER_TABLE_NAME = "asset_user_group_member";

    public static final String ASSET_USER_GROUP_MEMBER_GROUP_ID_COLUMN_NAME = "asset_user_group_id_member";
    public static final String ASSET_USER_GROUP_MEMBER_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME = "asset_user_group_member_publicKey";


    public static final String ASSET_USER_GROUP_MEMBER_FIRST_KEY_COLUMN = ASSET_USER_GROUP_MEMBER_GROUP_ID_COLUMN_NAME;

    /**
     * Asset User Relation Asset Issuer database table definition.
     */
    public static final String ASSET_USER_CRYPTO_TABLE_NAME = "asset_user_crypto_member";

    public static final String ASSET_USER_CRYPTO_PUBLIC_KEY_COLUMN_NAME = "asset_user_crypto_public_key";
    public static final String ASSET_USER_CRYPTO_CRYPTO_ADDRESS_COLUMN_NAME = "asset_user_crypto_address";
    public static final String ASSET_USER_CRYPTO_CRYPTO_CURRENCY_COLUMN_NAME = "asset_user_crypto_currency";
    public static final String ASSET_USER_CRYPTO_NETWORK_TYPE_COLUMN_NAME = "asset_user_crypto_network_type";

    public static final String ASSET_USER_CRYPTO_FIRST_KEY_COLUMN = ASSET_USER_CRYPTO_CRYPTO_ADDRESS_COLUMN_NAME;

}
