package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure;

/**
 * Created by rodrigo on 7/21/15.
 */
public class WalletStoreNetworkServiceDatabaseConstants {

    /**
     * WalletSkin database table definition.
     */
    static final String WALLETSKIN_TABLE_NAME = "walletskin";

    static final String WALLETSKIN_ID_COLUMN_NAME = "id";
    static final String WALLETSKIN_NAME_COLUMN_NAME = "name";
    static final String WALLETSKIN_VERSION_COLUMN_NAME = "version";
    static final String WALLETSKIN_WALLETID_COLUMN_NAME = "walletid";
    static final String WALLETSKIN_WALLETINITIALVERSION_COLUMN_NAME = "walletinitialversion";
    static final String WALLETSKIN_WALLETFINALVERSION_COLUMN_NAME = "walletfinalversion";
    static final String WALLETSKIN_URL_COLUMN_NAME = "url";
    static final String WALLETSKIN_DESIGNERID_COLUMN_NAME = "designerid";

    static final String WALLETSKIN_FIRST_KEY_COLUMN = "id";


    /**
     * Designer database table definition.
     */
    static final String DESIGNER_TABLE_NAME = "designer";

    static final String DESIGNER_ID_COLUMN_NAME = "id";
    static final String DESIGNER_NAME_COLUMN_NAME = "name";
    static final String DESIGNER_PUBLICKEY_COLUMN_NAME = "publickey";

    static final String DESIGNER_FIRST_KEY_COLUMN = "id";

    /**
     * WalletLanguage database table definition.
     */
    static final String WALLETLANGUAGE_TABLE_NAME = "walletlanguage";

    static final String WALLETLANGUAGE_ID_COLUMN_NAME = "id";
    static final String WALLETLANGUAGE_NAME_COLUMN_NAME = "name";
    static final String WALLETLANGUAGE_LABEL_COLUMN_NAME = "label";
    static final String WALLETLANGUAGE_VERSION_COLUMN_NAME = "version";
    static final String WALLETLANGUAGE_WALLETID_COLUMN_NAME = "walletid";
    static final String WALLETLANGUAGE_WALLETINITIALVERSION_COLUMN_NAME = "walletinitialversion";
    static final String WALLETLANGUAGE_WALLETFINALVERSION_COLUMN_NAME = "walletfinalversion";
    static final String WALLETLANGUAGE_URL_COLUMN_NAME = "url";
    static final String WALLETLANGUAGE_FILESIZE_COLUMN_NAME = "filesize";
    static final String WALLETLANGUAGE_TRANSLATORID_COLUMN_NAME = "translatorid";

    static final String WALLETLANGUAGE_FIRST_KEY_COLUMN = "id";

    /**
     * Translator database table definition.
     */
    static final String TRANSLATOR_TABLE_NAME = "translator";

    static final String TRANSLATOR_ID_COLUMN_NAME = "id";
    static final String TRANSLATOR_NAME_COLUMN_NAME = "name";
    static final String TRANSLATOR_PUBLICKEY_COLUMN_NAME = "publickey";

    static final String TRANSLATOR_FIRST_KEY_COLUMN = "id";

    /**
     * item database table definition.
     */
    static final String ITEM_TABLE_NAME = "item";

    static final String ITEM_ID_COLUMN_NAME = "id";
    static final String ITEM_NAME_COLUMN_NAME = "name";
    static final String ITEM_CATEGORY_COLUMN_NAME = "category";
    static final String ITEM_DESCRIPTION_COLUMN_NAME = "description";
    static final String ITEM_SIZE_COLUMN_NAME = "size";
    static final String ITEM_VERSION_COLUMN_NAME = "version";
    static final String ITEM_PLATFORMINITIALVERSION_COLUMN_NAME = "platforminitialversion";
    static final String ITEM_PLATFORMFINALVERSION_COLUMN_NAME = "platformfinalversion";

    static final String ITEM_FIRST_KEY_COLUMN = "id";

    /**
     * developer database table definition.
     */
    static final String DEVELOPER_TABLE_NAME = "developer";

    static final String DEVELOPER_ID_COLUMN_NAME = "id";
    static final String DEVELOPER_NAME_COLUMN_NAME = "name";
    static final String DEVELOPER_PUBLICKEY_COLUMN_NAME = "publickey";

    static final String DEVELOPER_FIRST_KEY_COLUMN = "id";



}
