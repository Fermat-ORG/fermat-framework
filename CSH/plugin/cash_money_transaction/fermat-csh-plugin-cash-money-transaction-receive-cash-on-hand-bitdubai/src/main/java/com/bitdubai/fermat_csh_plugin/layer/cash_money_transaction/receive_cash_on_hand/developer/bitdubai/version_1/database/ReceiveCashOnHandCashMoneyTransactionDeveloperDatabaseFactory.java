package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.receive_cash_on_hand.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.receive_cash_on_hand.developer.bitdubai.version_1.exceptions.CantInitializeReceiveCashOnHandCashMoneyTransactionDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.receive_cash_on_hand.developer.bitdubai.version_1.database.ReceiveCashOnHandCashMoneyTransactionDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 01/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class ReceiveCashOnHandCashMoneyTransactionDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public ReceiveCashOnHandCashMoneyTransactionDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeReceiveCashOnHandCashMoneyTransactionDatabaseException
     */
    public void initializeDatabase() throws CantInitializeReceiveCashOnHandCashMoneyTransactionDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeReceiveCashOnHandCashMoneyTransactionDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            ReceiveCashOnHandCashMoneyTransactionDatabaseFactory receiveCashOnHandCashMoneyTransactionDatabaseFactory = new ReceiveCashOnHandCashMoneyTransactionDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = receiveCashOnHandCashMoneyTransactionDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeReceiveCashOnHandCashMoneyTransactionDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Receive Cash On Hand", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Receive Cash On Hand columns.
         */
        List<String> receiveCashOnHandColumns = new ArrayList<String>();

        receiveCashOnHandColumns.add(ReceiveCashOnHandCashMoneyTransactionDatabaseConstants.RECEIVE_CASH_ON_HAND_CASH_TRANSACTION_ID_COLUMN_NAME);
        receiveCashOnHandColumns.add(ReceiveCashOnHandCashMoneyTransactionDatabaseConstants.RECEIVE_CASH_ON_HAND_STATUS_COLUMN_NAME);
        receiveCashOnHandColumns.add(ReceiveCashOnHandCashMoneyTransactionDatabaseConstants.RECEIVE_CASH_ON_HAND_PUBLIC_KEY_BROKER_COLUMN_NAME);
        receiveCashOnHandColumns.add(ReceiveCashOnHandCashMoneyTransactionDatabaseConstants.RECEIVE_CASH_ON_HAND_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        receiveCashOnHandColumns.add(ReceiveCashOnHandCashMoneyTransactionDatabaseConstants.RECEIVE_CASH_ON_HAND_BALANCE_TYPE_COLUMN_NAME);
        receiveCashOnHandColumns.add(ReceiveCashOnHandCashMoneyTransactionDatabaseConstants.RECEIVE_CASH_ON_HAND_TRANSACTION_TYPE_COLUMN_NAME);
        receiveCashOnHandColumns.add(ReceiveCashOnHandCashMoneyTransactionDatabaseConstants.RECEIVE_CASH_ON_HAND_AMOUNT_COLUMN_NAME);
        receiveCashOnHandColumns.add(ReceiveCashOnHandCashMoneyTransactionDatabaseConstants.RECEIVE_CASH_ON_HAND_CASH_CURRENCY_TYPE_COLUMN_NAME);
        receiveCashOnHandColumns.add(ReceiveCashOnHandCashMoneyTransactionDatabaseConstants.RECEIVE_CASH_ON_HAND_CASH_REFERENCE_COLUMN_NAME);
        /**
         * Table Receive Cash On Hand addition.
         */
        DeveloperDatabaseTable receiveCashOnHandTable = developerObjectFactory.getNewDeveloperDatabaseTable(ReceiveCashOnHandCashMoneyTransactionDatabaseConstants.RECEIVE_CASH_ON_HAND_TABLE_NAME, receiveCashOnHandColumns);
        tables.add(receiveCashOnHandTable);



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