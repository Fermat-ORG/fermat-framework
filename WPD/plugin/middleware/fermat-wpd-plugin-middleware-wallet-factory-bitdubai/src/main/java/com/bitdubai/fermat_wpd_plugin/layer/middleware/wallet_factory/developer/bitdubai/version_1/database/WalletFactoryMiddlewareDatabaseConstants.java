package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database;

/**
 * Created by rodrigo on 8/17/15.
 */
public class WalletFactoryMiddlewareDatabaseConstants {

    public static final String DATABASE_NAME = "Factory";

    /**
     * Project database table definition.
     */
    static final String PROJECT_TABLE_NAME = "project";

    public static final String PROJECT_PUBLICKEY_COLUMN_NAME = "publickey";
    public static final String PROJECT_NAME_COLUMN_NAME = "name";
    public static final String PROJECT_DESCRIPTION_COLUMN_NAME = "description";
    public static final String PROJECT_STATE_COLUMN_NAME = "state";
    public static final String PROJECT_WALLETTYPE_COLUMN_NAME = "wallettype";
    public static final String PROJECT_WALLETCATEGORY_COLUMN_NAME = "walletCategory";
    public static final String PROJECT_FACTORYPROJECTTYPE_COLUMN_NAME = "FactoryProjectType";
    public static final String PROJECT_CREATION_TIMESTAMP_COLUMN_NAME = "creation_timestamp";
    public static final String PROJECT_MODIFICATION_TIMESTAMP_COLUMN_NAME = "modification_timestamp";
    public static final String PROJECT_SIZE_COLUMN_NAME = "size";


    static final String PROJECT_FIRST_KEY_COLUMN = "publickey";

    /**
     * skin database table definition.
     */
    public static final String SKIN_TABLE_NAME = "skin";

    public static final String SKIN_PROJECT_PUBLICKEY_COLUMN_NAME = "project_publickey";
    public static final String SKIN_SKIN_ID_COLUMN_NAME = "skin_id";
    public static final String SKIN_DEFAULT_COLUMN_NAME = "isDefault";

    public static final String SKIN_FIRST_KEY_COLUMN = "project_publickey";

    /**
     * language database table definition.
     */
    public static final String LANGUAGE_TABLE_NAME = "language";

    public static final String LANGUAGE_PROJECT_PUBLICKEY_COLUMN_NAME = "project_publickey";
    public static final String LANGUAGE_LANGUAGE_ID_COLUMN_NAME = "language_id";
    public static final String LANGUAGE_DEFAULT_COLUMN_NAME = "isDefault";

    static final String LANGUAGE_FIRST_KEY_COLUMN = "project_publickey";

    /**
     * navigation_structure database table definition.
     */
    public static final String NAVIGATION_STRUCTURE_TABLE_NAME = "navigation_structure";

    public static final String NAVIGATION_STRUCTURE_PROJECT_PUBLICKEY_COLUMN_NAME = "project_publickey";
    public static final String NAVIGATION_STRUCTURE_PUBLICKEY_COLUMN_NAME = "publicKey";

    public static final String NAVIGATION_STRUCTURE_FIRST_KEY_COLUMN = "project_publickey";
}
