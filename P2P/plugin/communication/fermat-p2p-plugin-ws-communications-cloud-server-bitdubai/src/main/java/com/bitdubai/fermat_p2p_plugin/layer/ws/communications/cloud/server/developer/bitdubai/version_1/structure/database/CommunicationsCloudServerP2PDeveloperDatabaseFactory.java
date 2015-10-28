/*
 * @#CommunicationsCloudServerP2PDeveloperDatabaseFactory.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.database;


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
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.exceptions.CantInitializeCommunicationsCloudServerP2PDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.p2p.communications_cloud_server.developer.bitdubai.version_1.database.CommunicationsCloudServerP2PDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 22/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CommunicationsCloudServerP2PDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public CommunicationsCloudServerP2PDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeCommunicationsCloudServerP2PDatabaseException
     */
    public void initializeDatabase() throws CantInitializeCommunicationsCloudServerP2PDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCommunicationsCloudServerP2PDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CommunicationsCloudServerP2PDatabaseFactory communicationsCloudServerP2PDatabaseFactory = new CommunicationsCloudServerP2PDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = communicationsCloudServerP2PDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCommunicationsCloudServerP2PDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Communications Cloud Server", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table known servers catalog columns.
         */
        List<String> knownserverscatalogColumns = new ArrayList<String>();

        knownserverscatalogColumns.add(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        knownserverscatalogColumns.add(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_NAME_COLUMN_NAME);
        knownserverscatalogColumns.add(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_IP_COLUMN_NAME);
        knownserverscatalogColumns.add(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_DEFAULT_PORT_COLUMN_NAME);
        knownserverscatalogColumns.add(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_WEB_SERVICE_PORT_COLUMN_NAME);
        knownserverscatalogColumns.add(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LATITUDE_COLUMN_NAME);
        knownserverscatalogColumns.add(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LONGITUDE_COLUMN_NAME);
        knownserverscatalogColumns.add(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LATE_NOTIFICATION_COUNTER_COLUMN_NAME);
        knownserverscatalogColumns.add(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_OFFLINE_COUNTER_COLUMN_NAME);
        knownserverscatalogColumns.add(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_REGISTERED_TIMESTAMP_COLUMN_NAME);
        knownserverscatalogColumns.add(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME);
        /**
         * Table known servers catalog addition.
         */
        DeveloperDatabaseTable knownserverscatalogTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_TABLE_NAME, knownserverscatalogColumns);
        tables.add(knownserverscatalogTable);

        /**
         * Table platform component profile registered history columns.
         */
        List<String> platformcomponentprofileregisteredhistoryColumns = new ArrayList<String>();

        platformcomponentprofileregisteredhistoryColumns.add(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        platformcomponentprofileregisteredhistoryColumns.add(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_NAME_COLUMN_NAME);
        platformcomponentprofileregisteredhistoryColumns.add(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_ALIAS_COLUMN_NAME);
        platformcomponentprofileregisteredhistoryColumns.add(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_COMPONENT_TYPE_COLUMN_NAME);
        platformcomponentprofileregisteredhistoryColumns.add(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_NETWORK_SERVICE_TYPE_COLUMN_NAME);
        platformcomponentprofileregisteredhistoryColumns.add(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_LAST_LATITUDE_COLUMN_NAME);
        platformcomponentprofileregisteredhistoryColumns.add(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_LAST_LONGITUDE_COLUMN_NAME);
        platformcomponentprofileregisteredhistoryColumns.add(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_EXTRA_DATA_COLUMN_NAME);
        platformcomponentprofileregisteredhistoryColumns.add(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME);
        /**
         * Table platform component profile registered history addition.
         */
        DeveloperDatabaseTable platformcomponentprofileregisteredhistoryTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_TABLE_NAME, platformcomponentprofileregisteredhistoryColumns);
        tables.add(platformcomponentprofileregisteredhistoryTable);



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
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
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
                developerRow.add(field.getValue().toString());
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
