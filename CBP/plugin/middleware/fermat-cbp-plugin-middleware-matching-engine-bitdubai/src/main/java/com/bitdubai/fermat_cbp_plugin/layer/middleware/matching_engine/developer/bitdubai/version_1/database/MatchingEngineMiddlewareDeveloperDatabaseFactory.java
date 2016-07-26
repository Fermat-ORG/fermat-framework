package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/16.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */

public class MatchingEngineMiddlewareDeveloperDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;


    private Database database;

    public MatchingEngineMiddlewareDeveloperDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem,
                                                            final UUID pluginId) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeDatabaseException
     */
    public void initializeDatabase() throws CantInitializeDatabaseException {

        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, MatchingEngineMiddlewareDatabaseConstants.MATCHING_ENGINE_MIDDLEWARE_DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            MatchingEngineMiddlewareDatabaseFactory matchingEngineMiddlewareDatabaseFactory = new MatchingEngineMiddlewareDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = matchingEngineMiddlewareDatabaseFactory.createDatabase(pluginId, MatchingEngineMiddlewareDatabaseConstants.MATCHING_ENGINE_MIDDLEWARE_DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Matching Engine", MatchingEngineMiddlewareDatabaseConstants.MATCHING_ENGINE_MIDDLEWARE_DATABASE_NAME));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Table Wallets columns.
         */
        List<String> walletsColumns = new ArrayList<>();

        walletsColumns.add(MatchingEngineMiddlewareDatabaseConstants.WALLETS_PUBLIC_KEY_COLUMN_NAME);

        /**
         * Table Wallets addition.
         */
        DeveloperDatabaseTable walletsTable = developerObjectFactory.getNewDeveloperDatabaseTable(MatchingEngineMiddlewareDatabaseConstants.WALLETS_TABLE_NAME, walletsColumns);

        tables.add(walletsTable);

        /**
         * Table Earning Pair columns.
         */
        List<String> earningPairColumns = new ArrayList<>();

        earningPairColumns.add(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_ID_COLUMN_NAME);
        earningPairColumns.add(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_EARNING_CURRENCY_COLUMN_NAME);
        earningPairColumns.add(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_EARNING_CURRENCY_TYPE_COLUMN_NAME);
        earningPairColumns.add(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_LINKED_CURRENCY_COLUMN_NAME);
        earningPairColumns.add(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_LINKED_CURRENCY_TYPE_COLUMN_NAME);
        earningPairColumns.add(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_EARNINGS_WALLET_PUBLIC_KEY_COLUMN_NAME);
        earningPairColumns.add(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_WALLET_PUBLIC_KEY_COLUMN_NAME);
        earningPairColumns.add(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_STATE_COLUMN_NAME);

        /**
         * Table Earning Pair addition.
         */
        DeveloperDatabaseTable earningPairTable = developerObjectFactory.getNewDeveloperDatabaseTable(MatchingEngineMiddlewareDatabaseConstants.EARNING_PAIR_TABLE_NAME, earningPairColumns);
        tables.add(earningPairTable);

        /**
         * Table Earning Transaction columns.
         */
        List<String> earningTransactionColumns = new ArrayList<>();

        earningTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_ID_COLUMN_NAME);
        earningTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_EARNING_CURRENCY_COLUMN_NAME);
        earningTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_EARNING_CURRENCY_TYPE_COLUMN_NAME);
        earningTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_AMOUNT_COLUMN_NAME);
        earningTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_STATE_COLUMN_NAME);
        earningTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_TIME_COLUMN_NAME);
        earningTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_EARNING_PAIR_ID_COLUMN_NAME);

        /**
         * Table Earning Transaction addition.
         */
        DeveloperDatabaseTable earningTransactionTable = developerObjectFactory.getNewDeveloperDatabaseTable(MatchingEngineMiddlewareDatabaseConstants.EARNING_TRANSACTION_TABLE_NAME, earningTransactionColumns);
        tables.add(earningTransactionTable);

        /**
         * Table Input Transaction columns.
         */
        List<String> inputTransactionColumns = new ArrayList<>();

        inputTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_ID_COLUMN_NAME);
        inputTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_ORIGIN_TRANSACTION_ID_COLUMN_NAME);
        inputTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_CURRENCY_GIVING_COLUMN_NAME);
        inputTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_CURRENCY_GIVING_TYPE_COLUMN_NAME);
        inputTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_AMOUNT_GIVING_COLUMN_NAME);
        inputTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_CURRENCY_RECEIVING_COLUMN_NAME);
        inputTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_CURRENCY_RECEIVING_TYPE_COLUMN_NAME);
        inputTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_AMOUNT_RECEIVING_COLUMN_NAME);
        inputTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_TYPE_COLUMN_NAME);
        inputTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_STATE_COLUMN_NAME);
        inputTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_EARNING_TRANSACTION_ID_COLUMN_NAME);
        inputTransactionColumns.add(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_EARNING_PAIR_ID_COLUMN_NAME);

        /**
         * Table Input Transaction addition.
         */
        DeveloperDatabaseTable inputTransactionTable = developerObjectFactory.getNewDeveloperDatabaseTable(MatchingEngineMiddlewareDatabaseConstants.INPUT_TRANSACTION_TABLE_NAME, inputTransactionColumns);
        tables.add(inputTransactionTable);

        return tables;
    }

    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(final DeveloperObjectFactory developerObjectFactory,
                                                                      final DeveloperDatabaseTable developerDatabaseTable) {

        try {

            this.initializeDatabase();

            List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<>();

            final DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());

            selectedTable.loadToMemory();

            final List<DatabaseTableRecord> records = selectedTable.getRecords();

            List<String> developerRow;

            for (final DatabaseTableRecord row : records) {

                developerRow = new ArrayList<>();

                for (final DatabaseRecord field : row.getValues())
                    developerRow.add(field.getValue());

                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
            }

            return returnedRecords;

        } catch (final CantLoadTableToMemoryException |
                CantInitializeDatabaseException e) {

            System.err.println(e);

            return new ArrayList<>();

        } catch (final Exception e) {

            return new ArrayList<>();
        }
    }

}
