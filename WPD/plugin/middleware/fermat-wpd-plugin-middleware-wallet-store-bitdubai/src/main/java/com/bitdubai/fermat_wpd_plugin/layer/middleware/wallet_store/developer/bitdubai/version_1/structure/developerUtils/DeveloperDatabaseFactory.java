package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.developerUtils;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 7/24/15.
 */
public class DeveloperDatabaseFactory {
    String databaseId;

    public DeveloperDatabaseFactory(String databaseId) {
        this.databaseId = databaseId;
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(WalletStoreMiddlewareDatabaseConstants.DATABASE_NAME, databaseId));
        return databases;
    }

    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Wallet table columns
         */
        List<String> walletTableColumns = new ArrayList<String>();
        walletTableColumns.add(WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_ID_COLUMN_NAME);
        walletTableColumns.add(WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_INSTALATIONSTATUS_COLUMN_NAME);

        /**
         * Wallet table
         */
        DeveloperDatabaseTable  walletTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_TABLE_NAME, walletTableColumns);
        tables.add(walletTable);

        /**
         * Skin table columns
         */
        List<String> skinTableColumns = new ArrayList<String>();
        skinTableColumns.add(WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_ID_COLUMN_NAME);
        skinTableColumns.add(WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_INSTALATIONSTATUS_COLUMN_NAME);

        /**
         * Skin table
         */
        DeveloperDatabaseTable  skinTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_TABLE_NAME, skinTableColumns);
        tables.add(skinTable );

        /**
         * Language table columns
         */
        List<String> languageTableColumns = new ArrayList<String>();
        languageTableColumns .add(WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_ID_COLUMN_NAME);
        languageTableColumns .add(WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_INSTALATIONSTATUS_COLUMN_NAME);

        /**
         * Wallet table
         */
        DeveloperDatabaseTable  languageTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_TABLE_NAME, languageTableColumns );
        tables.add(languageTable);


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
