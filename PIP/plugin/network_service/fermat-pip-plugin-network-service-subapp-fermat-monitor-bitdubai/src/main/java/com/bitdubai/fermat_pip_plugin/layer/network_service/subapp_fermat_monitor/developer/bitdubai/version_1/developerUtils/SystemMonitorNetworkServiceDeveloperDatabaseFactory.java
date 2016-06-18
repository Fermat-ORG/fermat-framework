package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.developerUtils;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.SystemMonitorNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.SystemMonitorNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions.CantInitializeSystemMonitorNetworkServiceDataBaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_pip_plugin.layer.network_service.system_monitor.developer.bitdubai.version_1.database.SystemMonitorNetworkServiceDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Miguel Celedon - (miguelceledon@outlook.com) on 14/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class SystemMonitorNetworkServiceDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public SystemMonitorNetworkServiceDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeSystemMonitorNetworkServiceDataBaseException
     */
    public void initializeDatabase() throws CantInitializeSystemMonitorNetworkServiceDataBaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeSystemMonitorNetworkServiceDataBaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            SystemMonitorNetworkServiceDatabaseFactory systemMonitorNetworkServiceDatabaseFactory = new SystemMonitorNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = systemMonitorNetworkServiceDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeSystemMonitorNetworkServiceDataBaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("System Monitor", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table System Data columns.
         */
        List<String> systemDataColumns = new ArrayList<String>();

        systemDataColumns.add(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_SYSTEM_ID_COLUMN_NAME);
        systemDataColumns.add(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_NODE_TYPE_COLUMN_NAME);
        systemDataColumns.add(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_HARDWARE_COLUMN_NAME);
        systemDataColumns.add(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_OS_COLUMN_NAME);
        /**
         * Table System Data addition.
         */
        DeveloperDatabaseTable systemDataTable = developerObjectFactory.getNewDeveloperDatabaseTable(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_TABLE_NAME, systemDataColumns);
        tables.add(systemDataTable);

        /**
         * Table Connections columns.
         */
        List<String> connectionsColumns = new ArrayList<String>();

        connectionsColumns.add(SystemMonitorNetworkServiceDatabaseConstants.CONNECTIONS_CONNID_COLUMN_NAME);
        connectionsColumns.add(SystemMonitorNetworkServiceDatabaseConstants.CONNECTIONS_PEERID_COLUMN_NAME);
        connectionsColumns.add(SystemMonitorNetworkServiceDatabaseConstants.CONNECTIONS_PEER_IPV4_COLUMN_NAME);
        connectionsColumns.add(SystemMonitorNetworkServiceDatabaseConstants.CONNECTIONS_PEER_IPV6_COLUMN_NAME);
        connectionsColumns.add(SystemMonitorNetworkServiceDatabaseConstants.CONNECTIONS_NETWORK_SERVICE_NAME_COLUMN_NAME);
        /**
         * Table Connections addition.
         */
        DeveloperDatabaseTable connectionsTable = developerObjectFactory.getNewDeveloperDatabaseTable(SystemMonitorNetworkServiceDatabaseConstants.CONNECTIONS_TABLE_NAME, connectionsColumns);
        tables.add(connectionsTable);

        /**
         * Table Services columns.
         */
        List<String> servicesColumns = new ArrayList<String>();

        servicesColumns.add(SystemMonitorNetworkServiceDatabaseConstants.SERVICES_ID_COLUMN_NAME);
        servicesColumns.add(SystemMonitorNetworkServiceDatabaseConstants.SERVICES_NAME_COLUMN_NAME);
        servicesColumns.add(SystemMonitorNetworkServiceDatabaseConstants.SERVICES_TYPE_COLUMN_NAME);
        servicesColumns.add(SystemMonitorNetworkServiceDatabaseConstants.SERVICES_SUBTYPE_COLUMN_NAME);
        /**
         * Table Services addition.
         */
        DeveloperDatabaseTable servicesTable = developerObjectFactory.getNewDeveloperDatabaseTable(SystemMonitorNetworkServiceDatabaseConstants.SERVICES_TABLE_NAME, servicesColumns);
        tables.add(servicesTable);


        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
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
            List<DatabaseTableRecord> records = selectedTable.getRecords();
            for (DatabaseTableRecord row : records) {
                List<String> developerRow = new ArrayList<String>();
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
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            database.closeDatabase();
            return returnedRecords;
        } catch (Exception e) {
            database.closeDatabase();
            return returnedRecords;
        }
        database.closeDatabase();
        return returnedRecords;
    }

//    @Override
//    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
//        this.pluginDatabaseSystem = pluginDatabaseSystem;
//    }
//
//    @Override
//    public void setPluginId(UUID pluginId) {
//        this.pluginId = pluginId;
//    }
}