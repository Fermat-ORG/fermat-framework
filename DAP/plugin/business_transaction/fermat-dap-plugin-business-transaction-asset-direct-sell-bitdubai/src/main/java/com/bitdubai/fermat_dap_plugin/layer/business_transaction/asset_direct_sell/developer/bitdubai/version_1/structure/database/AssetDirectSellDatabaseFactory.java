package com.bitdubai.fermat_dap_plugin.layer.business_transaction.asset_direct_sell.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Luis Torres (lutor1106@gmail.com") 16/03/16
 */

public final class AssetDirectSellDatabaseFactory {
    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public AssetDirectSellDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * Create the database
     *
     * @return Database
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase() throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(pluginId, AssetDirectSellDatabaseConstants.ASSET_DIRECT_SELL_DATABASE);
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
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Asset Reception table.
             */

            DatabaseTableFactory table = databaseFactory.newTableFactory(pluginId, AssetDirectSellDatabaseConstants.ASSET_DIRECT_SELL_TABLE_NAME);
            table.addColumn(AssetDirectSellDatabaseConstants.ASSET_DIRECT_SELL_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);

            try {
                //Create the table
                databaseFactory.createTable(pluginId, table);
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


    public boolean databaseExists() {
        try {
            pluginDatabaseSystem.openDatabase(pluginId, AssetDirectSellDatabaseConstants.ASSET_DIRECT_SELL_DATABASE);
            return true;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            return false;
        }
    }


}