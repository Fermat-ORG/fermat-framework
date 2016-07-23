package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database;

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
 * The Class  <code>com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.Customer Broker UpdateNegotiationTransactionDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 * <p/>
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 17/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CustomerBrokerUpdateNegotiationTransactionDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public CustomerBrokerUpdateNegotiationTransactionDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
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
             * Create Customer Broker Update table.
             */
            table = databaseFactory.newTableFactory(ownerId, CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);

            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.TRUE);
            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_ID_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_PUBLIC_KEY_BROKER_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_TRANSACTION_COLUMN_NAME, DatabaseDataType.TEXT, 50, Boolean.FALSE);
            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_NEGOTIATION_COLUMN_NAME, DatabaseDataType.TEXT, 50, Boolean.FALSE);
            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATE_TRANSMISSION_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_TYPE_COLUMN_NAME, DatabaseDataType.TEXT, 50, Boolean.FALSE);
            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_XML_COLUMN_NAME, DatabaseDataType.TEXT, 4000, Boolean.FALSE);
            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_SEND_TRANSACTION_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_CONFIRM_TRANSACTION_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);

            table.addIndex(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Customer Broker New Event table.
             */
            table = databaseFactory.newTableFactory(ownerId, CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TABLE_NAME);

            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_ID_COLUMN_NAME, DatabaseDataType.TEXT, 36, Boolean.TRUE);
            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TYPE_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.TRUE);
            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_SOURCE_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.TRUE);
            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_STATUS_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.TRUE);
            table.addColumn(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TIMESTAMP_COLUMN_NAME, DatabaseDataType.TEXT, 0, Boolean.TRUE);

            table.addIndex(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_FIRST_KEY_COLUMN);

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