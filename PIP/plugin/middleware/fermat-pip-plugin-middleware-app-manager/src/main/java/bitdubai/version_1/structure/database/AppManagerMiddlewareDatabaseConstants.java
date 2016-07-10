package bitdubai.version_1.structure.database;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.database.AppManagerMiddlewareDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Natalia on 21/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AppManagerMiddlewareDatabaseConstants {

    public static final String WALLET_MANAGER_WALLETS_DATABASE = "WalletDatabase";

    /**
     * Wallet Manager Wallets Table database table definition.
     */
    public static final String WALLET_MANAGER_WALLETS_TABLE_TABLE_NAME = "wallet_manager_wallets_table";

    public static final String WALLET_MANAGER_WALLETS_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    public static final String WALLET_MANAGER_WALLETS_TABLE_WALLET_PRIVATE_KEY_COLUMN_NAME = "wallet_private_key";
    public static final String WALLET_MANAGER_WALLETS_TABLE_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME = "device_user_public_key";
    public static final String WALLET_MANAGER_WALLETS_TABLE_DEVELOPER_NAME_COLUMN_NAME = "developer_name";
    public static final String WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME = "wallet_catalog_id";
    public static final String WALLET_MANAGER_WALLETS_TABLE_WALLET_CATEGORY_COLUMN_NAME = "wallet_category";
    public static final String WALLET_MANAGER_WALLETS_TABLE_WALLET_TYPE_COLUMN_NAME = "wallet_type";
    public static final String WALLET_MANAGER_WALLETS_TABLE_WALLET_NAME_COLUMN_NAME = "wallet_name";
    public static final String WALLET_MANAGER_WALLETS_TABLE_WALLET_PLATFORM_IDENTIFIER_COLUMN_NAME = "wallet_platform_identifier";
    public static final String WALLET_MANAGER_WALLETS_TABLE_WALLET_ICON_NAME_COLUMN_NAME = "wallet_icon_name";
    public static final String WALLET_MANAGER_WALLETS_TABLE_WALLET_VERSION_COLUMN_NAME = "wallet_version";
    public static final String WALLET_MANAGER_WALLETS_TABLE_WALLET_SCREEN_SIZE_COLUMN_NAME = "wallet_screenSize";
    public static final String WALLET_MANAGER_WALLETS_TABLE_WALLET_SCREEN_DENSITY_COLUMN_NAME = "wallet_screenDensity";
    public static final String WALLET_MANAGER_WALLETS_TABLE_WALLET_NAVIGATION_VERSION_COLUMN_NAME = "wallet_navigation_structure_version";

    public static final String WALLET_MANAGER_WALLETS_TABLE_FIRST_KEY_COLUMN = "wallet_public_key";

    /**
     * Wallet Manager BlockChain Network Type
     */
    public static final String WALLET_MANAGER_WALLETS_TABLE_WALLET_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME = "blockchain_network_type";


    /**
     * Wallet Manager Skins Table database table definition.
     */
    public static final String WALLET_MANAGER_SKINS_TABLE_TABLE_NAME = "wallet_manager_skins_table";

    public static final String WALLET_MANAGER_SKINS_TABLE_SKIN_ID_COLUMN_NAME = "skin_id";
    public static final String WALLET_MANAGER_SKINS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME = "wallet_catalog_id";
    public static final String WALLET_MANAGER_SKINS_TABLE_SKIN_NAME_COLUMN_NAME = "skin_name";
    public static final String WALLET_MANAGER_SKINS_TABLE_SKIN_PREVIEW_IMAGE_COLUMN_NAME = "skin_preview_image";
    public static final String WALLET_MANAGER_SKINS_TABLE_SKIN_VERSION_COLUMN_NAME = "skin_version";

    public static final String WALLET_MANAGER_SKINS_TABLE_FIRST_KEY_COLUMN = "skin_id";

    /**
     * Wallet Manager Languages Table database table definition.
     */
    public static final String WALLET_MANAGER_LANGUAGES_TABLE_TABLE_NAME = "wallet_manager_languages_table";

    public static final String WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_ID_COLUMN_NAME = "language_id";
    public static final String WALLET_MANAGER_LANGUAGES_TABLE_WALLET_CATALOG_ID_COLUMN_NAME = "wallet_catalog_id";
    public static final String WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_NAME_COLUMN_NAME = "language_name";
    public static final String WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_LABEL_COLUMN_NAME = "language_label";
    public static final String WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_VERSION_COLUMN_NAME = "language_version";

    public static final String WALLET_MANAGER_LANGUAGES_TABLE_FIRST_KEY_COLUMN = "language_id";

}