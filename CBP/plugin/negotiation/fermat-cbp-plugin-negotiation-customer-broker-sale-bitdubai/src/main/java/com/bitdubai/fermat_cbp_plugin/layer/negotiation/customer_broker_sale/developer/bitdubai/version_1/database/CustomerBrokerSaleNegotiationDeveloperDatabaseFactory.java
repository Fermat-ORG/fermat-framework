package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleNegotiationDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleNegotiationDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 30/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CustomerBrokerSaleNegotiationDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public CustomerBrokerSaleNegotiationDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeCustomerBrokerSaleNegotiationDatabaseException
     */
    public void initializeDatabase() throws CantInitializeCustomerBrokerSaleNegotiationDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerSaleNegotiationDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCustomerBrokerSaleNegotiationDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CustomerBrokerSaleNegotiationDatabaseFactory customerBrokerSaleNegotiationDatabaseFactory = new CustomerBrokerSaleNegotiationDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = customerBrokerSaleNegotiationDatabaseFactory.createDatabase(pluginId, CustomerBrokerSaleNegotiationDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCustomerBrokerSaleNegotiationDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(CustomerBrokerSaleNegotiationDatabaseConstants.DATABASE_NAME, this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Negotiations Sale columns.
         */
        List<String> negotiationsSaleColumns = new ArrayList<String>();

        negotiationsSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME);
        negotiationsSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        negotiationsSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME);
        negotiationsSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_START_DATE_TIME_COLUMN_NAME);
        negotiationsSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_EXPIRATION_DATE_TIME_COLUMN_NAME);
        negotiationsSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_STATUS_COLUMN_NAME);
        negotiationsSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME);
        negotiationsSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_MEMO_COLUMN_NAME);
        negotiationsSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_CANCEL_REASON_COLUMN_NAME);
        negotiationsSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_LAST_NEGOTIATION_UPDATE_DATE_COLUMN_NAME);
        /**
         * Table Negotiations Sale addition.
         */
        DeveloperDatabaseTable negotiationsSaleTable = developerObjectFactory.getNewDeveloperDatabaseTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_SALE_TABLE_NAME, negotiationsSaleColumns);
        tables.add(negotiationsSaleTable);

        /**
         * Table Clauses Sale columns.
         */
        List<String> clausesSaleColumns = new ArrayList<String>();

        clausesSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_CLAUSE_ID_COLUMN_NAME);
        clausesSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_NEGOTIATION_ID_COLUMN_NAME);
        clausesSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TYPE_COLUMN_NAME);
        clausesSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_VALUE_COLUMN_NAME);
        clausesSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_STATUS_COLUMN_NAME);
        clausesSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_PROPOSED_BY_COLUMN_NAME);
        clausesSaleColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_INDEX_ORDER_COLUMN_NAME);
        /**
         * Table Clauses Sale addition.
         */
        DeveloperDatabaseTable clausesSaleTable = developerObjectFactory.getNewDeveloperDatabaseTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_SALE_TABLE_NAME, clausesSaleColumns);
        tables.add(clausesSaleTable);

        /**
         * Table Locations Broker columns.
         */
        List<String> locationsBrokerColumns = new ArrayList<String>();

        locationsBrokerColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_ID_COLUMN_NAME);
        locationsBrokerColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_LOCATION_COLUMN_NAME);
        locationsBrokerColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_URI_COLUMN_NAME);
        /**
         * Table Locations Broker addition.
         */
        DeveloperDatabaseTable locationsBrokerTable = developerObjectFactory.getNewDeveloperDatabaseTable(CustomerBrokerSaleNegotiationDatabaseConstants.LOCATIONS_BROKER_TABLE_NAME, locationsBrokerColumns);
        tables.add(locationsBrokerTable);

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