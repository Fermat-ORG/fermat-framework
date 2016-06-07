package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantInitializeBankMoneyWalletDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database.BankMoneyWalletDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 13/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class BankMoneyWalletDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public BankMoneyWalletDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeBankMoneyWalletDatabaseException
     */
    public void initializeDatabase() throws CantInitializeBankMoneyWalletDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeBankMoneyWalletDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            BankMoneyWalletDatabaseFactory bankMoneyWalletDatabaseFactory = new BankMoneyWalletDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = bankMoneyWalletDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeBankMoneyWalletDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Bank Money", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Bank Money columns.
         */
        List<String> bankMoneyColumns = new ArrayList<String>();

        bankMoneyColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_TRANSACTION_ID_COLUMN_NAME);
        bankMoneyColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_BALANCE_TYPE_COLUMN_NAME);
        bankMoneyColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTION_TYPE_COLUMN_NAME);
        bankMoneyColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_AMOUNT_COLUMN_NAME);
        bankMoneyColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_CURRENCY_TYPE_COLUMN_NAME);
        bankMoneyColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_OPERATION_TYPE_COLUMN_NAME);
        bankMoneyColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_DOCUMENT_REFERENCE_COLUMN_NAME);
        bankMoneyColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_NAME_COLUMN_NAME);
        bankMoneyColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME);
        bankMoneyColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_TYPE_COLUMN_NAME);
        bankMoneyColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        bankMoneyColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        bankMoneyColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_TIMESTAMP_COLUMN_NAME);
        bankMoneyColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_MEMO_COLUMN_NAME);
        bankMoneyColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_STATUS_COLUMN_NAME);
        /**
         * Table Bank Money addition.
         */
        DeveloperDatabaseTable bankMoneyTable = developerObjectFactory.getNewDeveloperDatabaseTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTIONS_TABLE_NAME, bankMoneyColumns);
        tables.add(bankMoneyTable);

        /**
         * Table Bank Money Total Balances columns.
         */
        List<String> bankMoneyTotalBalancesColumns = new ArrayList<String>();

        bankMoneyTotalBalancesColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_PUBLIC_KEY_COLUMN_NAME);
        bankMoneyTotalBalancesColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME);
        bankMoneyTotalBalancesColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_BANK_CURRENCY_TYPE_COLUMN_NAME);
        bankMoneyTotalBalancesColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_ALIAS_COLUMN_NAME);
        bankMoneyTotalBalancesColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_ACCOUNT_TYPE_COLUMN_NAME);
        bankMoneyTotalBalancesColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_AVAILABLE_BALANCE_COLUMN_NAME);
        bankMoneyTotalBalancesColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_BOOK_BALANCE_COLUMN_NAME);
        bankMoneyTotalBalancesColumns.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_IMAGE_ID_COLUMN_NAME);

        /**
         * Table Bank Money Total Balances addition.
         */
        DeveloperDatabaseTable bankMoneyTotalBalancesTable = developerObjectFactory.getNewDeveloperDatabaseTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_TABLE_NAME, bankMoneyTotalBalancesColumns);
        tables.add(bankMoneyTotalBalancesTable);


        /**
         * Table Bank Money BANK NAME columns.
         */
        List<String> bankMoneyBankName = new ArrayList<String>();

        bankMoneyBankName.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_PUBLIC_KEY_COLUMN);
        bankMoneyBankName.add(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_NAME_COLUMN);

        /**
         * Table Bank Money BANK NAME addition.
         */
        DeveloperDatabaseTable bankMoneyBankNameTable = developerObjectFactory.getNewDeveloperDatabaseTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_NAME_TABLE, bankMoneyBankName);
        tables.add(bankMoneyBankNameTable);


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

//    @Override
//    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
//        this.pluginDatabaseSystem = pluginDatabaseSystem;
//    }
//
//    @Override
//    public void setPluginId(UUID pluginId) {
//        this.pluginId = pluginId;
//    }
}