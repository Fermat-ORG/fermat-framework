package com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;

/**
 * Created by firuzzz on 5/4/15.
 * Updated by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public class LocationServiceDatabaseFactory {

    private final PlatformDatabaseSystem platformDatabaseSystem;

    public LocationServiceDatabaseFactory(final PlatformDatabaseSystem platformDatabaseSystem) {

        this.platformDatabaseSystem = platformDatabaseSystem;
    }

    public final Database createDatabase(String databaseName) throws CantCreateDatabaseException, CantCreateTableException {
        Database database = platformDatabaseSystem.createDatabase(databaseName);


        DatabaseFactory databaseFactory = database.getDatabaseFactory();

        DatabaseTableFactory table = databaseFactory.newTableFactory(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_NAME);

        table.addColumn(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_ID_COLUMN, DatabaseDataType.INTEGER, 11, true);
        table.addColumn(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_PROVIDER_COLUMN, DatabaseDataType.STRING, 7, false);
        table.addColumn(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_TIME_COLUMN, DatabaseDataType.LONG_INTEGER, 13, false);
        table.addColumn(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_LATITUDE_COLUMN, DatabaseDataType.REAL, 20, false);
        table.addColumn(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_LONGITUDE_COLUMN, DatabaseDataType.REAL, 20, false);
        table.addColumn(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_ALTITUDE_COLUMN, DatabaseDataType.REAL, 10, false);
        table.addColumn(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_ACCURACY_COLUMN, DatabaseDataType.REAL, 10, false);

        databaseFactory.createTable(table);

        return database;
    }

}
