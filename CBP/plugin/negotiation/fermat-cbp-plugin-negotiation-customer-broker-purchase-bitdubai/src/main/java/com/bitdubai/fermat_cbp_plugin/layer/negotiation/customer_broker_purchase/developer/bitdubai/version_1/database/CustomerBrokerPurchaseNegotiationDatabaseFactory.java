package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * The Class  <code>com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.Customer Broker PurchaseNegotiationDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 * <p/>
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 09/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CustomerBrokerPurchaseNegotiationDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public CustomerBrokerPurchaseNegotiationDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     * @return Database
     * @throws CantCreateDatabaseException
     */
    protected Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Negotiations Purchase table.
             */
            table = databaseFactory.newTableFactory(ownerId, CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_TABLE_NAME);

            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, DatabaseDataType.TEXT, 36, Boolean.TRUE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.TEXT, 64, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.TEXT, 64, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_START_DATE_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_EXPIRATION_DATE_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_STATUS_COLUMN_NAME, DatabaseDataType.TEXT, 20, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME, DatabaseDataType.TEXT, 1, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_MEMO_COLUMN_NAME, DatabaseDataType.TEXT, 300, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_CANCEL_REASON_COLUMN_NAME, DatabaseDataType.TEXT, 300, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_LAST_NEGOTIATION_UPDATE_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            table.addIndex(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_PURCHASE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Clauses Purchase table.
             */
            table = databaseFactory.newTableFactory(ownerId, CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_TABLE_NAME);

            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_CLAUSE_ID_COLUMN_NAME, DatabaseDataType.TEXT, 36, Boolean.TRUE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, DatabaseDataType.TEXT, 36, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_TYPE_COLUMN_NAME, DatabaseDataType.TEXT, 20, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_VALUE_COLUMN_NAME, DatabaseDataType.TEXT, 20, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_STATUS_COLUMN_NAME, DatabaseDataType.TEXT, 20, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_PROPOSED_BY_COLUMN_NAME, DatabaseDataType.TEXT, 64, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_INDEX_ORDER_COLUMN_NAME, DatabaseDataType.INTEGER, 20, Boolean.FALSE);

            table.addIndex(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PURCHASE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Changes Purchase table.
             */
            table = databaseFactory.newTableFactory(ownerId, CustomerBrokerPurchaseNegotiationDatabaseConstants.CHANGES_PURCHASE_TABLE_NAME);

            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.CHANGES_PURCHASE_CHANGE_ID_COLUMN_NAME, DatabaseDataType.TEXT, 36, Boolean.TRUE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.CHANGES_PURCHASE_CLAUSE_ID_COLUMN_NAME, DatabaseDataType.TEXT, 36, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.CHANGES_PURCHASE_STATUS_COLUMN_NAME, DatabaseDataType.TEXT, 20, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.CHANGES_PURCHASE_START_DATE_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            table.addIndex(CustomerBrokerPurchaseNegotiationDatabaseConstants.CHANGES_PURCHASE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Locations Customer table.
             */
            table = databaseFactory.newTableFactory(ownerId, CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_TABLE_NAME);

            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_LOCATION_ID_COLUMN_NAME, DatabaseDataType.TEXT, 36, Boolean.TRUE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_LOCATION_COLUMN_NAME, DatabaseDataType.TEXT, 300, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_URI_COLUMN_NAME, DatabaseDataType.TEXT, 200, Boolean.FALSE);

            table.addIndex(CustomerBrokerPurchaseNegotiationDatabaseConstants.LOCATIONS_CUSTOMER_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Bank Accounts Customer table.
             */
            table = databaseFactory.newTableFactory(ownerId, CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_TABLE_NAME);

            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_ID_COLUMN_NAME, DatabaseDataType.TEXT, 36, Boolean.TRUE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_COLUMN_NAME, DatabaseDataType.TEXT, 300, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_TYPE_COLUMN_NAME, DatabaseDataType.TEXT, 20, Boolean.FALSE);

            table.addIndex(CustomerBrokerPurchaseNegotiationDatabaseConstants.BANK_ACCOUNTS_CUSTOMER_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}