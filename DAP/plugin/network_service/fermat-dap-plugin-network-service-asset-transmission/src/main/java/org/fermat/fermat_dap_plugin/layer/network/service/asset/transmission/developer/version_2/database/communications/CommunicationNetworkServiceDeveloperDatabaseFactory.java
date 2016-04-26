/*
 * @#TemplateNetworkServiceDeveloperDatabaseFactory.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.database.communications;

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

import org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions.CantInitializeDAPMessageNetworkServiceDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_2.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory</code> is
 * throw when error occurred updating new record in a table of the data base
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/10/15 - Jose Briceño - (josebricenor@gmail.com) on 18/02/16.
 *
 * @version 2.0
 * @since Java JDK 1.7
 */

public class CommunicationNetworkServiceDeveloperDatabaseFactory {


    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;
    Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public CommunicationNetworkServiceDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeDAPMessageNetworkServiceDatabaseException
     */
    public Database initializeDatabase() throws CantInitializeDAPMessageNetworkServiceDatabaseException {
        try {

             /*
              * Open new database connection
              */
            return database = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeDAPMessageNetworkServiceDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */

            CommunicationNetworkServiceDatabaseFactory dapMessageNetworkServiceDatabaseFactory = new CommunicationNetworkServiceDatabaseFactory(pluginDatabaseSystem, pluginId);

            try {
                  /*
                   * We create the new database
                   */
                return database = dapMessageNetworkServiceDatabaseFactory.createDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeDAPMessageNetworkServiceDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME, this.pluginId.toString()));
//        databases.add(developerObjectFactory.getNewDeveloperDatabase("Asset Transmission", CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table DAP_Message columns.
         */
        List<String> dapMessageColumns = new ArrayList<String>();

        dapMessageColumns.add(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_ID_COLUMN_NAME);
        dapMessageColumns.add(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_DATA_COLUMN_NAME);
        dapMessageColumns.add(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_TYPE_COLUMN_NAME);
        dapMessageColumns.add(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_SUBJECT_COLUMN_NAME);
        dapMessageColumns.add(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_STATUS_COLUMN_NAME);

        /**
         * Table dapMessageColumns addition.
         */
        DeveloperDatabaseTable dapMessageTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_TABLE_NAME, dapMessageColumns);
        tables.add(dapMessageTable);

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

            return returnedRecords;
        } catch (Exception e) {

            return returnedRecords;
        }

        return returnedRecords;
    }
}