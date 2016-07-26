package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database;

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
 * The Class <code>CryptoBrokerWalletDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 30/10/15.
 * Modified by Franklin 01.12.2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CryptoBrokerWalletDeveloperDatabaseFactory {
    String pluginId;
    List<String> wallets;

    public CryptoBrokerWalletDeveloperDatabaseFactory(String pluginId, List<String> wallets) {
        this.pluginId = pluginId;
        this.wallets = wallets;
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * We have one database for each walletId, so we will return all their names.
         * Remember that a database name in this plug-in is the internal wallet id.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        for (String databaseName : this.wallets)
            databases.add(developerObjectFactory.getNewDeveloperDatabase(databaseName, this.pluginId));
        return databases;
    }

    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();


        /*
         * We only have one table in each database, let's complete it
         */
        List<String> stockWalletColumns = new ArrayList<>();

        stockWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME);
        stockWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME);
        stockWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_PRICE_REFERENCE_COLUMN_NAME);
        stockWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME);
        stockWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MONEY_TYPE_COLUMN_NAME);
        stockWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME);
        stockWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME);
        stockWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME);
        stockWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME);
        stockWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_COLUMN_NAME);
        stockWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        stockWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        stockWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_ID_COLUMN_NAME);
        stockWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_SEEN_COLUMN_NAME);

        /**
         * stockWalletColumns table
         */
        DeveloperDatabaseTable cryptoTransactionsTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME, stockWalletColumns);
        tables.add(cryptoTransactionsTable);

        /**
         * Added new table stockWalletTotalBalances
         */
        List<String> stockWalletTotalBalancesColumns = new ArrayList<>();
        stockWalletTotalBalancesColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BROKER_PUBLIC_KEY_COLUMN_NAME);
        stockWalletTotalBalancesColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME);
        stockWalletTotalBalancesColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MONEY_TYPE_COLUMN_NAME);
        stockWalletTotalBalancesColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME);
        stockWalletTotalBalancesColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME);

        /**
         * stockWalletTotalBalanceColumns table
         */
        DeveloperDatabaseTable stockWalletTotalBalances = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_TABLE_NAME, stockWalletTotalBalancesColumns);
        tables.add(stockWalletTotalBalances);

        /**
         * Added new table wallet setting spread
         */
        List<String> walletSettingSpreadColumns = new ArrayList<>();
        walletSettingSpreadColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_ID_COLUMN_NAME);
        walletSettingSpreadColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_VALUE_COLUMN_NAME);
        walletSettingSpreadColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_BROKER_PUBLIC_KEY_COLUMN_NAME);
        walletSettingSpreadColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_RESTOCK_AUTOMATIC);

        /**
         * walletSettingSpreadColumns table
         */
        DeveloperDatabaseTable walletSettingSpread = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_SPREAD_TABLE_NAME, walletSettingSpreadColumns);
        tables.add(walletSettingSpread);

        /**
         * Added new table wallet setting associated
         */
        List<String> walletSettingAssociatedColumns = new ArrayList<>();
        walletSettingAssociatedColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_ID_COLUMN_NAME);
        walletSettingAssociatedColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_PLATFORM_COLUMN_NAME);
        walletSettingAssociatedColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_MERCHANDISE_COLUMN_NAME);
        walletSettingAssociatedColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_MONEY_TYPE_COLUMN_NAME);
        walletSettingAssociatedColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_PUBLIC_KEY_COLUMN_NAME);
        walletSettingAssociatedColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_BANK_ACCOUNT_COLUMN_NAME);
        walletSettingAssociatedColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_BROKER_PUBLIC_KEY_COLUMN_NAME);
        /**
         * walletSettingAssociatedColumns table
         */
        DeveloperDatabaseTable walletSettingAssociated = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_ASSOCIATED_TABLE_NAME, walletSettingAssociatedColumns);
        tables.add(walletSettingAssociated);

        /**
         * Added new table wallet setting provider
         */
        List<String> walletSettingProviderColumns = new ArrayList<>();
        walletSettingProviderColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_ID_COLUMN_NAME);
        walletSettingProviderColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_PLUGIN_COLUMN_NAME);
        walletSettingProviderColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_DESCRIPTION_COLUMN_NAME);
        walletSettingProviderColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_CURRENCY_FROM_COLUMN_NAME);
        walletSettingProviderColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_CURRENCY_TO_COLUMN_NAME);
        walletSettingProviderColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_BROKER_PUBLIC_KEY_COLUMN_NAME);
        /**
         * walletSettingAssociatedColumns table
         */
        DeveloperDatabaseTable walletSettingProvider = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_PROVIDER_TABLE_NAME, walletSettingProviderColumns);
        tables.add(walletSettingProvider);

        return tables;
    }

    public static List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, Database database, DeveloperDatabaseTable developerDatabaseTable) {
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

}