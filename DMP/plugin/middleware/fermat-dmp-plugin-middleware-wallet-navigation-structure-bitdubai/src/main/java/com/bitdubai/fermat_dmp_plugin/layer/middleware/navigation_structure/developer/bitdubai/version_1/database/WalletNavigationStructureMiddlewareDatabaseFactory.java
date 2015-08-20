package com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.database;

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
 *  The Class  <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.structure.WalletNavigationStructureMiddlewareDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 *  Created by Natalia on 07/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletNavigationStructureMiddlewareDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public WalletNavigationStructureMiddlewareDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     * Modified by Manuel Perez on 11/08/2015
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
             * Create Wallet Manager Wallets Table table.
             */
            DatabaseTableFactory table = null;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            table=databaseFactory.newTableFactory(ownerId, WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_TABLE_NAME);
            table.addColumn(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_PUBLIC_KEY, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_ACTIVITY, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_START_ACTIVITY, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_LAST_ACTIVITY, DatabaseDataType.STRING, 36, Boolean.FALSE);

            table.addIndex(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_FIRST_KEY_COLUMN);

            databaseFactory.createTable(ownerId, table);

        }catch (CantCreateTableException cantCreateTableException) {

                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");

        }catch (InvalidOwnerIdException invalidOwnerId) {
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