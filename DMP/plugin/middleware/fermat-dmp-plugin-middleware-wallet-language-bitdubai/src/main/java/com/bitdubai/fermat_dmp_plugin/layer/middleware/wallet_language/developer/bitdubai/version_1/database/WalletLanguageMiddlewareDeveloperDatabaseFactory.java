package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.exceptions.CantInitializeWalletLanguageMiddlewareDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.database.WalletLanguageMiddlewareDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class WalletLanguageMiddlewareDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
     * @param pluginDatabaseSystem plugin database system instance
     * @param pluginId plugin id
     */
    public WalletLanguageMiddlewareDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeWalletLanguageMiddlewareDatabaseException
     */
    public void initializeDatabase() throws CantInitializeWalletLanguageMiddlewareDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            database.closeDatabase();

        } catch (CantOpenDatabaseException e) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeWalletLanguageMiddlewareDatabaseException(CantInitializeWalletLanguageMiddlewareDatabaseException.DEFAULT_MESSAGE, e, "", "");

        } catch (DatabaseNotFoundException databaseNotFoundException) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            WalletLanguageMiddlewareDatabaseFactory walletLanguageMiddlewareDatabaseFactory = new WalletLanguageMiddlewareDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = walletLanguageMiddlewareDatabaseFactory.createDatabase(pluginId, pluginId.toString());
                database.closeDatabase();
            } catch (CantCreateDatabaseException e) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeWalletLanguageMiddlewareDatabaseException(CantInitializeWalletLanguageMiddlewareDatabaseException.DEFAULT_MESSAGE, e, "Error creating database.", "");
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Wallet Language", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {

        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Table Wallet Language columns.
         */
        List<String> walletLanguageColumns = new ArrayList<>();

        walletLanguageColumns.add(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_ID_COLUMN_NAME);
        walletLanguageColumns.add(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_ID_COLUMN_NAME);
        walletLanguageColumns.add(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_NAME_COLUMN_NAME);
        walletLanguageColumns.add(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_TYPE_COLUMN_NAME);
        walletLanguageColumns.add(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_LANGUAGE_STATE_COLUMN_NAME);
        walletLanguageColumns.add(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_TRANSLATOR_PUBLIC_KEY_COLUMN_NAME);
        walletLanguageColumns.add(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_VERSION_COLUMN_NAME);
        /**
         * Table Wallet Language addition.
         */
        DeveloperDatabaseTable walletLanguageTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletLanguageMiddlewareDatabaseConstants.WALLET_LANGUAGE_TABLE_NAME, walletLanguageColumns);
        tables.add(walletLanguageTable);


        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<>();


        /**
         * I load the passed table name from the SQLite database.
         */
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();
            database.closeDatabase();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            database.closeDatabase();
            return returnedRecords;
        }

        List<DatabaseTableRecord> records = selectedTable.getRecords();
        List<String> developerRow = new ArrayList<>();
        for (DatabaseTableRecord row : records) {
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
        return returnedRecords;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}