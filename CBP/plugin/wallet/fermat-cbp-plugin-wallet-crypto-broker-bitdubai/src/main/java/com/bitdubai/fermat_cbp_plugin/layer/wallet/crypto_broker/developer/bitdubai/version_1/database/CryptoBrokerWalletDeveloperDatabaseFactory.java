package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerWalletDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 30/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CryptoBrokerWalletDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public CryptoBrokerWalletDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeCryptoBrokerWalletDatabaseException
     */
    public void initializeDatabase() throws CantInitializeCryptoBrokerWalletDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCryptoBrokerWalletDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CryptoBrokerWalletDatabaseFactory cryptoBrokerWalletDatabaseFactory = new CryptoBrokerWalletDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = cryptoBrokerWalletDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCryptoBrokerWalletDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Crypto Broker", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Crypto Broker Wallet columns.
         */
        List<String> cryptoBrokerWalletColumns = new ArrayList<String>();

        cryptoBrokerWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_WALLET_PUBLIC_KEY_COLUMN_NAME);
        cryptoBrokerWalletColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_BROKER_PUBLIC_KEY_COLUMN_NAME);
        /**
         * Table Crypto Broker Wallet addition.
         */
        DeveloperDatabaseTable cryptoBrokerWalletTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_TABLE_NAME, cryptoBrokerWalletColumns);
        tables.add(cryptoBrokerWalletTable);

        /**
         * Table Crypto Broker Stock Balance columns.
         */
        List<String> cryptoBrokerStockBalanceColumns = new ArrayList<String>();

        cryptoBrokerStockBalanceColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_WALLET_PUBLIC_KEY_COLUMN_NAME);
        cryptoBrokerStockBalanceColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BROKER_PUBLIC_KEY_COLUMN_NAME);
        cryptoBrokerStockBalanceColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_CURRENCY_TYPE_COLUMN_NAME);
        cryptoBrokerStockBalanceColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME);
        cryptoBrokerStockBalanceColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_DESCRIPTION_COLUMN_NAME);
        cryptoBrokerStockBalanceColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME);
        cryptoBrokerStockBalanceColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME);
        /**
         * Table Crypto Broker Stock Balance addition.
         */
        DeveloperDatabaseTable cryptoBrokerStockBalanceTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_TABLE_NAME, cryptoBrokerStockBalanceColumns);
        tables.add(cryptoBrokerStockBalanceTable);

        /**
         * Table Crypto Broker Stock Transactions columns.
         */
        List<String> cryptoBrokerStockTransactionsColumns = new ArrayList<String>();

        cryptoBrokerStockTransactionsColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME);
        cryptoBrokerStockTransactionsColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_WALLET_PUBLIC_KEY_COLUMN_NAME);
        cryptoBrokerStockTransactionsColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BROKER_PUBLIC_KEY_COLUMN_NAME);
        cryptoBrokerStockTransactionsColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME);
        cryptoBrokerStockTransactionsColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME);
        cryptoBrokerStockTransactionsColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME);
        cryptoBrokerStockTransactionsColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_CURRENCY_TYPE_COLUMN_NAME);
        cryptoBrokerStockTransactionsColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME);
        cryptoBrokerStockTransactionsColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        cryptoBrokerStockTransactionsColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        cryptoBrokerStockTransactionsColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME);
        cryptoBrokerStockTransactionsColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME);
        cryptoBrokerStockTransactionsColumns.add(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_STATUS_COLUMN_NAME);
        /**
         * Table Crypto Broker Stock Transactions addition.
         */
        DeveloperDatabaseTable cryptoBrokerStockTransactionsTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME, cryptoBrokerStockTransactionsColumns);
        tables.add(cryptoBrokerStockTransactionsTable);



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