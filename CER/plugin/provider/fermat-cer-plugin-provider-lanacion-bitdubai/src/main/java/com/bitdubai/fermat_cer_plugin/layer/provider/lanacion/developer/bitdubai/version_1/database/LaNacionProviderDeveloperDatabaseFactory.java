package com.bitdubai.fermat_cer_plugin.layer.provider.lanacion.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cer_plugin.layer.provider.lanacion.developer.bitdubai.version_1.exceptions.CantInitializeLaNacionProviderDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cer_plugin.layer.provider.lanacion.developer.bitdubai.version_1.database.LaNacionProviderDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 08/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class LaNacionProviderDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public LaNacionProviderDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeLaNacionProviderDatabaseException
     */
    public void initializeDatabase() throws CantInitializeLaNacionProviderDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeLaNacionProviderDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            LaNacionProviderDatabaseFactory lanacionProviderDatabaseFactory = new LaNacionProviderDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = lanacionProviderDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeLaNacionProviderDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("LaNacion", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Provider Info columns.
         */
        List<String> providerInfoColumns = new ArrayList<String>();

        providerInfoColumns.add(LaNacionProviderDatabaseConstants.PROVIDER_INFO_ID_COLUMN_NAME);
        providerInfoColumns.add(LaNacionProviderDatabaseConstants.PROVIDER_INFO_NAME_COLUMN_NAME);
        /**
         * Table Provider Info addition.
         */
        DeveloperDatabaseTable providerInfoTable = developerObjectFactory.getNewDeveloperDatabaseTable(LaNacionProviderDatabaseConstants.PROVIDER_INFO_TABLE_NAME, providerInfoColumns);
        tables.add(providerInfoTable);

        /**
         * Table Query History columns.
         */
        List<String> queryHistoryColumns = new ArrayList<String>();

        queryHistoryColumns.add(LaNacionProviderDatabaseConstants.QUERY_HISTORY_ID_COLUMN_NAME);
        queryHistoryColumns.add(LaNacionProviderDatabaseConstants.QUERY_HISTORY_FROM_CURRENCY_COLUMN_NAME);
        queryHistoryColumns.add(LaNacionProviderDatabaseConstants.QUERY_HISTORY_TO_CURRENCY_COLUMN_NAME);
        queryHistoryColumns.add(LaNacionProviderDatabaseConstants.QUERY_HISTORY_SALE_PRICE_COLUMN_NAME);
        queryHistoryColumns.add(LaNacionProviderDatabaseConstants.QUERY_HISTORY_PURCHASE_PRICE_COLUMN_NAME);
        queryHistoryColumns.add(LaNacionProviderDatabaseConstants.QUERY_HISTORY_TIMESTAMP_COLUMN_NAME);
        /**
         * Table Query History addition.
         */
        DeveloperDatabaseTable queryHistoryTable = developerObjectFactory.getNewDeveloperDatabaseTable(LaNacionProviderDatabaseConstants.QUERY_HISTORY_TABLE_NAME, queryHistoryColumns);
        tables.add(queryHistoryTable);


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
        for (DatabaseTableRecord row : records) {
            List<String> developerRow = new ArrayList<String>();
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