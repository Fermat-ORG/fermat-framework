package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantInitializeCashMoneyWalletDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database.CashMoneyWalletDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 13/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CashMoneyWalletDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public CashMoneyWalletDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeCashMoneyWalletDatabaseException
     */
    public void initializeDatabase() throws CantInitializeCashMoneyWalletDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCashMoneyWalletDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CashMoneyWalletDatabaseFactory cashMoneyWalletDatabaseFactory = new CashMoneyWalletDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = cashMoneyWalletDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCashMoneyWalletDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Cash Money", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Cash Money columns.
         */
        List<String> cashMoneyColumns = new ArrayList<String>();

        cashMoneyColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_TRANSACTION_ID_COLUMN_NAME);
        cashMoneyColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_WALLET_KEY_BROKER_COLUMN_NAME);
        cashMoneyColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        cashMoneyColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_BROKER_COLUMN_NAME);
        cashMoneyColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_TYPE_COLUMN_NAME);
        cashMoneyColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_TRANSACTION_TYPE_COLUMN_NAME);
        cashMoneyColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_AMOUNT_COLUMN_NAME);
        cashMoneyColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_CURRENCY_TYPE_COLUMN_NAME);
        cashMoneyColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_REFERENCE_COLUMN_NAME);
        cashMoneyColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        cashMoneyColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        cashMoneyColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_TIMESTAMP_COLUMN_NAME);
        cashMoneyColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_MEMO_COLUMN_NAME);
        cashMoneyColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_STATUS_COLUMN_NAME);
        /**
         * Table Cash Money addition.
         */
        DeveloperDatabaseTable cashMoneyTable = developerObjectFactory.getNewDeveloperDatabaseTable(CashMoneyWalletDatabaseConstants.CASH_MONEY_TABLE_NAME, cashMoneyColumns);
        tables.add(cashMoneyTable);

        /**
         * Table Cash Money Total Balances columns.
         */
        List<String> cashMoneyTotalBalancesColumns = new ArrayList<String>();

        cashMoneyTotalBalancesColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_WALLET_KEY_BROKER_COLUMN_NAME);
        cashMoneyTotalBalancesColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_PUBLIC_KEY_BROKER_COLUMN_NAME);
        cashMoneyTotalBalancesColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_CASH_CURRENCY_TYPE_COLUMN_NAME);
        cashMoneyTotalBalancesColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_NAME_COLUMN_NAME);
        cashMoneyTotalBalancesColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_DESCRIPTION_COLUMN_NAME);
        cashMoneyTotalBalancesColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_AVAILABLE_BALANCE_COLUMN_NAME);
        cashMoneyTotalBalancesColumns.add(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_BOOK_BALANCE_COLUMN_NAME);
        /**
         * Table Cash Money Total Balances addition.
         */
        DeveloperDatabaseTable cashMoneyTotalBalancesTable = developerObjectFactory.getNewDeveloperDatabaseTable(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_TABLE_NAME, cashMoneyTotalBalancesColumns);
        tables.add(cashMoneyTotalBalancesTable);



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

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}