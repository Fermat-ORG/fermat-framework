package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeNetworkIntraUserDataBaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 02/09/15.
 */
public class IntraUserNetworkServiceDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;


    Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public IntraUserNetworkServiceDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeNetworkIntraUserDataBaseException
     */
    public void initializeDatabase() throws CantInitializeNetworkIntraUserDataBaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            database.closeDatabase();

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeNetworkIntraUserDataBaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            IntraUserNetworkServiceDatabaseFactory intraUserIdentityDatabaseFactory = new IntraUserNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = intraUserIdentityDatabaseFactory.createDatabase(pluginId);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeNetworkIntraUserDataBaseException(cantCreateDatabaseException.getMessage());
            }
        } catch (Exception e) {

            throw new CantInitializeNetworkIntraUserDataBaseException(e.getMessage());

        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Intra User", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Intra User columns.
         */
        List<String> intraUserColumns = new ArrayList<String>();

        intraUserColumns.add(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_ID_COLUMN_NAME);
        intraUserColumns.add(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_USER_NAME_COLUMN_NAME);
        intraUserColumns.add(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_INTRA_USER_PUBLIC_KEY_COLUMN_NAME);
        intraUserColumns.add(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_INTRA_USER_LOGGED_IN_PUBLIC_KEY_COLUMN_NAME);
        intraUserColumns.add(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_CREATED_TIME_COLUMN_NAME);
        intraUserColumns.add(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_DESCRIPTOR_COLUMN_NAME);


        /**
         * Table Intra User addition.
         */
        DeveloperDatabaseTable intraUserTable = developerObjectFactory.getNewDeveloperDatabaseTable(IntraUserNetworkServiceDatabaseConstants.INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_NAME, intraUserColumns);
        tables.add(intraUserTable);



        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();


        /**
         * I load the passed table name from the SQLite database.
         */
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();
            database.closeDatabase();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            database.closeDatabase();
            /**
             * if there was an error, I will returned an empty list.
             */
            return returnedRecords;
        }

        List<DatabaseTableRecord> records = selectedTable.getRecords();
        List<String> developerRow = new ArrayList<String>();
        for (DatabaseTableRecord row : records) {
            /**
             * for each row in the table list
             */
            for (DatabaseRecord field : row.getValues()) {
                /**
                 * I get each row and save them into a List<String>
                 */
                developerRow.add(field.getValue());
            }
            /**
             * I create the Developer Database record
             */
            returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
        }


        /**
         * return the list of DeveloperRecords for the passed table.
         */
        return returnedRecords;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}