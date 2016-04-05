package org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.database;

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

    public static final String REDEEM_POINT_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME = "linked_identity_publicKey";
    public static final String REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME = "publicKey";
    public static final String REDEEM_POINT_NAME_COLUMN_NAME = "name";
    public static final String REDEEM_POINT_CONNECTION_STATE_COLUMN_NAME = "redeem_point_connection_state";
    public static final String REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME = "registration_date";
    public static final String REDEEM_POINT_LAST_CONNECTION_DATE_COLUMN_NAME = "last_connection_date";
    public static final String REDEEM_POINT_ACTOR_TYPE_COLUMN_NAME = "actor_type";
    public static final String REDEEM_POINT_LOCATION_LATITUDE_COLUMN_NAME = "location_latitude";
    public static final String REDEEM_POINT_LOCATION_LONGITUDE_COLUMN_NAME = "location_longitude";
    //public static final String REDEEM_POINT_CRYPTO_ADDRESS_COLUMN_NAME = "crypto_address";
    //public static final String REDEEM_POINT_CRYPTO_CURRENCY_COLUMN_NAME = "crypto_currency";
    public static final String REDEEM_POINT_CONTACT_INFORMATION_COLUMN_NAME = "contact_information";
    public static final String REDEEM_POINT_ADDRESS_COUNTRY_NAME_COLUMN_NAME = "address_country_name";
    public static final String REDEEM_POINT_ADDRESS_PROVINCE_NAME_COLUMN_NAME = "address_province_name";
    public static final String REDEEM_POINT_ADDRESS_CITY_NAME_COLUMN_NAME = "address_city_name";
    public static final String REDEEM_POINT_ADDRESS_POSTAL_CODE_COLUMN_NAME = "address_postal_code";
    public static final String REDEEM_POINT_ADDRESS_STREET_NAME_COLUMN_NAME = "address_street_name";
    public static final String REDEEM_POINT_ADDRESS_HOUSE_NUMBER_COLUMN_NAME = "address_house_number";
    public static final String REDEEM_POINT_HOURS_OF_OPERATION_COLUMN_NAME = "hours_of_operation";

    public static final String REDEEM_POINT_FIRST_KEY_COLUMN = REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME;

    /**
     * Redeem Point Actor REGISTERED in Actor Network Service Redeem Point database table definition.
     */
    public static final String REDEEM_POINT_REGISTERED_TABLE_NAME = "registered_redeem_point_actor";

    public static final String REDEEM_POINT_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME = "register_linked_identity_publicKey";
    public static final String REDEEM_POINT_REGISTERED_PUBLIC_KEY_COLUMN_NAME = "register_publicKey";
    public static final String REDEEM_POINT_REGISTERED_NAME_COLUMN_NAME = "register_name";
    public static final String REDEEM_POINT_REGISTERED_CONNECTION_STATE_COLUMN_NAME = "register_redeem_point_connection_state";
    public static final String REDEEM_POINT_REGISTERED_REGISTRATION_DATE_COLUMN_NAME = "register_registration_date";
    public static final String REDEEM_POINT_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME = "register_last_connection_date";
    public static final String REDEEM_POINT_REGISTERED_ACTOR_TYPE_COLUMN_NAME = "register_actor_type";
    public static final String REDEEM_POINT_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME = "register_location_latitude";
    public static final String REDEEM_POINT_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME = "register_location_longitude";
    //public static final String REDEEM_POINT_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME = "register_crypto_address";
    //public static final String REDEEM_POINT_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME = "register_crypto_currency";
    public static final String REDEEM_POINT_REGISTERED_CONTACT_INFORMATION_COLUMN_NAME = "register_contact_information";
    public static final String REDEEM_POINT_REGISTERED_ADDRESS_COUNTRY_NAME_COLUMN_NAME = "register_address_country_name";
    public static final String REDEEM_POINT_REGISTERED_ADDRESS_PROVINCE_NAME_COLUMN_NAME = "register_address_province_name";
    public static final String REDEEM_POINT_REGISTERED_ADDRESS_CITY_NAME_COLUMN_NAME = "register_address_city_name";
    public static final String REDEEM_POINT_REGISTERED_ADDRESS_POSTAL_CODE_COLUMN_NAME = "register_address_postal_code";
    public static final String REDEEM_POINT_REGISTERED_ADDRESS_STREET_NAME_COLUMN_NAME = "register_address_street_name";
    public static final String REDEEM_POINT_REGISTERED_ADDRESS_HOUSE_NUMBER_COLUMN_NAME = "register_address_house_number";
    public static final String REDEEM_POINT_REGISTERED_HOURS_OF_OPERATION_COLUMN_NAME = "register_hours_of_operation";

    public static final String REDEEM_POINT_REGISTERED_FIRST_KEY_COLUMN = REDEEM_POINT_REGISTERED_PUBLIC_KEY_COLUMN_NAME;

    public static final String REGISTERED_ASSET_ISSUERS_TABLE_NAME = "registered_asset_issuers";
    public static final String REGISTERED_ASSET_ISSUERS_REDEEM_POINT_PUBLICKEY_COLUMN = "redeemPointPublicKey";
    public static final String REGISTERED_ASSET_ISSUERS_ISSUER_PUBLICKEY_COLUMN = "issuerPublicKey";
    public static final String REGISTERED_ASSET_ISSUERS_FIRST_KEY_COLUMN = REGISTERED_ASSET_ISSUERS_REDEEM_POINT_PUBLICKEY_COLUMN;

    /**
     * Redeem Point Actor database table CRYPTO ADDRESS definition.
     */
    public static final String REDEEM_POINT_CRYPTO_TABLE_NAME = "redeem_point_crypto_member";

    public static final String REDEEM_POINT_CRYPTO_PUBLIC_KEY_COLUMN_NAME = "redeem_point_crypto_public_key";
    public static final String REDEEM_POINT_CRYPTO_CRYPTO_ADDRESS_COLUMN_NAME = "redeem_point_crypto_address";
    public static final String REDEEM_POINT_CRYPTO_CRYPTO_CURRENCY_COLUMN_NAME = "redeem_point_crypto_currency";
    public static final String REDEEM_POINT_CRYPTO_NETWORK_TYPE_COLUMN_NAME = "redeem_point_crypto_network_type";

    public static final String REDEEM_POINT_CRYPTO_FIRST_KEY_COLUMN = REDEEM_POINT_CRYPTO_PUBLIC_KEY_COLUMN_NAME;
}
