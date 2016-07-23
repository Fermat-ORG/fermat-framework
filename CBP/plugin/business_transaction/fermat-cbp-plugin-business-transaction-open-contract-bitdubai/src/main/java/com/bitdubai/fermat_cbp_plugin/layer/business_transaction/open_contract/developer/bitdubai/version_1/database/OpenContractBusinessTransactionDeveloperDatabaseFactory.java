package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.exceptions.CantInitializeOpenContractBusinessTransactionDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Manuel Perez - (darkestpriest@gmail.com) on 29/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class OpenContractBusinessTransactionDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {
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
    public OpenContractBusinessTransactionDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeOpenContractBusinessTransactionDatabaseException
     */
    public void initializeDatabase() throws CantInitializeOpenContractBusinessTransactionDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, OpenContractBusinessTransactionDatabaseConstants.DATABASE_NAME);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeOpenContractBusinessTransactionDatabaseException(cantOpenDatabaseException.getMessage());
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            OpenContractBusinessTransactionDatabaseFactory openContractBusinessTransactionDatabaseFactory = new OpenContractBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = openContractBusinessTransactionDatabaseFactory.createDatabase(pluginId, OpenContractBusinessTransactionDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeOpenContractBusinessTransactionDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(OpenContractBusinessTransactionDatabaseConstants.DATABASE_NAME, this.pluginId.toString()));
        return databases;
    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();
        /**
         * Table Open Contract columns.
         */
        List<String> openContractColumns = new ArrayList<String>();

        openContractColumns.add(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSACTION_ID_COLUMN_NAME);
        openContractColumns.add(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_NEGOTIATION_ID_COLUMN_NAME);
        openContractColumns.add(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME);
        openContractColumns.add(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_STATUS_COLUMN_NAME);
        openContractColumns.add(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
        openContractColumns.add(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TRANSMISSION_STATUS_COLUMN_NAME);
        openContractColumns.add(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_TYPE_COLUMN_NAME);
        openContractColumns.add(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_CONTRACT_XML_COLUMN_NAME);

        /**
         * Table Open Contract addition.
         */
        DeveloperDatabaseTable openContractTable = developerObjectFactory.getNewDeveloperDatabaseTable(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_TABLE_NAME, openContractColumns);
        tables.add(openContractTable);

        /**
         * Events Recorder table
         * */
        List<String> eventsRecorderColumns = new ArrayList<String>();

        eventsRecorderColumns.add(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_ID_COLUMN_NAME);
        eventsRecorderColumns.add(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_EVENT_COLUMN_NAME);
        eventsRecorderColumns.add(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_SOURCE_COLUMN_NAME);
        eventsRecorderColumns.add(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_STATUS_COLUMN_NAME);
        eventsRecorderColumns.add(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME);

        DeveloperDatabaseTable eventsRecorderTable = developerObjectFactory.getNewDeveloperDatabaseTable(OpenContractBusinessTransactionDatabaseConstants.OPEN_CONTRACT_EVENTS_RECORDED_TABLE_NAME, eventsRecorderColumns);
        tables.add(eventsRecorderTable);

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
//    @Override
//    public void setPluginId(UUID pluginId) {
//        this.pluginId = pluginId;
//    }
}