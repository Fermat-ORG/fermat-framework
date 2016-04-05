package org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database;

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
    public static final String ASSET_ISSUER_TYPE_COLUMN_NAME = "issuer_type";
    public static final String ASSET_ISSUER_DESCRIPTION_COLUMN_NAME = "description";
    public static final String ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME = "location_latitude";
    public static final String ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME = "location_longitude";

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
    public static final String ASSET_ISSUER_REGISTERED_TYPE_COLUMN_NAME = "issuer_registered_type";
    public static final String ASSET_ISSUER_REGISTERED_DESCRIPTION_COLUMN_NAME = "register_description";
    public static final String ASSET_ISSUER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME = "register_location_latitude";
    public static final String ASSET_ISSUER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME = "register_location_longitude";

    public static final String ASSET_ISSUER_REGISTERED_FIRST_KEY_COLUMN = ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME;

}
