package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.exceptions.CantInitializeCashMoneyRestockDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>BusinessTransactionBankMoneyDestockDeveloperFactory.java
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Franklin Marcano - (franklinmarcano970@gmail.com) on 16/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class StockTransactionsCashMoneyRestockDeveloperFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {
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
    public StockTransactionsCashMoneyRestockDeveloperFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    public void initializeDatabase() throws CantInitializeCashMoneyRestockDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_DATABASE_NAME);
            database.closeDatabase();

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCashMoneyRestockDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            StockTransactionsCashMoneyRestockDatabaseFactory stockTransactionsCashMoneyRestockDatabaseFactory = new StockTransactionsCashMoneyRestockDatabaseFactory(this.pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = stockTransactionsCashMoneyRestockDatabaseFactory.createDatabase(pluginId, StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_DATABASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCashMoneyRestockDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_DATABASE_NAME, StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_DATABASE_NAME));

        return databases;
    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Project columns.
         */
        List<String> projectColumns = new ArrayList<String>();

        /**
         * Table Asset Factory addition.
         */
        projectColumns.add(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_TRANSACTION_ID_COLUMN_NAME);
        projectColumns.add(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME);
        projectColumns.add(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_FIAT_CURRENCY_COLUMN_NAME);
        projectColumns.add(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME);
        projectColumns.add(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_CSH_WALLET_PUBLIC_KEY_COLUMN_NAME);
        projectColumns.add(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_CASH_REFERENCE_COLUMN_NAME);
        projectColumns.add(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_CONCEPT_COLUMN_NAME);
        projectColumns.add(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_AMOUNT_COLUMN_NAME);
        projectColumns.add(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_TIMESTAMP_COLUMN_NAME);
        projectColumns.add(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_MEMO_COLUMN_NAME);
        projectColumns.add(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_TRANSACTION_STATUS_COLUMN_NAME);
        projectColumns.add(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_PRICE_REFERENCE_COLUMN_NAME);
        projectColumns.add(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_ORIGIN_TRANSACTION_COLUMN_NAME);
        projectColumns.add(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_ORIGIN_TRANSACTION_ID_COLUMN_NAME);

        DeveloperDatabaseTable bankMoneyRestockTable = developerObjectFactory.getNewDeveloperDatabaseTable(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_TABLE_NAME, projectColumns);
        tables.add(bankMoneyRestockTable);

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
}
