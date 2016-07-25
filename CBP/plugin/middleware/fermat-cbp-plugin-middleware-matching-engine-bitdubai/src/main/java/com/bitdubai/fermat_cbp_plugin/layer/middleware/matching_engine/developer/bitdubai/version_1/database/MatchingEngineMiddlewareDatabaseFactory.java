package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * The Class  <code>com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/16.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MatchingEngineMiddlewareDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public MatchingEngineMiddlewareDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem) {

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
             * Create Wallets table.
             */
            table = databaseFactory.newTableFactory(ownerId, MatchingEngineMiddlewareDatabaseConstants.WALLETS_TABLE_NAME);

            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.WALLETS_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.TRUE);

            table.addIndex(MatchingEngineMiddlewareDatabaseConstants.WALLETS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Earning Pair table.
             */
            table = databaseFactory.newTableFactory(ownerId, MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_TABLE_NAME);

            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_ID_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.TRUE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_EARNING_CURRENCY_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_EARNING_CURRENCY_TYPE_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_LINKED_CURRENCY_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_LINKED_CURRENCY_TYPE_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_EARNINGS_WALLET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_WALLET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_STATE_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);

            table.addIndex(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Earning Transaction table.
             */
            table = databaseFactory.newTableFactory(ownerId, MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_TABLE_NAME);

            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.TRUE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_EARNING_CURRENCY_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_EARNING_CURRENCY_TYPE_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_AMOUNT_COLUMN_NAME, DatabaseDataType.MONEY, 0, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_STATE_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_EARNING_PAIR_ID_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);

            table.addIndex(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Input Transaction table.
             */
            table = databaseFactory.newTableFactory(ownerId, MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_TABLE_NAME);

            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.TRUE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_ORIGIN_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_CURRENCY_GIVING_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_CURRENCY_GIVING_TYPE_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_AMOUNT_GIVING_COLUMN_NAME, DatabaseDataType.MONEY, 0, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_CURRENCY_RECEIVING_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_CURRENCY_RECEIVING_TYPE_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_AMOUNT_RECEIVING_COLUMN_NAME, DatabaseDataType.MONEY, 0, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_TYPE_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_STATE_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_EARNING_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);
            table.addColumn(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_EARNING_PAIR_ID_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);

            table.addIndex(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_FIRST_KEY_COLUMN);

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

}
