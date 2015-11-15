package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.developerUtils;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eze on 2015.06.29..
 */
public class IncomingExtraUserDeveloperDatabaseFactory {
    String pluginId;
    String databaseNAme;

    public IncomingExtraUserDeveloperDatabaseFactory (String pluginId, String databaseNAme){
        this.pluginId = pluginId;
        this.databaseNAme = databaseNAme;
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * We have one database for the whole plugin, so we will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(this.databaseNAme, this.pluginId));
        return databases;
    }

    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /*
         * First we add the IncomingExtraUserRegistry table
         * We start with the columns
         */
        List<String> incomingExtraUserRegistryColumns = new ArrayList<>();
        incomingExtraUserRegistryColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ID_COLUMN.columnName);
        incomingExtraUserRegistryColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TRANSACTION_HASH_COLUMN.columnName);
        incomingExtraUserRegistryColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ADDRESS_TO_COLUMN.columnName);
        incomingExtraUserRegistryColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_CURRENCY_COLUMN.columnName);
        incomingExtraUserRegistryColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_AMOUNT_COLUMN.columnName);
        incomingExtraUserRegistryColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ADDRESS_FROM_COLUMN.columnName);
        incomingExtraUserRegistryColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_STATUS_COLUMN.columnName);
        incomingExtraUserRegistryColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ACTION_COLUMN.columnName);
        incomingExtraUserRegistryColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName);
        incomingExtraUserRegistryColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName);
        incomingExtraUserRegistryColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TIMESTAMP_COLUMN.columnName);

        /**
         * incomingExtraUserRegistry table
         */
        DeveloperDatabaseTable  incomingExtraUserRegistryTable = developerObjectFactory.getNewDeveloperDatabaseTable(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_NAME, incomingExtraUserRegistryColumns);
        tables.add(incomingExtraUserRegistryTable);

        /*
         * EventsRecorded Registry table columns
         */
        List<String> eventsRecordedColumns = new ArrayList<>();
        eventsRecordedColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_ID_COLUMN.columnName);
        eventsRecordedColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_EVENT_COLUMN.columnName);
        eventsRecordedColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_SOURCE_COLUMN.columnName);
        eventsRecordedColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_STATUS_COLUMN.columnName);
        eventsRecordedColumns.add(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN.columnName);

        /*
         * EventsRecorded Registry table
         */
        DeveloperDatabaseTable  eventsRecordedTable = developerObjectFactory.getNewDeveloperDatabaseTable(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_NAME, eventsRecordedColumns);
        tables.add(eventsRecordedTable);


        return tables;
    }


    public static List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory,  Database database, DeveloperDatabaseTable developerDatabaseTable) {
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
            for (DatabaseTableRecord row: records){
                List<String> developerRow = new ArrayList<String>();
                /**
                 * for each row in the table list
                 */
                for (DatabaseRecord field : row.getValues()){
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
        } catch (Exception e){
            database.closeDatabase();
            return returnedRecords;
        }
        database.closeDatabase();
        return returnedRecords;
    }
}
