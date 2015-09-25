package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.exceptions.CantInitializeWalletSkinMiddlewareDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.database.WalletSkinMiddlewareDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class WalletSkinMiddlewareDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public WalletSkinMiddlewareDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeWalletSkinMiddlewareDatabaseException
     */
    public void initializeDatabase() throws CantInitializeWalletSkinMiddlewareDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            database.closeDatabase();

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeWalletSkinMiddlewareDatabaseException(CantInitializeWalletSkinMiddlewareDatabaseException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            WalletSkinMiddlewareDatabaseFactory walletSkinMiddlewareDatabaseFactory = new WalletSkinMiddlewareDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = walletSkinMiddlewareDatabaseFactory.createDatabase(pluginId, pluginId.toString());
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeWalletSkinMiddlewareDatabaseException(CantInitializeWalletSkinMiddlewareDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot create the database.");
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Wallet Skin", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Table Wallet Skin columns.
         */
        List<String> walletSkinColumns = new ArrayList<>();

        walletSkinColumns.add(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_ID_COLUMN_NAME);
        walletSkinColumns.add(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_ID_COLUMN_NAME);
        walletSkinColumns.add(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_NAME_COLUMN_NAME);
        walletSkinColumns.add(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_ALIAS_COLUMN_NAME);
        walletSkinColumns.add(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_STATE_COLUMN_NAME);
        walletSkinColumns.add(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_DESIGNER_PUBLIC_KEY_COLUMN_NAME);
        walletSkinColumns.add(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COLUMN_NAME);
        walletSkinColumns.add(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COMPATIBILTY_INITIAL_COLUMN_NAME);
        walletSkinColumns.add(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COMPATIBILTY_FINAL_COLUMN_NAME);
        /**
         * Table Wallet Skin addition.
         */
        DeveloperDatabaseTable walletSkinTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_TABLE_NAME, walletSkinColumns);
        tables.add(walletSkinTable);



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