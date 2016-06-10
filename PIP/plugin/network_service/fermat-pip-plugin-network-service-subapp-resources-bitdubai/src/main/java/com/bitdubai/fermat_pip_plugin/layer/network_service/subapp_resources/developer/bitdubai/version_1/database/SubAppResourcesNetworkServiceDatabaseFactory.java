package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.database;

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
 * The Class  <code>com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.database.SubAppResourcesNetworkServiceDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 * <p/>
 * Created by Matias Furszyfer - (matiasfurszyfer@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class SubAppResourcesNetworkServiceDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public SubAppResourcesNetworkServiceDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
             * Create Repositories table.
             */
            table = databaseFactory.newTableFactory(ownerId, SubAppResourcesNetworkServiceDatabaseConstants.REPOSITORIES_TABLE_NAME);

            table.addColumn(SubAppResourcesNetworkServiceDatabaseConstants.REPOSITORIES_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(SubAppResourcesNetworkServiceDatabaseConstants.REPOSITORIES_SKIN_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(SubAppResourcesNetworkServiceDatabaseConstants.REPOSITORIES_PATH_TO_REPO_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(SubAppResourcesNetworkServiceDatabaseConstants.REPOSITORIES_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(SubAppResourcesNetworkServiceDatabaseConstants.REPOSITORIES_NAVIGATION_STRUCTURE_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(SubAppResourcesNetworkServiceDatabaseConstants.REPOSITORIES_FIRST_KEY_COLUMN);

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
        } catch (Exception e) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Error.");
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