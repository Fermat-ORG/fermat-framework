package com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 11/12/15.
 */
public class UserLevelBusinessTransactionCustomerBrokerSaleDeveloperFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {
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
    public UserLevelBusinessTransactionCustomerBrokerSaleDeveloperFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    public void initializeDatabase() throws CantInitializeCustomerBrokerSaleDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_DATABASE_NAME);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCustomerBrokerSaleDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            UserLevelBusinessTransactionCustomerBrokerSaleDatabaseFactory userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseFactory = new UserLevelBusinessTransactionCustomerBrokerSaleDatabaseFactory(this.pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseFactory.createDatabase(pluginId, UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCustomerBrokerSaleDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_DATABASE_NAME, this.pluginId.toString()));

        return databases;
    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Customer Broker Sale columns.
         */
        List<String> customerBrokerSaleColumns = new ArrayList<String>();

        /**
         * Table Customer Broker Purchase addition.
         */
        customerBrokerSaleColumns.add(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_ID_COLUMN_NAME);
        customerBrokerSaleColumns.add(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TIMESTAMP_COLUMN_NAME);
        customerBrokerSaleColumns.add(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_CONTRACT_TRANSACTION_ID_COLUMN_NAME);
        customerBrokerSaleColumns.add(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_PURCHASE_STATUS_COLUMN_NAME);
        customerBrokerSaleColumns.add(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_CONTRACT_STATUS_COLUMN_NAME);
        customerBrokerSaleColumns.add(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_STATUS_COLUMN_NAME);
        customerBrokerSaleColumns.add(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_CURRENCY_TYPE_COLUMN_NAME);
        customerBrokerSaleColumns.add(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_TYPE_COLUMN_NAME);
        customerBrokerSaleColumns.add(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_MEMO_COLUMN_NAME);

        DeveloperDatabaseTable customerBrokerSaleTable = developerObjectFactory.getNewDeveloperDatabaseTable(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TABLE_NAME, customerBrokerSaleColumns);
        tables.add(customerBrokerSaleTable);

        /**
         * Table Events Recorded columns.
         */
        List<String> eventsColumns = new ArrayList<String>();

        /**
         * Table Customer Broker Purchase Events Recorded addition.
         */
        eventsColumns.add(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_EVENTS_RECORDED_ID_COLUMN_NAME);
        eventsColumns.add(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_EVENTS_RECORDED_EVENT_COLUMN_NAME);
        eventsColumns.add(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_EVENTS_RECORDED_SOURCE_COLUMN_NAME);
        eventsColumns.add(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_EVENTS_RECORDED_STATUS_COLUMN_NAME);
        eventsColumns.add(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME);

        DeveloperDatabaseTable customerBrokerSaleEventsRecordedTable = developerObjectFactory.getNewDeveloperDatabaseTable(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_EVENTS_RECORDED_TABLE_NAME, eventsColumns);
        tables.add(customerBrokerSaleEventsRecordedTable);

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