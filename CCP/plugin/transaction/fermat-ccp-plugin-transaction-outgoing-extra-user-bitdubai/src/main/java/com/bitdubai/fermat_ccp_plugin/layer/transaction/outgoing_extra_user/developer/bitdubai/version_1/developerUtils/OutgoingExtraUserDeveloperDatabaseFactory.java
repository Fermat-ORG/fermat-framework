package com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.developerUtils;

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
public class OutgoingExtraUserDeveloperDatabaseFactory {
    String pluginId;
    String databaseNAme;

    public OutgoingExtraUserDeveloperDatabaseFactory (String pluginId, String databaseNAme){
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
         * First we add the OutgoingExtraUser table
         * We start with the columns
         */
        List<String> outgoingExtraUserTableColumns = new ArrayList<>();
        outgoingExtraUserTableColumns.add(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_ID_COLUMN_NAME);
        outgoingExtraUserTableColumns.add(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_WALLET_ID_TO_DEBIT_COLUMN_NAME);
        outgoingExtraUserTableColumns.add(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_HASH_COLUMN_NAME);
        outgoingExtraUserTableColumns.add(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ADDRESS_FROM_COLUMN_NAME);
        outgoingExtraUserTableColumns.add(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ADDRESS_TO_COLUMN_NAME);
        outgoingExtraUserTableColumns.add(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_CURRENY_COLUMN_NAME);
        outgoingExtraUserTableColumns.add(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_AMOUNT_COLUMN_NAME);
        outgoingExtraUserTableColumns.add(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TRANSACTION_STATUS_COLUMN_NAME);
        outgoingExtraUserTableColumns.add(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_DESCRIPTION_COLUMN_NAME);
        outgoingExtraUserTableColumns.add(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_TIMESTAMP_COLUMN_NAME);
        outgoingExtraUserTableColumns.add(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_CRYPTO_STATUS_COLUMN_NAME);

        outgoingExtraUserTableColumns.add(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_FROM_ID_COLUMN_NAME);
        outgoingExtraUserTableColumns.add(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME);
        outgoingExtraUserTableColumns.add(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_TO_ID_COLUMN_NAME);
        outgoingExtraUserTableColumns.add(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_ACTOR_TO_TYPE_COLUMN_NAME);


        /**
         * outgoingExtraUser table
         */
        DeveloperDatabaseTable  outgoingExtraUserRegistryTable = developerObjectFactory.getNewDeveloperDatabaseTable(com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_TABLE_NAME, outgoingExtraUserTableColumns);
        tables.add(outgoingExtraUserRegistryTable);

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
