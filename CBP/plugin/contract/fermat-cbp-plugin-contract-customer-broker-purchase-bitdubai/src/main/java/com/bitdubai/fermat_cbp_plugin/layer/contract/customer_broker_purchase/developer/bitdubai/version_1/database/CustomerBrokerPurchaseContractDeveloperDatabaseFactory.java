package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseContractDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseContractDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 23/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CustomerBrokerPurchaseContractDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public CustomerBrokerPurchaseContractDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeCustomerBrokerPurchaseContractDatabaseException
     */
    public void initializeDatabase() throws CantInitializeCustomerBrokerPurchaseContractDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerPurchaseContractDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCustomerBrokerPurchaseContractDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CustomerBrokerPurchaseContractDatabaseFactory customerBrokerPurchaseContractDatabaseFactory = new CustomerBrokerPurchaseContractDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = customerBrokerPurchaseContractDatabaseFactory.createDatabase(pluginId, CustomerBrokerPurchaseContractDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCustomerBrokerPurchaseContractDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(CustomerBrokerPurchaseContractDatabaseConstants.DATABASE_NAME, this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Contracts Purchase columns.
         */
        List<String> contractsPurchaseColumns = new ArrayList<String>();

        contractsPurchaseColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME);
        contractsPurchaseColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME);
        contractsPurchaseColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        contractsPurchaseColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME);
        contractsPurchaseColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_DATE_TIME_COLUMN_NAME);
        contractsPurchaseColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_STATUS_COLUMN_NAME);
        contractsPurchaseColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME);
        contractsPurchaseColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CANCEL_REASON_COLUMN_NAME);
        /**
         * Table Contracts Purchase addition.
         */
        DeveloperDatabaseTable contractsPurchaseTable = developerObjectFactory.getNewDeveloperDatabaseTable(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_TABLE_NAME, contractsPurchaseColumns);
        tables.add(contractsPurchaseTable);

        /**
         * Table Clause Contract columns.
         */
        List<String> clauseContractColumns = new ArrayList<String>();

        clauseContractColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_CLAUSE_ID_COLUMN_NAME);
        clauseContractColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_CONTRACT_ID_COLUMN_NAME);
        clauseContractColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_TYPE_COLUMN_NAME);
        clauseContractColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_EXECUTION_ORDER_COLUMN_NAME);
        clauseContractColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_CURRENT_STATUS_COLUMN_NAME);
        /**
         * Table Clause Contract addition.
         */
        DeveloperDatabaseTable clauseContractTable = developerObjectFactory.getNewDeveloperDatabaseTable(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_TABLE_NAME, clauseContractColumns);
        tables.add(clauseContractTable);

        /**
         * Table Clause Status Log columns.
         */
        List<String> clauseStatusLogColumns = new ArrayList<String>();

        clauseStatusLogColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_STATUS_LOG_LOG_ID_COLUMN_NAME);
        clauseStatusLogColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_STATUS_LOG_CLAUSE_ID_COLUMN_NAME);
        clauseStatusLogColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_STATUS_LOG_STATUS_COLUMN_NAME);
        clauseStatusLogColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_STATUS_LOG_DATE_TIME_COLUMN_NAME);
        clauseStatusLogColumns.add(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_STATUS_LOG_CHANGE_BY_COLUMN_NAME);
        /**
         * Table Clause Status Log addition.
         */
        DeveloperDatabaseTable clauseStatusLogTable = developerObjectFactory.getNewDeveloperDatabaseTable(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_STATUS_LOG_TABLE_NAME, clauseStatusLogColumns);
        tables.add(clauseStatusLogTable);


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