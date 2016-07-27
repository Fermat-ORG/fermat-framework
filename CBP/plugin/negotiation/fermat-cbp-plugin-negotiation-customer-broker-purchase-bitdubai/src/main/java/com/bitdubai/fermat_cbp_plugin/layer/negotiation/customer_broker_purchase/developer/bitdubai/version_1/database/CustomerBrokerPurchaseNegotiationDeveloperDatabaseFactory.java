package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseNegotiationDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 09/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CustomerBrokerPurchaseNegotiationDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public CustomerBrokerPurchaseNegotiationDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException
     */
    public void initializeDatabase() throws CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerPurchaseNegotiationDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CustomerBrokerPurchaseNegotiationDatabaseFactory customerBrokerPurchaseNegotiationDatabaseFactory = new CustomerBrokerPurchaseNegotiationDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = customerBrokerPurchaseNegotiationDatabaseFactory.createDatabase(pluginId, CustomerBrokerPurchaseNegotiationDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(CustomerBrokerPurchaseNegotiationDatabaseConstants.DATABASE_NAME, this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Negotiations Purchase columns.
         */
        List<String> negotiationsPurchaseColumns = new ArrayList<String>();

        negotiationsPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME);
        negotiationsPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        negotiationsPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME);
        negotiationsPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_START_DATE_TIME_COLUMN_NAME);
        negotiationsPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_EXPIRATION_DATE_TIME_COLUMN_NAME);
        negotiationsPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_STATUS_COLUMN_NAME);
        negotiationsPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME);
        negotiationsPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_MEMO_COLUMN_NAME);
        negotiationsPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_CANCEL_REASON_COLUMN_NAME);
        negotiationsPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_LAST_NEGOTIATION_UPDATE_DATE_COLUMN_NAME);
        /**
         * Table Negotiations Purchase addition.
         */
        DeveloperDatabaseTable negotiationsPurchaseTable = developerObjectFactory.getNewDeveloperDatabaseTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_TABLE_NAME, negotiationsPurchaseColumns);
        tables.add(negotiationsPurchaseTable);

        /**
         * Table Clauses Purchase columns.
         */
        List<String> clausesPurchaseColumns = new ArrayList<String>();

        clausesPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_CLAUSE_ID_COLUMN_NAME);
        clausesPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_NEGOTIATION_ID_COLUMN_NAME);
        clausesPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_TYPE_COLUMN_NAME);
        clausesPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_VALUE_COLUMN_NAME);
        clausesPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_STATUS_COLUMN_NAME);
        clausesPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_PROPOSED_BY_COLUMN_NAME);
        clausesPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_INDEX_ORDER_COLUMN_NAME);
        /**
         * Table Clauses Purchase addition.
         */
        DeveloperDatabaseTable clausesPurchaseTable = developerObjectFactory.getNewDeveloperDatabaseTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_TABLE_NAME, clausesPurchaseColumns);
        tables.add(clausesPurchaseTable);

        /**
         * Table Changes Purchase columns.
         */
        List<String> changesPurchaseColumns = new ArrayList<String>();

        changesPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.CHANGES_PURCHASE_CHANGE_ID_COLUMN_NAME);
        changesPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.CHANGES_PURCHASE_CLAUSE_ID_COLUMN_NAME);
        changesPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.CHANGES_PURCHASE_STATUS_COLUMN_NAME);
        changesPurchaseColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.CHANGES_PURCHASE_START_DATE_TIME_COLUMN_NAME);
        /**
         * Table Changes Purchase addition.
         */
        DeveloperDatabaseTable changesPurchaseTable = developerObjectFactory.getNewDeveloperDatabaseTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CHANGES_PURCHASE_TABLE_NAME, changesPurchaseColumns);
        tables.add(changesPurchaseTable);

        /**
         * Table Locations Customer columns.
         */
        List<String> locationsCustomerColumns = new ArrayList<String>();

        locationsCustomerColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_LOCATION_ID_COLUMN_NAME);
        locationsCustomerColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_LOCATION_COLUMN_NAME);
        locationsCustomerColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_URI_COLUMN_NAME);
        /**
         * Table Locations Customer addition.
         */
        DeveloperDatabaseTable locationsCustomerTable = developerObjectFactory.getNewDeveloperDatabaseTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_TABLE_NAME, locationsCustomerColumns);
        tables.add(locationsCustomerTable);

        /**
         * Table Bank Accounts Customer columns.
         */
        List<String> bankAccountsCustomerColumns = new ArrayList<String>();

        bankAccountsCustomerColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_ID_COLUMN_NAME);
        bankAccountsCustomerColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_COLUMN_NAME);
        bankAccountsCustomerColumns.add(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_TYPE_COLUMN_NAME);
        /**
         * Table Bank Accounts Customer addition.
         */
        DeveloperDatabaseTable bankAccountsCustomerTable = developerObjectFactory.getNewDeveloperDatabaseTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_TABLE_NAME, bankAccountsCustomerColumns);
        tables.add(bankAccountsCustomerTable);


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