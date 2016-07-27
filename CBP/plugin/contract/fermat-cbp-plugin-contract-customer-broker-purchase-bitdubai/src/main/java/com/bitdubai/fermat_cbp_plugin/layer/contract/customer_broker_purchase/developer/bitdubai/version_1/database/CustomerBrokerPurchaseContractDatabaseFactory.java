package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database;

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
 * The Class  <code>com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.Customer Broker PurchaseContractDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 * <p/>
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 23/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CustomerBrokerPurchaseContractDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public CustomerBrokerPurchaseContractDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
             * Create Contracts Purchase table.
             */
            table = databaseFactory.newTableFactory(ownerId, CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_TABLE_NAME);

            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.TRUE);
            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.TEXT, 64, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.TEXT, 64, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_DATE_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_STATUS_COLUMN_NAME, DatabaseDataType.TEXT, 20, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME, DatabaseDataType.TEXT, 1, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CANCEL_REASON_COLUMN_NAME, DatabaseDataType.TEXT, 300, Boolean.FALSE);

            table.addIndex(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Clause Contract table.
             */
            table = databaseFactory.newTableFactory(ownerId, CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_TABLE_NAME);

            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_CLAUSE_ID_COLUMN_NAME, DatabaseDataType.TEXT, 36, Boolean.TRUE);
            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_CONTRACT_ID_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_TYPE_COLUMN_NAME, DatabaseDataType.TEXT, 20, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_EXECUTION_ORDER_COLUMN_NAME, DatabaseDataType.INTEGER, 20, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_CURRENT_STATUS_COLUMN_NAME, DatabaseDataType.TEXT, 20, Boolean.FALSE);

            table.addIndex(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Clause Status Log table.
             */
            table = databaseFactory.newTableFactory(ownerId, CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_STATUS_LOG_TABLE_NAME);

            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_STATUS_LOG_LOG_ID_COLUMN_NAME, DatabaseDataType.TEXT, 36, Boolean.TRUE);
            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_STATUS_LOG_CLAUSE_ID_COLUMN_NAME, DatabaseDataType.TEXT, 36, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_STATUS_LOG_STATUS_COLUMN_NAME, DatabaseDataType.TEXT, 20, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_STATUS_LOG_DATE_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_STATUS_LOG_CHANGE_BY_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);

            table.addIndex(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_STATUS_LOG_FIRST_KEY_COLUMN);

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