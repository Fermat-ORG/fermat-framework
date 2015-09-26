package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database;

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
 *  The Class  <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database.Wallet FactoryMiddlewareDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 17/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewareDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public WalletFactoryMiddlewareDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Project table.
             */
            table = databaseFactory.newTableFactory(ownerId, WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);

            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 200, Boolean.FALSE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_STATE_COLUMN_NAME, DatabaseDataType.STRING, 20, Boolean.FALSE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLETTYPE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLETCATEGORY_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_FACTORYPROJECTTYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_CREATION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_MODIFICATION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SIZE_COLUMN_NAME, DatabaseDataType.INTEGER, 10, Boolean.FALSE);

            table.addIndex(WalletFactoryMiddlewareDatabaseConstants.PROJECT_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
            /**
             * Create skin table.
             */
            table = databaseFactory.newTableFactory(ownerId, WalletFactoryMiddlewareDatabaseConstants.SKIN_TABLE_NAME);

            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.SKIN_PROJECT_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.SKIN_SKIN_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.SKIN_DEFAULT_COLUMN_NAME, DatabaseDataType.STRING, 5, Boolean.TRUE);

            table.addIndex(WalletFactoryMiddlewareDatabaseConstants.SKIN_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create language table.
             */
            table = databaseFactory.newTableFactory(ownerId, WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_TABLE_NAME);

            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_PROJECT_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_LANGUAGE_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_DEFAULT_COLUMN_NAME, DatabaseDataType.STRING, 5, Boolean.TRUE);

            table.addIndex(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create navigation_structure table.
             */
            table = databaseFactory.newTableFactory(ownerId, WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_TABLE_NAME);

            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_PROJECT_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
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
