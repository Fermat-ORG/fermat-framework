package com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_pip_api.layer.platform_service.location_subsystem.LocationSubsystem;

import java.util.ArrayList;
import java.util.List;

public class LocationServiceRegistry {

    private final PlatformDatabaseSystem platformDatabaseSystem;

    private Database database;

    public LocationServiceRegistry(final PlatformDatabaseSystem platformDatabaseSystem) {

        this.platformDatabaseSystem = platformDatabaseSystem;
    }

    public void initialize() throws CantCreateDatabaseException,
            CantOpenDatabaseException,
            CantCreateTableException {

        try {
            database = platformDatabaseSystem.openDatabase(LocationServiceDatabaseConstants.DATABASE_NAME);

        } catch (final CantOpenDatabaseException | DatabaseNotFoundException databaseNotFoundException) {

            LocationServiceDatabaseFactory databaseFactory = new LocationServiceDatabaseFactory(platformDatabaseSystem);
            database = databaseFactory.createDatabase(LocationServiceDatabaseConstants.DATABASE_NAME);
        }
    }


    /**
     * Retrieve the most recent location saved
     *
     * @return can be {@code null} if there is no saved registry yet
     * @throws CantLoadTableToMemoryException
     */
    public LocationSubsystem findLastLocation() throws CantLoadTableToMemoryException {
        DatabaseTable table = database.getTable(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_NAME);
        /**
         * It is not ordered by ID, because not  necessarily every register has a later date to the previous
         */
        table.addFilterOrder(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_TIME_COLUMN, DatabaseFilterOrder.DESCENDING);
        table.setFilterTop("1");
        table.loadToMemory();
        table.clearAllFilters();
        List<DatabaseTableRecord> records = table.getRecords();
        if (!records.isEmpty()) {
            DatabaseTableRecord record = records.get(0);
            return parseTo(record);
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    public List<LocationSubsystem> findLastLocations(int limit) throws CantLoadTableToMemoryException {
        List<LocationSubsystem> l = new ArrayList<>(limit);
        DatabaseTable table = database.getTable(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_NAME);
        table.addFilterOrder(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_TIME_COLUMN, DatabaseFilterOrder.DESCENDING);
        table.setFilterTop(limit + "");
        table.loadToMemory();
        table.clearAllFilters();
        for (DatabaseTableRecord record : table.getRecords()) {
            l.add(parseTo(record));
        }
        return l;
    }

    /**
     * Parser between {@link LocationServiceDatabaseConstants} and {@link LocationSubsystem}
     *
     * @param record
     * @return
     * @see #parseTo(LocationService)
     */
    private LocationSubsystem parseTo(DatabaseTableRecord record) {
        return new LocationService(
                record.getIntegerValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_ID_COLUMN),
                record.getStringValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_PROVIDER_COLUMN),
                record.getLongValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_TIME_COLUMN),
                record.getDoubleValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_LATITUDE_COLUMN),
                record.getDoubleValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_LONGITUDE_COLUMN),
                record.getDoubleValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_ALTITUDE_COLUMN),
                record.getDoubleValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_ACCURACY_COLUMN)

        );
    }

    /**
     * @param locationService
     * @return a new instances
     * @see #parseTo(DatabaseTableRecord)
     */

    private DatabaseTableRecord parseTo(LocationService locationService) {
        DatabaseTableRecord record = database.getTable(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_NAME).getEmptyRecord();
        record.setStringValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_PROVIDER_COLUMN, locationService.getProvider());
        record.setLongValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_TIME_COLUMN, locationService.getTime());
        record.setDoubleValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_LATITUDE_COLUMN, locationService.getLatitude());
        record.setDoubleValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_LONGITUDE_COLUMN, locationService.getLongitude());
        record.setDoubleValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_ALTITUDE_COLUMN, locationService.getAltitude());
        record.setDoubleValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_ACCURACY_COLUMN, locationService.getAccuracy());
        return record;
    }

    private DatabaseTableRecord parseTo(Location location) {
        DatabaseTableRecord record = database.getTable(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_NAME).getEmptyRecord();
        record.setStringValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_PROVIDER_COLUMN, location.getSource().toString());
        record.setLongValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_TIME_COLUMN, location.getTime());
        record.setDoubleValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_LATITUDE_COLUMN, location.getLatitude());
        record.setDoubleValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_LONGITUDE_COLUMN, location.getLongitude());
        record.setDoubleValue(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_ALTITUDE_COLUMN, location.getAltitude());
        return record;
    }

    public void create(Location location) throws CantInsertRecordException {
        DatabaseTableRecord record = parseTo(location);
        DatabaseTable table = database.getTable(LocationServiceDatabaseConstants.LOCATION_SERVICE_TABLE_NAME);
        table.insertRecord(record);

    }

    void update(LocationService locationService) throws CantUpdateRecordException {
        throw new UnsupportedOperationException("update is unsupported yet");
    }

    void remove(LocationService locationService) {
        throw new UnsupportedOperationException("remove is unsupported yet");
    }
}
