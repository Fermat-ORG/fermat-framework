package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.database;

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
 * The Class  <code>com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.database.Close ContractBusinessTransactionDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 * <p/>
 * Created by Manuel Perez - (darkestpriest@gmail.com) on 03/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CloseContractBusinessTransactionDatabaseFactory implements DealsWithPluginDatabaseSystem {
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
    public CloseContractBusinessTransactionDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
             * Create Close Contract table.
             */
            table = databaseFactory.newTableFactory(ownerId, CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TABLE_NAME);

            table.addColumn(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.TEXT, 36, Boolean.TRUE);
            table.addColumn(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_NEGOTIATION_ID_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);
            table.addColumn(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_HASH_COLUMN_NAME, DatabaseDataType.TEXT, 64, Boolean.FALSE);
            table.addColumn(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_STATUS_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_TRANSMISSION_STATUS_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);
            table.addColumn(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_TYPE_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_CONTRACT_XML_COLUMN_NAME, DatabaseDataType.TEXT, 4000, Boolean.FALSE);
            table.addColumn(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_COMPLETION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);

            table.addIndex(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            DatabaseTableFactory eventsRecorderTable = databaseFactory.newTableFactory(ownerId, CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_TABLE_NAME);

            eventsRecorderTable.addColumn(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_ID_COLUMN_NAME, DatabaseDataType.TEXT, 36, Boolean.TRUE);
            eventsRecorderTable.addColumn(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_EVENT_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_SOURCE_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_STATUS_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            eventsRecorderTable.addIndex(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, eventsRecorderTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, new StringBuilder().append("Creating ").append(CloseContractBusinessTransactionDatabaseConstants.CLOSE_CONTRACT_EVENTS_RECORDED_TABLE_NAME).append(" table").toString(), "Exception not handled by the plugin, There is a problem and I cannot create the table.");
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
