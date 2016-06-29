package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.crypto_wallet.developer.bitdubai.version_1.developerUtils;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletDatabaseConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eze on 2015.06.29..
 */
public class DeveloperDatabaseFactory {

    String pluginId;
    List<String> walletsIds;

    public DeveloperDatabaseFactory (String pluginId, List<String> walletsIds){
        this.pluginId = pluginId;
        this.walletsIds = walletsIds;
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * We have one database for each walletId, so we will return all their names.
         * Remember that a database name in this plug-in is the internal wallet id.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        for(String databaseName : this.walletsIds)
            databases.add(developerObjectFactory.getNewDeveloperDatabase(databaseName, this.pluginId));
        return databases;
    }

    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /*
         * We only have one table in each database, let's complete it
         */
        List<String> basicWalletBitcoinWalletColumns = new ArrayList<>();
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ID_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_TO_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_TO_TYPE_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_AMOUNT_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TYPE_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TIME_STAMP_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_MEMO_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_RUNNING_NETWORK_TYPE);
        basicWalletBitcoinWalletColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME);

        /**
         * basicWalletBitcoinWalletColumns table
         */
        DeveloperDatabaseTable  cryptoTransactionsTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_NAME, basicWalletBitcoinWalletColumns);
        tables.add(cryptoTransactionsTable);

        /**
         * Added new table BitcoinWalletWalletTotalBalances
         */
        List<String> bitcoinWalletWalletTotalBalancesColumns = new ArrayList<>();
        bitcoinWalletWalletTotalBalancesColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_ID_COLUMN_NAME);
        bitcoinWalletWalletTotalBalancesColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
        bitcoinWalletWalletTotalBalancesColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
        bitcoinWalletWalletTotalBalancesColumns.add(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_RUNNING_NETWORK_TYPE);



        /**
         * basicWalletBitcoinWalletColumns table
         */
        DeveloperDatabaseTable  bitcoinWalletWalletTotalBalances = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_NAME, bitcoinWalletWalletTotalBalancesColumns);
        tables.add(bitcoinWalletWalletTotalBalances);


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
