package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.developerUtils;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 2015.06.25..
 */
public class DeveloperDatabaseFactory {
    String userId;
    String pluginId;

    /**
     * Constructor
     * @param userId
     * @param pluginId
     */
    public DeveloperDatabaseFactory(String userId, String pluginId){
        this.userId = userId;
        this.pluginId = pluginId;
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(this.userId.toString(), this.pluginId.toString()));
        return databases;
    }


    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        try {
            List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

            /**
             * Crypto Transactions columns
             */
            List<String> cryptoTransactionsTableColumns = new ArrayList<String>();
            cryptoTransactionsTableColumns.add(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME);
            cryptoTransactionsTableColumns.add(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_HASH_COLUMN_NAME);
            cryptoTransactionsTableColumns.add(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_PROTOCOL_STS_COLUMN_NAME);
            cryptoTransactionsTableColumns.add(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRANSACTION_STS_COLUMN_NAME);
            /**
             * cryptoTransactions table
             */
            DeveloperDatabaseTable cryptoTransactionsTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_NAME, cryptoTransactionsTableColumns);
            tables.add(cryptoTransactionsTable);

            /**
             * Fermat transaction columns
             */
            List<String> FermatTransactionTableColumns = new ArrayList<String>();
            FermatTransactionTableColumns.add(CryptoVaultDatabaseConstants.FERMAT_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME);

            /**
             * Fermat transaction table
             */
            DeveloperDatabaseTable fermatTransactionsTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoVaultDatabaseConstants.FERMAT_TRANSACTIONS_TABLE_NAME, FermatTransactionTableColumns);
            tables.add(fermatTransactionsTable);

            /**
             * TransitionProtocol_Status  columns
             */
            List<String> TransitionProtocol_StatusTableColumns = new ArrayList<String>();
            TransitionProtocol_StatusTableColumns.add(CryptoVaultDatabaseConstants.TRANSITION_PROTOCOL_STATUS_TABLE_TIMESTAMP_COLUMN_NAME);
            TransitionProtocol_StatusTableColumns.add(CryptoVaultDatabaseConstants.TRANSITION_PROTOCOL_STATUS_TABLE_OCURRENCES_COLUMN_NAME);

            /**
             * TransitionProtocol_Status table
             */
            DeveloperDatabaseTable TransitionProtocol_StatusTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoVaultDatabaseConstants.TRANSITION_PROTOCOL_STATUS_TABLE_NAME, TransitionProtocol_StatusTableColumns);
            tables.add(TransitionProtocol_StatusTable);

            return tables;
        }
        catch(Exception exception){
            throw exception;
        }
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
