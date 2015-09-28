package com.bitdubai.fermat_pip_addon.layer.platform_service.location_subsystem.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by firuzzz on 5/4/15.
 */
class LocationServiceDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    Database createDatabase(UUID ownerId, UUID walletId) throws CantCreateDatabaseException, CantCreateTableException {
        Database database = pluginDatabaseSystem.createDatabase(ownerId, walletId.toString());
        /**
         * Next, I will add the needed tables.
         */
        try {
            //DatabaseTableFactory table;
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            table = databaseFactory.newTableFactory(ownerId, LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_NAME);
//            table = ((DatabaseFactory) database).newTableFactory(ownerId, LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_NAME);
            table.addColumn(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_ID_COLUMN, DatabaseDataType.INTEGER, 11, true);
            table.addColumn(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_PROVIDER_COLUMN, DatabaseDataType.STRING, 7, false);
            table.addColumn(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_TIME_COLUMN, DatabaseDataType.LONG_INTEGER, 13, false);
            table.addColumn(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_LATITUDE_COLUMN, DatabaseDataType.REAL, 20, false);
            table.addColumn(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_LONGITUDE_COLUMN, DatabaseDataType.REAL, 20, false);
            table.addColumn(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_ALTITUDE_COLUMN, DatabaseDataType.REAL, 10, false);
            table.addColumn(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_ACCURACY_COLUMN, DatabaseDataType.REAL, 10, false);

            databaseFactory.createTable(ownerId, table);
//            ((DatabaseFactory) database).createTable(ownerId, table);
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             * * *
             */
            throw new CantCreateDatabaseException();
        }
        return database;
    }
}
