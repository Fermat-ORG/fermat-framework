package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database;

/**
 * Created by rodrigo on 7/21/15.
 */
public class WalletStoreCatalogDatabaseConstants {

    /**
     * Database name
     */
    public static final String WALLET_STORE_DATABASE = "Catalog";

    /**
     * WalletSkin database table definition.
     */
    public static final String WALLETSKIN_TABLE_NAME = "walletSkin";

    public static final String WALLETSKIN_ID_COLUMN_NAME = "id";
    public static final String WALLETSKIN_NAME_COLUMN_NAME = "name";
    public static final String WALLETSKIN_VERSION_COLUMN_NAME = "version";
    public static final String WALLETSKIN_WALLETID_COLUMN_NAME = "walletId";
    public static final String WALLETSKIN_WALLETINITIALVERSION_COLUMN_NAME = "walletInitialVersion";
    public static final String WALLETSKIN_WALLETFINALVERSION_COLUMN_NAME = "walletFinalVersion";
    public static final String WALLETSKIN_SIZE_COLUMN_NAME = "size";
    public static final String WALLETSKIN_DESIGNERID_COLUMN_NAME = "designerId";
    public static final String WALLETSKIN_ISDEFAULT_COLUMN_NAME = "isDefault";
    /**
     * New field on WalletSkin table Screen Size information
     */
    public static final String WALLETSKIN_SCREEN_SIZE="screenSize";

    static final String WALLETSKIN_FIRST_KEY_COLUMN = "id";


    /**
     * Designer database table definition.
     */
    public static final String DESIGNER_TABLE_NAME = "designer";

    public static final String DESIGNER_ID_COLUMN_NAME = "id";
    public static final String DESIGNER_NAME_COLUMN_NAME = "name";
    public static final String DESIGNER_PUBLICKEY_COLUMN_NAME = "publicKey";

    static final String DESIGNER_FIRST_KEY_COLUMN = "id";

    /**
     * WalletLanguage database table definition.
     */
    public static final String WALLETLANGUAGE_TABLE_NAME = "walletLanguage";

    public static final String WALLETLANGUAGE_ID_COLUMN_NAME = "id";
    public static final String WALLETLANGUAGE_NAME_COLUMN_NAME = "name";
    public static final String WALLETLANGUAGE_LABEL_COLUMN_NAME = "label";
    public static final String WALLETLANGUAGE_VERSION_COLUMN_NAME = "version";
    public static final String WALLETLANGUAGE_WALLETID_COLUMN_NAME = "walletid";
    public static final String WALLETLANGUAGE_WALLETINITIALVERSION_COLUMN_NAME = "walletInitialVersion";
    public static final String WALLETLANGUAGE_WALLETFINALVERSION_COLUMN_NAME = "walletFinalVersion";
    public static final String WALLETLANGUAGE_FILESIZE_COLUMN_NAME = "filesize";
    public static final String WALLETLANGUAGE_TRANSLATORID_COLUMN_NAME = "translatorId";
    public static final String WALLETLANGUAGE_ISDEFAULT_COLUMN_NAME = "isDefault";

    static final String WALLETLANGUAGE_FIRST_KEY_COLUMN = "id";

    /**
     * Translator database table definition.
     */
    public static final String TRANSLATOR_TABLE_NAME = "translator";

    public static final String TRANSLATOR_ID_COLUMN_NAME = "id";
    public static final String TRANSLATOR_NAME_COLUMN_NAME = "name";
    public static final String TRANSLATOR_PUBLICKEY_COLUMN_NAME = "publickey";

    static final String TRANSLATOR_FIRST_KEY_COLUMN = "id";

    /**
     * item database table definition.
     */
    public static final String ITEM_TABLE_NAME = "item";

    public static final String ITEM_ID_COLUMN_NAME = "id";
    public static final String ITEM_NAME_COLUMN_NAME = "name";
    public static final String ITEM_CATEGORY_COLUMN_NAME = "category";
    public static final String ITEM_DESCRIPTION_COLUMN_NAME = "description";
    public static final String ITEM_SIZE_COLUMN_NAME = "size";
    public static final String ITEM_VERSION_COLUMN_NAME = "version";
    public static final String ITEM_PLATFORMINITIALVERSION_COLUMN_NAME = "platformInitialVersion";
    public static final String ITEM_PLATFORMFINALVERSION_COLUMN_NAME = "platformFinalVersion";
    public static final String ITEM_DEVELOPER_ID_COLUMN_NAME = "developerId";
    public static final String ITEM_PUBLISHER_WEB_SITE_URL_COLUMN_NAME = "url";

    static final String ITEM_FIRST_KEY_COLUMN = "id";

    /**
     * developer database table definition.
     */
    public static final String DEVELOPER_TABLE_NAME = "developer";

    public static final String DEVELOPER_ID_COLUMN_NAME = "id";
    public static final String DEVELOPER_NAME_COLUMN_NAME = "name";
    public static final String DEVELOPER_PUBLICKEY_COLUMN_NAME = "publicKey";

    static final String DEVELOPER_FIRST_KEY_COLUMN = "id";


}
