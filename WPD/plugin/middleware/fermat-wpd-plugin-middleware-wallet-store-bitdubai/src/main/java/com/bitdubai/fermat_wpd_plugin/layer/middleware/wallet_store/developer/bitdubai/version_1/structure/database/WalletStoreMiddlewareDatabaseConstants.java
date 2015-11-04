package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database;

/**
 * Created by rodrigo on 7/24/15.
 */
/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 24/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletStoreMiddlewareDatabaseConstants {
    /**
     * Database name definition
     */
    public static final  String DATABASE_NAME = "Installations";

    /**
     * WalletStatus database table definition.
     */
    public static final String WALLETSTATUS_TABLE_NAME = "walletstatus";

    public static final String WALLETSTATUS_ID_COLUMN_NAME = "id";
    public static final String WALLETSTATUS_INSTALATIONSTATUS_COLUMN_NAME = "instalationstatus";

    public static final String WALLETSTATUS_FIRST_KEY_COLUMN = "id";

    /**
     * SkinStatus database table definition.
     */
    public static final String SKINSTATUS_TABLE_NAME = "skinstatus";

    public static final String SKINSTATUS_ID_COLUMN_NAME = "id";
    public static final String SKINSTATUS_INSTALATIONSTATUS_COLUMN_NAME = "instalationstatus";

    public static final String SKINSTATUS_FIRST_KEY_COLUMN = "id";

    /**
     * LanguageStatus database table definition.
     */
    public static final String LANGUAGESTATUS_TABLE_NAME = "languagestatus";

    public static final String LANGUAGESTATUS_ID_COLUMN_NAME = "id";
    public static final String LANGUAGESTATUS_INSTALATIONSTATUS_COLUMN_NAME = "instalationstatus";

    public static final String LANGUAGESTATUS_FIRST_KEY_COLUMN = "id";
}
