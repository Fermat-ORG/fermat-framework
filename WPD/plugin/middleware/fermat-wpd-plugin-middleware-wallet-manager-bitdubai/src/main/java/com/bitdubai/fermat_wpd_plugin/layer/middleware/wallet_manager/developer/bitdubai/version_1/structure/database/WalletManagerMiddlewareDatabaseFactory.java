package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * The Class  <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager_middleware.developer.bitdubai.version_1.structure.Wallet Manager MiddlewareMiddlewareDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 * <p/>
 * Created by Ezequiel Postan - (ezequiel.postan@gmail.com) on 24/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletManagerMiddlewareDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public WalletManagerMiddlewareDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     * @return Database
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
            /**
             * Next, I will add the needed tables.
             */
            /**
             * Create Wallet Manager Wallets Table table.
             */
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            table = databaseFactory.newTableFactory(ownerId, WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_TABLE_NAME);

            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.TRUE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PRIVATE_KEY_COLUMN_NAME, DatabaseDataType.STRING, 64, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_DEVELOPER_NAME_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);

            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATEGORY_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_NAME_COLUMN_NAME, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PLATFORM_IDENTIFIER_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_ICON_NAME_COLUMN_NAME, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_SCREEN_SIZE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_SCREEN_DENSITY_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_NAVIGATION_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);


            table.addIndex(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_FIRST_KEY_COLUMN);

            //Create the table
            databaseFactory.createTable(ownerId, table);

            /**
             * Create Wallet Manager Skins Table table.
             */
            table = databaseFactory.newTableFactory(ownerId, WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_TABLE_NAME);

            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_NAME_COLUMN_NAME, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_PREVIEW_IMAGE_COLUMN_NAME, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            table.addIndex(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_FIRST_KEY_COLUMN);
            //Create the table
            databaseFactory.createTable(ownerId, table);

            /**
             * Create Wallet Manager Languages Table table.
             */
            table = databaseFactory.newTableFactory(ownerId, WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_TABLE_NAME);

            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_WALLET_CATALOG_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_LABEL_COLUMN_NAME, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            table.addIndex(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_FIRST_KEY_COLUMN);
            //Create the table
            databaseFactory.createTable(ownerId, table);
        } catch (CantCreateTableException cantCreateTableException) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        } catch (Exception e) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }
        return database;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}